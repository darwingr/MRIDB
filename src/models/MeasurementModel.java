package models;

public class MeasurementModel extends ActiveRecord {
	private static final String TABLE_NAME = "measurements";

	private int    id;
	private String hemisphere;
	private String label;
	private String brain_region;

	public MeasurementModel() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return id;
	}

}
