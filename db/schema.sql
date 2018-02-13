DROP TYPE measurement_set;
DROP TABLE visits;
DROP TABLE patients;
DROP TABLE mri_scans;
DROP TABLE diagnoses;
DROP TABLE regimens;
DROP TABLE measurements;
DROP TABLE genomes;
DROP TABLE conditions;
DROP TABLE treatments;
DROP TABLE devices;
DROP TABLE users;
DROP TABLE physicians;


CREATE TYPE measurement_set AS VARRAY(1125) OF DOUBLE;

CREATE TABLE patients
(
        number          INTEGER         NOT NULL,	
        first_name      VARCHAR2(20)    NOT NULL,
        last_name       VARCHAR2(20)    NOT NULL,

        PRIMARY KEY (number),
);
GRANT SELECT ON patients TO PUBLIC;

CREATE TABLE visits
(
        id                      INTEGER         NOT NULL,
        gender                  CHAR,
        check_in                TIMESTAMP,
        check_out               TIMESTAMP,
        patient_number          INTEGER         NOT NULL,

        PRIMARY KEY(id);

        CONSTRAINT patient_number FOREIGN KEY(patient_number); REFERENCES patients(number),
);
GRANT SELECT ON visits TO PUBLIC;


CREATE TABLE mri_scans
(
    id                  INTEGER            NOT NULL,
    technician_notes    VARCHAR2,
    technician_id       INTEGER,
    measurements        measurement_set    NOT NULL,
    visit_id            INTEGER            NOT NULL,
    device_id           INTEGER,
    
    PRIMARY KEY(id);
    
    CONSTRAINT visit_id FOREIGN KEY(visit_id) REFERENCES visits(id),
    CONSTRAINT technician_id FOREIGN KEY(technician_id); REFERENCES technicians(id),
    CONSTRAINT device_id FOREIGN KEY(device_id); REFERENCES devices(id),
);
GRANT SELECT ON mri_scans TO PUBLIC;


CREATE TABLE diagnoses
(
    id              INTEGER        NOT NULL,
    condition_id    INTEGER        NOT NULL,
    patient_number  INTEGER        NOT NULL,
    physician_id    INTEGER        NOT NULL,
    test_results    VARCHAR2(10),
    date            TIMESTAMP,
    change          VARCHAR2(5),
    
    PRIMARY KEY (id);
    
    CONSTRAINT condition_id  FOREIGN KEY(condition_id); REFERENCES conditions(id),
    CONSTRAINT patient_number  FOREIGN KEY(patient_number); REFERENCES patients(patient_number),
    CONSTRAINT physician_id FOREIGN KEY(physician_id); REFERENCES physicians(id),
);
GRANT SELECT ON diagnoses TO PUBLIC;

CREATE TABLE regimens
(
    id              INTEGER,
    physician_id    INTEGER,
    start_date      TIMESTAMP,
    
    PRIMARY KEY (id);
    
    CONSTRAINT physician_id FOREIGN KEY(physician_id) REFERENCES physicians(id),
);
GRANT SELECT ON regimens TO PUBLIC;

CREATE TABLE measurements
(
    id              INTEGER,
    label           VARCHAR2(20),
    region          VARCHAR2(20)        UNIQUE,
    
    PRIMARY KEY (id);
    
    CONSTRAINT physician_id FOREIGN KEY(physician_id) REFERENCES physicians(id),
);
GRANT SELECT ON regimens TO PUBLIC;

CREATE TABLE genomes
(
    id              INTEGER,
    sequence        BINARY,
    date_taken      TIMESTAMP,
    visit_id        TIMESTAMP,
    
    PRIMARY KEY (id);
    
    CONSTRAINT visit_id FOREIGN KEY(visit_id) REFERENCES visits(id),
);
GRANT SELECT ON genomes TO PUBLIC;

CREATE TABLE conditions
(
    id                      INTEGER,
    similar_conditions      VARCHAR2(50),
    name                    VARCHAR2(50),
    signs                   VARCHAR2(50),
    symptoms                VARCHAR2(2000),
    
    PRIMARY KEY (id);
);
GRANT SELECT ON conditions TO PUBLIC;

CREATE TABLE treatments
(
    id      INTEGER,
    type    VARCHAR2(30),
    
    PRIMARY KEY (id);
);
GRANT SELECT ON treatments TO PUBLIC;

CREATE TABLE devices
(
    id              INTEGER,
    manufacturer    VARCHAR2(20),
    model_number    VARCHAR2(15),
    
    PRIMARY KEY (id);
);
GRANT SELECT ON patients TO PUBLIC;

CREATE TABLE physicians
(
    id           INTEGER,
    first_name   VARCHAR2(20),
    last_name    VARCHAR2(20),
    specialty    VARCHAR2(25),
    
    PRIMARY KEY (id);
);
GRANT SELECT ON physicians TO PUBLIC;

CREATE TABLE users
(
    id                  INTEGER,
    email               VARCHAR2(50),
    first_name          VARCHAR2(20),
    last_name           VARCHAR2(20),
    hipaa_authorized    BOOLEAN,
    
    PRIMARY KEY (id);
);
GRANT SELECT ON users TO PUBLIC;