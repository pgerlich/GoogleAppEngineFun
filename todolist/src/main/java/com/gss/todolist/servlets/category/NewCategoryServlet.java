package com.gss.todolist.servlets.category;

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

import com.gss.todolist.objects.TaskList;

/**
 * Servlet used to add new task categories for a user -- Currently not in use because datastore filtering was being funky.
 */
public class NewCategoryServlet extends HttpServlet {

  // Process the http POST of the form
  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    TaskList taskList;

	//Grab name for post request
    String name = req.getParameter("categoryName");
	String userId = req.getParameter("userId");
	
    taskList = new TaskList(name, userId);

    //Synchronously save data
    ObjectifyService.ofy().save().entity(taskList).now();

	//Redirect to homepage
    resp.sendRedirect("/home.jsp");
  }
}