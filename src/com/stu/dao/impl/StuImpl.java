package com.stu.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.stu.dao.stuInfo;
import com.stu.entity.tblscore;
import com.stu.util.Connect;
import com.stu.util.PageModel;
public class StuImpl implements stuInfo{
		@Override
		public List<tblscore> queryScore(String goods_type, double goods_price) {
			Connection conn= null;
			PreparedStatement ps =null;
			ResultSet rs = null;
			List<tblscore> ss= new ArrayList<tblscore>();
			String sql= "select * from tblscore";
			conn = new Connect().getConn();
			try {
				ps = conn.prepareStatement(sql);
				rs= ps.executeQuery();
				while(rs.next()){
					tblscore st= new tblscore(rs.getInt("StuId"),rs.getInt("CourseId"),rs.getInt("Score"));
					ss.add(st);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return ss;
		}

		@Override
		public boolean saveOrUpdate(tblscore gdEnt) {
			Connection conn = null;
			PreparedStatement ps = null;
			//创建工具类 获的对象
			conn = new Connect().getConn();
			boolean bool = false;
			try {
				if(gdEnt.getStuId()>0 && !("").equals(gdEnt.getStuId())){
					//修改
					String sqlUpdate = "update  tblscore set "
											
											+"CourseId     =?,"
											+"Score    =? "
											+"where StuId = ? ";
					ps = conn.prepareStatement(sqlUpdate);
					//赋值 
					ps.setInt(1, gdEnt.getCourseId());
					ps.setInt(2, gdEnt.getScore());
					ps.setInt(3, gdEnt.getStuId());
					//运行
				    int rowNum = ps.executeUpdate();
				    if(rowNum>0){
				    	bool = true;
				    	
				    }else{
					//添加
					String sqlAdd = "insert into tblscore("
											+"StuId,"
											+"CourseId,"
											+"Score"										
											+") values(?,?,?)"; 
					ps = conn.prepareStatement(sqlAdd);
					//赋值 
					ps.setInt(1, gdEnt.getStuId());
					ps.setInt(2, gdEnt.getCourseId());
					ps.setInt(3, gdEnt.getScore());
					
					//运行
				    int rowNum1 = ps.executeUpdate();
				    if(rowNum1>0){
				    	bool = true;
				    }
				}
			} }catch (SQLException e) {
				e.printStackTrace();
			}
			return bool;
			
		}

		@Override
		public tblscore getScoreInfo(int StuId) {
			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			
			tblscore gt = null;;
			String sql = "select * from tblscore where StuId = ?";
			//链接数据库 查询 数据
			conn = new Connect().getConn();
			try {
			
				//sql语句 预编译 
				ps = conn.prepareStatement(sql);
				//通过 编译 给sql 语句赋值
				ps.setInt(1, StuId);
				//执行 SQL 语句
				rs = ps.executeQuery();
				if(rs.next()){
					 gt = new tblscore(
							rs.getInt("StuId"), 
							rs.getInt("CourseId"), 
							rs.getInt("Score"));	
				}
				ps.close();
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return gt;
		}

		@Override
		public boolean deleteRow(int stu_id) {
			Connection conn = null;
			PreparedStatement ps = null;
			String sql="delete from tblscore where StuId=?";
			conn = new Connect().getConn();
			boolean b= false;
			int a;
			try {
				
				ps= conn.prepareStatement(sql);
				ps.setInt(1, stu_id);
				 a = ps.executeUpdate();
				if(a>0){
					b=true;
					return b;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
				return b;
		}

		@Override
		public tblscore searchStu(int stu_id) {
			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			tblscore gt = null;;
			String sql = "select * from tblscore where StuId = ?";
			conn = new Connect().getConn();
			try {
				ps = conn.prepareStatement(sql);
				//通过 编译 给sql 语句赋值
				ps.setInt(1, stu_id);
				//执行 SQL 语句
				rs = ps.executeQuery();
				if(rs.next()){
					 gt = new tblscore(
							rs.getInt("StuId"), 
							rs.getInt("CourseId"), 
							rs.getInt("Score"));	
				}
				ps.close();
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return gt;
		}
		public int countRow(Map<String, Object> map) {
			
			Connect db = new Connect();
			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			//返回的数据条数
			int retNum=0;
			
			String sql="select count(good_id) gids from goods_table";
			
			conn= db.getConn();
			
			try {
				ps=conn.prepareStatement(sql);
				
				//执行
				rs = ps.executeQuery();
				
				while(rs.next()){
					//取值
					retNum = rs.getInt(1);
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				//关闭
				db.CloseDB(rs, ps, conn);
			}
			return retNum;
		}

		@Override
		public PageModel<tblscore> queryPage(Map<String, Object> map) {
			//定义返回对象
			PageModel<tblscore> pm = new PageModel<tblscore>();
			int size = Integer.parseInt(map.get("size")+"");
			int currPage = Integer.parseInt(map.get("currentPage")+"");
			//赋值 每页查询的数据条数 
			pm.setSize(size);
			//当前 是 多少页
			pm.setCurrentPage(currPage);
			
			//计算总页数
			pm.setSumCount(this.countRow(map));
			
			//定义执行的sql语句
			String sql="select * from goods_table limit "+(currPage-1)*size+","+size;
			//调用方法
			ArrayList<tblscore> list = this.arrList(sql);
			pm.setList(list);
			
			return pm;
		}
		
		/**
		 * 分页查询方法
		 */
		public ArrayList<tblscore> arrList(String sql){
			
			//定义 方法 用到的对象
			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			
			ArrayList<tblscore> list = new ArrayList<tblscore>();
			//链接数据库 查询 数据
			conn = new Connect().getConn();
			try {
				//sql语句 预编译 
				ps = conn.prepareStatement(sql);
				//通过 编译 给sql 语句赋值
				
				//执行 SQL 语句
				rs = ps.executeQuery();
				//遍历封装结果集
				while(rs.next()){
					tblscore gt = new tblscore(
							rs.getInt("StuId"), 
							rs.getInt("CourseId"), 
							rs.getInt("Score") );
							
					list.add(gt);	
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return list;
		}
		
		
		
	}

