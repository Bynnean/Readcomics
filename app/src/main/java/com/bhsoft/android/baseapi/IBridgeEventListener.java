package com.bhsoft.android.baseapi;
/**   
 * @Title: IEventDownListener.java 
 * @Package com.iguicoo.iot.system.baseapi
 * @author binr.he@gmail.com
 * @date 2016年1月4日 上午10:47:05 
 * @version V1.0   
 */
public interface IBridgeEventListener extends IBridgeEvent{

	public void onTaskDown(int eventId,Object object);
}
