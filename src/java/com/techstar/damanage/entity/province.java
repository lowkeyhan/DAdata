
package com.techstar.damanage.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


/**
 * 地区字典
 * @author hxb
 *
 */
@Entity
@Table(name = "sys_province")
public class province  {

	@Id
	// @GeneratedValue //主键自动生成策略：数据库自动增长
	// @GeneratedValue(strategy = GenerationType.IDENTITY)//主键自动生成策略：数据库IDENTITY
	// @GeneratedValue(strategy = GenerationType.SEQUENCE)//主键自动生成策略：数据库SEQUENCE
	@GeneratedValue(generator = "system-uuid")// 主键自动生成策略：UUID
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(length=32)
	private String id;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProvincename() {
		return provincename;
	}
	public void setProvincename(String provincename) {
		this.provincename = provincename;
	}
	public String getParentid() {
		return parentid;
	}
	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
	private String provincename;//地区名称
	private String parentid;//城市id
	
 
}

