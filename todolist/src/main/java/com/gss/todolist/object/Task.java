package com.gss.todolist.objects;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;
import com.googlecode.objectify.Key;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import java.lang.String;
import java.util.Date;
import java.util.List;

/**
 * Object representation of a task
 **/
@Entity
public class Task {
	//Hierarchy info
	//@Parent Key<TaskList> mParentCategory;
	
	//User info
	@Index public String mUserId;

	//Task attributes
	@Id public Long mId;
	public String mTaskContent;
	@Index public int mPriority;
	
	/**
	* Base constructor that sets priority, and user info
	*/
	public Task(){
		mPriority = 1;
	}
  
	/**
	* Additionally sets content
	*/ 
	public Task(String content, String userId){
		this();
		mUserId = userId;
		mTaskContent = content;
	}
	
	/**
	* Additionally sets Priority
	*/ 
	public Task(String content, String userId, int priority){
		this(content, userId);
		mPriority = priority;
	}

}