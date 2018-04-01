package models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.sql.ResultSet;
import java.sql.SQLException;

import adapters.DBAdapter;

public class MRIScanModel extends ActiveRecord {
	private static final String TABLE_NAME = "mri_scans";
	public static final int MEASUREMENTS_ARRAY_SIZE = 4788;

	private int     id;
	private String  technician_notes;
	private int     technician_id;
	private int     visit_id;
	private int     device_id;
	private List<Double> measurements_array; // initialized in constructor

	public static MRIScanModel findByID(int rec_id) throws SQLException {
		MRIScanModel scan = new MRIScanModel();
		DBAdapter db = new DBAdapter();

		try (ResultSet rs = db.executeQuery("select * from " + TABLE_NAME + " where id = " + rec_id)) {
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

	public int getID() { return id; }
}
