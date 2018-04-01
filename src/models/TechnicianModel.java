package models;

public class TechnicianModel extends ActiveRecord {
	private static final String TABLE_NAME = "technicians";

	private int id;
    private String first_name;
    private String last_name;

	public TechnicianModel(String fname, String lname) {
		fname = first_name;
		lname = last_name;
	}

    @Override
    public String table() { return "technicians"; }

	public String fullname() {
		return first_name + last_name;
	}

	@Override
	public int getID() { return id; }
}
