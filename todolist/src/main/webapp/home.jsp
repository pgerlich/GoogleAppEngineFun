<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!-- Google/Standard Imports -->
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="com.googlecode.objectify.Key" %>
<%@ page import="com.googlecode.objectify.ObjectifyService" %>
<%@ page import="java.util.List" %>

<!-- Custom imports -->
<%@ page import="com.gss.todolist.objects.TaskList" %>
<%@ page import="com.gss.todolist.objects.Task" %>


<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
<!-- Bootstrap -->	
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" integrity="sha512-dTfge/zgoMYpP7QbHy4gWMEGsbsdZeCXz7irItjcC3sPUFtf0kuFbDz/ixG7ArTxmDjLXDmezHubeNikyKGVyQ==" crossorigin="anonymous">

<!-- JQuery -->
<link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/themes/smoothness/jquery-ui.css">
<title>To-Do List</title>	
</head>

<body>
<%
	//Setting login and debugging up
	UserService userService = UserServiceFactory.getUserService();
	User user = userService.getCurrentUser();
	
	//For displaying some stuff to figure out why filtering won't work!!
	boolean debugging = false;
%>

<!-- Static navbar -->
    <nav class="navbar navbar-default navbar-static-top">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <h4><b><a class="navbar-brand" href="#">My To-Do List</a></b></h4>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav navbar-right">
            <li>
				<% if ( user != null ) { 
					pageContext.setAttribute("user", user);
					pageContext.setAttribute("userId", user.getUserId());
				%>
				
				<p style="font-size:18px;">Welcome, ${fn:escapeXml(user.nickname)}! (<a href="<%= userService.createLogoutURL(request.getRequestURI()) %>">sign out</a>)</p><% } %>
			</li>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </nav>

<div style="width:800px;margin:auto">
<!-- Begin force login section -->		
<%	
	//Force sign in for use of the service
	if ( user == null ) {
%>
	<p>You are not signed in. You must <a href="<%= userService.createLoginURL(request.getRequestURI()) %>">Sign in</a> to continue</p>
<%
	} else {
%>

<!-- End login display section -->
<%
	//Grab each task category for this user
	List<Task> tasks = ObjectifyService.ofy()
	.load()
	.type(Task.class)
	.order("-mPriority")
	.filter("mUserId", user.getUserId())
	.list();
%>
		
<!-- Begin Tasks Display -->
<table class="table table-hover">
	<tr>
		<% if ( debugging ) { %><th>User Id (Debugging)</th><% } %>
		<th>Task</th>
		<th>Priority</th>
		<th>Complete</th>
	</tr>

<%
		for (Task t : tasks) {
			pageContext.setAttribute("taskId", t.mId);
			pageContext.setAttribute("taskContent", t.mTaskContent);
			pageContext.setAttribute("taskPriority", t.mPriority);
			pageContext.setAttribute("tUserId", t.mUserId); //For testing purposes
%>
	<!-- Display each task -->
	<tr>
		<% if ( debugging ) { %><td>${fn:escapeXml(tUserId)}</td><% } %>
		<td><a href="#" onClick="showUpdateTaskDialog(${fn:escapeXml(taskId)},'${fn:escapeXml(taskContent)}', ${fn:escapeXml(taskPriority)})">${fn:escapeXml(taskContent)}</a></td>
		<td>${fn:escapeXml(taskPriority)}</td>
		<td><a href="#" onClick="completeTask(${fn:escapeXml(taskId)})">Complete Task</a></td>
	</tr>
	
<%
		}
%>

	<!-- Add Task Portion -->
	<tr>
		<% if ( debugging ) { %><td></td><% } %>
		<td><input type="text" id="content" name="content" value="Do Paul's Laundry? :D" /></td>
		<td>
			<select id="priority" name="priority">
				<option value="1">1</option>
				<option value="2">2</option>
				<option value="3">3</option>
			</select>
		</td>
		<td><button onClick="addTask()">Add Task</button></td>
	</tr>
	
	<!-- Mail Tasks Portion -->
	<tr>
		<td colspan="3">
			<p style="text-align:center"><a href="#" onClick="mailTasks()">E-Mail me my task list</a></a>
		</td>
	</tr>
	
<%
	}
%>

</table>
</div>

<!-- Update Task Dialog -->
<div id="update-task-dialog" title="Update Task">
	<label for="updatedContent">Task</label><br />
	<input type="text" name="updatedContent" id="updatedContent" value="Do Paul's Laundry" class="text ui-widget-content ui-corner-all">
  
	<label for="updatedPriority">Priority</label><br />
	<select id="updatedPriority" name="updatedPriority"> <!-- TODO: Way to not have to repeate this select twice? -->
		<option value="1">1</option>
		<option value="2">2</option>
		<option value="3">3</option>
	</select>
	
	<input type="hidden" name="updatedId" id="updatedId" value="" />
</div>
<!-- JQuery -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>

<!-- Bootstrap -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js" integrity="sha512-K1qjQ+NcF2TYO/eI3M6v8EiNYZfA95pQumfvcVrTHtwQVDG+aHRqLi/ETn2uB+1JqwYqVG3LIvdm9lj6imS/pQ==" crossorigin="anonymous"></script>

<!-- Custom Scripts -->
<script src="js/main.js"></script>
</body>
</html>
