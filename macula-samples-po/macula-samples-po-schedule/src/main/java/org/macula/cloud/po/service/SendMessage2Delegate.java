package org.macula.cloud.po.service;

public interface SendMessage2Delegate extends java.rmi.Remote {
	/**
	 * 发送短信
	 * 参1:短信接收方手机号码
	 * 参2:短信发送内容
	 * 参3:密码
	 * 参4:用户名
	 */
	String send(String mobile, String content, String password, String messageServiceid) throws java.rmi.RemoteException;
}
