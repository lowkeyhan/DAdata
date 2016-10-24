package com.techstar.damanage.service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpRequest;
import org.hibernate.loader.custom.Return;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.techstar.damanage.entity.weeklydata;
import com.techstar.damanage.jpa.WeeklydataDao;
import com.techstar.sys.dingAPI.OApiException;
import com.techstar.sys.dingAPI.auth.AuthHelper;
import com.techstar.sys.dingAPI.department.Department;
import com.techstar.sys.dingAPI.department.DepartmentHelper;
import com.techstar.sys.dingAPI.user.User;
import com.techstar.sys.dingAPI.user.UserHelper;


@Service 
@Transactional
public class WeeklydataService {
	@Autowired
	private  WeeklydataDao weeklydataDao;
	
	

	public void save(weeklydata weeklydata) {
		weeklydataDao.save(weeklydata);
	}
	public void delete(weeklydata weeklydata) {
		weeklydataDao.delete(weeklydata);
	}
	
	public weeklydata findById(String id) {
		return weeklydataDao.findById(id);
	}
	public List<weeklydata> findByOperationeridAndDatatime(String Operationerid,Date datatime){
		return weeklydataDao.findByOperationeridAndDatatime(Operationerid, datatime);
	}
	public List<weeklydata> findByProvincenameAndCitynameAndDatatime(String Provincename,String Cityname,Date datatime){
		return weeklydataDao.findByProvincenameAndCitynameAndDatatime(Provincename, Cityname, datatime);
	}
	public List<weeklydata> findByDatatimeBetweenAndCityname(Date etime,Date stime,String cityname){
		return weeklydataDao.findByDatatimeBetweenAndCityname(etime, stime, cityname);
	}
	public List<weeklydata> findByDatatimeBetweenAndProvincename(Date etime,Date stime,String cityname){
		return weeklydataDao.findByDatatimeBetweenAndProvincename(etime, stime, cityname);
	}
	public List<weeklydata> findByCitynameOrderByDatatimeDesc(String cityname,Pageable page){
		return weeklydataDao.findByCitynameOrderByDatatimeDesc(cityname, page);
	}
	public List<weeklydata> findLatesttime(String cityname){
		return weeklydataDao.findLatesttime(cityname);
	}
	public String getuser(HttpServletRequest request,HttpServletResponse response,String code) throws UnsupportedEncodingException, OApiException {
		String authuser="";
		Cookie[] cookies = request.getCookies();//这样便可以获取一个cookie数组
		for(Cookie cookie : cookies){
			if(cookie.getName().equals("user")){
				authuser=URLDecoder.decode(cookie.getValue(),"UTF-8");
			}
		}
		if(authuser.equals("")){
			JSONObject jsonuser=UserHelper.getUserInfo(AuthHelper.getAccessToken(), code);
					//System.out.println(code);
			User dingdingUser=UserHelper.getUser(AuthHelper.getAccessToken(), jsonuser.getString("userid"));
			authuser=dingdingUser.toJSONString();
			Cookie cookie =new Cookie("user",URLEncoder.encode(authuser,"UTF-8") );
			cookie.setMaxAge(3600*24);
			cookie.setPath("/");
			response.addCookie(cookie);
		}
		
		System.out.print(authuser);
		return authuser;
	}
}
