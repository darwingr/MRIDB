LOAD DATA
INFILE "db/data/regimens.csv"
INTO TABLE regimens
FIELDS TERMINATED BY ','
(id, patient_number, physician_id, physician_notes,treatment_id)