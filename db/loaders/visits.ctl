LOAD DATA
INFILE "db/data/visits.csv"
INTO TABLE visits
FIELDS TERMINATED BY ','
(id, gender, patient_age, check_in DATE "yyyy-mm-dd hh24:mi:ss", check_out DATE "yyyy-mm-dd hh24:mi:ss", patient_number)