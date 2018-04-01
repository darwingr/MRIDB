package models;

import java.util.ArrayList;
import java.util.Date;

import adapters.DBAdapter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
<<<<<<< HEAD
import java.text.ParseException;
=======
>>>>>>> branch 'master' of https://github.com/ThreeFourSeven/Database-Gui.git
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
	private Date      dob;		// FIXME This should be age (during visit) not dob
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
			if (rs.next()) {
				visit.id = rs.getInt("id");
				visit.setGender(rs.getInt("gender"));
				visit.setDob(rs.getDate("dob"));
				visit.check_in = rs.getTimestamp("check_in");
				visit.check_out = rs.getTimestamp("check_out");
				visit.patient_id = rs.getInt("patient_id");
			}
		} catch (SQLException sqle) {
            System.err.println("Exception occurred while processing Building ResultSet after findByID.");
		} finally {
			db.close();
		}
		return visit;
	}

	public static ArrayList<VisitModel> findByPatientID(int pat_id) throws SQLException {
		ArrayList<VisitModel> visits = new ArrayList<VisitModel>();
		DBAdapter db = new DBAdapter();
		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE patient_id = " + pat_id;
		try (ResultSet rs = db.executeQuery(sql)) {
			while (rs.next()) {
				VisitModel visit = new VisitModel();
				visit.id = rs.getInt("id");
				visit.setGender(rs.getInt("gender"));
				visit.setDob(rs.getDate("dob"));
				visit.check_in = rs.getTimestamp("check_in");
				visit.check_out = rs.getTimestamp("check_out");
				visit.patient_id = rs.getInt("patient_id");
				visits.add(visit);
			}
		} catch (SQLException sqle) {
            System.err.println("Exception occurred while processing Building ResultSet after findByID.");
		} finally {
			db.close();
		}
		return visits;
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

	public int getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	@Override public String table() { return "visits"; }

	public boolean create(int pat_id) throws SQLException {
		boolean success = false;
		patient_id = pat_id;
		DBAdapter db = new DBAdapter();
		DateFormat df = DateFormat.getDateInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String plsql = 
				"DECLARE \n" +
				"  rec_id NUMBER; \n" +
				"BEGIN \n" +
				"  INSERT INTO "+ table() +" \n" +
				"    (gender, dob, check_in, patient_id) \n" +
				"    VALUES \n" +
				"    ('" + gender + "', " +
				"    to_timestamp('" + df.format(dob) + " 00:00:00', 'dd-mon-yyyy hh24:mi:ss'), " +
				"'" + formatter.format(check_in) + "', " + patient_id + ")" +
				"    RETURNING id INTO rec_id; \n" +
				"  ? := rec_id;" +
				"END; \n";

		try {
			int rec_id = db.executeCall(plsql);
			if (rec_id != -1) {
				success = true;
				id = rec_id;
			}
		} catch (Exception sqle) {
            System.err.println("Exception occurred while processing returning the new record id.");
		} finally {
			db.close();
		}
		return success;
	}


	// Required to test findByID
	public int getID() { return id; }

	public void setGender(int gen) {
		gender = gen;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date date_of_birth) {
		dob = date_of_birth;
	}

	public int getPatientID() { return patient_id; }
}
