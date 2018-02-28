LOAD DATA
INFILE 'db/data/patients.csv'
INFILE 'db/data/visits.csv'
INFILE 'db/data/mri_scans.csv'
INFILE 'db/data/diagnoses.csv'
INFILE 'db/data/conditions.csv'
INFILE 'db/data/physicians.csv'
INFILE 'db/data/regimens.csv'
INFILE 'db/data/treatments.csv'
INFILE 'db/data/genomes.csv'
INFILE 'db/data/devices.csv'
INFILE 'db/data/technicians.csv'
INFILE 'db/data/measurements.csv'
INFILE 'db/data/users.csv'

INTO TABLE patients
FIELDS TERMINATED BY ','
(id, first_name, last_name)

INTO TABLE visits
FIELDS TERMINATED BY ','
(id, gender, check_in, check_out, patient_number)

INTO TABLE mri_scans
FIELDS TERMINATED BY ','
(id, technician_notes, technician_id, measurements, visit_id, device_id, image_filepath)

INTO TABLE diagnoses
FIELDS TERMINATED BY ','
(id, condition_id, patient_number, physician_id, test_results, date, change)

INTO TABLE conditions
FIELDS TERMINATED BY ','
(id, name, signs, symptoms)

INTO TABLE physicians
FIELDS TERMINATED BY ','
(id, first_name, last_name, specialty)

INTO TABLE regimens
FIELDS TERMINATED BY ','
(id, physician_id, start_date)

INTO TABLE treatments
FIELDS TERMINATED BY ','
(id, type)

INTO TABLE genomes
FIELDS TERMINATED BY ','
(id, sequence, date_taken, visit_id)

INTO TABLE devices
FIELDS TERMINATED BY ','
(id, manufacturer, model_number)

INTO TABLE technicians
FIELDS TERMINATED BY ','
(id, first_name, last_name)

INTO TABLE measurements
FIELDS TERMINATED BY ','
(id, label, region)

INTO TABLE users
FIELDS TERMINATED BY ','
(id, email, first_name, last_name, hippa_authorized)