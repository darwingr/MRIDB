/*
 * 
 * Author: Cynthia F
 * Based off of D. Groskleg's Patient Model.
 * 
 * 
 */


package models;

public class PhysicianModel extends ActiveRecord {
	
	private static final String TABLE_NAME = "physicians";

	 private static int id;                      
	 private static String first_name;          
	 private static String last_name;          
	 private static String specialty; 
	 
	 
	 public PhysicianModel() {
	}
	 
	 public String fullname() {
			return getFirst_name() + getLast_name();
		}

	public static int getId() {
		return id;
	}

	public static void setId(int id) {
		PhysicianModel.id = id;
	}

	public static String getSpecialty() {
		return specialty;
	}

	public static void setSpecialty(String specialty) {
		PhysicianModel.specialty = specialty;
	}

	public static String getFirst_name() {
		return first_name;
	}

	public static void setFirst_name(String first_name) {
		PhysicianModel.first_name = first_name;
	}

	public static String getLast_name() {
		return last_name;
	}

	public static void setLast_name(String last_name) {
		PhysicianModel.last_name = last_name;
	}

}
