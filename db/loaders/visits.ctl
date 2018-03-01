LOAD DATA
INFILE "db/data/visits.csv"
INTO TABLE visits
FIELDS TERMINATED BY ','
(id, gender, patient_number)