options(readsize = 1006000, bindsize = 500000, rows = 32)
LOAD DATA
INFILE "db/data/mri_scans.txt"
INTO TABLE mri_scans
FIELDS TERMINATED BY ','
(id, technician_notes, technician_id, visit_id, device_id, vals varray count(constant 4788) (vals))