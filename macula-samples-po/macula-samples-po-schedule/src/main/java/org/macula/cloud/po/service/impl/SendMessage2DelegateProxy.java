package org.macula.cloud.po.service.impl;

import org.macula.cloud.po.service.SendMessage2Delegate;
import org.springframework.stereotype.Component;

@Component
public class SendMessage2DelegateProxy implements SendMessage2Delegate {
	private String _endpoint = null;

	private SendMessage2Delegate sendMessage2Delegate = null;

	public SendMessage2DelegateProxy() {
		_initSendMessage2DelegateProxy();
	}

	public SendMessage2DelegateProxy(String endpoint) {
		_endpoint = endpoint;
		_initSendMessage2DelegateProxy();
	}

	private void _initSendMessage2DelegateProxy() {
		try {
			sendMessage2Delegate = (new SendMessage2ServiceLocator()).getSendMessage2Service();
			if (sendMessage2Delegate != null) {
				if (_endpoint != null)
					((javax.xml.rpc.Stub) sendMessage2Delegate)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
				else
					_endpoint = (String) ((javax.xml.rpc.Stub) sendMessage2Delegate)._getProperty("javax.xml.rpc.service.endpoint.address");
			}

		} catch (javax.xml.rpc.ServiceException serviceException) {
		}
	}

	public String send(String arg0, String arg1, String arg2, String arg3) throws java.rmi.RemoteException {
		if (sendMessage2Delegate == null) {
			_initSendMessage2DelegateProxy();
		}
		return sendMessage2Delegate.send(arg0, arg1, arg2, arg3);
	}

}