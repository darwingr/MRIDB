LOAD DATA
INFILE "db/data/genomes.csv"
INTO TABLE genomes
FIELDS TERMINATED BY ','
(id, sequence, date_taken, visit_id)