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
	private char      gender;
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
			visit.gender = rs.getString("gender").charAt(0);
			visit.dob = rs.getDate("dob");
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
		// TODO Auto-generated constructor stub
	}

	// Required to test findByID
	public int getID() {
        return id;
    }

}
