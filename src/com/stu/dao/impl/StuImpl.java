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
			//���������� ��Ķ���
			conn = new Connect().getConn();
			boolean bool = false;
			try {
				if(gdEnt.getStuId()>0 && !("").equals(gdEnt.getStuId())){
					//�޸�
					String sqlUpdate = "update  tblscore set "
											
											+"CourseId     =?,"
											+"Score    =? "
											+"where StuId = ? ";
					ps = conn.prepareStatement(sqlUpdate);
					//��ֵ 
					ps.setInt(1, gdEnt.getCourseId());
					ps.setInt(2, gdEnt.getScore());
					ps.setInt(3, gdEnt.getStuId());
					//����
				    int rowNum = ps.executeUpdate();
				    if(rowNum>0){
				    	bool = true;
				    	
				    }else{
					//���
					String sqlAdd = "insert into tblscore("
											+"StuId,"
											+"CourseId,"
											+"Score"										
											+") values(?,?,?)"; 
					ps = conn.prepareStatement(sqlAdd);
					//��ֵ 
					ps.setInt(1, gdEnt.getStuId());
					ps.setInt(2, gdEnt.getCourseId());
					ps.setInt(3, gdEnt.getScore());
					
					//����
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
			//�������ݿ� ��ѯ ����
			conn = new Connect().getConn();
			try {
			
				//sql��� Ԥ���� 
				ps = conn.prepareStatement(sql);
				//ͨ�� ���� ��sql ��丳ֵ
				ps.setInt(1, StuId);
				//ִ�� SQL ���
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
				//ͨ�� ���� ��sql ��丳ֵ
				ps.setInt(1, stu_id);
				//ִ�� SQL ���
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
			//���ص���������
			int retNum=0;
			
			String sql="select count(good_id) gids from goods_table";
			
			conn= db.getConn();
			
			try {
				ps=conn.prepareStatement(sql);
				
				//ִ��
				rs = ps.executeQuery();
				
				while(rs.next()){
					//ȡֵ
					retNum = rs.getInt(1);
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				//�ر�
				db.CloseDB(rs, ps, conn);
			}
			return retNum;
		}

		@Override
		public PageModel<tblscore> queryPage(Map<String, Object> map) {
			//���巵�ض���
			PageModel<tblscore> pm = new PageModel<tblscore>();
			int size = Integer.parseInt(map.get("size")+"");
			int currPage = Integer.parseInt(map.get("currentPage")+"");
			//��ֵ ÿҳ��ѯ���������� 
			pm.setSize(size);
			//��ǰ �� ����ҳ
			pm.setCurrentPage(currPage);
			
			//������ҳ��
			pm.setSumCount(this.countRow(map));
			
			//����ִ�е�sql���
			String sql="select * from goods_table limit "+(currPage-1)*size+","+size;
			//���÷���
			ArrayList<tblscore> list = this.arrList(sql);
			pm.setList(list);
			
			return pm;
		}
		
		/**
		 * ��ҳ��ѯ����
		 */
		public ArrayList<tblscore> arrList(String sql){
			
			//���� ���� �õ��Ķ���
			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			
			ArrayList<tblscore> list = new ArrayList<tblscore>();
			//�������ݿ� ��ѯ ����
			conn = new Connect().getConn();
			try {
				//sql��� Ԥ���� 
				ps = conn.prepareStatement(sql);
				//ͨ�� ���� ��sql ��丳ֵ
				
				//ִ�� SQL ���
				rs = ps.executeQuery();
				//������װ�����
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

