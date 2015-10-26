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

import com.googlecode.objectify.NotFoundException;
import com.googlecode.objectify.ObjectifyService;

import com.gss.todolist.objects.Task;

/**
 * Servlet used to delete / "complete" a task
 */
public class CompleteTaskServlet extends HttpServlet {

  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
	
	try {
		long id = Long.valueOf(req.getParameter("id"));
		
		ObjectifyService.ofy().delete().type(Task.class).id(id);
		
		resp.setStatus(resp.SC_OK);
	} catch ( NotFoundException e) {
		resp.sendError(resp.SC_FORBIDDEN);
	} catch ( NumberFormatException e ) {
		resp.sendError(resp.SC_FORBIDDEN);
	}

  }
}