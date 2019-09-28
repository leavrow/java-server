package com.stu.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PageModel<T> {

	//分页对象需要的参数:当前页数，总条数, 总页数, 每页显示的数据，条件查询参数 ，当前页的数据集合........；
	private int currentPage;
	private int sumCount; 	//总条数
	private int size;
	private int sumPage;
	
	private Map<String, Object> map;
	private ArrayList<T> list;
	
	//生成get  set方法
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getSumCount() {
		return sumCount;
	}
	//计算总页数
	public void setSumCount(int sumCount) {
		this.sumCount = sumCount;
		//除法
		this.sumPage = this.sumCount/this.size;
		//取模 大于0 加一页
		if((this.sumCount%this.size)>0){
			this.sumPage++;
		}
	}
	
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getSumPage() {
		return sumPage;
	}
	public void setSumPage(int sumPage) {
		this.sumPage = sumPage;
	}
	public Map<String, Object> getMap() {
		return map;
	}
	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
	public ArrayList<T> getList() {
		return list;
	}
	public void setList(ArrayList<T> list) {
		this.list = list;
	}
	

	
	
	
	
	
}
