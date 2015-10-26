package com.gss.todolist.servlets.task;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.googlecode.objectify.ObjectifyService;

import com.gss.todolist.objects.Task;

/**
 * Servlet used to add a new task 
 */
public class NewTaskServlet extends HttpServlet {

  // Process the http POST of the form
  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    Task task;

	//Grab user info
    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser(); 
	
	String content = req.getParameter("content");
	int priority = Integer.valueOf(req.getParameter("priority"));
	
    task = new Task(content, user.getUserId(), priority);

    //Synchronously save data
    ObjectifyService.ofy().save().entity(task).now();
	
	resp.setStatus(resp.SC_OK);
  }
}