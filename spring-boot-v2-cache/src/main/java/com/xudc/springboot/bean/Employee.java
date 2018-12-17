package com.xudc.springboot.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author xudc
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
	
	private Integer id;
	private String lastName;
	private String email;
	/**
	 * 性别 1男  0女
	 */
	private Integer gender;
	private Integer dId;

	public Employee(String lastName, String email, Integer gender, Integer dId) {
		this.lastName = lastName;
		this.email = email;
		this.gender = gender;
		this.dId = dId;
	}
}
