package com.gss.todolist.servlets.mail;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.googlecode.objectify.ObjectifyService;

import com.gss.todolist.objects.Task;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Servlet used to mail your to-do list to yourself
 */
public class MailAllTasks extends HttpServlet {

  // Process the http POST of the form
  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
	//Grab the users email
	UserService userService = UserServiceFactory.getUserService();
	User user = userService.getCurrentUser();
    
	//Grab each task
	List<Task> tasks = ObjectifyService.ofy().load().type(Task.class).order("-mPriority").filter("mUserId", user.getUserId()).list();
	String msgBody = "";
	
	for (Task t : tasks ) {
		msgBody += "Task: " + t.mTaskContent + " Priority: " + t.mPriority + "\n"; 
	}
	
	//Setup properties and send mail
	Properties props = new Properties();
	Session session = Session.getDefaultInstance(props, null);

	try {
		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress("yourList@todolist.com", "todolist emailer"));
		msg.addRecipient(Message.RecipientType.TO,
						 new InternetAddress(user.getEmail(), user.getNickname()));
		msg.setSubject("To-do list for " + user.getNickname());
		msg.setText(msgBody);
		Transport.send(msg);

		resp.setStatus(resp.SC_OK);
	} catch (AddressException e) {
		resp.sendError(resp.SC_FORBIDDEN);
	} catch (MessagingException e) {
		resp.sendError(resp.SC_FORBIDDEN);
	}
	
		
  }
}