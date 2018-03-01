LOAD DATA
INFILE "db/data/treatments.csv"
INTO TABLE treatments
FIELDS TERMINATED BY ','
(id, type, description)