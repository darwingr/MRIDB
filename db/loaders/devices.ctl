LOAD DATA
INFILE "db/data/devices.csv"
INTO TABLE devices
FIELDS TERMINATED BY ','
(id, manufacturer, model_number, hospital_location)