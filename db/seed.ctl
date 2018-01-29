LOAD DATA
INFILE "....patients.dat"
INTO TABLE patients
FIELDS TERMINATED BY ','
(number, first_name, last_name)

LOAD DATA
INFILE "....visits.dat"
INTO TABLE visits
FIELDS TERMINATED BY ','
(id, gender, check_in, check_out, patient_number)

LOAD DATA
INFILE "....mri_scans.dat"
INTO TABLE mri_scans
FIELDS TERMINATED BY ','
(id, technician_notes, technician_id, measurements, visit_id, device_id, image_filepath)

LOAD DATA
INFILE "....diagnoses.dat"
INTO TABLE diagnoses
FIELDS TERMINATED BY ','
(id, condition_id, patient_number, physician_id, test_results, date, change)

LOAD DATA
INFILE "....conditions.dat"
INTO TABLE conditions
FIELDS TERMINATED BY ','
(id, name, signs, symptoms)

LOAD DATA
INFILE "....physicians.dat"
INTO TABLE physicians
FIELDS TERMINATED BY ','
(id, first_name, last_name, specialty)

LOAD DATA
INFILE "....regimens.dat"
INTO TABLE regimens
FIELDS TERMINATED BY ','
(id, physician_id, start_date)

LOAD DATA
INFILE "....treatments.dat"
INTO TABLE treatments
FIELDS TERMINATED BY ','
(id, type)

LOAD DATA
INFILE "....genomes.dat"
INTO TABLE genomes
FIELDS TERMINATED BY ','
(id, sequence, date_taken, visit_id)

LOAD DATA
INFILE "....devices.dat"
INTO TABLE devices
FIELDS TERMINATED BY ','
(id, manufacturer, model_number)

LOAD DATA
INFILE "....technicians.dat"
INTO TABLE technicians
FIELDS TERMINATED BY ','
(id, first_name, last_name)

LOAD DATA
INFILE "....measurements.dat"
INTO TABLE measurements
FIELDS TERMINATED BY ','
(id, label, region)

LOAD DATA
INFILE "....users.dat"
INTO TABLE users
FIELDS TERMINATED BY ','