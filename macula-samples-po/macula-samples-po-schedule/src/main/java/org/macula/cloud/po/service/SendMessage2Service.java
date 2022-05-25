package org.macula.cloud.po.service;

public interface SendMessage2Service extends javax.xml.rpc.Service {

	String getSendMessage2ServiceAddress();

	SendMessage2Delegate getSendMessage2Service() throws javax.xml.rpc.ServiceException;

	SendMessage2Delegate getSendMessage2Service(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
