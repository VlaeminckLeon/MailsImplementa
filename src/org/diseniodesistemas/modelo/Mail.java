package org.diseniodesistemas.modelo;

public class Mail {

	

	public Mail(String to, String co, String bcc,
			String subject, String text) {

		super();
		this.to = to;
		this.co = co;
		this.bcc = bcc;
		this.subject = subject;
		this.text = text;	
	}
	
	/**
	 * 
	 * @param from
	 * @param to
	 * @param co
	 * @param bcc
	 * @param subject
	 * @param text
	 */
	public Mail(String from, String to, String co, String bcc,
			String subject, String text) {

		super();
		this.from = from;		
		this.to = to;
		this.co = co;
		this.bcc = bcc;
		this.subject = subject;
		this.text = text;	
	}


	@Override
	public String toString() {
			
		return "Mail [subject=" + subject + ", to=" + to + ", co=" + co
				+ ", coo=" + bcc + ", body=" + text + "]";
	}

	private String subject;
	
	private String from;
	
	private String to;
	
	private String co;
	
	private String bcc;
	
	private String text;

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}	
	
	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getCo() {
		return co;
	}

	public void setCo(String co) {
		this.co = co;
	}

	public String geBcc() {
		return bcc;
	}

	public void setBcc(String bcc) {
		this.bcc = bcc;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
}
