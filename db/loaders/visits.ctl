LOAD DATA
INFILE "db/data/visits.csv"
INTO TABLE visits
FIELDS TERMINATED BY ','
(id, gender, dob DATE "dd/mm/yyyy hh24:mi:ss", check_in DATE "dd/mm/yyyy hh24:mi:ss", check_out DATE "dd/mm/yyyy hh24:mi:ss", patient_id)