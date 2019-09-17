package com.xit.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xit.entity.MessageEntity;
import com.xit.service.FriendService;
import com.xit.service.UserService;

@ServerEndpoint(value = "/socketServer/{wechatId}")
@Component
public class SocketServer {

	private Session session;
	private static Map<String,Session> sessionPool = new HashMap<>();
	private static Map<String,String> sessionIds = new HashMap<>();
	
	public static FriendService friendService;
	public static UserService userService;
	
	@OnOpen
	public void open(Session session,@PathParam(value="wechatId")String wechatId){  
		System.out.println("wechatId"+wechatId);
		this.session = session;
		sessionPool.put(wechatId, session);
		sessionIds.put(session.getId(), wechatId);
//		System.out.println("wechatId"+sessionPool.get(wechatId));
	}
	
	@OnMessage
	public void onMessage(String message){
		JSONObject jsStr = JSONObject.parseObject(message);
//		System.out.println("message"+jsStr);
//		System.out.println("message1"+jsStr.getString("a"));
//		System.out.println("当前发送人sessionid1为:"+session.getId()+"发送的内容为:"+message);	
		MessageEntity messageEntity = (MessageEntity) JSONObject.toJavaObject(jsStr,MessageEntity.class);
		System.out.println("发送消息1" + messageEntity.getType());
		if( "chat".equals( messageEntity.getType() ) || "chatImg".equals( messageEntity.getType() )  ) {  //发送消息
			System.out.println("发送消息"+messageEntity.getReceiver());
			sendMessage(message,messageEntity.getReceiver());
		}
		if("addFriend".equals(messageEntity.getType())) {  //发送添加好友请求
			messageEntity.setStatus("waite");
//			System.out.println("添加好友：" + JSON.toJSONString(messageEntity) );
			sendMessage( JSON.toJSONString(messageEntity),messageEntity.getReceiver());
		}
		if("acceptAdd".equals(messageEntity.getType())) {  //同意添加好友
			System.out.println("同意添加"+message );
			friendService.addFriend(messageEntity.getSender(), messageEntity.getReceiver());
			friendService.addFriend(messageEntity.getReceiver(),messageEntity.getSender());
//			messageEntity.setStatus("acceptAdd");
			sendMessage( JSON.toJSONString(messageEntity),messageEntity.getReceiver());
		}
		if( "queryUser".equals(messageEntity.getType()) ) { //查询用户消息
			
		}
		if( "webrtcOffer".equals(messageEntity.getType()) ) {
			
			sendMessage(message,messageEntity.getReceiver());
			
		}
	}
	
	@OnClose
	public void onClose(){
		sessionPool.remove(sessionIds.get(session.getId()));
		sessionIds.remove(session.getId());
	}
	
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }
	
	public static void sendMessage(String message,String receiver){
		Session s = sessionPool.get(receiver);
		if(s!=null){
			try {
				s.getBasicRemote().sendText(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static int getOnlineNum(){
		return sessionPool.size();
	}
	
	public static String getOnlineUsers(){
		StringBuffer users = new StringBuffer();
	    for (String key : sessionIds.keySet()) {
		   users.append(sessionIds.get(key)+",");
		}
	    return users.toString();
	}

	public static void sendAll(String msg) {
		for (String key : sessionIds.keySet()) {
			sendMessage(msg, sessionIds.get(key));
	    }
	}
}
