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
import com.techstar.damanage.entity.province;
import com.techstar.damanage.entity.weeklydata;
import com.techstar.damanage.jpa.WeeklydataDao;
import com.techstar.damanage.jpa.ProvinceDao;
import com.techstar.sys.dingAPI.OApiException;
import com.techstar.sys.dingAPI.auth.AuthHelper;
import com.techstar.sys.dingAPI.department.Department;
import com.techstar.sys.dingAPI.department.DepartmentHelper;
import com.techstar.sys.dingAPI.user.User;
import com.techstar.sys.dingAPI.user.UserHelper;


@Service 
@Transactional
public class ProvinceService {
	@Autowired
	private  ProvinceDao provinceDao;
	
	

	public void save(province province) {
		provinceDao.save(province);
	}
	public void delete(province province) {
		provinceDao.delete(province);
		
	}
	
	/**
	 * 获得子地区
	 * @param parentid
	 * @return
	 */
	public List<province> findByParentid(String parentid) {
		return provinceDao.findByParentid(parentid);
	}
	
}
