LOAD DATA
INFILE "db/data/technicians.csv"
INTO TABLE technicians
FIELDS TERMINATED BY ','
(id, first_name, last_name)