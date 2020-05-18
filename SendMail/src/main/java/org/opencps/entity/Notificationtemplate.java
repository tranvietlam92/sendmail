package org.opencps.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import java.io.Serializable;
import java.util.Date;


@Getter
@Setter
@Entity

@Table(name = "opencps_notificationtemplate" )
public class Notificationtemplate implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
    @Column(name="notificationTemplateId")
    private long notificationTemplateId;
    @Column(name="groupId")
    private long groupId;
    @Column(name="companyId")
    private long companyId;
    @Column(name="userId")
    private long userId;
    @Column(name="userName")
    private String userName;
    @Column(name="createDate")
    private Date createDate;
    @Column(name="modifiedDate")
    private Date modifiedDate;
    @Column(name="notificationType")
    private String notificationType;
    @Column(name="emailSubject")
    private String emailSubject;
    @Column(name="emailBody")
    private String emailBody;
    @Column(name="textMessage")
    private String textMessage;
    @Column(name="notifyMessage")
    private String notifyMessage;
    @Column(name="sendSMS")
    private boolean sendSMS;
    @Column(name="sendEmail")
    private boolean sendEmail;
    @Column(name="sendNotification")
    private boolean sendNotification;
    @Column(name="expireDuration")
    private int expireDuration;
    @Column(name="userUrlPattern")
    private String userUrlPattern;
    @Column(name="guestUrlPattern")
    private String guestUrlPattern;
    @Column(name="interval_")
    private String interval;
    @Column(name="grouping")
    private boolean grouping;
    public Notificationtemplate() {
    }

	public long getNotificationTemplateId() {
		return notificationTemplateId;
	}
	public void setNotificationTemplateId(long notificationTemplateId) {
		this.notificationTemplateId = notificationTemplateId;
	}
	public long getGroupId() {
		return groupId;
	}
	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}
	public long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getNotificationType() {
		return notificationType;
	}
	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}
	public String getEmailSubject() {
		return emailSubject;
	}
	public void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
	}
	public String getEmailBody() {
		return emailBody;
	}
	public void setEmailBody(String emailBody) {
		this.emailBody = emailBody;
	}
	public String getTextMessage() {
		return textMessage;
	}
	public void setTextMessage(String textMessage) {
		this.textMessage = textMessage;
	}
	public String getNotifyMessage() {
		return notifyMessage;
	}
	public void setNotifyMessage(String notifyMessage) {
		this.notifyMessage = notifyMessage;
	}
	public boolean isSendSMS() {
		return sendSMS;
	}
	public void setSendSMS(boolean sendSMS) {
		this.sendSMS = sendSMS;
	}
	public boolean isSendEmail() {
		return sendEmail;
	}
	public void setSendEmail(boolean sendEmail) {
		this.sendEmail = sendEmail;
	}
	public boolean isSendNotification() {
		return sendNotification;
	}
	public void setSendNotification(boolean sendNotification) {
		this.sendNotification = sendNotification;
	}
	public int getExpireDuration() {
		return expireDuration;
	}
	public void setExpireDuration(int expireDuration) {
		this.expireDuration = expireDuration;
	}
	public String getUserUrlPattern() {
		return userUrlPattern;
	}
	public void setUserUrlPattern(String userUrlPattern) {
		this.userUrlPattern = userUrlPattern;
	}
	public String getGuestUrlPattern() {
		return guestUrlPattern;
	}
	public void setGuestUrlPattern(String guestUrlPattern) {
		this.guestUrlPattern = guestUrlPattern;
	}
	public String getInterval() {
		return interval;
	}
	public void setInterval(String interval) {
		this.interval = interval;
	}
	public boolean isGrouping() {
		return grouping;
	}
	public void setGrouping(boolean grouping) {
		this.grouping = grouping;
	}
}
