LOAD DATA
INFILE "db/data/regimens.csv"
INTO TABLE regimens
FIELDS TERMINATED BY ','
(id, physician_id, start_date)