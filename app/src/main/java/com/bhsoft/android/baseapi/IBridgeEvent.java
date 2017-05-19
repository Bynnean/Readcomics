package com.bhsoft.android.baseapi;
/**   
 * @Title: IBridgeEvent.java 
 * @Package com.iguicoo.iot.system.baseapi
 * @author binr.he@gmail.com
 * @date 2016年1月4日 下午1:34:34 
 * @version V1.0   
 */
public interface IBridgeEvent {

	/****************  网络连接状态   事件  *********************/
	public final int EVENT_NET_CONNECT = 0x100;
	public final int EVENT_NET_DISCONNECT = 0x101;
	//主界面按两次退出
	public final int EVENT_EXITFLAG = 0x102;
	
	//获取到xmpp信息
	public static final int GET_XMPP_CHAT_MESSAGE = 0x106;
}
