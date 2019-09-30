package com.xit.service;


import java.util.List;

//import org.apache.ibatis.annotations.Param;

//import com.xit.entity.Group;
import com.xit.entity.GroupRelation;

public interface GroupService {
	public List<GroupRelation> queryMemberAll();
	
	public int queryGroupId();
	
	public String addMember( int groupId, String member );
	
	public String createGroup( String name, String creator );
	
}