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
 * Servlet used to update a task
 */
public class UpdateTaskServlet extends HttpServlet {

  // Process the http POST of the form
  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    Task task;
	
	try { 
		//Grab params
		long id = Long.valueOf(req.getParameter("id"));
		String newVal = req.getParameter("newVal");
		int newPriority = Integer.valueOf(req.getParameter("newPriority"));
		
		//Load and update object
		task = ObjectifyService.ofy().load().type(Task.class).filter("mId", id).first().now();

		task.mTaskContent = newVal;
		task.mPriority = newPriority;
		
		//Synchronously save data
		ObjectifyService.ofy().save().entity(task).now();
		
		resp.setStatus(resp.SC_OK);
	} catch ( NumberFormatException e ) {
		resp.sendError(resp.SC_FORBIDDEN);
	}

  }
}