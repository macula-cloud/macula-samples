package org.macula.cloud.po.gbss.exception;

import org.macula.cloud.api.exception.MaculaException;

/**
 * <p>
 * <b>BizException</b> 
 * </p>
 *
 
 
 
 *
 */
public class BizException extends MaculaException {

	public BizException(String message) {
		super(message);
	}

	public BizException(String message, Throwable cause) {
		super(message, cause);
	}

	public BizException(String message, Object[] args) {
		super(message, args);
	}

	public BizException(String message, Object[] args, Throwable cause) {
		super(message, args, cause);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see org.macula.exception.MaculaException#getParentCode()
	 */
	@Override
	public String getParentCode() {
		return "gbss-trade";
	}
}
