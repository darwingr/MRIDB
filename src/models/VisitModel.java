/**
 * 
 */
package models;

import java.util.Date;
import java.sql.Timestamp;

/**
 *
 */
public class VisitModel extends ActiveRecord {
	private static final String TABLE_NAME = "visits";

	private int       id;
	private char      gender;
	private Date      dob;
	private Timestamp check_in;
	private Timestamp check_out;
	private int       patient_id;	
	
	public VisitModel() {
		// TODO Auto-generated constructor stub
	}

}
