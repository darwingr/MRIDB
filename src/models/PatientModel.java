/**
 * 
 */
package models;

import java.util.Date;

/**
 *
 */
public class PatientModel extends ActiveRecord {
	private static final String TABLE_NAME = "patients";

	private static int 	   id;
	private static String first_name;
	private static String last_name;
	private static String address;
	private static String gender;
	private static Date dob;

	/**
	 * 
	 */
	public PatientModel(String fname,String lname) {
		setFirst_name(fname);
		setLast_name(lname);
	}
	
	public String fullName() {
		return getFirst_name() + getLast_name();
	}

	public static int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public static String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		PatientModel.first_name = first_name;
	}

	public static String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		PatientModel.last_name = last_name;
	}

	public static String getAddress() {
		return address;
	}

	public static void setAddress(String address) {
		PatientModel.address = address;
	}

}
