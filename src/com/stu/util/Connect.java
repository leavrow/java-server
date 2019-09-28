package com.stu.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.mysql.cj.jdbc.Driver;

public class Connect {
	public static String url="jdbc:mysql://localhost:3306/8-23?userUnicode=true&characterEncoding=utf-8&serverTimezone=GMT%2B8";
	public static String user="root";
	public static String password="root";
	
	static{
//		ResourceBundle rb= ResourceBundle.getBundle("jdbc");
//		url = rb.getString("url");
//		user = rb.getString("user");
//		password = rb.getString("password");
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
	}
	public Connection getConn(){
		Connection conn = null;
		try {
			conn= DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	public void CloseDB(ResultSet rs, PreparedStatement ps, Connection conn){
		try {
			if (rs!=null)rs.close();
			if (ps!=null)ps.close();
			if (conn!=null)conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
