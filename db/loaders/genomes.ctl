LOAD DATA
INFILE "db/data/genomes.csv"
INTO TABLE genomes
FIELDS TERMINATED BY ','
(id, sequence, date_taken DATE "yyyy-mm-dd hh24:mi:ss", visit_id)