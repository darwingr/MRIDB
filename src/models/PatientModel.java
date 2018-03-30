/**
 * 
 */
package models;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import java.sql.ResultSet;
import java.sql.SQLException;

import adapters.DBAdapter;

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
	
	public create() throws SQLException {
		DBAdapter db = new DBAdapter();
		boolean userExists = false;
		String sql = 
				"SELECT count(first_name), count(last_name)\n" + 
				"FROM users " + 
				"WHERE first_name = '" + first_name + "' " + 
				"AND last_name = '" + last_name + "';";
		try (ResultSet rs = db.executeQuery(sql)) {
			userExists = rs.next();
		}
		boolean success = false;
		if(!userExists) {
			String sql2 = "INSERT INTO patients " + 
						"VALUES (" + first_name + ", " + last_name + ");";
			//display message on gender that lets user know 1=male, 2=female, 3=N/A
			System.out.println("1=male, 2=female, 3=N/A");
			VisitModel visit = new VisitModel();
			visit.create();

			try (ResultSet rs = db.executeQuery(sql3)) {
				success = rs.next();
			}finally {
				db.close();
			}
		}
		assertTrue(success);
	}
	}

}
