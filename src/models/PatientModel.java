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
	public PatientModel() {
		// TODO Auto-generated constructor stub
	}
	
	public String fullname() {
		return first_name + last_name;
	}

}
