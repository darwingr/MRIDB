package models;

public class PhysicianModel extends ActiveRecord {
	private static final String TABLE_NAME = "physicians";

	private static int    id;
	private static String first_name;
	private static String last_name;
	private static String specialty;


	public PhysicianModel() {
	}

    public String table() { return "physicians"; }
	
	public String fullname() {
    	return getFirst_name() + getLast_name();
	}

    @Override
	public int getID() { return id; }

	public String getSpecialty() {
		return specialty;
	}

	public  void setSpecialty(String specialty) {
		PhysicianModel.specialty = specialty;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		PhysicianModel.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		PhysicianModel.last_name = last_name;
	}
}
