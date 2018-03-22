LOAD DATA
INFILE "db/data/patients.csv"
INTO TABLE patients
FIELDS TERMINATED BY ','
(id, first_name, last_name, address)