package com.amar.natural.util;

public class EmailData {
	
	private String from;
    String password;
    String to;
    String subject;
    String content;
    
    boolean isMultipleRec;
    String[] toMulti;
    
    boolean isAttachment;
    String filePath;
    
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public boolean isMultipleRec() {
		return isMultipleRec;
	}
	public void setIsMultipleRec(boolean isMultipleRec) {
		this.isMultipleRec = isMultipleRec;
	}
	public String[] getToMulti() {
		return toMulti;
	}
	public void setToMulti(String[] toMulti) {
		this.toMulti = toMulti;
	}
	public boolean isAttachment() {
		return isAttachment;
	}
	public void setAttachment(boolean isAttachment) {
		this.isAttachment = isAttachment;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}
