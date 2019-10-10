package com.hightech.manager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

@ManagedBean
@SessionScoped
public class EmployeeController {

	private EmployeeDbUtil employeeDbUtil;
	private DepartmentDbUtil departmentDbUtil;
	private Collection<Employee> employees;
	private Collection<Department> departments;
	private Logger logger = Logger.getLogger(this.getClass().getName());

	public EmployeeController() {
		try {
			this.employeeDbUtil = EmployeeDbUtil.getInstance();
			this.departmentDbUtil = DepartmentDbUtil.getInstance();
		} catch (Exception ex) {
			addErrorMessage(ex);
		}
	}

	public Collection<Employee> getEmployees() {
		return this.employees;
	}
	
	public Collection<Department> getDepartments() {
		return this.departments;
	}

	public void loadEmployeesAndDepartments() {
		logger.info("=========================== Loading employees ===========================");
		try {
			if (this.employees == null) {
				this.employees = new ArrayList<>();
			}
			if (this.departments == null) {
				this.departments = new ArrayList<>();
			}
			this.employees = this.employeeDbUtil.getEmployees();
			this.departments = this.departmentDbUtil.getDepartments();
		} catch (Exception ex) {
			addErrorMessage(ex);
		}
	}
	
	/*
	 * Load a specific employee from database 
	 */
	public String loadEmployee(Long employeeId) throws Exception{
		Employee employee = this.employeeDbUtil.getEmployee(employeeId);
		FacesContext context = FacesContext.getCurrentInstance();
		// Getting request map instead of session map to clear it after request immediately
		Map<String, Object> contextData = context.getExternalContext().getRequestMap();
		contextData.put("employee", employee);
		return "update-employee";
	}

	/*
	 * Add employee controller method
	 */
	public String addEmployee(Employee employee) {
		try {
			logger.info("=========================== Invoking Add Employee from DAO ===========================");
			this.employeeDbUtil.addEmploye(employee);
			addMessage("Employee has been added successfully");
		} catch (Exception ex) {
			addErrorMessage(ex);
		}
		return "list-employees?faces-redirect=true";
	}
	
	/*
	 * Update employee controller method
	 */
	public String updateEmployee(Employee employee) {
		try {
			logger.info("=========================== Invoking Update Employee from DAO ===========================");
			System.out.println(employee);
			this.employeeDbUtil.updateEmploye(employee);
			addMessage("Employee has been updated successfully");
		} catch (Exception ex) {
			ex.printStackTrace();
			addErrorMessage(ex);
		}
		return "list-employees?faces-redirect=true";
	}
	
	/*
	 * Delete employee controller method
	 */
	public String deleteEmployee(Long employeeId) {
		System.out.println("Emplyee id: " + employeeId);
		try {
			logger.info("=========================== Invoking Delete Employee from DAO ===========================");
			this.employeeDbUtil.deleteEmployee(employeeId);
			addMessage("Employe has been deleted successfully");
		} catch (Exception ex) {
			ex.printStackTrace();
			addErrorMessage(ex);
		}
		return "list-employees?faces-redirect=true";
	}

	/*
	 * Add error message method
	 */
	public void addErrorMessage(Exception ex) {
		FacesMessage facesMessage = new FacesMessage(ex.getMessage());
		FacesContext facesContext = FacesContext.getCurrentInstance();
		Flash flash = facesContext.getExternalContext().getFlash();
		flash.setKeepMessages(true);
		facesContext.addMessage(null, facesMessage);
	}
	
	/*
	 * Add error message method
	 */
	public void addMessage(String message) {
		FacesMessage facesMessage = new FacesMessage(message);
		FacesContext facesContext = FacesContext.getCurrentInstance();
		Flash flash = facesContext.getExternalContext().getFlash();
		flash.setKeepMessages(true);
		facesContext.addMessage(null, facesMessage);
	}
}
