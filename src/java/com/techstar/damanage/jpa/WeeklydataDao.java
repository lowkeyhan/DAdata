package com.techstar.damanage.jpa;
	


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Id;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.techstar.damanage.entity.weeklydata;


public interface WeeklydataDao<T, ID extends Serializable> extends JpaRepository<weeklydata, ID> ,JpaSpecificationExecutor<weeklydata>{
	
	
	weeklydata findById(String id);
	List<weeklydata> findByOperationeridAndDatatime(String Operationerid,Date datatime);
	List<weeklydata> findByProvincenameAndCitynameAndDatatime(String Provincename,String Cityname,Date datatime);
	List<weeklydata> findByDatatimeBetweenAndCityname(Date etime,Date stime,String cityname);
	List<weeklydata> findByDatatimeBetweenAndProvincename(Date etime,Date stime,String cityname);
	List<weeklydata> findByCitynameOrderByDatatimeDesc(String cityname,Pageable page);
	 @Query("SELECT t FROM weeklydata t WHERE t.cityname=?1 and t.datatime=(SELECT MAX(b.datatime) FROM weeklydata b WHERE b.provincename=t.provincename and b.cityname=?1) order by t.datatime desc ") 
	 List<weeklydata> findLatesttime(String cityname); 
	
}
		


