package org.opencps.scheduler;

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
import org.hibernate.query.Query;
import org.opencps.dto.MailMessageEntry;
import org.opencps.entity.NotificationQueue;
import org.opencps.entity.Notificationtemplate;
import org.opencps.utils.NotificationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@EnableScheduling
public class TESTEMAL
{

	@Autowired
	private SessionFactory sessionFactory;

	@RequestMapping(value = "/products")
	public void getProducts() throws Exception {
		//Session session = sessionFactory.openSession();
		//session.beginTransaction();
//		Class.forName("com.mysql.jdbc.Driver");  
//		Connection connect=DriverManager.getConnection(  
//		"jdbc:mysql://hanoi.fds.vn:3243/qa_bxd","qa_bxd_user","qa_bxd@2018!@#"); 

//		String HQL = "SELECT * FROM opencps_notificationqueue n WHERE n.expireDate < ? AND n.notificationType = ?";
		
//		PreparedStatement pstmtDetail = connect.prepareStatement(HQL);
//		pstmtDetail.setDate(1, new java.sql.Date(new Date().getTime()));
//		pstmtDetail.setString(2, "APPLICANT-01");
		// get data from table 'student'
//		ResultSet rsDetail = pstmtDetail.executeQuery();
		//Query query = session.createQuery(HQL).setMaxResults(500000);
		//query.setParameter("now",new java.sql.Date(new Date().getTime()));
		//List<NotificationQueue> notificationQueueList = query.getResultList();
//		while (rsDetail.next()) {
//			System.out.println(rsDetail.getInt(1)+"  "+rsDetail.getString(2)+"  "+rsDetail.getString(3));
//		}
		//
//		NotificationQueueDAO actions = new NotificationQueueDAOImpl();
//		List<NotificationQueue> aaList = actions.getQueueList();
//		System.out.println(aaList);

		Session session = sessionFactory.openSession();
		session.beginTransaction();

		String HQL1 = "FROM NotificationQueue n WHERE n.expireDate < :now AND n.notificationType =:notificationType";
		Query query = session.createQuery(HQL1).setMaxResults(1);
		query.setParameter("now",new java.sql.Date(new Date().getTime()));
		query.setParameter("notificationType", "APPLICANT-01");
		List<NotificationQueue> notificationQueueList = query.getResultList();

		for (NotificationQueue queue : notificationQueueList) {

			HQL1 = "FROM Notificationtemplate n WHERE n.interval=:interval AND n.groupId = :groupId AND n.notificationType = :notificationType ";
			query = session.createQuery(HQL1).setMaxResults(500);
			query.setParameter("interval", "minutely");
			query.setParameter("groupId", queue.getGroupId());
			query.setParameter("notificationType", "APPLICANT-01");
			Notificationtemplate notificationtemplate = (Notificationtemplate) query.uniqueResult();
			if (notificationtemplate != null) {
				
				MailMessageEntry messageEntry = NotificationUtils.createMessageEntry(queue, notificationtemplate);
				System.out.println("messageEntry: "+messageEntry);
				String subject = notificationtemplate.getEmailSubject();
				String text = notificationtemplate.getEmailBody();
				sendEmailHTML("tranvietlam92@gmail.com", messageEntry);
			}
		}

		if (session.isOpen()) {
			session.close();
		}

	}

	private static void sendEmailHTML(String to,MailMessageEntry messageEntry) throws MessagingException {
		javax.mail.Session mailSession = null;
		try {
			Properties props = System.getProperties();  
			props.setProperty("mail.smtp.host", "mail.mofa.gov.vn");
			props.setProperty("mail.smtp.auth", "true");
			props.setProperty("mail.smtp.port", "25");
			props.setProperty("mail.debug", "true");
			props.setProperty("mail.smtp.starttls.enable", "false");

			mailSession = javax.mail.Session.getDefaultInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("motcong", "Gb@20hn21");
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Message simpleMessage = new MimeMessage(mailSession);

		InternetAddress fromAddress = null;
		InternetAddress toAddress = null;
		try {
			System.out.println("messageEntry.getFrom().getAddress(): "+messageEntry.getFrom().getAddress());
			fromAddress = new InternetAddress(messageEntry.getFrom().getAddress());
			toAddress = new InternetAddress(to);
		} catch (AddressException e) {
			e.printStackTrace();
		}

		simpleMessage.setFrom(fromAddress);
		simpleMessage.setRecipient(RecipientType.TO, toAddress);
		simpleMessage.setSubject(messageEntry.getEmailSubject());
		simpleMessage.setSentDate(new Date());
		simpleMessage.setContent(messageEntry.getEmailBody(), "text/html; charset=UTF-8");

		Transport.send(simpleMessage);
		System.out.println("message sent successfully....");  
	}

}
