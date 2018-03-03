LOAD DATA
INFILE "db/data/users.csv"
INTO TABLE users
FIELDS TERMINATED BY ','
(id, first_name, last_name, username, email, password, hipaa_authorized)