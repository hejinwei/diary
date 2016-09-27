package com.hejinwei.diary.util;


import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.hejinwei.weixin.TextMessage;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;

public class MessageUtil {
	
	public static final String RESP_MESSAGE_TYPE_TEXT = "text";
	
	public static final String RESP_MESSAGE_TYPE_MUSIC = "music";
	
	public static final String RESP_MESSAGE_TYPE_NEWS = "news";
	
	public static final String REQ_MESSAGE_TYPE_TEXT = "text";
	
	public static final String REQ_MESSAGE_TYPE_IMAGE = "image";
	
	public static final String REQ_MESSAGE_TYPE_VOICE = "voice";
	
	public static final String REQ_MESSAGE_TYPE_VIDEO = "video";
	
	public static final String REQ_MESSAGE_TYPE_SHORTVIDEO = "shortvideo";
	
	public static final String REQ_MESSAGE_TYPE_LOCATION = "location";
	
	public static final String REQ_MESSAGE_TYPE_LINK = "link";
	
	public static final String REQ_MESSAGE_TYPE_EVENT = "event";
	
	// 用户还未关注公众号，扫描带参数二维码推送事件
	public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";
	
	public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";
	
	// 用户已关注公众号，扫描带参数二维码推送事件
	public static final String EVENT_TYPE_SCAN = "SCAN";
	
	// 上报地理位置事件
	public static final String EVENT_TYPE_LOCATION = "LOCATION";
	
	// 自定义菜单事件
	public static final String EVENT_TYPE_CLICK = "CLICK";
	
	
	/**
	 * 扩展xstream，使其支持CDATA块
	 * 
	 */
	private static XStream xstream = new XStream(new XppDriver() {
		public HierarchicalStreamWriter createWriter(Writer out) {
			return new PrettyPrintWriter(out) {
				// 对所有xml节点的转换都增加CDATA标记
				boolean cdata = true;

				public void startNode(String name, @SuppressWarnings("rawtypes") Class clazz) {
					super.startNode(name);
				}

				protected void writeText(QuickWriter writer, String text) {
					if (cdata) {
						writer.write("<![CDATA[");
						writer.write(text);
						writer.write("]]>");
					} else {
						writer.write(text);
					}
				}
			};
		}
	});
	
	@SuppressWarnings("unchecked")
	public static Map<String, String> parseXml(HttpServletRequest request) throws Exception {
		Map<String, String> map = new HashMap<String, String>();

		InputStream inputStream = request.getInputStream();
		
		SAXReader reader = new SAXReader();
		Document document = reader.read(inputStream);
		//System.out.println("读取输入流document>>>>>>>>="+document.getText());
		
		Element root = document.getRootElement();
		List<Element> elementList = root.elements();

		for (Element e : elementList){
			map.put(e.getName(), e.getText());
		}
		
		// 释放资源
		inputStream.close();
		inputStream = null;

		return map;
	}
	
	public static String textMessageToXml(TextMessage textMessage) {
		xstream.alias("xml", textMessage.getClass());
		return xstream.toXML(textMessage);
	}

}
