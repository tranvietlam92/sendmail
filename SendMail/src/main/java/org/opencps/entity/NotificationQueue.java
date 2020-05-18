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
@Table(name="opencps_notificationqueue")
public class NotificationQueue implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
    @Column(name="notificationQueueId")
    private long notificationQueueId;
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
    @Column(name="className")
    private String className;
    @Column(name="classPK")
    private String classPK;
    @Column(name="payload")
    private String payload;
    @Column(name="fromUsername")
    private String fromUsername;
    @Column(name="toUsername")
    private String toUsername;
    @Column(name="toUserId")
    private long toUserId;
    @Column(name="toEmail")
    private String toEmail;
    @Column(name="toTelNo")
    private String toTelNo;
    @Column(name="publicationDate")
    private Date publicationDate;
    @Column(name="expireDate")
    private Date expireDate;
    public NotificationQueue() {
    }

	public long getNotificationQueueId() {
		return notificationQueueId;
	}
	public void setNotificationQueueId(long notificationQueueId) {
		this.notificationQueueId = notificationQueueId;
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
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getClassPK() {
		return classPK;
	}
	public void setClassPK(String classPK) {
		this.classPK = classPK;
	}
	public String getPayload() {
		return payload;
	}
	public void setPayload(String payload) {
		this.payload = payload;
	}
	public String getFromUsername() {
		return fromUsername;
	}
	public void setFromUsername(String fromUsername) {
		this.fromUsername = fromUsername;
	}
	public String getToUsername() {
		return toUsername;
	}
	public void setToUsername(String toUsername) {
		this.toUsername = toUsername;
	}
	public long getToUserId() {
		return toUserId;
	}
	public void setToUserId(long toUserId) {
		this.toUserId = toUserId;
	}
	public String getToEmail() {
		return toEmail;
	}
	public void setToEmail(String toEmail) {
		this.toEmail = toEmail;
	}
	public String getToTelNo() {
		return toTelNo;
	}
	public void setToTelNo(String toTelNo) {
		this.toTelNo = toTelNo;
	}
	public Date getPublicationDate() {
		return publicationDate;
	}
	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}
	public Date getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

}

