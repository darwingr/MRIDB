/**
 * 
 */
package models;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import adapters.DBAdapter;

/**
 * Author: cynthiaforgeron
 *
 */
public class PatientFileModel extends ActiveRecord {
	private static final String TABLE_NAME = "patient_files";
	
	/* Patient Identifying Information */
	
	private int id = PatientModel.getId();
	private String first_name;
	private String last_name;
	private String address;
	private String gender;
	private String dob;
	
	/* Medical Information */
	
	final static class physicians {
		private static int id;
		private static String first_name;
		private static String last_name;
		private static String specialty;
	}
	
	final static class diagnoses {
		private Date diagnosis_date;
		private String physician_notes;
	}
	
	final static class regimens {
		private String physician_notes;
		private Date start_date;
	}
	
	final static class conditions {
		private static String name;
		private static String signs;
		private static String symptoms;
	}
	
	final static class treatments {
		private static String treatment_type;
		private static String description;
	}
	
	final static class visits {
		private static String checkin;
		private static String checkout;
	}
	
	final static class genomes {
		private static String sequence;
		private static String date_taken;
	}
	
	public PatientFileModel() {
	}
	
	public boolean delete() throws SQLException {
		DBAdapter db = new DBAdapter();
		String sql = "DELETE FROM users WHERE id = '" + id + "'";
		boolean success = false;
		try (ResultSet rs = db.executeQuery(sql)) {
			success = rs.next();
		} finally {
			db.close();
		}
		return success;
	}
	
	public static PatientFileModel findByID(int id) throws SQLException {
		DBAdapter db = new DBAdapter();
		PatientFileModel patientfile = new PatientFileModel();
		String sql = "SELECT * FROM patient_files WHERE id= '" + id + "'";	
		boolean success = false;
		try (ResultSet rs = db.executeQuery(sql)) {
			success = rs.next();
			patientfile.id = rs.getInt("patient_id");
			patientfile.first_name = rs.getString("patient_firstname");
			patientfile.last_name = rs.getString("patient_lastname");
			patientfile.address = rs.getString("patient_address");
			patientfile.gender = rs.getString("visit_gender");
			patientfile.dob = rs.getString("visit_dob");
			visits.checkin = rs.getString("visit_checkin");
			visits.checkout = rs.getString("visit_checkout");
			genomes.sequence = rs.getString("genome_sequence");
			genomes.date_taken = rs.getString("genome_datetaken");
			conditions.name  = rs.getString("condition_name");
			conditions.signs  = rs.getString("condition_signs");
			conditions.symptoms = rs.getString("condition_symptoms");
			treatments.treatment_type  = rs.getString("treatment_type"); 
			treatments.description  = rs.getString("treatment_description");
			physicians.id  = rs.getInt("physician_id");
			physicians.first_name  = rs.getString("physician_firstname");
			physicians.last_name  = rs.getString("physician_lastname");
			physicians.specialty  = rs.getString("physician_specialty");
		} catch (SQLException sqle) {
            System.err.println("Exception occurred while processing Building ResultSet after findByID.");
		} finally {
			db.close();
		}
		return patientfile;
	}


}
