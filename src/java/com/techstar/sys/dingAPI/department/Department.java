package com.techstar.sys.dingAPI.department;

public class Department {

	public String id;
	public String name;
	public String parentid;
	
	@Override
	public String toString() {
		return "Department[id:" + id + ", name:" + name + ", parentId:" + parentid + "]";
	}
}
