package models;

import java.util.Date;
import java.sql.Timestamp;

public class DiagnosisModel extends ActiveRecord {
	private static final String TABLE_NAME = "diagnoses";

	private static int       id;
	private static int       condition_id;
	private static int       patient_id;
	private static int       physician_id;
	private static String    physician_notes;
	private	static Date      diagnosis_date;

	public DiagnosisModel() {
		// TODO Auto-generated constructor stub
	}

    @Override
    public String table() { return "diagnoses"; }

	@Override
	public int getID() { return id; }
	}

}
