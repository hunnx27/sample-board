package com.twodollar.support.webutils;


public class BizRuntimeException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String[]         messageParams    = null;
    protected String           messageId        = null;
    
	public BizRuntimeException(String messageId) {
        super(messageId);
        this.messageId = messageId;
    }
	
	public BizRuntimeException(String messageId, String[] messageParams) {
		super(messageId);
		this.messageId = messageId;
		this.messageParams = messageParams;
    }
	
	public BizRuntimeException(String messageId, Throwable throwable) {
		super(messageId, throwable);
		this.messageId = messageId;
    }
	
	public BizRuntimeException(String messageId, String[] messageParams, Throwable throwable) {
		super(messageId, throwable);
		this.messageId = messageId;
		this.messageParams = messageParams;
    }
	
	public String getMessageId(){
		return this.messageId;
	}
	
	public String[] getMessageParams(){
		return this.messageParams;
	}
}
