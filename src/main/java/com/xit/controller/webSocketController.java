package com.xit.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xit.utils.SocketServer;



@Controller("webSocketController")
public class webSocketController {
//	@RequestMapping("sockettest")
//	public String sockettest(){
//		return "sockettest";
//	}
//	
//	@RequestMapping("tongji")
//	public String tongji(Model model){
//		model.addAttribute("num", SocketServer.getOnlineNum());
//		model.addAttribute("users", SocketServer.getOnlineUsers());
//		return "tongji";
//	}
//	
//	@RequestMapping("sendmsg")
//	@ResponseBody
//	public String sendmsg(HttpServletRequest request){
//		String username = request.getParameter("username");
//		String msg = request.getParameter("msg");
//		SocketServer.sendMessage(msg, username);
//		return "success";
//	}
//	
//	@RequestMapping("sendAll")
//	@ResponseBody
//	public String sendAll(HttpServletRequest request){
//		String msg = request.getParameter("msg");
//		SocketServer.sendAll(msg);
//		return "success";
//	}
	
	@RequestMapping("sockettest")
	public String sockettest(){
		return "sockettest";
	}
	
	@RequestMapping("tongji")
	public String tongji(Model model){
		model.addAttribute("num", SocketServer.getOnlineNum());
		model.addAttribute("users", SocketServer.getOnlineUsers());
		return "tongji";
	}
	
	@RequestMapping("sendmsg")
	@ResponseBody
	public String sendmsg(HttpServletRequest request){
		String username = request.getParameter("username");
		String msg = request.getParameter("msg");
		SocketServer.sendMessage(msg, username);
		return "success";
	}
	
	@RequestMapping("sendAll")
	@ResponseBody
	public String sendAll(HttpServletRequest request){
		String msg = request.getParameter("msg");
		SocketServer.sendAll(msg);
		return "success";
	} 
}
