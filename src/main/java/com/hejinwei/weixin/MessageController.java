package com.hejinwei.weixin;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hejinwei.diary.util.MessageUtil;

@Controller
@RequestMapping("/weixin")
public class MessageController {
	
	private static final String TOKEN = "6666667777777";
	
	@RequestMapping(value = "/receiveMessage", method = {RequestMethod.GET, RequestMethod.POST})
	public void receiveMessage(HttpServletRequest request, HttpServletResponse response) {
		
		boolean validateResult = validate(request, response);
		if (!validateResult) {
			System.out.println("not weixin's message");
			return;
		}
		
		boolean isGet = request.getMethod().equalsIgnoreCase("get");
		
		if (isGet) {
			access(request, response);
		} else {
			try {
				accept(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * 接收消息
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void accept(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 从请求中读取中各种参数
		Map<String, String> requestMap = MessageUtil.parseXml(request);
		StringBuffer requestSb = new StringBuffer();
		for (String key : requestMap.keySet()) {
			requestSb.append("key:" + key + ",value:" + requestMap.get(key) + ";");
		}
		System.out.println(requestSb.toString());
		
		// 回复文本消息
		TextMessage textMessage = new TextMessage();
		textMessage.setToUserName(requestMap.get("FromUserName"));
		textMessage.setFromUserName(requestMap.get("ToUserName"));
		textMessage.setCreateTime(new Date().getTime());
		textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
		
		String msgType = requestMap.get("MsgType");
		String respContent = "未知的消息类型";
		
		switch (msgType) {
		case MessageUtil.REQ_MESSAGE_TYPE_TEXT:
			respContent = "您发送的是文本消息";
			break;
			
		case MessageUtil.REQ_MESSAGE_TYPE_IMAGE:
			respContent = "您发送的是图片消息";
			break;
			
		case MessageUtil.REQ_MESSAGE_TYPE_VOICE:
			respContent = "您发送的是语音消息";
			break;
			
		case MessageUtil.REQ_MESSAGE_TYPE_VIDEO:
			respContent = "您发送的是视频消息";
			break;
			
		case MessageUtil.REQ_MESSAGE_TYPE_LOCATION:
			respContent = "您发送的是地理位置消息";
			break;
			
		case MessageUtil.REQ_MESSAGE_TYPE_LINK:
			respContent = "您发送的是链接消息";
			break;
			
		case MessageUtil.REQ_MESSAGE_TYPE_EVENT:
			String eventType = requestMap.get("Event");
			switch (eventType) {
			case MessageUtil.EVENT_TYPE_SUBSCRIBE:
				respContent = "谢谢您的关注";
				break;
				
			case MessageUtil.EVENT_TYPE_UNSUBSCRIBE:
				// 取消关注收不到消息
				break;
				
			case MessageUtil.EVENT_TYPE_SCAN:
				respContent = "处理扫描带参数二维码事件";
				break;
				
			case MessageUtil.EVENT_TYPE_LOCATION:
				respContent = "处理上报地理位置事件";
				break;
				
			case MessageUtil.EVENT_TYPE_CLICK:
				respContent = "处理菜单点击事件";
				break;
				
			default:
				break;
			}
			break;

		default:
			break;
		}
		
		textMessage.setContent(respContent);
		String respXml = MessageUtil.textMessageToXml(textMessage);
		
		PrintWriter out = response.getWriter();
		out.print(respXml);
		out.flush();
		out.close();
	}
	
	/**
	 * 接入微信
	 * @param request
	 * @param response
	 */
	private void access(HttpServletRequest request, HttpServletResponse response) {
		String echostr = request.getParameter("echostr");
		
		try {
			PrintWriter out = response.getWriter();
			out.print(echostr);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("deprecation")
	private boolean validate(HttpServletRequest request, HttpServletResponse response) {
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		
		List<String> list = new ArrayList<>();
		list.add(TOKEN);
		list.add(timestamp);
		list.add(nonce);
		
		Collections.sort(list);
		String localSignature = "";
		for (String str : list) {
			localSignature += str;
		}
		
		localSignature = DigestUtils.shaHex(localSignature);
		
		return localSignature.equalsIgnoreCase(signature);
	}

	
	
	 
}
