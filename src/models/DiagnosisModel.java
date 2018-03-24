package models;

import java.util.Date;
import java.sql.Timestamp;

public class DiagnosisModel extends ActiveRecord {
	private static final String TABLE_NAME = "diagnoses";

	private int       id;
	private int       condition_id;
	private int       patient_id;
	private int       physician_id;
	private int       physician_notes;
	private Timestamp diagnosis_date;

	public DiagnosisModel() {
		// TODO Auto-generated constructor stub
	}

}
