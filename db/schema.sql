DROP TYPE measurement_set;
CREATE TYPE measurement_set AS VARRAY(1125) OF DOUBLE;

DROP TABLE patients;
CREATE TABLE patients
(
	number		INTEGER		NOT NULL,
	first_name	VARCHAR(20)	NOT NULL,
	last_name	VARCHAR(20)	NOT NULL,
	
	PRIMARY KEY (number),
);
GRANT SELECT ON patients TO PUBLIC;

DROP TABLE visits;
CREATE TABLE visits
(
	id				INTEGER		NOT NULL,
	gender			CHAR,
	check_in 		TIMESTAMP,
	check_out 		TIMESTAMP,
	patient_number	INTEGER		NOT NULL,
	
	PRIMARY KEY(id);
	
	CONSTRAINT patient_number FOREIGN KEY(patient_number); REFERENCES patients(number),
);
GRANT SELECT ON visits TO PUBLIC;

DROP TABLE mri_scans;
CREATE TABLE mri_scans
(
	id					INTEGER			NOT NULL,
	technician_notes	VARCHAR2,	
	technician_id		INTEGER,
	measurements 		measurement_set NOT NULL,
	visit_id			INTEGER			NOT NULL,
	device_id			INTEGER,
	image_path			VARCHAR,
	
	PRIMARY KEY(id);
	
	CONSTRAINT visit_id FOREIGN KEY(visit_id) REFERENCES visits(id),
	CONSTRAINT technician_id FOREIGN KEY(technician_id); REFERENCES technicians(id),
	CONSTRAINT device_id FOREIGN KEY(device_id); REFERENCES devices(id),
);
GRANT SELECT ON mri_scans TO PUBLIC;

DROP TABLE diagnoses;
CREATE TABLE diagnoses
(
	id 				INTEGER		NOT NULL,
	condition_id	INTEGER		NOT NULL,
	patient_number	INTEGER		NOT NULL,
	physician_id	INTEGER		NOT NULL,
	test_results	VARCHAR,
	date			TIMESTAMP,
	change			VARCHAR,
	
	PRIMARY KEY (id);
	
	CONSTRAINT condition_id  FOREIGN KEY(condition_id); REFERENCES conditions(id),
	CONSTRAINT patient_number  FOREIGN KEY(patient_number); REFERENCES patients(patient_number),
	CONSTRAINT physician_id FOREIGN KEY(physician_id); REFERENCES physicians(id),
);
GRANT SELECT ON diagnoses TO PUBLIC;

DROP TABLE regimens;
CREATE TABLE regimens
(
	id				INTEGER,
	physician_id	INTEGER,
	start_date		TIMESTAMP,
	
	PRIMARY KEY (id);
	
	CONSTRAINT physician_id FOREIGN KEY(physician_id) REFERENCES physicians(id),
);
GRANT SELECT ON regimens TO PUBLIC;

DROP TABLE measurements;
CREATE TABLE measurements
(
	id				INTEGER,
	label			VARCHAR,
	region			VARCHAR		UNIQUE,
	
	PRIMARY KEY (id);
	
	CONSTRAINT physician_id FOREIGN KEY(physician_id) REFERENCES physicians(id),
);
GRANT SELECT ON regimens TO PUBLIC;

DROP TABLE genomes;
CREATE TABLE genomes
(
	id				INTEGER,
	sequence		BINARY,
	date_taken		TIMESTAMP,
	visit_id		TIMESTAMP,
	
	PRIMARY KEY (id);
	
	CONSTRAINT visit_id FOREIGN KEY(visit_id) REFERENCES visits(id),
);
GRANT SELECT ON genomes TO PUBLIC;

DROP TABLE conditions;
CREATE TABLE conditions
(
	id						INTEGER,
	similar_conditions		VARCHAR,
	name					VARCHAR,
	signs					VARCHAR,
	symptoms				VARCHAR,
	
	PRIMARY KEY (id);
);
GRANT SELECT ON conditions TO PUBLIC;

DROP TABLE treatments;
CREATE TABLE treatments
(
	id		INTEGER,
	type	VARCHAR,
	
	PRIMARY KEY (id);
);
GRANT SELECT ON treatments TO PUBLIC;

DROP TABLE devices;
CREATE TABLE devices
(
	id				INTEGER,
	manufacturer	VARCHAR,
	model_number	VARCHAR,
	
	PRIMARY KEY (id);
);
GRANT SELECT ON patients TO PUBLIC;

DROP TABLE physicians;
CREATE TABLE physicians
(
	id			INTEGER,
	first_name	VARCHAR,
	last_name	VARCHAR,
	specialty	VARCHAR,
	
	PRIMARY KEY (id);
);
GRANT SELECT ON physicians TO PUBLIC;

DROP TABLE users;
CREATE TABLE users
(
	id					INTEGER,
	email				VARCHAR,
	first_name			VARCHAR,
	last_name			VARCHAR,
	hipaa_authorized	BOOLEAN,
	
	PRIMARY KEY (id);
);
GRANT SELECT ON users TO PUBLIC;