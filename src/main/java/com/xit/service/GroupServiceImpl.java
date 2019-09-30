package com.xit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import com.xit.entity.Group;
import com.xit.entity.GroupRelation;
import com.xit.mapper.GroupMapper;
//import com.xit.utils.MD5;

@Service
public class GroupServiceImpl implements GroupService {

	@Autowired
	private GroupMapper groupMapper;
	
	
	@Override
	public List<GroupRelation> queryMemberAll() {
		// TODO Auto-generated method stub
		System.out.println("groupService");
		return groupMapper.queryMemberAll();
	}


	@Override
	public String addMember(int groupId, String member) {
		// TODO Auto-generated method stub
		
		try {
			groupMapper.addMember(groupId, member);
			return "添加群成员成功";
		}catch (Exception e) {
			return e.toString();
		}
	}


	@Override
	public String createGroup(String name, String creator) {
		
		try {
			groupMapper.createGroup(name, creator);
			return "创建群组成功";
		}catch (Exception e) {
			return e.toString();
		}
	}


	@Override
	public int queryGroupId() {
		// TODO Auto-generated method stub
		
		return groupMapper.queryGroupId();
		
	}



	

}
