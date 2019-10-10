package com.hightech.manager;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("departmentConverter")
public class DepartmentConverter implements Converter {

	private DepartmentDbUtil departmentDbUtil;

	public DepartmentConverter() throws Exception {
		this.departmentDbUtil = DepartmentDbUtil.getInstance();
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object object) {
		Long id = (object instanceof Department) ? ((Department) object).getId() : null;
		return (id != null) ? String.valueOf(id) : null;
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String submittedValue) {
		try {
			Long id = (submittedValue != null) ? Long.valueOf(submittedValue) : null;
			System.out.println((id != null) ? this.departmentDbUtil.getDepartment(id) : null);
			return (id != null) ? this.departmentDbUtil.getDepartment(id) : null;
		} catch (Exception ex) {
			return null;
		}
	}


}