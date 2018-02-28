LOAD DATA
INFILE "db/data/measurements.csv"
INTO TABLE measurements
FIELDS TERMINATED BY ','
(id, label, brain_region)