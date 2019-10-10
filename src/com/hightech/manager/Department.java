package com.hightech.manager;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class Department {

	private Long id;
	private String name;

	public Department() {

	}

	public Department(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Departement [id=" + id + ", name=" + name + "]";
	}

	// overridden equals method
	public boolean equals(Object obj) {
		if (!(obj instanceof Department)) {
			return false;
		}
		Department department = (Department) obj;

		return (this.id == department.id);

	}

}
