package org.opencps.scheduler;

import java.io.FileInputStream;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.opencps.dto.MailMessageEntry;
import org.opencps.entity.NotificationQueue;
import org.opencps.entity.Notificationtemplate;
import org.opencps.utils.ConstantsUtils;
import org.opencps.utils.NotificationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import lombok.extern.log4j.Log4j2;

@Configuration
@EnableScheduling
@Log4j2
public class SchedulingEmail {

	@Autowired
	private SessionFactory sessionFactory;

	java.sql.Date now = new java.sql.Date(new Date().getTime());
	private volatile boolean isRunning = false;
	//private static final String CONFIG_FILE = "configMail.properties";

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Scheduled(fixedRate = 30000) 
	public void actionScheduler() {
		if (!isRunning) {
			isRunning = true;
		}
		else {
			return;
		}

		Session session = null;
		try {
			session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();

			String HQL = "FROM NotificationQueue n WHERE n.expireDate = n.modifiedDate AND n.modifiedDate > n.createDate";
			Query query = session.createQuery(HQL).setMaxResults(5);
			List<NotificationQueue> notificationQueueList = query.getResultList();

			for (NotificationQueue queue : notificationQueueList) {

				System.out.println("queueId: "+queue.getNotificationQueueId());
				HQL = "FROM Notificationtemplate n WHERE n.interval=:interval AND n.groupId = :groupId AND n.notificationType = :notificationType ";
				query = session.createQuery(HQL);
				query.setParameter("interval", "minutely");
				query.setParameter("groupId", queue.getGroupId());
				query.setParameter("notificationType", queue.getNotificationType());
				Notificationtemplate notificationtemplate = (Notificationtemplate) query.uniqueResult();
				if (notificationtemplate != null) {
					
					MailMessageEntry messageEntry = NotificationUtils.createMessageEntry(queue, notificationtemplate);
					//System.out.println("messageEntry: "+messageEntry);
					//String subject = notificationtemplate.getEmailSubject();
					//System.out.println("subject: "+subject);
					//String text = notificationtemplate.getEmailBody();
					//System.out.println("text: "+text);
					System.out.println("queueId: "+queue.getNotificationQueueId());
					try {
						sendEmailHTML(messageEntry);
					} catch (MessagingException e) {
					}
					// Delete queue
					Query queryDel = session.createQuery("DELETE NotificationQueue n where n.notificationQueueId = :notificationQueueId");
					queryDel.setParameter("notificationQueueId", queue.getNotificationQueueId());
					int result = queryDel.executeUpdate();
					//tx.commit();
					System.out.println("result: "+result);
				}
			}
			tx.commit();
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		isRunning = false;
	}

	private static void sendEmailHTML(MailMessageEntry messageEntry) throws MessagingException {
		javax.mail.Session mailSession = null;
		try {
			//InputStream stream = SchedulingEmail.class.getClassLoader().getResourceAsStream(CONFIG_FILE);
			FileInputStream stream = new FileInputStream(ConstantsUtils.PROPERETIES_CONFIG_PATH);
			Properties props = System.getProperties();
			props.load(stream);

			mailSession = javax.mail.Session.getDefaultInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(props.getProperty("mail.user"), props.getProperty("mail.password"));
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Message simpleMessage = new MimeMessage(mailSession);

		InternetAddress fromAddress = null;
		try {
			System.out.println("messageEntry.getFrom().getAddress(): "+messageEntry.getFrom().getAddress());
			fromAddress = new InternetAddress(messageEntry.getFrom().getAddress());
		} catch (AddressException e) {
			e.printStackTrace();
		}

		simpleMessage.setFrom(fromAddress);
		simpleMessage.setRecipients(RecipientType.TO, messageEntry.getToAddress());
//		simpleMessage.setRecipient(RecipientType.TO, new InternetAddress("lamtv19@yopmail.com"));
		simpleMessage.setSubject(messageEntry.getEmailSubject());
		simpleMessage.setSentDate(new Date());
		simpleMessage.setContent(messageEntry.getEmailBody(), "text/html; charset=UTF-8");

		Transport.send(simpleMessage);
		System.out.println("message sent successfully....");  
	}

}
