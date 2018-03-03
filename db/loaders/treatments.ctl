LOAD DATA
INFILE "db/data/treatments.csv"
INTO TABLE treatments
FIELDS TERMINATED BY ','
(id, treatment_type, description)