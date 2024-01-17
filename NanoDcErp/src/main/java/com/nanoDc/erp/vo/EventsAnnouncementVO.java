package com.nanoDc.erp.vo;

import java.util.Date;

import org.springframework.stereotype.Repository;

@Repository(value="EventsAnnouncementVO")
public class EventsAnnouncementVO {
	
	private long id;
	private Date reg_date;
	private String title;
	private String body;
	private String admin;
	private String event_or_announcement;
	private Date read_date;
	
	
	public String getEvent_or_announcement() {
		return event_or_announcement;
	}
	public void setEvent_or_announcement(String event_or_announcement) {
		this.event_or_announcement = event_or_announcement;
	}
	public Date getRead_date() {
		return read_date;
	}
	public void setRead_date(Date read_date) {
		this.read_date = read_date;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getReg_date() {
		return reg_date;
	}
	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getAdmin() {
		return admin;
	}
	public void setAdmin(String admin) {
		this.admin = admin;
	}
}
