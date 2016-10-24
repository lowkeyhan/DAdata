package com.techstar.damanage.jpa;
	


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Id;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.techstar.damanage.entity.province;
import com.techstar.damanage.entity.weeklydata;


public interface ProvinceDao<T, ID extends Serializable> extends JpaRepository<province, ID> ,JpaSpecificationExecutor<province>{
	
	
	List<province> findByParentid(String parentid);
	province findById(String id);
	
	
	
	}
		


