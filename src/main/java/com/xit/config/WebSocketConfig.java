package com.xit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import com.xit.service.FriendService;
import com.xit.service.UserService;
import com.xit.utils.SocketServer;
import org.springframework.beans.factory.annotation.Autowired;

@Configuration  
public class WebSocketConfig {
	
    @Bean  
    public ServerEndpointExporter serverEndpointExporter(){  
        return new ServerEndpointExporter();  
    }  
    
    
    /**
     * 因 SpringBoot WebSocket 对每个客户端连接都会创建一个 WebSocketServer（@ServerEndpoint 注解对应的） 对象，Bean 注入操作会被直接略过，因而手动注入一个全局变量
     *
     * @param friendService
     */
    @Autowired(required=false)
    public void setMessageService(FriendService friendService) {
    	SocketServer.friendService = friendService;
    }
    @Autowired(required=false)
    public void setUserService(UserService userService) {
    	SocketServer.userService = userService;
    }
    
    
}  