package com.ashu.domain;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.keyvalue.annotation.KeySpace;

import lombok.Data;

@Data
@KeySpace("employees")
public class Employee implements Comparable<Employee>, Serializable {

	private static final long serialVersionUID = 5656667804886349644L;

	@Id
	private Integer id;

	private String name;

	@Override
	public int compareTo(Employee that) {
		return getId().compareTo(that.getId());
	}
}
