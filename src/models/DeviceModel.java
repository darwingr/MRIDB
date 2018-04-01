package models;

public class DeviceModel extends ActiveRecord {
	private static final String TABLE_NAME = "devices";

	private int    id;
	private String manufacturer;
	private String model_number;
	private String hospital_location;

	public DeviceModel() {
		// TODO Auto-generated constructor stub
	}

    @Override
    public String table() { return "devices"; }

	@Override
	public int getID() { return id; }
}
