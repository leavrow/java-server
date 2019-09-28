package com.stu.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PageModel<T> {

	//��ҳ������Ҫ�Ĳ���:��ǰҳ����������, ��ҳ��, ÿҳ��ʾ�����ݣ�������ѯ���� ����ǰҳ�����ݼ���........��
	private int currentPage;
	private int sumCount; 	//������
	private int size;
	private int sumPage;
	
	private Map<String, Object> map;
	private ArrayList<T> list;
	
	//����get  set����
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getSumCount() {
		return sumCount;
	}
	//������ҳ��
	public void setSumCount(int sumCount) {
		this.sumCount = sumCount;
		//����
		this.sumPage = this.sumCount/this.size;
		//ȡģ ����0 ��һҳ
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
