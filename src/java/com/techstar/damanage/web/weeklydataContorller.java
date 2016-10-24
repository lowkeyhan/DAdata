package com.techstar.damanage.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.techstar.damanage.entity.province;
import com.techstar.damanage.entity.weeklydata;
import com.techstar.damanage.service.ProvinceService;
import com.techstar.damanage.service.WeeklydataService;
import com.techstar.sys.config.Global;
import com.techstar.sys.dingAPI.OApiException;
import com.techstar.sys.dingAPI.auth.AuthHelper;
import com.techstar.sys.jpadomain.Results;



/**
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/data")
public class weeklydataContorller {
	@Autowired
	private WeeklydataService weeklydataService;
	@Autowired
	private ProvinceService provinceService;
	
	@RequestMapping("/mindex")
	public String index(Model model,HttpServletRequest request) throws OApiException, UnsupportedEncodingException {
		//model.addAttribute("code", code);
		String configstr= AuthHelper.getappidConfig(request,Global.getConfig("dingding.appid2"));
		model.addAttribute("authconfig", configstr);
		model.addAttribute("oper", "add");
		return "mobile/dataadd";
	}
	
	@RequestMapping("/mchartindex")
	public String mchartindex(Model model,HttpServletRequest request) throws OApiException, UnsupportedEncodingException {
		//model.addAttribute("code", code);
		String configstr= AuthHelper.getConfig(request);
		model.addAttribute("authconfig", configstr);
		return "mobile/indexchart";
	}
	
	@RequestMapping("/citypage")
	public String citypage(@RequestParam(value="pname",required=false)String pname,
			@RequestParam(value="color",required=false)String color,
			Model model,HttpServletRequest request) throws OApiException, UnsupportedEncodingException {
		//model.addAttribute("code", code);
		String configstr= AuthHelper.getConfig(request);
		model.addAttribute("authconfig", configstr);
		String str=new String(pname.getBytes("ISO-8859-1"),"UTF-8");
		model.addAttribute("pname", str);
		model.addAttribute("color", color);
		return "mobile/citypage";
	}
	

	@RequestMapping("/pcindex")
	public String pcindex(Model model,HttpServletRequest request) throws OApiException, UnsupportedEncodingException {
		//model.addAttribute("code", code);
		String configstr= AuthHelper.getappidConfig(request,Global.getConfig("dingding.appid2"));
		model.addAttribute("authconfig", configstr);
		model.addAttribute("oper", "add");
		return "pc/pcdataadd";
	}
	
	@RequestMapping("/pcchartindex")
	public String pcchartindex(Model model,HttpServletRequest request) throws OApiException, UnsupportedEncodingException {
		//model.addAttribute("code", code);
		String configstr= AuthHelper.getConfig(request);
		model.addAttribute("authconfig", configstr);
		return "pc/pcindexchart";
	}
	
	@RequestMapping("/pccitypage")
	public String pccitypage(@RequestParam(value="pname",required=false)String pname,
			@RequestParam(value="color",required=false)String color,
			Model model,HttpServletRequest request) throws OApiException, UnsupportedEncodingException {
		//model.addAttribute("code", code);
		String configstr= AuthHelper.getConfig(request);
		model.addAttribute("authconfig", configstr);
		String str=new String(pname.getBytes("ISO-8859-1"),"UTF-8");
		model.addAttribute("pname", str);
		model.addAttribute("color", color);
		return "pc/pccitypage";
	}
	
	@RequestMapping("/getonedata")
	public @ResponseBody Results  getonedata(@RequestParam(value="id",required=false)String id,
			Model model,HttpServletRequest request,
			HttpServletResponse response) throws OApiException, UnsupportedEncodingException {
		weeklydata editWeeklydata=weeklydataService.findById(id);
		return  new Results(editWeeklydata);
	}
	
	@RequestMapping("/delonedata")
	public @ResponseBody Results  delonedata(@RequestParam(value="id",required=false)String id,
			Model model,HttpServletRequest request,
			HttpServletResponse response) throws OApiException, UnsupportedEncodingException {
		weeklydata editWeeklydata=weeklydataService.findById(id);
		weeklydataService.delete(editWeeklydata);
		return  new Results("删除成功");
	}
	
	
	
	
	@RequestMapping("/getcitydata")
	public @ResponseBody Results  getcitydata(@RequestParam(value="stime",required=false)Date stime,
			@RequestParam(value="etime",required=false)Date etime,
			@RequestParam(value="pname",required=false)String pname,
			Model model,HttpServletRequest request,
			HttpServletResponse response) throws OApiException, UnsupportedEncodingException {
		
		List<weeklydata> weeklydatas=weeklydataService.findByDatatimeBetweenAndProvincename(stime, etime, pname);
		List<province> proList=provinceService.findByParentid(pname);
		return  new Results(weeklydatas,proList);
	}
	
	
	@RequestMapping("/getpaopao")
	public @ResponseBody Results  getpaopao(@RequestParam(value="stime",required=false)Date stime,
			@RequestParam(value="etime",required=false)Date etime,
			@RequestParam(value="pname",required=false)String pname,
			Model model,HttpServletRequest request,
			HttpServletResponse response) throws OApiException, UnsupportedEncodingException {
		
		List<weeklydata> weeklydatas=weeklydataService.findByDatatimeBetweenAndCityname(stime, etime, pname);
		List<province> proList=provinceService.findByParentid("0");
		return  new Results(weeklydatas,proList);
	}
	@RequestMapping("/getdatalist")
	public @ResponseBody Results  getdatalist(
			Model model,HttpServletRequest request,
			HttpServletResponse response) throws OApiException, UnsupportedEncodingException {
		List<weeklydata> weeklydatas2=weeklydataService.findLatesttime("");
		return  new Results(weeklydatas2);
	}
	
	/**
	 * 分页查询市区数据
	 * @param cityname
	 * @param page
	 * @param size
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws OApiException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/getcitydatalist")
	public @ResponseBody Results  getcitydatalist(@RequestParam(value="cityname",required=false)String cityname,
			@RequestParam(value="page",required=false)int page,
			@RequestParam(value="size",required=false)int size,
			Model model,HttpServletRequest request,
			HttpServletResponse response) throws OApiException, UnsupportedEncodingException {
		Pageable pageable=new PageRequest(page, size);
		List<weeklydata> weeklydatas2=weeklydataService.findByCitynameOrderByDatatimeDesc(cityname, pageable);
		return  new Results(weeklydatas2);
	}
	
	
	
	
	
	/** 获得用户信息存储cookie并返回任务列表
	 * @param code 授权码
	 * @param model
	 * @param request
	 * @param response
	 * @return  部门列表
	 * @throws OApiException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/login")
	public @ResponseBody Results  login(@RequestParam(value="code",required=false)String code,
			@RequestParam(value="datatime",required=false)Date datatime,
			Model model,HttpServletRequest request,
			HttpServletResponse response) throws OApiException, UnsupportedEncodingException {
		Cookie[] cookies = request.getCookies();//这样便可以获取一个cookie数组
		String userString="";
		for(Cookie cookie : cookies){
			if(cookie.getName().equals("user")){
				userString=URLDecoder.decode(cookie.getValue(),"UTF-8");
			}
		}
		if(userString.equals("")){
			userString=weeklydataService.getuser(request, response, code);
		}
		JSONObject userJsonObject=JSON.parseObject(userString);
		List<weeklydata> weeklydatas=weeklydataService.findByOperationeridAndDatatime(userJsonObject.get("userid").toString(), datatime);
		return  new Results(weeklydatas);
	}
	
	
	@RequestMapping("/add")
	public @ResponseBody
	Results edit(@ModelAttribute weeklydata weeklydata,
				@RequestParam(value = "oper", required = false) String oper,
				HttpServletRequest request,HttpSession session) throws UnsupportedEncodingException {
		String message = "修改成功";
		List<weeklydata> weeklydatas = null;
		if (StringUtils.equals("add", oper)) {
			message = "保存成功";
			weeklydatas=weeklydataService.findByProvincenameAndCitynameAndDatatime(weeklydata.getProvincename(), weeklydata.getCityname(), weeklydata.getDatatime());
			if(weeklydatas.size()>0){
				message = "已有"+weeklydata.getProvincename()+weeklydata.getCityname()+"数据";
				return new Results(true, message, "");
			}
			
		}
		Cookie[] cookies = request.getCookies();//这样便可以获取一个cookie数组
		for(Cookie cookie : cookies){
			if(cookie.getName().equals("user")){
				JSONObject userJsonObject=JSON.parseObject(URLDecoder.decode(cookie.getValue(),"UTF-8"));
				weeklydata.setOperationer(userJsonObject.get("name").toString());
				weeklydata.setOperationerid(userJsonObject.get("userid").toString());
			}
		}
		weeklydataService.save(weeklydata);
		
		
		return new Results(true, message, "");
	}	
}
