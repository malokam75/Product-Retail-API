package com.cognixia.jump.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Range;

public class Author {

	@NotBlank
	private String name;
	
	@NotBlank
	@Range(min = 18, max = 124)
	private int age;
	
	@Pattern( regexp = "[A-Z]{1}$", message = "Must only contain one capital letter." )
	private String gender;
	
	@Range(min = 1)
	private int published;

	public Author() {
		
	}
	
	public Author(@NotBlank String name, @Range(min = 18, max = 124) int age,
			@Pattern(regexp = "[A-Z]{1}$", message = "Must only contain one capital letter.") String gender,
			@Range(min = 1) int published) {
		super();
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.published = published;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getPublished() {
		return published;
	}

	public void setPublished(int published) {
		this.published = published;
	}

	@Override
	public String toString() {
		return "Author [name=" + name + ", age=" + age + ", gender=" + gender + ", published=" + published + "]";
	}
	
}
