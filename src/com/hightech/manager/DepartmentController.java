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
public class DepartmentController {

	private DepartmentDbUtil departmentDbUtil;
	private Collection<Department> departments;
	private Logger logger = Logger.getLogger(this.getClass().getName());

	public DepartmentController() {
		try {
			this.departmentDbUtil = DepartmentDbUtil.getInstance();
		} catch (Exception ex) {
			addErrorMessage(ex);
		}
	}
	
	public Collection<Department> getDepartments() {
		return this.departments;
	}

	public void loadDepartments() {
		logger.info("=========================== Loading departments ===========================");
		try {
			if (this.departments == null) {
				this.departments = new ArrayList<>();
			}
			this.departments = this.departmentDbUtil.getDepartments();
		} catch (Exception ex) {
			addErrorMessage(ex);
		}
	}
	
	/*
	 * Load a specific employee from database 
	 */
	public String loadDepartment(Long departmentId) throws Exception{
		Department department = this.departmentDbUtil.getDepartment(departmentId);
		FacesContext context = FacesContext.getCurrentInstance();
		// Getting request map instead of session map to clear it after request immediately
		Map<String, Object> contextData = context.getExternalContext().getRequestMap();
		contextData.put("department", department);
		return "update-department";
	}

	/*
	 * Add department controller method
	 */
	public String addDepartment(Department department) {
		try {
			logger.info("=========================== Invoking Add Department from DAO ===========================");
			this.departmentDbUtil.addDepartment(department);
			addMessage("Department has been added successfully");
		} catch (Exception ex) {
			addErrorMessage(ex);
		}
		return "list-departments?faces-redirect=true";
	}
	
	/*
	 * Update department controller method
	 */
	public String updateDepartment(Department department) {
		try {
			logger.info("=========================== Invoking Update Department from DAO ===========================");
			this.departmentDbUtil.updateDepartment(department);
			addMessage("Department has been updated successfully");
		} catch (Exception ex) {
			ex.printStackTrace();
			addErrorMessage(ex);
		}
		return "list-departments?faces-redirect=true";
	}
	
	/*
	 * Delete employee controller method
	 */
	public String deleteDepartment(Long departmentId) {
		try {
			logger.info("=========================== Invoking Delete Department from DAO ===========================");
			this.departmentDbUtil.deleteDepartment(departmentId);
			addMessage("Department has been deleted successfully");
		} catch (Exception ex) {
			addErrorMessage(ex);
		}
		return "list-departments";
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
