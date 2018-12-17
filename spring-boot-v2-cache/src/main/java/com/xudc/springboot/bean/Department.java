package com.xudc.springboot.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Department {
	
	private Integer id;
	private String departmentName;

	public Department(String departmentName) {
		this.departmentName = departmentName;
	}
}
