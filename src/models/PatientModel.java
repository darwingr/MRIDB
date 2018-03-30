/**
 * 
 */
package models;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.ResultSet;
import java.sql.SQLException;

import adapters.DBAdapter;

/**
 *
 */
public class PatientModel extends ActiveRecord {
	private static final String TABLE_NAME = "patients";

	private int 	   id;
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
