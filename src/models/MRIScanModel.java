package models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.sql.ResultSet;
import java.sql.SQLException;

import adapters.DBAdapter;
import adapters.OracleDBAdapter;

public class MRIScanModel extends ActiveRecord {
	private static final String TABLE_NAME = "mri_scans";
	public static final int MEASUREMENTS_ARRAY_SIZE = 4788;

	private int     id;
	private String  technician_notes;
	private int     technician_id;
	private int     visit_id;
	private int     device_id;
	private List<Double> measurements_array; // initialized in constructor

    // Additional attribute from visit
	private int     visit_age;

	public static MRIScanModel findByID(int rec_id) throws SQLException {
		MRIScanModel scan = new MRIScanModel();
		DBAdapter db = new OracleDBAdapter();

		String sql = "select * from " + scan.table() + " where id = " + rec_id;
		try (ResultSet rs = db.executeQuery(sql)) {
			rs.next();

			scan.id = rs.getInt("id");
			scan.technician_notes = rs.getString("technician_notes");
			scan.technician_id = rs.getInt("technician_id");
			scan.visit_id = rs.getInt("visit_id");
			scan.device_id = rs.getInt("device_id");

			java.sql.Array db_vals = rs.getArray("measurements_array");
			scan.measurements_array.addAll(
					Arrays.asList((Double[]) db_vals.getArray())
			);
		} catch (SQLException sqle) {
            System.err.println("Exception occurred while building ResultSet after findByID.");
		} finally {
			db.close();
		}

		return scan;
	}

	public static ArrayList<MRIScanModel> scansForAgeRange(int low, int high) throws SQLException {
		return scansForAgeRange(low, high, -1);
	}

	public static ArrayList<MRIScanModel> scansForAgeRange(int low, int high, int genderFilter)
			throws SQLException {
		ArrayList<MRIScanModel> scans = new ArrayList<MRIScanModel>();
		DBAdapter db = new OracleDBAdapter();
		String sql =
				"SELECT \n" +
				"  mri_scans.id AS id, \n" +
				"  technician_notes, \n" +
				"  technician_id, \n" +
				"  visit_id, \n" +
				"  device_id, \n" +
				"  measurements_array, \n" +
				"  FLOOR( MONTHS_BETWEEN(SYSDATE, visits.dob) / 12 ) AS visit_age, \n" +
				"  visits.gender AS visit_gender \n" +
				"FROM mri_scans \n" +
				"  JOIN visits ON visits.id = mri_scans.visit_id \n" +
				"WHERE \n" +
				"  FLOOR( MONTHS_BETWEEN(SYSDATE, visits.dob) / 12 ) >= " + low + " \n" +
				"  AND FLOOR( MONTHS_BETWEEN(SYSDATE, visits.dob) / 12 ) <= " + high;
		try (ResultSet rs = db.executeQuery(sql)) {
			while (rs.next()) {
				MRIScanModel scan = new MRIScanModel();
				scan.id = rs.getInt("id");
				scan.technician_notes = rs.getString("technician_notes");
				scan.technician_id = rs.getInt("technician_id");
				scan.visit_id = rs.getInt("visit_id");
				scan.device_id = rs.getInt("device_id");

				java.sql.Array db_vals = rs.getArray("measurements_array");
				scan.measurements_array.addAll(
						Arrays.asList((Double[]) db_vals.getArray())
				);

				scan.visit_age = rs.getInt("visit_age");

				if (genderFilter == -1)
					scans.add(scan);
				else if (rs.getInt("visit_gender") == genderFilter)
					scans.add(scan);
			}
		} catch (SQLException sqle) {
            System.err.println("Exception occurred while building ResultSet for scansForAgeRange.");
            System.err.println("\nSQL statement:\n" + sql);
            sqle.printStackTrace();
		} finally {
			db.close();
		}
		return scans;
	}

	public MRIScanModel() {
		measurements_array = new ArrayList<Double>(MEASUREMENTS_ARRAY_SIZE);
	}

    public String table() { return "mri_scans";}

	public ArrayList<Double> getMeasurementSubset(int measurement_indices[]) {
		ArrayList<Double> measurements_subset =
				new ArrayList<Double>(MEASUREMENTS_ARRAY_SIZE);

		for (int i : measurement_indices) {
			measurements_subset.add(measurements_array.get(i));
		}

		return measurements_subset;
	}
	
	public int getVisitID() {
		return visit_id;
	}

	@Override
	public int getID() { return id; }
	
	public int getVisitAge() { return visit_age; }
}
