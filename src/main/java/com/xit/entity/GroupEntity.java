package com.xit.entity;

import java.util.List;

public class GroupEntity {
	private String groupId;
	private String name;
	private String notice;
	private String creator;
	private List<GroupRelation> groupList;
	
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
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
	public List<GroupRelation> getGroupList() {
		return groupList;
	}
	public void setGroupList(List<GroupRelation> groupList) {
		this.groupList = groupList;
	}
	
	
	
}
