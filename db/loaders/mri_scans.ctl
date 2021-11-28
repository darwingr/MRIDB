OPTIONS (
  READSIZE=1006000,
  BINDSIZE=500000,
--  ROWS=64,
  ERRORS=999999,
--  skip=370,
  DIRECT=true,
  SKIP_INDEX_MAINTENANCE=true
)
-- only with direct path
UNRECOVERABLE
LOAD DATA
INFILE "db/data/mri_scans.csv"
INTO TABLE mri_scans
FIELDS TERMINATED BY ','
(id, technician_notes, technician_id, visit_id, device_id,
  measurements_array VARRAY COUNT(CONSTANT 4788) (measurements_array)
)
