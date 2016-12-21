package com.xss.web.util;

import java.security.Security;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.stereotype.Service;

import com.xss.web.cache.EmailCache;
import com.xss.web.model.Email;
@Service
public class EmailSenderUtil {
	@Resource
	EmailCache emailCache;
	
	public  Boolean send(List<Email> emails, String title, String txt,
			String targeEmail) {
		for (Email email:emails) {
			if(email.getActivite()==0){
				continue;
			}
			if (sendEmail(email.getSmtp(), email.getEmail(),
					email.getPassword(), title, txt, targeEmail)) {
				return true;
			}
		}
		return false;
	}

	private  boolean sendEmail(String smtp, String email,
			String password, String title, String txt, String targeEmail) {
		Properties props = new Properties();
		// 设置发送邮件的邮件服务器的属性（这里使用网易的smtp服务器）
		props.put("mail.smtp.host", smtp);
		// 需要经过授权，也就是有户名和密码的校验，这样才能通过验证（一定要有这一条）
		props.put("mail.smtp.auth", "true");

		props.put("mail.smtp.socketFactory.fallback", "false");
		props.put("mail.smtp.starttls.enable", "true");
		// 用刚刚设置好的props对象构建一个session
		Session session = Session.getDefaultInstance(props);
		// 有了这句便可以在发送邮件的过程中在console处显示过程信息，供调试使
		// 用（你可以在控制台（console)上看到发送邮件的过程）
		session.setDebug(true);
		// 用session为参数定义消息对象
		MimeMessage message = new MimeMessage(session);
		try {
			// 加载发件人地址
			message.setFrom(new InternetAddress(email));
			// 加载收件人地址
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					targeEmail));
			// 加载标题
			message.setSubject(title);
			// 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
			Multipart multipart = new MimeMultipart();

			// 设置邮件的文本内容
			BodyPart contentPart = new MimeBodyPart();
			contentPart.setText(txt);
			multipart.addBodyPart(contentPart);
			// 将multipart对象放到message中
			message.setContent(multipart);
			// 保存邮件
			message.saveChanges();
			// 发送邮件
			Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
			Transport transport = session.getTransport("smtp");
			// 连接服务器的邮箱
			transport.connect(smtp, email, password);
			// 把邮件发送出去
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}
}
