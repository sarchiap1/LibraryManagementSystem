package lms.core.entities;

import java.io.Serializable;


public class Account implements Serializable{
	private String email;
	
	public Account(String email) {
		this.email = email;
	}
	
	public String getEmail() {return this.email;}
}
