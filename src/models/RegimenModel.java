package models;

import java.util.Date;

public class RegimenModel extends ActiveRecord {
	private static final String TABLE_NAME = "regimens";
	
    private String physician_notes;
    private Date   start_date;
    private int    treatment_id;

	public RegimenModel() {
		// TODO Auto-generated constructor stub
	}

    @Override
    public String table() { return "regimens"; }

	@Override
	public int getID() { return treatment_id; }
}
