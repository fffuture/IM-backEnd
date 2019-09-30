package com.xit.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.xit.entity.FriendEntity;



@Mapper
public interface FriendMapper {
	
	public List<FriendEntity> findFriendList(String weChatId);
	
	public void addFriend( @Param("weChatId")String weChatId, @Param("friendWeChatId")String friendWeChatId );
	
	
	public void deleteFriend( @Param("weChatId")String weChatId, @Param("friendWeChatId")String friendWeChatId );
	
	public List<FriendEntity> queryFriend( @Param("weChatId")String weChatId, @Param("friendWeChatId")String friendWeChatId );
	
	public void modifyFriend( @Param("friendState")String friendState,@Param("weChatId")String weChatId, @Param("friendWeChatId")String friendWeChatId );
	
	public void modifyRemarks( @Param("remarks")String remarks,@Param("weChatId")String weChatId, @Param("friendWeChatId")String friendWeChatId );
	
	
	/*public User findUser(String id);
	
	public User login( @Param("weChatId")String weChatId, @Param("password")String password );
	
	public void register( @Param("weChatId")String weChatId, @Param("password")String password );*/

}
