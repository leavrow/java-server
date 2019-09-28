package com.stu.dao;

import java.util.List;
import java.util.Map;

import com.stu.entity.tblscore;
import com.stu.util.PageModel;

public interface stuInfo {
		public List<tblscore> queryScore(String goods_type,double goods_price);
		public boolean saveOrUpdate(tblscore gdEnt);
		public tblscore getScoreInfo(int goodId);
		public boolean deleteRow(int stu_id);
		public tblscore searchStu(int stu_id);
		PageModel<tblscore> queryPage(Map<String, Object> map);
}
