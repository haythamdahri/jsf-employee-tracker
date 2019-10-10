package com.hightech.manager;

import java.sql.Connection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class DBConfig {

	private static DBConfig instance;
	private DataSource dataSource;
	private static String jndiName = "java:comp/env/jdbc/employee_tracker";
	
	private DBConfig() throws Exception {
		this.dataSource = this.getDataSource();
	}
	
	private DataSource getDataSource() throws Exception {
		Context context = new InitialContext();
		DataSource dataSource = (DataSource) context.lookup(jndiName);
		return dataSource;
	}
	
	/*
	 * Method to get a singleton instance of DBConfig 
	 */
	public static DBConfig getInstance() throws Exception {
		// Check local instance
		if( instance == null ) {
			// Instantiate the local instance
			instance = new DBConfig();
		}
		// Return the local instance
		return instance;
	}
	
	public Connection getConnection() {
		Connection connection = null;
		try {
			connection =  this.dataSource.getConnection();
		}
		catch(Exception ex) {
			return null;
		}
		return connection;
	}
	
}
