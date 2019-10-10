package com.hightech.manager;

import java.io.PrintWriter;
import java.sql.Connection;

import javax.annotation.Resource;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@WebServlet("/dbconnect")
public class DatabaseConnectionChecker extends HttpServlet {

	// Inject DataSource instance
	@Resource(name = "jdbc/employe_tracker")
	private DataSource dataSource;
	
	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) {
		Connection connection = null;
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			response.setContentType("text/plain");
			connection = this.dataSource.getConnection();
			if( connection != null ) {
				writer.println("Database connection has been established successfully!");
			} else {
				throw new Exception("An error occurred, error establishing database connection!");
			}
			connection.close();
			writer.close();
		}
		catch(Exception ex) {
			writer.println(ex.getMessage());
		}
	}
	
	
}
