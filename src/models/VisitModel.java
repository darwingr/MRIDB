/**
 * 
 */
package models;

import java.util.Date;

import adapters.DBAdapter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

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
	
	public VisitModel() {
		this(1, new Date(1979-1900, 06-1, 30));
	}

	public VisitModel(int gen, Date dateOfBirth) {
		gender = gen;
		dob = dateOfBirth;
		check_in = Timestamp.valueOf(LocalDateTime.now());
	}

	public boolean create() throws SQLException {
		DBAdapter db = new DBAdapter();
		DateFormat df = DateFormat.getDateInstance();
		String sql = "INSERT INTO visits " +
					 "(gender, dob, check_in)\n" +
					 "VALUES " +
					 "('" + gender + "', to_timestamp('" + df.format(dob) + " 00:00:00', 'dd-mon-yyyy hh24:mi:ss'), '" + check_in.toString() + "')";
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

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		VisitModel.dob = dob;
	}

}
