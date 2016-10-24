package com.techstar.sys.dingAPI.auth;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Sort;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.techstar.sys.config.Global;
import com.techstar.sys.dingAPI.Env;
import com.techstar.sys.dingAPI.OApiException;
import com.techstar.sys.dingAPI.OApiResultException;
import com.techstar.sys.dingAPI.utils.FileUtils;
import com.techstar.sys.dingAPI.utils.HttpHelper;

public class AuthHelper {

	// public static String jsapiTicket = null;
	// public static String accessToken = null;
	public static Timer timer = null;
	// 调整到1小时50分钟
	public static final long cacheTime = 1000 * 60 * 55 * 2;
	public static long currentTime = 0 + cacheTime + 1;
	public static long lastTime = 0;
	public static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/*
	 * 在此方法中，为了避免频繁获取access_token，
	 * 在距离上一次获取access_token时间在两个小时之内的情况，
	 * 将直接从持久化存储中读取access_token
	 * 
	 * 因为access_token和jsapi_ticket的过期时间都是7200秒
	 * 所以在获取access_token的同时也去获取了jsapi_ticket
	 * 注：jsapi_ticket是在前端页面JSAPI做权限验证配置的时候需要使用的
	 * 具体信息请查看开发者文档--权限验证配置
	 */
	public static String getAccessToken() throws OApiException {
		long curTime = System.currentTimeMillis();
		String corpId=Global.getConfig("dingding.Corpid");
		JSONObject accessTokenValue = (JSONObject) FileUtils.getValue("accesstoken", corpId);
		String accToken = "";
		String jsTicket = "";
		JSONObject jsontemp = new JSONObject();
//		if(accessTokenValue!=null){
//			System.out.println("accessTokenValue:" 
//					+ accessTokenValue.toJSONString() + " beginT_time:"
//					+ Long.valueOf(accessTokenValue.get("begin_time").toString()) + " cur:" + curTime + " max:"
//					+ Long.MAX_VALUE);
//		}
		if (accessTokenValue == null || curTime - accessTokenValue.getLong("begin_time") >= cacheTime) {
			//System.out.println(df.format(new Date())+" authhelper: get new access_token and ticket");
			String url = Env.OAPI_HOST + "/gettoken?corpid="+corpId+"&corpsecret="+Global.getConfig("dingding.CorpSecret");
			
			//JSONObject args = new JSONObject();
			//args.put("auth_corpid", corpId);
			//args.put("permanent_code", FileUtils.getValue("permanentcode", corpId));
			JSONObject response = HttpHelper.httpGet(url);
			if (response.containsKey("access_token")) {
				accToken = response.getString("access_token");

				// save accessToken
				JSONObject jsonAccess = new JSONObject();
				jsontemp.clear();
				jsontemp.put("access_token", accToken);
				jsontemp.put("begin_time", curTime);
				jsonAccess.put(corpId, jsontemp);

				FileUtils.write2File(jsonAccess, "accesstoken");
			} else {
				throw new OApiResultException("access_token");
			}

			String url_ticket = Env.OAPI_HOST + "/get_jsapi_ticket?" + "type=jsapi" + "&access_token=" + accToken;
			JSONObject response_ticket = HttpHelper.httpGet(url_ticket);
			if (response_ticket.containsKey("ticket")) {
				jsTicket = response_ticket.getString("ticket");

				// save jsticket
				JSONObject jsonTicket = new JSONObject();
				jsontemp.clear();
				jsontemp.put("ticket", jsTicket);
				jsontemp.put("begin_time", curTime);
				jsonTicket.put(corpId, jsontemp);

				FileUtils.write2File(jsonTicket, "jsticket");
			} else {
				throw new OApiResultException("ticket");
			}

		} else {
			return accessTokenValue.getString("access_token");
		}

		return accToken;
	}

	// 正常的情况下，jsapi_ticket的有效期为7200秒，所以开发者需要在某个地方设计一个定时器，定期去更新jsapi_ticket
	public static String getJsapiTicket(String accessToken, String corpId) throws OApiException {
		JSONObject jsTicketValue = (JSONObject) FileUtils.getValue("jsticket", corpId);
		long curTime = System.currentTimeMillis();
		String jsTicket = "";

		 if (jsTicketValue == null || curTime -
		 jsTicketValue.getLong("begin_time") >= cacheTime) {
			String url = Env.OAPI_HOST + "/get_jsapi_ticket?" + "type=jsapi" + "&access_token=" + accessToken;
			JSONObject response = HttpHelper.httpGet(url);
			if (response.containsKey("ticket")) {
				jsTicket = response.getString("ticket");
				
				JSONObject jsonTicket = new JSONObject();
				JSONObject jsontemp = new JSONObject();
				jsontemp.clear();
				jsontemp.put("ticket", jsTicket);
				jsontemp.put("begin_time", curTime);
				jsonTicket.put(corpId, jsontemp);
				FileUtils.write2File(jsonTicket, "jsticket");

				return jsTicket;
			} else {
				throw new OApiResultException("ticket");
			}
		 } else {
			 return jsTicketValue.getString("ticket");
		 }
//
	}

	public static String sign(String ticket, String nonceStr, long timeStamp, String url) throws OApiException {
		String plain = "jsapi_ticket=" + ticket + "&noncestr=" + nonceStr + "&timestamp=" + String.valueOf(timeStamp)
				+ "&url=" + url;
		//System.out.println(df.format(new Date())+plain);
		try {
			MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
			sha1.reset();
			sha1.update(plain.getBytes("UTF-8"));
			return bytesToHex(sha1.digest());
		} catch (NoSuchAlgorithmException e) {
			throw new OApiResultException(e.getMessage());
		} catch (UnsupportedEncodingException e) {
			throw new OApiResultException(e.getMessage());
		}
	}

	private static String bytesToHex(byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}

	
	public static String getConfig(HttpServletRequest request) throws UnsupportedEncodingException {
		String urlString="";
		urlString = request.getScheme() +"://" + request.getServerName()+ ":" +request.getServerPort()+request.getContextPath()+request.getServletPath();
		String queryString = request.getQueryString();

		String corpId =Global.getConfig("dingding.Corpid");
		String appId =Global.getConfig("dingding.appid");
		
		String queryStringEncode = null;
		String url;
		if (queryString != null) {
			//queryStringEncode = URLDecoder.decode(queryString,"UTF-8");
			url = urlString + "?" + queryString;
		} else {
			url = urlString;
		}
		String nonceStr = "abcdefg";
		long timeStamp = System.currentTimeMillis() / 1000;
		String signedUrl = url;
		String accessToken = null;
		String ticket = null;
		String signature = null;
		String agentid = null;

		try {
			accessToken = AuthHelper.getAccessToken();
			ticket = AuthHelper.getJsapiTicket(accessToken, corpId);
			signature = AuthHelper.sign(ticket, nonceStr, timeStamp, signedUrl);
			agentid =Global.getConfig("dingding.appid");
		} catch (OApiException e) {
			e.printStackTrace();
		}

		return "{\"jsticket\":\"" + ticket + "\",\"signature\":\"" + signature + "\",\"nonceStr\":\"" + nonceStr + "\",\"timeStamp\":\""
				+ timeStamp + "\",\"corpId\":\"" + corpId + "\",\"agentid\":\"" + agentid+ "\",\"appid\":\"" + appId + "\"}";
	}
	
	public static String getappidConfig(HttpServletRequest request,String appid) throws UnsupportedEncodingException {
		String urlString="";
		urlString = request.getScheme() +"://" + request.getServerName()+ ":" +request.getServerPort()+request.getContextPath()+request.getServletPath();
		String queryString = request.getQueryString();

		String corpId =Global.getConfig("dingding.Corpid");
		String appId =appid;
		
		String queryStringEncode = null;
		String url;
		if (queryString != null) {
			queryStringEncode = URLDecoder.decode(queryString,"UTF-8");
			url = urlString + "?" + queryStringEncode;
		} else {
			url = urlString;
		}
		String nonceStr = "abcdefg";
		long timeStamp = System.currentTimeMillis() / 1000;
		String signedUrl = url;
		String accessToken = null;
		String ticket = null;
		String signature = null;
		String agentid = null;

		try {
			accessToken = AuthHelper.getAccessToken();
			ticket = AuthHelper.getJsapiTicket(accessToken, corpId);
			signature = AuthHelper.sign(ticket, nonceStr, timeStamp, signedUrl);
			agentid =appid;
		} catch (OApiException e) {
			e.printStackTrace();
		}

		return "{\"jsticket\":\"" + ticket + "\",\"signature\":\"" + signature + "\",\"nonceStr\":\"" + nonceStr + "\",\"timeStamp\":\""
				+ timeStamp + "\",\"corpId\":\"" + corpId + "\",\"agentid\":\"" + agentid+ "\",\"appid\":\"" + appId + "\"}";
	}
	
	
	
	

	public static String getAgentId(String corpId, String appId) throws OApiException {
		String agentId = null;
		String accessToken = FileUtils.getValue("ticket", "suiteToken").toString();
		String url = "https://oapi.dingtalk.com/service/get_auth_info?suite_access_token=" + accessToken;
		JSONObject args = new JSONObject();
		args.put("suite_key", Env.SUITE_KEY);
		args.put("auth_corpid", corpId);
		args.put("permanent_code", FileUtils.getValue("permanentcode", corpId));
		JSONObject response = HttpHelper.httpPost(url, args);
		// System.out.println("response11:" + response.toJSONString() + "
		// appid:" + appId);
		if (response.containsKey("auth_info")) {
			JSONArray agents = (JSONArray) ((JSONObject) response.get("auth_info")).get("agent");
			// System.out.println("size11:" + agents.size() + " :" +
			// agents.toJSONString());
			for (int i = 0; i < agents.size(); i++) {
				// System.out.println("appid:" + ((JSONObject)
				// agents.get(i)).get("appid").toString());
				if (((JSONObject) agents.get(i)).get("appid").toString().equals(appId)) {
					agentId = ((JSONObject) agents.get(i)).get("agentid").toString();
					break;
				}
			}
			// agentId = response.getString("agentid");
		} else {
			throw new OApiResultException("agentid");
		}
		return agentId;
	}

	public static String getSsoToken() throws OApiException {
		String url = "https://oapi.dingtalk.com/sso/gettoken?corpid=" +Global.getConfig("dingding.Corpid")+ "&corpsecret=" + Global.getConfig("dingding.SSOsecret");
		JSONObject response = HttpHelper.httpGet(url);
		String ssoToken;
		if (response.containsKey("access_token")) {
			ssoToken = response.getString("access_token");
		} else {
			throw new OApiResultException("Sso_token");
		}
		return ssoToken;

	}
	//构造规范化请求串
	public static  String getStandardRequestString(Map<String,String> param){
        String[] keys  = new String[param.size()];
        int point = 0;
        for (Map.Entry<String, String> entry : param.entrySet()) {
            keys[point++] = entry.getKey();
        }
        //对key按照字典序排序
        Arrays.sort(keys);
        String param_str = "";
            for(int i=0 ;i<keys.length; i++){
                param_str += (keys[i] + "=" +param.get(keys[i]));
                if(i+1 < keys.length)
                    param_str += "&";
            }
       
        return param_str;
    }

	// public static String[] getValues(String values){
	// return values.split(":");
	// }

}
