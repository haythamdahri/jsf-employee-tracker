package com.hightech.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

public class DepartmentDbUtil {

	private static DepartmentDbUtil instance;
	private static DBConfig dbConfig;
	private static Connection connection;
	private static String sql = "";
	private static ResultSet resultSet;
	private static Statement statement;
	private static PreparedStatement preparedStatement;

	private DepartmentDbUtil() throws Exception {
		dbConfig = DBConfig.getInstance();
	}

	/*
	 * Method to get a singleton instance of departmentDbUtil
	 */
	public static DepartmentDbUtil getInstance() throws Exception {
		// Check local instance
		if (instance == null) {
			// Instantiate the local instance
			instance = new DepartmentDbUtil();
		}
		// Return the local instance
		return instance;
	}

	/*
	 * Get departments list
	 */
	public Collection<Department> getDepartments() throws Exception {
		Collection<Department> departments = new ArrayList<>();
		try {
			connection = dbConfig.getConnection();
			sql = "select *from department;";
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				departments.add(new Department(resultSet.getLong(1), resultSet.getString(2)));
			}
			return departments;
		} finally {
			close();
		}
	}
	
	/*
	 * Get a specific department
	 */
	public Department getDepartment(Long departmentId) throws Exception {
		Department department = null;
		try {
			connection = dbConfig.getConnection();
			sql = "select *from department where id = ?;";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, departmentId);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				department = new Department(resultSet.getLong(1), resultSet.getString(2));
			}
			return department;
		} finally {
			close();
		}
	}
	
	/*
	 * Add a new employee method
	 */
	public void addDepartment(Department department) throws Exception {
		try {
			connection = dbConfig.getConnection();
			sql = "insert into department(name) values (?);";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, department.getName());
			preparedStatement.executeUpdate();
		} finally {
			close();
		}
	}
	
	/*
	 * Update an existing department method
	 */
	public void updateDepartment(Department department) throws Exception {
		try {
			connection = dbConfig.getConnection();
			sql = "update department set name = ? where id = ?;";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, department.getName());
			preparedStatement.setLong(2, department.getId());
			preparedStatement.executeUpdate();
		} finally {
			close();
		}
	}
	
	/*
	 * Update an existing department method
	 */
	public void deleteDepartment(Long departmentId) throws Exception {
		try {
			connection = dbConfig.getConnection();
			sql = "delete from department where id = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, departmentId);
			preparedStatement.executeUpdate();
		} finally {
			close();
		}
	}
	
	/*
	 * close and clear buffers
	 */
	private void close() throws Exception {
		// Check connection then close it
		if (connection != null) {
			connection.close();
			connection = null;
		}
		// Check statement then close it
		if (statement != null) {
			statement.close();
			statement = null;
		}
		// Check resultSet then close it
		if (resultSet != null) {
			resultSet.close();
			resultSet = null;
		}
		// Empty sql query string
		sql = "";
	}

}
