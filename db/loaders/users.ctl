LOAD DATA
INFILE "db/data/users.csv"
INTO TABLE users
FIELDS TERMINATED BY ','
(id, username, email, first_name, last_name, hipaa_authorized)