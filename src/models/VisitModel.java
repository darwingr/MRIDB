/**
 * 
 */
package models;

import java.util.Date;

import adapters.DBAdapter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 *
 */
public class VisitModel extends ActiveRecord {
	private static final String TABLE_NAME = "visits";

	private int       id;
	private int       gender;
	private Date      dob;
	private Timestamp check_in;
	private Timestamp check_out;
	private int       patient_id;	

	public static VisitModel findByID(int rec_id) throws SQLException {
		VisitModel visit = new VisitModel();
		DBAdapter db = new DBAdapter();
		try (ResultSet rs = db.executeQuery("select * from " + TABLE_NAME + " where id = " + rec_id)) {
			rs.next();
			visit.id = rs.getInt("id");
			visit.setGender(rs.getString("gender").charAt(0));
			visit.setDob(rs.getDate("dob"));
			visit.check_in = rs.getTimestamp("check_in");
			visit.check_out = rs.getTimestamp("check_out");
			visit.patient_id = rs.getInt("patient_id");
		} catch (SQLException sqle) {
            System.err.println("Exception occurred while processing Building ResultSet after findByID.");
		} finally {
			db.close();
		}
		return visit;
	}

	public VisitModel(int gen, Date dateOfBirth) {
		gender = gen;
		dob = dateOfBirth;
		check_in = Timestamp.valueOf(LocalDateTime.now());
	}

	public boolean create() throws SQLException {
		DBAdapter db = new DBAdapter();
		String sql = "INSERT INTO visits " +
					 "(gender, dob, check_in)\n" +
					 "VALUES " +
					 "('" + gender + "', " + dob.toString() + ", '" + check_in.toString() + "')";
		boolean success = false;
		try (ResultSet rs = db.executeQuery(sql)) {
			success = rs.next();
		} finally {
			db.close();
		}
		return success;
	}

	// Required to test findByID
	public int getID() {
        return id;
    }

	public static char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	public static Date getDob() {
		return dob;
	}

	public static void setDob(Date dob) {
		VisitModel.dob = dob;
	}

}
