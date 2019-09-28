package com.stu.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.stu.dao.stuInfo;
import com.stu.dao.impl.StuImpl;
import com.stu.entity.tblscore;
import com.stu.util.PageModel;
public class StuServlet extends HttpServlet {



	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		String op = req.getParameter("op");
		if(("StuList").equals(op)){
			queryStuList(req, resp);
		}else if(("saveOrUpdate").equals(op)){
			saveOrUpdateI(req, resp);
		}else if(("queryStuById").equals(op)){
			queryStuById(req, resp);
		}else if(("deleteRow").equals(op)){
			deleteRow(req,resp);
		}else if(("searchStu").equals(op)){
			searchStu(req,resp);
		}else if(("queryPage").equals(op)){
			queryPage(req,resp);	
		}else if(("myPage").equals(op)){
			myPgaeInfo(req, resp);
		}
		

	}
	private void myPgaeInfo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		stuInfo gdao = new StuImpl();
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("rows", req.getParameter("rows"));
		map.put("page", req.getParameter("page"));
		
		
		PageModel<tblscore> pm = gdao.queryPage(map);
		
		req.setAttribute("pm", pm);
		//响应到页面中
//		req.getRequestDispatcher("../goodPage.jsp").forward(req, resp);
	}

	private void queryPage(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		stuInfo goodDao = new StuImpl();

		List<tblscore> list = goodDao.queryScore(null, 0.00);
		
		Gson g = new Gson();
		String jsonStr = g.toJson(list);

		PrintWriter out = resp.getWriter();
		System.out.println("===>"+jsonStr);
		
		out.print(jsonStr);
	}

	public void queryStuById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		stuInfo iStu = new StuImpl();

		String StuId = request.getParameter("StuId");
	//	System.out.println(StuId);
		tblscore gdEnt = iStu.getScoreInfo(Integer.parseInt(StuId));
		
		Gson gs = new Gson();
		String jsonStu = gs.toJson(gdEnt);
		System.out.println("jsonGoods==>"+jsonStu);

		PrintWriter out = response.getWriter();
		out.print(jsonStu);

		out.flush();
		out.close();
	
	}
	

	public void saveOrUpdateI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String stu_id = request.getParameter("stu_id");
		String course_id = request.getParameter("course_id");
		String score = request.getParameter("score");
		
		tblscore gdEnt = new tblscore(
							(("").equals(stu_id)||stu_id==null) ? 0 : Integer.parseInt(stu_id),
									Integer.parseInt(course_id),
							Integer.parseInt(score));
		
		stuInfo istu = new StuImpl();
		boolean bool = istu.saveOrUpdate(gdEnt);
		String retMessage = "";
		if(bool){
			retMessage = "success";
		}else{
			retMessage = "error";
		}
	
		PrintWriter out = response.getWriter();
		out.print(retMessage);
		out.flush();
		out.close();
	}

	
	public void queryStuList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		stuInfo iStu = new StuImpl();

		List<tblscore> retList = iStu.queryScore(null, 0.00);
		
		Gson gs = new Gson();
		String jsonScore = gs.toJson(retList);
		System.out.println("jsonSCore1==>"+jsonScore);
		PrintWriter out = response.getWriter();
		out.print(jsonScore);
		out.flush();
		out.close();
		}
	public void deleteRow(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String stu_id = request.getParameter("StuId");
		System.out.println("jsonScore2==>"+ stu_id);
		stuInfo s=new StuImpl();
		boolean bool =s.deleteRow(Integer.parseInt(stu_id));
		String retMessage = "";
		if(bool){
			retMessage = "success";
		}else{
			retMessage = "error";
		}
	
		PrintWriter out = response.getWriter();
		out.print(retMessage);
		out.flush();
		out.close();
		}
	public void searchStu(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String stu_id = request.getParameter("StuId");
		System.out.println("jsonScore2==>"+ stu_id);
		stuInfo s=new StuImpl();
		tblscore gdEnt= new tblscore();
		gdEnt =s.searchStu(Integer.parseInt(stu_id));
		Gson gs = new Gson();
		String jsonStu = gs.toJson(gdEnt);
		System.out.println("jsonGoods==>"+jsonStu);

		PrintWriter out = response.getWriter();
		out.print(jsonStu);

		out.flush();
		out.close();
	}
	}
