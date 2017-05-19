package com.bhsoft.android.baseapi;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import android.os.Handler;
import android.os.Message;

/**   
 * @Title: BridgeEventDispatch.java 
 * @Package com.iguicoo.iot.system.baseapi
 * @author binr.he@gmail.com
 * @date 2016年1月4日 上午10:51:09 
 * @version V1.0   
 */
public class BridgeEventDispatch extends Handler {

	private String TAG = BridgeEventDispatch.class.getSimpleName();
	private static BridgeEventDispatch dispatch = null;
	private List<IBridgeEventListener> eventLists = new LinkedList<IBridgeEventListener>();

	private BridgeEventDispatch() {

	}

	public static BridgeEventDispatch getInstance() {
		if (null == dispatch) {
				if (null == dispatch) {
					dispatch = new BridgeEventDispatch();
				}
		}
		return dispatch;
	}

	/**
	 * 在总线注册事件监听
	 * @param listener
	 */
	public void registerListener(IBridgeEventListener listener) {
         if(null != listener){
        	Iterator<IBridgeEventListener> iterator =  eventLists.iterator();
        	while (iterator.hasNext()){
        		IBridgeEventListener eventListener = iterator.next();
        		if(eventListener.equals(listener)) return;
        	}
        	eventLists.add(listener);
         }
	}
	
	/**
	 * 取消事件注册
	 * @param listener
	 */
	public void unRegisterListener(IBridgeEventListener listener){
        if(null != listener){
        	Iterator<IBridgeEventListener> iterator =  eventLists.iterator();
        	while (iterator.hasNext()){
        		IBridgeEventListener eventListener = iterator.next();
        		if(eventListener.equals(listener)){
					iterator.remove();
					eventLists.remove(eventListener);
				}
        	}
        }
	}
	

	@Override
	public void handleMessage(Message msg) {
		try {
			if (null != msg) {
				Iterator<IBridgeEventListener> iterator = eventLists
						.iterator();
				while (iterator.hasNext()) {
					IBridgeEventListener listener = iterator.next();
					listener.onTaskDown(msg.what, msg.obj);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
