package models;

public class ConditionModel extends ActiveRecord {
	private static final String TABLE_NAME = "conditions";

	private int    id;
	private String name;
	private String signs;
	private String symptoms;

	public ConditionModel() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String table() { return "conditions"; }

	@Override
	public int getID() { return id; }
}
