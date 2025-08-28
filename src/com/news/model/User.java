package com.news.model;

import java.util.List;

public class User {
	private String name;
	private List<String> interests;
	
	public User(String name, List<String> interests){
		super();
		this.name = name;
		this.interests = interests;
	}
	
	public String getName() { return name; }
	public List<String> getInterests() { return interests; }
	
	@Override
	public String toString() {
		return "Name : " + name + "\nInterests : " + interests;
	}
	
}
