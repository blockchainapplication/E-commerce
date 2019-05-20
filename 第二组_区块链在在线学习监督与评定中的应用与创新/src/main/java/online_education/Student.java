package online_education;

import java.util.ArrayList;
import java.util.List;

public class Student {
	private String sName ;
	private List<Course> courses = new ArrayList<Course> ();
	private String classs ;
	public String getsName() {
		return sName;
	}
	public void setsName(String sName) {
		this.sName = sName;
	}
	public List<Course> getCourses() {
		return courses;
	}
	public void setCourses(List<Course> courses) {
		this.courses.addAll(courses);
	}
	public String getClasss() {
		return classs;
	}
	public void setClasss(String classs) {
		this.classs = classs;
	}
	
}
