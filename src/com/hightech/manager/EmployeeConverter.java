package com.hightech.manager;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("employeeConverter")
public class EmployeeConverter implements Converter {

	private EmployeeDbUtil employeeDbUtil;

	public EmployeeConverter() throws Exception {
		this.employeeDbUtil = EmployeeDbUtil.getInstance();
	}
	

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object object) {
		Long id = (object instanceof Employee) ? ((Employee) object).getId() : null;
		return (id != null) ? String.valueOf(id) : null;
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String submittedValue) {
		try {
			Long id = (submittedValue != null) ? Long.valueOf(submittedValue) : null;
			return (id != null) ? this.employeeDbUtil.getEmployee(id) : null;
		} catch (Exception ex) {
			return null;
		}
	}

}