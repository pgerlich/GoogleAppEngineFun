package com.gss.todolist.objects;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.Key;

/**
 * Object representing a list (i.e, school, work, etc.)
 */
@Entity
public class TaskList {
	//AI ID
	@Id public Long mId;
	
	//Name of post
	public String mName;

	//User that this category is associated to
	@Index public String mUserId;
	
	//General Constructor (Isn't used)
	public TaskList(){
	}
	
	public TaskList(String name, String userId){
		mName = name;
		mUserId = userId;
	}
  
}