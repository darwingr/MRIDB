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
        number              INTEGER            NOT NULL,	
        first_name          VARCHAR2(20)       NOT NULL,
        last_name           VARCHAR2(20)       NOT NULL,
);

CREATE TABLE visits
(
        id                  INTEGER            NOT NULL,
        gender              CHAR,
        check_in            TIMESTAMP,
        check_out           TIMESTAMP,
        patient_number      INTEGER            NOT NULL,
);

CREATE TABLE mri_scans
(
    id                      INTEGER            NOT NULL,
    technician_notes        VARCHAR2,
    technician_id           INTEGER,
    measurements            measurement_set    NOT NULL,
    visit_id                INTEGER            NOT NULL,
    device_id               INTEGER,
);

CREATE TABLE diagnoses
(
    id                      INTEGER            NOT NULL,
    condition_id            INTEGER            NOT NULL,
    patient_number          INTEGER            NOT NULL,
    physician_id            INTEGER            NOT NULL,
    test_results            VARCHAR2(10),
    date                    TIMESTAMP,
    change                  VARCHAR2(5),
);

CREATE TABLE regimens
(
    id                      INTEGER,
    physician_id            INTEGER,
    start_date              TIMESTAMP,
);

CREATE TABLE measurements
(
    id                      INTEGER,
    label                   VARCHAR2(20),
    region                  VARCHAR2(20)       UNIQUE,
);

CREATE TABLE genomes
(
    id                      INTEGER,
    sequence                BINARY,
    date_taken              TIMESTAMP,
    visit_id                TIMESTAMP,
);

CREATE TABLE conditions
(
    id                      INTEGER,
    similar_conditions      VARCHAR2(50),
    name                    VARCHAR2(50),
    signs                   VARCHAR2(50),
    symptoms                VARCHAR2(2000),
);

CREATE TABLE treatments
(
    id                      INTEGER,
    type                    VARCHAR2(30),
);

CREATE TABLE devices
(
    id                      INTEGER,
    manufacturer            VARCHAR2(20),
    model_number            VARCHAR2(15),
);

CREATE TABLE physicians
(
    id                      INTEGER,
    first_name              VARCHAR2(20),
    last_name               VARCHAR2(20),
    specialty               VARCHAR2(25),
);

CREATE TABLE users
(
    id                      INTEGER,
    email                   VARCHAR2(50),
    first_name              VARCHAR2(20),
    last_name               VARCHAR2(20),
    hipaa_authorized        BOOLEAN,
);