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
	
	@Override
	public String deleteFriend(String weChatId, String friendWeChatId) {
		System.out.println("add  weChatId"+weChatId+"   friendWeChatId"+friendWeChatId );
		try{
			friendMapper.deleteFriend(weChatId, friendWeChatId);
			return "删除成功";
		}catch( Exception e ) {
			return e.toString();
		}
		
	}

	@Override
	public Boolean queryFriend(String weChatId, String friendWeChatId,String type) {
		List<FriendEntity> Friendlist = friendMapper.queryFriend(weChatId, friendWeChatId);
		
		if( "exitFriend".equalsIgnoreCase(type) ) {
			return !Friendlist.isEmpty();
		}
		if( "friend".equalsIgnoreCase(type) ) {
			if( "friend".equalsIgnoreCase(Friendlist.get(0).getFriend()) ) {
				return true;
			}else {
				return false;
			}
		}
		return false;
	}

	@Override
	public String modifyFriend(String friendState, String weChatId, String friendWeChatId) {
		try{
			friendMapper.modifyFriend(friendState,weChatId, friendWeChatId);
			return "friend修改成功";
		}catch( Exception e ) {
			return e.toString();
		}
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public String modifyRemarks(String remarks, String weChatId, String friendWeChatId) {
		try{
			friendMapper.modifyRemarks(remarks,weChatId, friendWeChatId);
			System.out.println("修改成功");
			return "remarks修改成功";
		}catch( Exception e ) {
			return e.toString();
		}
	}

	
}
