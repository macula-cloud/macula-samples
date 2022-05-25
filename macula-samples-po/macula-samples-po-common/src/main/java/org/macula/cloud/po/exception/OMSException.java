package org.macula.cloud.po.exception;

import org.macula.cloud.api.exception.MaculaException;

public class OMSException extends MaculaException {

	private static final long serialVersionUID = 1949318937017425443L;

	public OMSException(String message) {
		super(message);
	}

	public OMSException(String message, Throwable cause) {
		super(message, cause);
	}

	public OMSException(String message, Object[] args) {
		super(message, args);
	}

	public OMSException(String message, Object[] args, Throwable cause) {
		super(message, args, cause);
	}

	@Override
	public String getParentCode() {
		return "macula-samples-oms";
	}

}
