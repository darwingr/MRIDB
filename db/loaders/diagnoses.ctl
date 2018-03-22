LOAD DATA
INFILE "db/data/diagnoses.csv"
INTO TABLE diagnoses
FIELDS TERMINATED BY ','
(id, condition_id, patient_id, physician_id, physician_notes, diagnoses_date DATE "yyyy-mm-dd")