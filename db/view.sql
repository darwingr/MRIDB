CREATE or REPLACE VIEW patient_files AS
SELECT
  patients.id AS patient_id,
  patients.first_name AS patient_firstname,
  patients.last_name AS patient_lastname,
  patients.address AS patient_address,
  visits.gender AS visit_gender,
  visits.dob AS visit_dob,
  visits.check_in AS visit_checkin,
  visits.check_out AS visit_checkout,
  genomes.sequence AS genome_sequence,
  genomes.date_taken AS genome_datetaken,
  mri_scans.technician_notes AS mri_scan_technician_notes,
  technicians.first_name AS technician_firstname,
  technicians.last_name AS technician_lastname,
  devices.manufacturer AS device_manufacturer,
  devices.model_number AS device_model_number,
  devices.hospital_location AS device_hospital_location,
  diagnoses.diagnoses_date AS diagnosis_date,
  diagnoses.physician_notes AS diagnosis_physician_notes,
  regimens.physician_notes AS regimen_physician_notes,
  regimens.start_date AS regimen_start_date,
  conditions.name AS condition_name,
  conditions.signs AS condition_signs,
  conditions.symptoms AS condition_symptoms,
  treatments.treatment_type AS treatment_type,
  treatments.description AS treatment_description,
  physicians.id AS physician_id,
  physicians.first_name AS physician_firstname,
  physicians.last_name AS physician_lastname,
  physicians.specialty AS physician_specialty
FROM patients
  JOIN visits ON visits.patient_id = patients.id
  JOIN genomes ON genomes.visit_id = visits.id
  JOIN mri_scans ON mri_scans.visit_id = visits.id
  JOIN technicians ON technicians.id = mri_scans.technician_id
  JOIN devices ON devices.id = mri_scans.device_id
  JOIN diagnoses ON diagnoses.patient_id = patients.id
  JOIN regimens on regimens.patient_id = patients.id
  JOIN conditions ON conditions.id = diagnoses.condition_id
  JOIN treatments ON  treatments.id = regimens.treatment_id
  JOIN physicians ON physicians.id = diagnoses.physician_id;

CREATE or REPLACE VIEW Doctor_Diagnoses AS
SELECT
  physician_id,
  physician_firstname,
  physician_lastname,
  condition_name,
  condition_signs,
  condition_symptoms,
  diagnosis_date,
  diagnosis_physician_notes
FROM
  patient_files
WHERE
  diagnosis_date >= sysdate-45;

CREATE or REPLACE VIEW non_hipaa_authorized AS
SELECT
  patients.id AS patient_id,
  visits.gender AS visit_gender,
  EXTRACT(year FROM visits.dob) visit_dob,
  EXTRACT(year FROM visits.check_in) visit_checkin,
  EXTRACT(year FROM visits.check_out) visit_checkout,
  mri_scans.technician_notes AS mri_scan_technician_notes,
  mri_scans.measurements_array AS measurements_array,
  devices.manufacturer AS device_manufacturer,
  devices.model_number AS device_model_number,
  devices.hospital_location AS device_hospital_location,
  regimens.physician_notes AS regimen_physician_notes,
  EXTRACT(year FROM regimens.start_date) regimen_start_date,
  conditions.name AS condition_name,
  conditions.signs AS condition_signs,
  conditions.symptoms AS condition_symptoms,
  treatments.treatment_type AS treatment_type,
  treatments.description AS treatment_description
FROM patients
  JOIN visits ON visits.patient_id = patients.id
  JOIN mri_scans ON visits.id = mri_scans.visit_id
  JOIN technicians ON mri_scans.technician_id = technicians.id
  JOIN devices ON mri_scans.device_id = devices.id
  JOIN diagnoses ON patients.id = diagnoses.patient_id
  JOIN regimens on patients.id = regimens.patient_id
  JOIN conditions ON diagnoses.condition_id = conditions.id
  JOIN treatments ON regimens.treatment_id = treatments.id
  JOIN physicians ON diagnoses.physician_id = physicians.id
  JOIN physicians ON regimens.physician_id = physicians.id;

commit;
exit
