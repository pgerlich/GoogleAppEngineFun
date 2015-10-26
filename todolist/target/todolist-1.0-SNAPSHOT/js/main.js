//Register update dialog
$(function() {
	$( "#update-task-dialog" ).dialog({
	   autoOpen: false, 
		buttons: {
        "Update Task": function() {
			//Send a post request with id, new value, and new priority to update this task.
			var id = $("#updatedId").val();
			var newVal = $("#updatedContent").val();
			var newPriorty = $("#updatedPriority").val();
			$.post( "/updatetask", { id: id, newVal: newVal, newPriority: newPriorty })
			  .done(function( data ) {
				alert("Task Updated!");
				location.reload();
			  });
			$( this ).dialog( "close" );
        },
		"Cancel": function () {
		  $( this ).dialog( "close" );
		}}		
	});
});

//Function for adding a new task
function addTask(){
	var content = $("#content").val().replace(/[&\/\\#,+()$~%.'":*?<>{}]/g, '');
	var priority = $("#priority").val();
	
	$.post( "/newtask", { content: content, priority: priority })
	  .done(function( data ) {
		alert("Task added!");
		location.reload();
	  });
}

//Function for completing a task
function completeTask(id){
	$.post( "/completetask", { id: id })
	  .done(function( data ) {
		alert("Task Completed!");
		location.reload();
	  });	
}

//Function for calling the update dialog and pre-setting the values
function showUpdateTaskDialog(id, updatedContent, updatedPriority){
	//Set the dialog up
	$("#updatedId").val(id);
	$("#updatedContent").val(updatedContent);
	$("#updatedPriority").val(updatedPriority);

	//Show the dialog for remaining interaction
	$( "#update-task-dialog" ).dialog( "open" );	
}

//Function for completing a task
function mailTasks(){
	$.post( "/mailtasks")
	  .done(function( data ) {
		alert("Tasks sent!");
	  });	
}
