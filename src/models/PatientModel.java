/**
 * 
 */
package models;


/**
 *
 */
public class PatientModel extends ActiveRecord {
	private static final String TABLE_NAME = "patients";

	private int id;
	private String first_name;
	private String last_name;

	/**
	 * 
	 */
	public PatientModel(String fname,String lname) {
		first_name = fname;
		last_name = lname;
	}
	
	public String fullName() {
		return first_name + last_name;
	}

}
