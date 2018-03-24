CREATE or REPLACE VIEW patient_files AS
SELECT 
	patients.id AS "Patient: ID", 
	patients.first_name AS "Patient:|First Name", 
	patients.last_name AS "Patient:|Last Name", 
	patients.address AS "Patient: Address",  
	visits.gender AS"Visit: Gender", 
	visits.dob AS "Visit: Date Of Birth", 
	visits.check_in AS "Visit: Check In", 
	visits.check_out AS "Visit: Check Out", 
	genomes.sequence AS "Genome: Sequence", 
	genomes.date_taken AS "Genome: Sample Date",
	mri_scans.technician_notes AS "MRI Scan: Technician Notes",
	technicians.first_name AS "Technician:|First Name", 
	technicians.last_name AS "Technician:|Last Name", 
	devices.manufacturer AS "Device: Manufacturer", 
	devices.model_number AS "Device: Model Number", 
	devices.hospital_location AS "Device: Location",
	diagnoses.diagnoses_date AS "Diagnoses: Diagnoses Date",
	diagnoses.physician_notes AS "Diagnoses: Physician Notes",
	regimens.physician_notes AS "Regimen: Physician Notes", 
	regimens.start_date AS "Regimen: Start Date",
	conditions.name AS "Condition: Name", 
	conditions.signs AS "Condition: Signs", 
	conditions.symptoms AS "Condition: Symptoms",
	treatments.treatment_type AS "Treatment: Type", 
	treatments.description AS "Treatment: Description",
	physicians.id AS "Physician: ID",
	physicians.first_name AS "Physician:|First Name", 
	physicians.last_name AS "Physician:|Last Name", 
	physicians.specialty AS "Physician: Specialty" 
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
	"Physician: ID",
	"Physician:|First Name", 
	"Physician:|Last Name",
	"Condition: Name", 
	"Condition: Signs", 
	"Condition: Symptoms",
	"Diagnoses: Diagnoses Date",
	"Diagnoses: Physician Notes"
FROM
	patient_files
WHERE
	"Diagnoses: Diagnoses Date" >= sysdate-45;
	
CREATE or REPLACE VIEW non_hipaa_authorized AS
SELECT 
	patients.id AS "Patient: ID", 
	visits.gender AS "Visit: Gender", 
	EXTRACT(year FROM visits.dob) "Visit: Date Of Birth", 
	EXTRACT(year FROM visits.check_in) "Visit: Check In", 
	EXTRACT(year FROM visits.check_out) "Visit: Check Out", 
	mri_scans.technician_notes AS "MRI Scan: Technician Notes", 
	mri_scans.vals AS "MRI Scan: Measurements", 
	devices.manufacturer AS "Device: Manufacturer", 
	devices.model_number AS "Device: Model Number", 
	devices.hospital_location AS "Device: Location", 
	regimens.physician_notes AS "Regimen: Physician Notes", 
	EXTRACT(year FROM regimens.start_date) "Regimen: Start Date", 
	conditions.name AS "Condition: Name", 
	conditions.signs AS "Condition: Signs", 
	conditions.symptoms AS "Condition: Symptoms", 
	treatments.treatment_type"Treatment: Type", 
	treatments.description AS "Treatment: Description"
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