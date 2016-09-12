package com.hejinwei.weixin;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/weixin")
public class MessageController {
	
	private static final String TOKEN = "6666667777777";
	
	@SuppressWarnings("deprecation")
	@RequestMapping("/receiveMessage")
	public void receiveMessage(HttpServletRequest request, HttpServletResponse response) {
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");
		
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
		if ( !localSignature.equalsIgnoreCase(signature) ) {
			return;
		}
		
		try {
			PrintWriter out = response.getWriter();
			out.print(echostr);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	 
}
