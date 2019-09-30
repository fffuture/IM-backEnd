package com.xit.entity;

public class Group {
	private int id;
	private String name;
	private String notice;
	private String creator;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNotice() {
		return notice;
	}
	public void setNotice(String notice) {
		this.notice = notice;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	@Override
	public String toString() {
		return "Group [id=" + id + ", name=" + name + ", notice=" + notice + ", creator=" + creator + "]";
	}
	
	
}
