package com.xit.service;


import java.util.List;

import com.xit.entity.FriendEntity;



public interface FriendService {
	
	public List<FriendEntity> findFriendList(String weChatId);
	
	public String addFriend(String weChatId,String friendWeChatId);
	
	public String deleteFriend(String weChatId,String friendWeChatId);
	
	public Boolean queryFriend( String weChatId,String friendWeChatId,String type );

	public String modifyFriend( String friendState,String weChatId,String friendWeChatId );
	
	public String modifyRemarks( String remarks,String weChatId,String friendWeChatId );

}
