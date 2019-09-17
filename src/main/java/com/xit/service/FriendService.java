package com.xit.service;


import java.util.List;

import com.xit.entity.FriendEntity;



public interface FriendService {
	
	public List<FriendEntity> findFriendList(String weChatId);
	
	public String addFriend(String weChatId,String friendWeChatId);

}
