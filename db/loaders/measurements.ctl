LOAD DATA
INFILE "db/data/measurements.csv"
INTO TABLE measurements
FIELDS TERMINATED BY ','
(id, hemisphere, label, brain_region)