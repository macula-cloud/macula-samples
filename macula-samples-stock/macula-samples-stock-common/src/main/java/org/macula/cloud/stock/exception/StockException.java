package org.macula.cloud.stock.exception;

import org.macula.cloud.api.exception.MaculaException;

public class StockException extends MaculaException {

	private static final long serialVersionUID = 1949318937017425443L;

	public StockException(String message) {
		super(message);
	}

	public StockException(String message, Throwable cause) {
		super(message, cause);
	}

	public StockException(String message, Object[] args) {
		super(message, args);
	}

	public StockException(String message, Object[] args, Throwable cause) {
		super(message, args, cause);
	}

	@Override
	public String getParentCode() {
		return "macula-samples-stock";
	}

}
