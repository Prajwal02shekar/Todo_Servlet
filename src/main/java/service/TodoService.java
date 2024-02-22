package service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.TodoDao;
import dto.TodoTask;
import dto.TodoUser;
import lombok.Data;

public class TodoService {
	TodoDao dao = new TodoDao();

	public void signup(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		TodoUser user = new TodoUser();
		user.setDob(LocalDate.parse(req.getParameter("dob")));
		user.setEmail(req.getParameter("email"));
		user.setGender(req.getParameter("gender"));
		user.setMobile(Long.parseLong(req.getParameter("mobile")));
		user.setName(req.getParameter("name"));
		user.setPassword(req.getParameter("password"));

		List<TodoUser> list = dao.findByEmail(user.getEmail());
		
		

		if (list.isEmpty()) {
			dao.saveUser(user);
			resp.getWriter().print("<h1  style='color:green'>Account Created Success</h1>");
			req.getRequestDispatcher("login.html").include(req, resp);
		} else {
			resp.getWriter().print("<h1  style='color:red' >Email Should be uique</h1>");
			req.getRequestDispatcher("signup.html").include(req, resp);
		}

	}

	public void login(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String email = req.getParameter("name");
		String password = req.getParameter("password");
		List<TodoUser> list = dao.findByEmail(email);
		if (list.isEmpty()) {
			resp.getWriter().print("<h1 style='color:red'>Incorrect Email</h1>");
			req.getRequestDispatcher("login.html").include(req, resp);
		} else {
			TodoUser user = list.get(0);
			if (user.getPassword().equals(password)) {
				req.getSession().setAttribute("user", user);
				
				resp.getWriter().print("<h1  style='color:green'>Login Successfull</h1>");
				
				List<TodoTask> tasks=dao.fetchTaskByUser(user.getId());
				req.setAttribute("tasks", tasks);
				
				req.getRequestDispatcher("home.jsp").include(req, resp);
			} else {
				resp.getWriter().print("<h1  style='color:red'>Incorrect Passwordl</h1>");
				req.getRequestDispatcher("login.html").include(req, resp);
			}
		}
	}

	public void addTask(HttpServletRequest req, HttpServletResponse resp) throws  IOException, ServletException {
		String tname=req.getParameter("name");
		String tdescription=req.getParameter("descript");
		
		
		TodoTask task=new TodoTask();
		task.setName(tname);
		task.setDescription(tdescription);
		task.setStatus(false);
		task.setCreatedTime(LocalDateTime.now());
		
		TodoUser user=(TodoUser) req.getSession().getAttribute("user");
		task.setUser(user);
		
		dao.saveTask(task);
		
		resp.getWriter().print("<h1  style='color:green'>Task Saved Successfully</h1>");
		
		List<TodoTask> tasks=dao.fetchTaskByUser(user.getId());
		req.setAttribute("tasks", tasks);
		
		req.getRequestDispatcher("home.jsp").include(req, resp);
		
	}
	
	
	public void complete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id=Integer.parseInt(req.getParameter("id"));
		TodoTask task=dao.findById(id);
		task.setStatus(true);
		dao.updateTask(task);
		
		resp.getWriter().print("<h1 align='center' style='color:green'>Status Changed Success</h1>");
		TodoUser user=(TodoUser) req.getSession().getAttribute("user");
		List<TodoTask> tasks=dao.fetchTaskByUser(user.getId());
		req.setAttribute("tasks",tasks);
		
		req.getRequestDispatcher("home.jsp").include(req, resp);
		
	}
	
	public void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id=Integer.parseInt(req.getParameter("id"));
		TodoTask task=dao.findById(id);
		
		dao.deleteTask(task);
		
		resp.getWriter().print("<h1 align='center' style='color:green'>Task Deleted Success</h1>");
		TodoUser user=(TodoUser) req.getSession().getAttribute("user");
		List<TodoTask> tasks=dao.fetchTaskByUser(user.getId());
		req.setAttribute("tasks",tasks);
		
		req.getRequestDispatcher("home.jsp").include(req, resp);
	}

	public void updateTask(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String tname=req.getParameter("tname");
		String tdescription=req.getParameter("tdescription");
		int id=Integer.parseInt(req.getParameter("id"));
		TodoUser user=(TodoUser) req.getSession().getAttribute("user");
		
		TodoTask task=new TodoTask();
		task.setId(id);
		task.setName(tname);
		task.setDescription(tdescription);
		task.setCreatedTime(LocalDateTime.now());
		task.setStatus(false);
		task.setUser(user);
		
		dao.updateTask(task);
		
		
		List<TodoTask> tasks=dao.fetchTaskByUser(user.getId());
		req.setAttribute("tasks",tasks);
		resp.getWriter().print("<h1 align='center' style='color:green'>Task Updated Success</h1>");
		
		req.getRequestDispatcher("home.jsp").include(req, resp);
	}
}
	


