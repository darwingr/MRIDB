LOAD DATA
INFILE "db/data/physicians.csv"
INTO TABLE physicians
FIELDS TERMINATED BY ','
(id, first_name, last_name, specialty)