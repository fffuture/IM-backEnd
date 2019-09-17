package com.xit.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xit.entity.User;

import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface UserMapper {
	
	public List<User> findAll();
	
	public User findUser(String weChatId);
	
	public User login( @Param("weChatId")String weChatId, @Param("password")String password );
	
	public void register( @Param("weChatId")String weChatId, @Param("password")String password );

}
