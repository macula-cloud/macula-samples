package org.macula.cloud.po.service.impl;

import javax.xml.namespace.QName;

import org.macula.cloud.po.service.SendMessage2Delegate;
import org.macula.cloud.po.service.SendMessage2Service;

@SuppressWarnings("rawtypes")
public class SendMessage2ServiceLocator extends org.apache.axis.client.Service implements SendMessage2Service {

	private static final long serialVersionUID = 1L;

	public SendMessage2ServiceLocator() {
	}

	public SendMessage2ServiceLocator(org.apache.axis.EngineConfiguration config) {
		super(config);
	}

	public SendMessage2ServiceLocator(String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
		super(wsdlLoc, sName);
	}

	// 配置uri
	private String SendMessage2Service_address = "http://newsms.javalabs.top/Message3WebService/SendMessage2Service";

	public String getSendMessage2ServiceAddress() {
		return SendMessage2Service_address;
	}

	// The WSDD service name defaults to the port name.
	private String SendMessage2ServiceWSDDServiceName = "SendMessage2Service";

	public String getSendMessage2ServiceWSDDServiceName() {
		return SendMessage2ServiceWSDDServiceName;
	}

	public void setSendMessage2ServiceWSDDServiceName(String name) {
		SendMessage2ServiceWSDDServiceName = name;
	}

	public SendMessage2Delegate getSendMessage2Service() throws javax.xml.rpc.ServiceException {
		java.net.URL endpoint;
		try {
			endpoint = new java.net.URL(SendMessage2Service_address);
		} catch (java.net.MalformedURLException e) {
			throw new javax.xml.rpc.ServiceException(e);
		}
		return getSendMessage2Service(endpoint);
	}

	public SendMessage2Delegate getSendMessage2Service(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
		try {
			SendMessage2ServiceBindingStub _stub = new SendMessage2ServiceBindingStub(portAddress, this);
			_stub.setPortName(getSendMessage2ServiceWSDDServiceName());
			return _stub;
		} catch (org.apache.axis.AxisFault e) {
			return null;
		}
	}

	public void setSendMessage2ServiceEndpointAddress(String address) {
		SendMessage2Service_address = address;
	}

	/**
	 * For the given interface, get the stub implementation.
	 * If this service has no port for the given interface,
	 * then ServiceException is thrown.
	 */
	public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
		try {
			if (SendMessage2Delegate.class.isAssignableFrom(serviceEndpointInterface)) {
				SendMessage2ServiceBindingStub _stub = new SendMessage2ServiceBindingStub(new java.net.URL(SendMessage2Service_address), this);
				_stub.setPortName(getSendMessage2ServiceWSDDServiceName());
				return _stub;
			}
		} catch (Throwable t) {
			throw new javax.xml.rpc.ServiceException(t);
		}
		throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  "
				+ (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
	}

	/**
	 * For the given interface, get the stub implementation.
	 * If this service has no port for the given interface,
	 * then ServiceException is thrown.
	 */
	public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
		if (portName == null) {
			return getPort(serviceEndpointInterface);
		}
		String inputPortName = portName.getLocalPart();
		if ("SendMessage2Service".equals(inputPortName)) {
			return getSendMessage2Service();
		} else {
			java.rmi.Remote _stub = getPort(serviceEndpointInterface);
			((org.apache.axis.client.Stub) _stub).setPortName(portName);
			return _stub;
		}
	}

	public javax.xml.namespace.QName getServiceName() {
		return new javax.xml.namespace.QName("http://webservices.xt.com/", "SendMessage2Service");
	}

	private java.util.HashSet<QName> ports = null;

	public java.util.Iterator getPorts() {
		if (ports == null) {
			ports = new java.util.HashSet<QName>();
			ports.add(new javax.xml.namespace.QName("http://webservices.xt.com/", "SendMessage2Service"));
		}
		return ports.iterator();
	}

	/**
	* Set the endpoint address for the specified port name.
	*/
	public void setEndpointAddress(String portName, String address) throws javax.xml.rpc.ServiceException {

		if ("SendMessage2Service".equals(portName)) {
			setSendMessage2ServiceEndpointAddress(address);
		} else { // Unknown Port Name
			throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
		}
	}

	/**
	* Set the endpoint address for the specified port name.
	*/
	public void setEndpointAddress(javax.xml.namespace.QName portName, String address) throws javax.xml.rpc.ServiceException {
		setEndpointAddress(portName.getLocalPart(), address);
	}

}
