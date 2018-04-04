package models;

import java.sql.ResultSet;
import java.sql.SQLException;

import adapters.DBAdapter;

public class MeasurementModel extends ActiveRecord {
	private static final String TABLE_NAME = "measurements";

	private int    id;
	private String hemisphere;
	private String label;
	private String brain_region;

	public static MeasurementModel findByID(int id) throws SQLException {
		MeasurementModel measurement = new MeasurementModel();
		DBAdapter db = new DBAdapter();
		String sql = "SELECT * FROM " + measurement.table() + " WHERE id = " + id;
		try (ResultSet rs = db.executeQuery(sql)) {
			if (rs.next()) {
				measurement.id = rs.getInt("id");
				measurement.hemisphere = rs.getString("hemisphere");
				measurement.label = rs.getString("label");
				measurement.brain_region = rs.getString("brain_region");
			}
		} catch (SQLException sqle) {
            System.err.println("Exception occurred while processing Building ResultSet after findByID.");
            sqle.printStackTrace();
		} finally {
			db.close();
		}
		return measurement;
	}

	public String getLabel() {
		return label;
	}
	
	public MeasurementModel() {
	}

	@Override
	public String table() { return "measurements"; }

	@Override
	public int getID() { return id; }
}
