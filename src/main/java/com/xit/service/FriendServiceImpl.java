package com.xit.service;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xit.entity.FriendEntity;
import com.xit.mapper.FriendMapper;

@Service
public class FriendServiceImpl implements FriendService {
	
	@Autowired(required=false)
	private FriendMapper friendMapper;

	@Override
	public List<FriendEntity> findFriendList(String weChatId) {
		List<FriendEntity> Friendlist = friendMapper.findFriendList(weChatId);
		return Friendlist;
	}

	@Override
	public String addFriend(String weChatId, String friendWeChatId) {
		System.out.println("add  weChatId" + weChatId +"   friendWeChatId" + friendWeChatId );
		try{
			friendMapper.addFriend(weChatId, friendWeChatId);
			return "添加成功";
		}catch( Exception e ) {
			return e.toString();
		}
		
	}	

	
}
