/**
 * 
 */
package models;


/**
 * @author darwingroskleg
 *
 */
public class PatientModel extends ActiveRecordModel {
	private int number;
	private String first_name;
	private String last_name;

	/**
	 * 
	 */
	public PatientModel(String fname,String lname) {
		first_name=fname;
		last_name=lname;
	}
	
	public String fullname() {
		return first_name + last_name;
	}

}
