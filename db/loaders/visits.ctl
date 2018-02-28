LOAD DATA
INFILE "db/data/visits.csv"
INTO TABLE visits
FIELDS TERMINATED BY ','
(id, gender, check_in, check_out, patient_number)