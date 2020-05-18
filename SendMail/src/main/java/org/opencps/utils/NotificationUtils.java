package org.opencps.utils;

import java.util.Base64;
import java.util.Properties;
import java.util.logging.Logger;

import org.opencps.dto.MailMessageEntry;
import org.opencps.dto.MessageDataModel;
import org.opencps.dto.TemplateProcessor;
import org.opencps.entity.NotificationQueue;
import org.opencps.entity.Notificationtemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

public class NotificationUtils {

	private static Logger _log = Logger.getLogger(NotificationUtils.class.getName());

	public static MailMessageEntry createMessageEntry(
		NotificationQueue queue, Notificationtemplate template) {

		MailMessageEntry messageEntry = null;
		if (queue != null && template != null) {

			String emailSubjectTemplate = template.getEmailSubject();
			String emailBodyTemplate = template.getEmailBody();
			String textMessageTemplate = template.getTextMessage();
			String userUrlPatternTemplate = template.getUserUrlPattern();
			String guestUrlPatternTemplate = template.getGuestUrlPattern();
			 _log.info("emailSubjectTemplate: "+emailSubjectTemplate);
			 _log.info("emailBodyTemplate: "+emailBodyTemplate);
			 _log.info("textMessageTemplate: "+textMessageTemplate);
			 _log.info("userUrlPatternTemplate: "+userUrlPatternTemplate);
			 _log.info("guestUrlPatternTemplate: "+guestUrlPatternTemplate);

			//Get Config mail server
			Properties props = ConfigurationUtils.getInfoConfig();

			String baseUrl = props.getProperty("base.url");
			String guestBaseUrl = props.getProperty("base.url");

			String security = "";
			try {

				// Creating a Gson Object
				ObjectMapper mapper = new ObjectMapper();
				Object payload = mapper.readValue(queue.getPayload(), Object.class);


				MessageDataModel dataModel = new MessageDataModel();

				dataModel.setPayload(payload);
				dataModel.setClassName(queue.getClassName());
				dataModel.setClassPK(queue.getClassPK());
				dataModel.setCreateDate(queue.getCreateDate());
				dataModel.setExpireDate(queue.getExpireDate());
				
				if (!props.getProperty("name.admin.server").isEmpty()) {
					dataModel.setFromUsername(props.getProperty("name.admin.server"));
				} else {
					dataModel.setFromUsername(queue.getFromUsername());
				}
				dataModel.setGroupId(queue.getGroupId());
				dataModel.setModifiedDate(queue.getModifiedDate());
				dataModel.setNotificationType(queue.getNotificationType());
				dataModel.setPublicationDate(queue.getPublicationDate());
				dataModel.setToEmail(queue.getToEmail());
				dataModel.setToTelNo(queue.getToTelNo());
				dataModel.setToUserId(queue.getToUserId());
				dataModel.setToUsername(queue.getToUsername());
				dataModel.setUserId(queue.getUserId());
				dataModel.setBaseUrl(baseUrl);
				dataModel.setGuestBaseUrl(guestBaseUrl);
				dataModel.setSecurity(security);
				dataModel.setSubActiveUrl(ConstantsUtils.SUB_URL_ACTIVE);
				String token = "";
				if (!security.isEmpty()) {
					token = Base64.getEncoder().encodeToString(
						(queue.getToEmail() + ":" + security).getBytes());
				}
				dataModel.setToken(token);

				TemplateProcessor emailBodyTemplateProcessor =
					new TemplateProcessor(emailBodyTemplate);

				String emailBody =
					emailBodyTemplateProcessor.process(dataModel);
				// _log.info("emailBody: "+emailBody);

				TemplateProcessor emailSubjectTemplateProcessor =
					new TemplateProcessor(emailSubjectTemplate);

				String emailSubject =
					emailSubjectTemplateProcessor.process(dataModel);

				TemplateProcessor textMessageTemplateProcessor =
					new TemplateProcessor(textMessageTemplate);

				String textMessage =
					textMessageTemplateProcessor.process(dataModel);
				// _log.info("textMessage: "+textMessage);

				String userUrl = "";

				if (userUrlPatternTemplate != null && !userUrlPatternTemplate.isEmpty()) {
					TemplateProcessor userUrlPatternTemplateProcessor =
						new TemplateProcessor(userUrlPatternTemplate);
					userUrl =
						userUrlPatternTemplateProcessor.process(dataModel);
					// _log.info("userUrl: "+userUrl);
				}

				String guestUrl = "";

				if (guestUrlPatternTemplate != null && !guestUrlPatternTemplate.isEmpty()) {
					TemplateProcessor guestUrlPatternTemplateProcessor =
						new TemplateProcessor(guestUrlPatternTemplate);

					guestUrl =
						guestUrlPatternTemplateProcessor.process(dataModel);
					// _log.info("guestUrl: "+guestUrl);
				}

				messageEntry = new MailMessageEntry(queue.getFromUsername(), queue.getToUserId(), queue.getToEmail(),
						queue.getToUsername(), queue.getUserId(), queue.getGroupId(), queue.getCompanyId());

				messageEntry.setCreateDate(queue.getCreateDate());
				messageEntry.setEmailBody(emailBody);
				messageEntry.setEmailSubject(emailSubject);
				messageEntry.setTextMessage(textMessage);
				messageEntry.setClassName(queue.getClassName());
				messageEntry.setUserUrl(userUrl);
				messageEntry.setGuestUrl(guestUrl);
				messageEntry.setToTelNo(queue.getToTelNo());
				messageEntry.setNotifyMessage(template.getNotifyMessage());
				messageEntry.setData(queue.getPayload());


				boolean sendEmail = true;
				messageEntry.setSendEmail(sendEmail);

			}
			catch (Exception e) {
				System.out.println(e);
				//_log.debug(e);
			}
		}

		return messageEntry;
	}
}
