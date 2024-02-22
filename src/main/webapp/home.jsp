<%@page import="dto.TodoTask"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Home</title>
<style>
body{
  	  background-image: url("https://img.freepik.com/free-vector/vibrant-fluid-gradient-background-with-curvy-shapes_1017-32108.jpg?size=626&ext=jpg&ga=GA1.1.1700460183.1708214400&semt=ais");
      background-repeat: no-repeat;
      background-size: cover;
  		}
div {
	display: flex;
	flex-direction: column;
	align-items: center;
	justify-content: center;
}

.extra {
	margin-top: 20px;
	margin-left: 305px;
	position: relative;
	left: 7%;
}
th{
    background-color: black;
    color: red;
}
td{
background-color: gray

}
</style>
</head>
<body>
	<%
	List<TodoTask> tasks = (List<TodoTask>) request.getAttribute("tasks");
	%>
	<h1 align="center">This is Home Page</h1>
	<%
	if (!tasks.isEmpty()) {
	%>


	<div>
		<table border="1">
			<tr>
				<th>Task Name</th>
				<th>Task Description</th>
				<th>Created Time</th>
				<th>Status</th>
				<th>Delete</th>
				<th>Edit</th>
			</tr>
			<%
			for (TodoTask task : tasks) {
			%>
			<tr>
				<td><%=task.getName()%></td>
				<td><%=task.getDescription()%></td>
				<td><%=task.getCreatedTime()%></td>
				<td>
					<%
					if (task.isStatus()) {
					%> Completed <%
					} else {
					%>
					<a href="complete?id=<%=task.getId()%>"><button>Complete</button></a> <%
 }
 %>
				</td>
				<td><a href="delete?id=<%=task.getId()%>"><button>Delete</button></a></td>
				<td><a href="edit-task.jsp?id=<%=task.getId()%>"><button>Edit</button></a></td>
			</tr>
			<%
			}
			%>
		</table>
	</div>
	<%
	}
	%>
	<a href="add-task.html"><button class="extra">Add Task</button></a>
	<a href="logout"><button class="extra">Logout</button></a>

</body>
</html>
