package com.xit.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
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
import com.xit.entity.GroupRelation;
import com.xit.entity.MessageEntity;
import com.xit.service.FriendService;
import com.xit.service.GroupService;
import com.xit.service.UserService;

@ServerEndpoint(value = "/socketServer/{wechatId}")
@Component
public class SocketServer {

	private Session session;
	private static Map<String,Session> sessionPool = new HashMap<>();
	private static Map<String,String> sessionIds = new HashMap<>();
	
	public static FriendService friendService;
	public static UserService userService;
	public static GroupService groupService;
	
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
//			System.out.println("发送消息"+messageEntity.getReceiver());
//			sendMessage(message,messageEntity.getReceiver());
			//如果你不是对方好友
			if( !friendService.queryFriend( messageEntity.getReceiver(),messageEntity.getSender(),"exitFriend" )  ) {
				messageEntity.setContent("你不在对方好友列表中");
				messageEntity.setType("sendFail");
				String receiver = messageEntity.getReceiver();
				messageEntity.setReceiver(messageEntity.getSender());
				messageEntity.setSender(receiver);
				sendMessage( JSON.toJSONString(messageEntity),messageEntity.getReceiver());
				return ;
			}
			//如果你在对方黑名单中
			if( !friendService.queryFriend( messageEntity.getReceiver(),messageEntity.getSender(),"friend") ) {
				System.out.println("你在对方黑名单中");
				messageEntity.setContent("消息被拒收");
				messageEntity.setType("sendFail");
				String receiver = messageEntity.getReceiver();
				messageEntity.setReceiver(messageEntity.getSender());
				messageEntity.setSender(receiver);
				sendMessage( JSON.toJSONString(messageEntity),messageEntity.getReceiver());
				return ;
			}
			System.out.println("发送消息"+messageEntity.getReceiver());
			sendMessage(message,messageEntity.getReceiver());
		}
		if("addFriend".equals(messageEntity.getType())) {  //发送添加好友请求
			messageEntity.setStatus("waite");
			sendMessage( JSON.toJSONString(messageEntity),messageEntity.getReceiver());
		}
		if("acceptAdd".equals(messageEntity.getType())) {  //同意添加好友
			if( !friendService.queryFriend( messageEntity.getSender(), messageEntity.getReceiver(),"exitFriend") ) {
				friendService.addFriend(messageEntity.getSender(), messageEntity.getReceiver());
			}
			if( !friendService.queryFriend( messageEntity.getReceiver(),messageEntity.getSender(),"exitFriend") ) {
				friendService.addFriend(messageEntity.getReceiver(),messageEntity.getSender());
			}
			System.out.println("同意添加"+message );
			sendMessage( JSON.toJSONString(messageEntity),messageEntity.getReceiver());
		}
		
		
		
		if("deleteFriend".equals(messageEntity.getType())) {  //删除好友
			
			if( !friendService.queryFriend( messageEntity.getSender(), messageEntity.getReceiver(),"exitFriend" )  ) {
				messageEntity.setContent("无该好友");
				sendMessage( JSON.toJSONString(messageEntity),messageEntity.getSender());
				return ;
			}
			
			System.out.println("删除好友"+message );
			friendService.deleteFriend(messageEntity.getSender(), messageEntity.getReceiver());
//			messageEntity.setStatus("acceptAdd");
			messageEntity.setContent("删除成功");
			messageEntity.setType("removeFriendSuccess");
			sendMessage( JSON.toJSONString(messageEntity),messageEntity.getSender());
		}
		if( "deFriend".equals(messageEntity.getType()) ) {
			System.out.println("加入黑名单"+message );
			friendService.modifyFriend("defriend", messageEntity.getSender(), messageEntity.getReceiver() );
			messageEntity.setContent("加入黑名单成功");
			messageEntity.setType("deFriendSuccess");
			sendMessage( JSON.toJSONString(messageEntity),messageEntity.getSender());
		}
		if( "friend".equals(messageEntity.getType()) ) {
			System.out.println("移出黑名单"+message );
			friendService.modifyFriend("friend", messageEntity.getSender(), messageEntity.getReceiver() );
			messageEntity.setContent("移出黑名单成功");
			messageEntity.setType("friendSuccess");
			sendMessage( JSON.toJSONString(messageEntity),messageEntity.getSender());
		}
		
		
		if( "queryUser".equals(messageEntity.getType()) ) { //查询用户消息
			
		}
		if( "webrtcOffer".equals(messageEntity.getType()) ) {
			
			sendMessage(message,messageEntity.getReceiver());
			
		}
		
		if( "queryGroup".equals(messageEntity.getType() ) ) {
			
			
			
		}
		
		if( "groupAdd".equals(messageEntity.getType() ) ) {
//			groupService.addMember(messageEntity.getReceiver(), member, remarks)
		}
		if( "groupChat".equals(messageEntity.getType() ) || "groupImg".equals(messageEntity.getType() ) ) {
			
			System.out.println("groupChat函数");
			
			//查询所有成员 转发消息
			List<GroupRelation> groupMemberList = groupService.queryMemberAll();
//			System.out.println("groupMemberList"+groupMemberList);
//			System.out.println("grouprelationList:"+grouprelationList);
			
			for( GroupRelation item: groupMemberList) {
				System.out.println("item:" + item);  
				System.out.println("item:" + item.getMember()); 
				
				sendMessage(message,item.getMember());
			}
			
		}
		
		if("modifyRemarks".equals(messageEntity.getType() ) ) {
			System.out.println("setRemarks:" + messageEntity.getContent() );  
			
//			String info = friendService.modifyRemarks( messageEntity.getContent(), messageEntity.getSender(), messageEntity.getReceiver() );
			
			friendService.modifyRemarks( messageEntity.getContent(), messageEntity.getSender(), messageEntity.getReceiver() );
			
//			messageEntity.setContent(info);
			sendMessage( JSON.toJSONString(messageEntity),messageEntity.getSender());
		}
			
		
		if( "createGroup".equals(messageEntity.getType() ) ) {
			System.out.println("createGroup:" + messageEntity.getContent() );
			
			groupService.createGroup(messageEntity.getReceiver(),messageEntity.getContent()  );
			int groupId = groupService.queryGroupId();
			messageEntity.setContent( groupId+"" );
			sendMessage( JSON.toJSONString(messageEntity),messageEntity.getSender());
			
		}
		if( "addGroupMember".equals(messageEntity.getType() )  ) {
			
			System.out.println("addGroupMember:");
			
//			System.out.println("addGroupMember:" + messageEntity.getContent() );
			
			// 因为没有重新设计实体 和消息格式  所以暂用之前的消息格式
			//messageEntity.getReceiver 是 群成员  member
			
			//查询最新创建的群号
			int groupId = groupService.queryGroupId();
			System.out.println( "群id"+ groupId );
			
			groupService.addMember( groupId, messageEntity.getReceiver() );
			
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
