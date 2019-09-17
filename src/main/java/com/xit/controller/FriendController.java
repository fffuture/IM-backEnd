package com.xit.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.xit.entity.FriendEntity;
import com.xit.entity.User;
import com.xit.service.FriendService;



@RestController
@RequestMapping("/api")
public class FriendController {
	
	@Autowired(required=false)
	private FriendService friendService;

	@RequestMapping(value="friendList",method = RequestMethod.POST)
	public List<FriendEntity> friendList(@RequestBody User user){
		System.out.println( "weChatId friend"+user.getWeChatId() );
		List<FriendEntity> list = friendService.findFriendList(user.getWeChatId());
		return list;
	}
	
	@RequestMapping(value="addFriend",method = RequestMethod.POST)
	public List<FriendEntity> addFriend(@RequestBody User user){
		System.out.println( "weChatId"+user.getWeChatId() );
		List<FriendEntity> list = friendService.findFriendList(user.getWeChatId());
		return list;
	}
	




	
	
	
	
	
	
	
}
