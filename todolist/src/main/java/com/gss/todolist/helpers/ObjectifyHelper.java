package com.gss.todolist.helpers;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

import javax.servlet.ServletContextListener;
import javax.servlet.ServletContextEvent;

import com.gss.todolist.objects.Task;
import com.gss.todolist.objects.TaskList;

/**
 * Objectify library helper. 
 **/
public class ObjectifyHelper implements ServletContextListener {
  public void contextInitialized(ServletContextEvent event) {
	//Registering classes for object mapping
    ObjectifyService.register(TaskList.class);
    ObjectifyService.register(Task.class);
  }

  public void contextDestroyed(ServletContextEvent event) {
    //TODO: Not currently used by app engine
  }
}