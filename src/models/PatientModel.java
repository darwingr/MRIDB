package models;

import java.util.ArrayList;

import java.sql.ResultSet;
import java.sql.SQLException;

import adapters.DBAdapter;

/**
 *
 */
public class PatientModel extends ActiveRecord {
	public String TABLE_NAME = "patients";

	private int 	   id;
	private String first_name;
	private String last_name;
	private String address;


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
	}

	@Override
	public String table() { return "patients"; }
	
	public String fullName() {
		return first_name + ' ' + last_name;
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