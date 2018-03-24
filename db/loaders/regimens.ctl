LOAD DATA
INFILE "db/data/regimens.csv"
INTO TABLE regimens
FIELDS TERMINATED BY ','
(id, patient_id, physician_id, physician_notes,start_date DATE "yyyy-mm-dd hh24:mi:ss",treatment_id)