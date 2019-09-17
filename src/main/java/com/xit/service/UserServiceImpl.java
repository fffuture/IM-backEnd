package com.xit.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xit.entity.RespCode;
import com.xit.entity.RespEntity;
import com.xit.entity.User;
import com.xit.mapper.UserMapper;
import com.xit.service.UserService;
import com.xit.utils.MD5;
import com.xit.utils.jwtHandler;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired(required=false)
	private UserMapper userMapper;
	
	@Override
	public List<User> findAllUser() {
		List<User> list = userMapper.findAll();
		return list;
	}

	@Override
	public User findUser(String weChatId) {
		User user = userMapper.findUser(weChatId);
		return user;
	}

	@Override
	public RespEntity login(String weChatId, String password,String device) {
		System.out.println("weChatId" + weChatId + "password" + MD5.encryption(password));
		User result =  userMapper.login( weChatId,MD5.encryption(password) );
		if(result != null) {
			return new RespEntity(RespCode.SUCCESS,jwtHandler.creatJWT( result.getWeChatId().toString(), device ));
		}
		return new RespEntity(RespCode.FAIL,"账号或者密码错误");
	}

	@Override
	public String register(String id, String password) {		
		if( userMapper.login(id,MD5.encryption(password)) != null ) {
			return "微信号已存在";
		}		
		try {
			userMapper.register(id, MD5.encryption(password) );
			return "注册成功";
		}catch (Exception e) {
			return e.toString();
		}
	}

	

	
}
