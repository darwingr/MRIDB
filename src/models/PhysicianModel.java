/*
 * 
 * Author: Cynthia F.
 * Based off of D. Groskleg's Patient Model.
 * 
 */


package models;

public class PhysicianModel extends ActiveRecord {

	 private int id;                      
	 private String first_name;          
	 private String last_name;          
	 private String specialty; 
	 
	 
	 public PhysicianModel() {
			String fname;
			String lname;
			fname = first_name;
			lname = last_name;
	}
	 
	 public String fullname() {
			return first_name + last_name;
		}

}
