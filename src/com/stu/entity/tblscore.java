package com.stu.entity;

public class tblscore {
	private int StuId;
	private int CourseId;
	private int Score;
	public int getStuId() {
		return StuId;
	}
	public void setStuId(int stuId) {
		StuId = stuId;
	}
	public int getCourseId() {
		return CourseId;
	}
	public void setCourseId(int courseId) {
		CourseId = courseId;
	}
	public int getScore() {
		return Score;
	}
	public void setScore(int score) {
		Score = score;
	}
	public tblscore(int stuId, int courseId, int score) {
		super();
		StuId = stuId;
		CourseId = courseId;
		Score = score;
	}
	public tblscore() {
		super();
	}
	@Override
	public String toString() {
		return "StuScore [StuId=" + StuId + ", CourseId=" + CourseId + ", Score=" + Score + "]";
	}
	
	
	
}
