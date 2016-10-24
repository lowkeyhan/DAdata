
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
 * 采集数据
 * @author hxb
 *
 */
@Entity
@Table(name = "sp_weeklydata")
public class weeklydata  {
	
	
	
	@Id
	// @GeneratedValue //主键自动生成策略：数据库自动增长
	// @GeneratedValue(strategy = GenerationType.IDENTITY)//主键自动生成策略：数据库IDENTITY
	// @GeneratedValue(strategy = GenerationType.SEQUENCE)//主键自动生成策略：数据库SEQUENCE
	@GeneratedValue(generator = "system-uuid")// 主键自动生成策略：UUID
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(length=32)
	private String id;

	private Date datatime;//数据时间
	private String provinceid;//省份id
	private String provincename;//省份名称
	private String cityid;//城市id
	private String cityname;//城市名称
	private String collectionrate;//采集率
	private String crcompare;//采集率对比
	private String installnum;//安装数
	private String inscompare;//安装数duibi
	private String companyid;//公司id
	private String companyname;//公司名称
	private String weeknum;//周数
	private String rank;//排名
	private Date operationdate=new Date();//添加时间
	private String operationer;//添加人
	private String operationerid;//添加人id
	private String shuoming;//private String operationerid;//添加人id
	/**
	 * id
	 * @return
	 */
	public String getId() {
		return id;
	}
	/**
	 * id
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}
	public Date getDatatime() {
		return datatime;
	}
	
	/**
	 * 数据时间
	 * @param datatime
	 */
	public void setDatatime(Date datatime) {
		this.datatime = datatime;
	}
	public String getProvinceid() {
		return provinceid;
	}
	/**
	 * 省份id
	 * @param provinceid
	 */
	public void setProvinceid(String provinceid) {
		this.provinceid = provinceid;
	}
	public String getProvincename() {
		return provincename;
	}
	/**
	 * 省份名称
	 * @param provincename
	 */
	public void setProvincename(String provincename) {
		this.provincename = provincename;
	}
	public String getCityid() {
		return cityid;
	}
	
	/**
	 * 城市id
	 * @param cityid
	 */
	public void setCityid(String cityid) {
		this.cityid = cityid;
	}
	public String getCityname() {
		return cityname;
	}
	
	/**
	 * 城市名称id
	 * @param cityname
	 */
	public void setCityname(String cityname) {
		this.cityname = cityname;
	}
	public String getCollectionrate() {
		return collectionrate;
	}
	
	/**
	 * 采集率
	 * @param collectionrate
	 */
	public void setCollectionrate(String collectionrate) {
		this.collectionrate = collectionrate;
	}
	public String getInstallnum() {
		return installnum;
	}
	
	/**
	 * 安装数量
	 * @param installnum
	 */
	public void setInstallnum(String installnum) {
		this.installnum = installnum;
	}
	public String getCompanyid() {
		return companyid;
	}
	
	/**
	 * 公司id
	 * @param companyid
	 */
	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}
	public String getCompanyname() {
		return companyname;
	}
	
	/**
	 *公司名称
	 * @param companyname
	 */
	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}
	public String getWeeknum() {
		return weeknum;
	}
	
	/**
	 * 周
	 * @param weeknum
	 */
	public void setWeeknum(String weeknum) {
		this.weeknum = weeknum;
	}
	public String getRank() {
		return rank;
	}
	
	/**
	 * 排名
	 * @param rank
	 */
	public void setRank(String rank) {
		this.rank = rank;
	}
	public Date getOperationdate() {
		return operationdate;
	}
	public void setOperationdate(Date operationdate) {
		this.operationdate = operationdate;
	}
	public String getOperationer() {
		return operationer;
	}
	public void setOperationer(String operationer) {
		this.operationer = operationer;
	}
	public String getOperationerid() {
		return operationerid;
	}
	public void setOperationerid(String operationerid) {
		this.operationerid = operationerid;
	}
	public String getCrcompare() {
		return crcompare;
	}
	public void setCrcompare(String crcompare) {
		this.crcompare = crcompare;
	}
	public String getInscompare() {
		return inscompare;
	}
	public void setInscompare(String inscompare) {
		this.inscompare = inscompare;
	}
	public String getShuoming() {
		return shuoming;
	}
	public void setShuoming(String shuoming) {
		this.shuoming = shuoming;
	}
	
	
 
}

