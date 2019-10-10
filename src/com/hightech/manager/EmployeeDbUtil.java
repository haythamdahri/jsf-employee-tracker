package com.hightech.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

public class EmployeeDbUtil {

	private static EmployeeDbUtil instance;
	private DepartmentDbUtil departementDbUtil;
	private static DBConfig dbConfig;
	private static Connection connection;
	private static String sql = "";
	private static ResultSet resultSet;
	private static Statement statement;
	private static PreparedStatement preparedStatement;
	private Logger logger = Logger.getLogger(this.getClass().getName());

	private EmployeeDbUtil() throws Exception {
		dbConfig = DBConfig.getInstance();
		departementDbUtil = DepartmentDbUtil.getInstance();
	}

	/*
	 * Method to get a singleton instance of EmployeDbUtil
	 */
	public static EmployeeDbUtil getInstance() throws Exception {
		// Check local instance
		if (instance == null) {
			// Instantiate the local instance
			instance = new EmployeeDbUtil();
		}
		// Return the local instance
		return instance;
	}

	/*
	 * Get employees list
	 */
	public Collection<Employee> getEmployees() throws Exception {
		Collection<Employee> employees = new ArrayList<>();
		Department tempDepartement = null;
		try {
			connection = dbConfig.getConnection();
			sql = "select *from employee;";
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				Long departementId = resultSet.getLong(5);
				tempDepartement = departementDbUtil.getDepartment(departementId);
				employees.add(new Employee(resultSet.getLong(1), resultSet.getString(2), resultSet.getString(3),
						resultSet.getString(4), tempDepartement));
			}
			return employees;
		} finally {
			close();
		}
	}

	/*
	 * Get a specific employee
	 */
	public Employee getEmployee(Long employeeId) throws Exception {
		Department tempDepartement = null;
		Employee employe = null;
		try {
			connection = dbConfig.getConnection();
			sql = "select *from employee where id = ?;";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, employeeId);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				Long departementId = resultSet.getLong(5);
				tempDepartement = departementDbUtil.getDepartment(departementId);
				employe = new Employee(resultSet.getLong(1), resultSet.getString(2), resultSet.getString(3),
						resultSet.getString(4), tempDepartement);
			}
			return employe;
		} finally {
			close();
		}
	}

	/*
	 * Add a new employee method
	 */
	public void addEmploye(Employee employee) throws Exception {
		try {
			connection = dbConfig.getConnection();
			sql = "insert into employee(first_name, last_name, email, department_id) values (?,?,?,?);";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, employee.getFirstName());
			preparedStatement.setString(2, employee.getLastName());
			preparedStatement.setString(3, employee.getEmail());
			preparedStatement.setLong(4, employee.getDepartment().getId());
			preparedStatement.executeUpdate();
		} finally {
			close();
		}
	}

	/*
	 * Update an existing employee method
	 */
	public void updateEmploye(Employee employee) throws Exception {
		try {
			connection = dbConfig.getConnection();
			sql = "update employee set first_name = ?, last_name = ?, email = ?, department_id = ? where id = ?;";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, employee.getFirstName());
			preparedStatement.setString(2, employee.getLastName());
			preparedStatement.setString(3, employee.getEmail());
			preparedStatement.setLong(4, employee.getDepartment().getId());
			preparedStatement.setLong(5, employee.getId());
			preparedStatement.executeUpdate();
		} finally {
			close();
		}
	}

	/*
	 * Update an existing employee method
	 */
	public void deleteEmployee(Long employeeId) throws Exception {
		try {
			logger.info("=========================== Deleting Employee ===========================");
			connection = dbConfig.getConnection();
			sql = "delete from employee where id = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, employeeId);
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
