package edu.hm.cs.fs.rest.data;

import java.util.Date;

public class Message {

	private final String id;
	private final String autor;
	private final String subject;
	private final String text;
	private final String scope;
	private final String url;
	
	private final Date publish;
	private final Date expires;
	
	public Message(String id, String autor, String subject, String text, String scope, String url, Date publish, Date expires) {
		this.id = id;
		this.autor = autor;
		this.subject = subject;
		this.text = text;
		this.scope = scope;
		this.url = url;
		this.publish = publish;
		this.expires = expires;
	}

	public String getId() {
		return id;
	}

	public String getAutor() {
		return autor;
	}

	public String getSubject() {
		return subject;
	}

	public String getText() {
		return text;
	}

	public String getScope() {
		return scope;
	}

	public String getUrl() {
		return url;
	}

	public Date getPublish() {
		return publish;
	}

	public Date getExpires() {
		return expires;
	}
	
}
