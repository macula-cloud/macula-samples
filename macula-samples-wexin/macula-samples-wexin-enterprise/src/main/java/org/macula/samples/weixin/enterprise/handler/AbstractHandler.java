package org.macula.samples.weixin.enterprise.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.chanjar.weixin.cp.message.WxCpMessageHandler;

/**
 * 
 */
public abstract class AbstractHandler implements WxCpMessageHandler {
  protected Logger logger = LoggerFactory.getLogger(getClass());
}
