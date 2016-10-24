package com.techstar.damanage.web;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.techstar.damanage.entity.province;
import com.techstar.damanage.service.ProvinceService;
import com.techstar.sys.dingAPI.OApiException;
import com.techstar.sys.jpadomain.Results;



/**
 * @author hxb
 *
 */
@Controller
@RequestMapping("/province")
public class provinceContorller {
	@Autowired
	private ProvinceService provinceService;
	
	
	@RequestMapping("/getprovince")
	public @ResponseBody Results  getauthuser(
			@RequestParam(value="pid",required=false)String pid,
			Model model,HttpServletRequest request,
			HttpServletResponse response) throws OApiException, UnsupportedEncodingException {
		List<province> listprovince=provinceService.findByParentid(pid);
		return  new Results(listprovince);
	}	
	
}
