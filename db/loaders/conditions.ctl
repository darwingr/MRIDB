LOAD DATA
INFILE "db/data/conditions.csv"
INTO TABLE conditions
FIELDS TERMINATED BY ','
(id, name, signs, symptoms)