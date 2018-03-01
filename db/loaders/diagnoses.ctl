LOAD DATA
INFILE "db/data/diagnoses.csv"
INTO TABLE diagnoses
FIELDS TERMINATED BY ','
(id, condition_id, patient_number, physician_id, physician_notes, diagnoses_date DATE "yyyy-mm-dd hh24:mi:ss")