package com.beater.springmvc.entities;

public class User {
	private String userName;
	private String password;
	private int age;
	private Address address;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "User [userName=" + userName + ", password=" + password + ", age=" + age + ", address=" + address + "]";
	}

	public User(String userName, String password, int age) {
		super();
		this.userName = userName;
		this.password = password;
		this.age = age;
	}

	public User() {
		super();
	}

}
