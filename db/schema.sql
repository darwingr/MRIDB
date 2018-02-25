DROP TYPE measurement_set;
DROP TABLE technicians;
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

CREATE TABLE technicians
(
    id                      INTEGER(5)         NOT NULL    unique,
    first_name              VARCHAR(20)        NOT NULL,
    last_name               VARCHAR(20)        NOT NULL
);

CREATE TABLE patients
(
        number              INTEGER(9)         NOT NULL    unique,	
        first_name          VARCHAR2(20)       NOT NULL,
        last_name           VARCHAR2(20)       NOT NULL
);

CREATE TABLE visits
(
        id                  INTEGER(12)        NOT NULL    unique,
        gender              int(1)             NOT NULL,
        check_in            TIMESTAMP          NOT NULL,
        check_out           TIMESTAMP,
        patient_number      INTEGER            NOT NULL
);

CREATE TABLE mri_scans
(
    id                      INTEGER(12)        NOT NULL    unique,
    technician_notes        VARCHAR2(100),
    technician_id           INTEGER,
    measurements            measurement_set    NOT NULL,
    visit_id                INTEGER            NOT NULL,
    device_id               INTEGER
);

CREATE TABLE diagnoses
(
    id                      INTEGER(10)        NOT NULL    unique,
    condition_id            INTEGER            NOT NULL,
    patient_number          INTEGER            NOT NULL,
    physician_id            INTEGER            NOT NULL,
    physician_notes         VARCHAR2(200),    
    date                    TIMESTAMP,
);

CREATE TABLE regimens
(
    id                      INTEGER(10)        NOT NULL    unique,
    patient_number          INTEGER            NOT NULL,
    physician_id            INTEGER,           NOT NULL,
    physician_notes         VARCHAR(200),
    start_date              TIMESTAMP          NOT NULL,
    treatment_id            INTEGER            NOT NULL,
);

CREATE TABLE measurements
(
    id                      INTEGER,
    label                   VARCHAR2(100)      NOT NULL    unique,
    brain_region            VARCHAR2(20)
);

CREATE TABLE genomes
(
    id                      INTEGER(10)        NOT NULL    unique,
    sequence                VARCHAR(100)       NOT NULL,
    date_taken              TIMESTAMP          NOT NULL,
    visit_id                INTEGER            NOT NULL
);

CREATE TABLE conditions
(
    id                      INTEGER(5)         NOT NULL    unique,
    name                    VARCHAR2(50)       NOT NULL    unique,
    signs                   VARCHAR2(50),
    symptoms                VARCHAR2(2000)
);

CREATE TABLE treatments
(
    id                      INTEGER(6)         NOT NULL    unique,
    type                    VARCHAR2(10)       NOT NULL,
    description             VARCHAR(100)       NOT NULL
);

CREATE TABLE devices
(
    id                      INTEGER(5)         NOT NULL    unique,
    manufacturer            VARCHAR2(20),
    model_number            VARCHAR2(20),
    hospital_location       VARCHAR(20)        NOT NULL
);

CREATE TABLE physicians
(
    id                      INTEGER(5)         NOT NULL    unique,
    first_name              VARCHAR2(20)       NOT NULL,
    last_name               VARCHAR2(20)       NOT NULL,
    specialty               VARCHAR2(25)       NOT NULL
);

CREATE TABLE users
(
    id                      INTEGER(5)         NOT NULL    unique,
    username                VARCHAR(45)        NOT NULL    unique,
    email                   VARCHAR2(50)       NOT NULL    unique,
    first_name              VARCHAR2(20)       NOT NULL,
    last_name               VARCHAR2(20)       NOT NULL,
    hipaa_authorized        BOOLEAN,
);