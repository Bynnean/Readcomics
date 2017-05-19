//package com.bhsoft.android.pay;
//
//import java.io.UnsupportedEncodingException;
//import java.net.URLEncoder;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.Locale;
//
//import com.alipay.android.app.sdk.AliPay;
//
//import android.app.Activity;
//import android.os.Handler;
//import android.os.Message;
//import android.util.Log;
//
//public class AliPayProxy {
//
//	private final  String  TAG  = AliPayProxy.class.getSimpleName();
//	
//	public  void  pay(final Activity activity ,String subject, String body,String price, final Handler mHandler ) {
////		final String orderInfo = getNewOrderInfo(subject,body,price);
////		// 这里根据签名方式对订单信息进行签名
////		String signType = getSignType();
////		String strsign = sign(signType, orderInfo);
//		String info = getNewOrderInfo(subject,body,price);
//		String sign = Rsa.sign(info, AliPayConstant.RSA_PRIVATE);
//		String signinfo = URLEncoder.encode(sign);
//	    String updateinfoString = info +"&sign=\"" + signinfo + "\"&" + getSignType();
//		Log.i("ExternalPartner", "start pay");
//		// start the pay.
//		Log.i(TAG, "info = " + updateinfoString);
//
//		final String orderInfo = updateinfoString;
//		// 对签名进行编码
////		try {
////			strsign = URLEncoder.encode(strsign, "UTF-8");
////		} catch (UnsupportedEncodingException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
////		// 组装好参数
////		String info = orderInfo + "&sign=" + "\"" + strsign + "\"" + "&"
////				+ getSignType();
//		// start the pay.
//		// 调用pay方法进行支付
//		new Thread() {
//			public void run() {
//				AliPay alipay = new AliPay(activity, mHandler);
//				
//				//设置为沙箱模式，不设置默认为线上环境
//				//alipay.setSandBox(true);
//				String result = "";
//                 try {
//                	 result = alipay.pay(orderInfo);
//				} catch (Exception e) {
//					// TODO: handle exception
//					if(null != e) e.printStackTrace();
//				}
//				
//
//				Log.i("pay", "result = " + result);
//				Message msg = new Message();
//				msg.what = 2;
//				msg.obj = result;
//				mHandler.sendMessage(msg);
//			}
//		}.start();
//
//	}
//	
//	
//	public String getOrderInfo(String subject, String body,String price) {
//		String strOrderInfo = "partner=" + "\"" + AliPayConstant.PARTNER + "\"";
//		strOrderInfo += "&";
//		strOrderInfo += "seller=" + "\"" + AliPayConstant.SELLER + "\"";
//		strOrderInfo += "&";
//		strOrderInfo += "out_trade_no=" + "\"" + getOutTradeNo() + "\"";
//		strOrderInfo += "&";
//		strOrderInfo += "subject=" + "\"" + subject
//				+ "\"";
//		strOrderInfo += "&";
//		strOrderInfo += "body=" + "\"" + body + "\"";
//		strOrderInfo += "&";
//		strOrderInfo += "total_fee=" + "\""
//				+ price + "\"";
////		strOrderInfo += "&";
////		strOrderInfo += "notify_url=" + "\""
////				+ "http://notify.java.jpxx.org/index.jsp" + "\"";
//        
//		return strOrderInfo;
//	}
//	
//	private String getNewOrderInfo(String subject, String body,String price) {
//		StringBuilder sb = new StringBuilder();
//		sb.append("partner=\"");
//		sb.append(AliPayConstant.PARTNER);
//		sb.append("\"&out_trade_no=\"");
//		sb.append(getOutTradeNo());
//		sb.append("\"&subject=\"");
//		sb.append(subject);
//		sb.append("\"&body=\"");
//		sb.append(body);
//		sb.append("\"&total_fee=\"");
//		sb.append(price);
//		sb.append("\"&notify_url=\"");
//
//		// 网址需要做URL编码
//		sb.append("");
//		sb.append("\"&service=\"mobile.securitypay.pay");
//		sb.append("\"&_input_charset=\"UTF-8");
//		sb.append("\"&return_url=\"");
//		sb.append(URLEncoder.encode("http://m.alipay.com"));
//		sb.append("\"&payment_type=\"1");
//		sb.append("\"&seller_id=\"");
//		sb.append(AliPayConstant.SELLER);
//
//		// 如果show_url值为空，可不传
//		// sb.append("\"&show_url=\"");
//		sb.append("\"&it_b_pay=\"1m");
//		sb.append("\"");
//
//		return new String(sb);
//	}
//
//	/**
//	 * get the out_trade_no for an order. 获取外部订单号
//	 * 
//	 * @return
//	 */
//	String getOutTradeNo() {
//		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss",
//				Locale.getDefault());
//		Date date = new Date();
//		String strKey = format.format(date);
//
//		java.util.Random r = new java.util.Random();
//		strKey = strKey + r.nextInt();
//		strKey = strKey.substring(0, 15);
//		return strKey;
//	}
//	
//	String sign(String signType, String content) {
//		return Rsa.sign(content, AliPayConstant.RSA_PRIVATE);
//	}
//
//	/**
//	 * get the sign type we use. 获取签名方式
//	 * 
//	 * @return
//	 */
//	String getSignType() {
//		String getSignType = "sign_type=" + "\"" + "RSA" + "\"";
//		return getSignType;
//	}
//}
