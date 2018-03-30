package models;

import java.util.Date;
import java.sql.Timestamp;

public class DiagnosisModel extends ActiveRecord {
	private static final String TABLE_NAME = "diagnoses";

	private static int       id;
	private static int       condition_id;
	private static int       patient_id;
	private static int       physician_id;
	private static int       physician_notes;
	private	static Date 	 diagnosis_date;

	public DiagnosisModel() {
		// TODO Auto-generated constructor stub
	}

	public static int getId() {
		return id;
	}

	public static void setId(int id) {
		DiagnosisModel.id = id;
	}

	public static int getCondition_id() {
		return condition_id;
	}

	public static void setCondition_id(int condition_id) {
		DiagnosisModel.condition_id = condition_id;
	}

	public static int getPatient_id() {
		return patient_id;
	}

	public static void setPatient_id(int patient_id) {
		DiagnosisModel.patient_id = patient_id;
	}

	public static int getPhysician_id() {
		return physician_id;
	}

	public static void setPhysician_id(int physician_id) {
		DiagnosisModel.physician_id = physician_id;
	}

	public static int getPhysician_notes() {
		return physician_notes;
	}

	public static void setPhysician_notes(int physician_notes) {
		DiagnosisModel.physician_notes = physician_notes;
	}

	public static Date getDiagnosis_date() {
		return diagnosis_date;
	}

	public static void setDiagnosis_date(Date diagnosis_date) {
		DiagnosisModel.diagnosis_date = diagnosis_date;
	}

}
