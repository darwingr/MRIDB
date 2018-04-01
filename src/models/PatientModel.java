package models;

<<<<<<< HEAD
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
=======
import java.util.ArrayList;
>>>>>>> branch 'master' of https://github.com/ThreeFourSeven/Database-Gui.git

import java.sql.ResultSet;
import java.sql.SQLException;

import adapters.DBAdapter;

/**
 *
 */
public class PatientModel extends ActiveRecord {
	public String TABLE_NAME = "patients";

<<<<<<< HEAD
	private int id;
	private String first_name;
	private String last_name;
<<<<<<< HEAD
=======
	private static int 	   id;
	private static String first_name;
	private static String last_name;
	private static String address;
	private static String gender;
	private static Date dob;
>>>>>>> branch 'master' of https://github.com/ThreeFourSeven/Database-Gui.git
=======
	private String address;
>>>>>>> branch 'master' of https://github.com/ThreeFourSeven/Database-Gui.git

<<<<<<< HEAD
	/**
	 * 
	 */
	public PatientModel(String fname,String lname) {
		setFirst_name(fname);
		setLast_name(lname);
=======

	public static PatientModel findByID(int patient_id) throws SQLException {
		PatientModel patient = new PatientModel();
		DBAdapter db = new DBAdapter();
		try (ResultSet rs = db.executeQuery("select * from " + patient.TABLE_NAME + " where id = " + patient_id)) {
			if (rs.next()) {
				patient.id = rs.getInt("id");
				patient.first_name = rs.getString("first_name");
				patient.last_name = rs.getString("last_name");
				patient.address = rs.getString("address");
			}
		} catch (SQLException sqle) {
            System.err.println("Exception occurred while processing Building ResultSet after findByID.");
		} finally {
			db.close();
		}
		return patient;
	}

	public PatientModel() {
		this("Jane", "Doe");
	}

	public PatientModel(String fname, String lname) {
		first_name = fname;
		last_name = lname;
>>>>>>> branch 'master' of https://github.com/ThreeFourSeven/Database-Gui.git
	}

	@Override
	public String table() { return "patients"; }
	
	public String fullName() {
<<<<<<< HEAD
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
=======
		return first_name + ' ' + last_name;
>>>>>>> branch 'master' of https://github.com/ThreeFourSeven/Database-Gui.git
	}

	/*
	 * Creates the current instance of patient as a new record in the database,
	 * while also signing them in for a visit for the current time.
	 */
	public boolean create() throws SQLException {
		boolean success = false;
		DBAdapter db = new DBAdapter();
		boolean patientExists = false;
		String patient_query = 
				"SELECT id \n" + //count(first_name), count(last_name)\n" + 
				"FROM " + table() + '\n' +
				"WHERE first_name = '" + first_name + "' " + 
				"AND last_name = '" + last_name + "'";
		try (ResultSet rs = db.executeQuery(patient_query)) {
			patientExists = rs.next();
		}
		if(!patientExists) {
			String plsql =
					"DECLARE \n" +
					"  rec_id NUMBER; \n" +
					"BEGIN \n" +
					"  INSERT INTO " + table() + " \n" + 
					"    (first_name, last_name) \n" +
					"    VALUES ('" + first_name + "', '" + last_name + "') \n" +
					"    RETURNING id INTO rec_id;\n" +
					"  ? := rec_id;" +
					"END; \n";

			try {
				int rec_id = db.executeCall(plsql);
				if (rec_id != -1) {
					success = true;
					id = rec_id;

					// creates a visit with the default dob and gender,
					// checking in for a visit immediately
					VisitModel visit = new VisitModel();
					visit.create(id);
				}
			} catch (Exception sqle) {
	            System.err.println("Exception occurred while processing returning the new record id.");
			}
		} else {
			System.out.println("Patient exists!");
		}
		db.close();
		return success;
	}

	@Override
	public boolean delete() throws SQLException {
		ArrayList<VisitModel> patient_visits = VisitModel.findByPatientID(id);
		for (VisitModel v : patient_visits) {
			v.delete(); // TODO Error checking on patient visit deletes
		}
		return super.delete();
	}

	public int getID() {
		return id;
	}

	public String getAddress() {
		return address;
	}
}