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

CREATE TABLE technicians
(
    id                      NUMBER(5)          NOT NULL    unique,
    first_name              VARCHAR2(20)        NOT NULL,
    last_name               VARCHAR2(20)        NOT NULL
);

CREATE TABLE patients
(
    id                  NUMBER(9)          NOT NULL    unique,	
    first_name          VARCHAR2(20)       NOT NULL,
    last_name           VARCHAR2(20)       NOT NULL
);

CREATE TABLE visits
(
        id                  NUMBER(12)        NOT NULL    unique,
        gender              NUMBER(1)         NOT NULL,
        check_in            TIMESTAMP,
        check_out           TIMESTAMP,
        patient_number      NUMBER            NOT NULL
);

CREATE TABLE mri_scans
(
    id                      NUMBER(12)        NOT NULL    unique,
    technician_notes        VARCHAR2(1000),
    technician_id           NUMBER,
    visit_id                NUMBER            NOT NULL,
    device_id               NUMBER
);

CREATE TABLE diagnoses
(
    id                      NUMBER(3)       NOT NULL,
    condition_id            NUMBER(3)       NOT NULL,
    patient_number          NUMBER(3)       NOT NULL,
    physician_id            NUMBER(3)       NOT NULL,
    physician_notes         VARCHAR2(2000)
);

CREATE TABLE regimens
(
    id                      NUMBER(10)        NOT NULL    unique,
    patient_number          NUMBER            NOT NULL,
    physician_id            NUMBER            NOT NULL,
    physician_notes         VARCHAR(4000),
    treatment_id            NUMBER            NOT NULL
);

CREATE TABLE measurements
(
    id                      NUMBER,
    label                   VARCHAR2(50)     NOT NULL,
    brain_region            VARCHAR2(20)
);

CREATE TABLE genomes
(
    id                      NUMBER(10)        NOT NULL    unique,
    sequence                VARCHAR(120)      NOT NULL,
    visit_id                NUMBER
);

CREATE TABLE conditions
(
    id                      NUMBER(5)         NOT NULL    unique,
    name                    VARCHAR2(50)      NOT NULL    unique,
    signs                   VARCHAR2(100),
    symptoms                VARCHAR2(4000)
);

CREATE TABLE treatments
(
    id                      NUMBER(6)         NOT NULL    unique,
    type                    VARCHAR2(4000)    NOT NULL,
    description             VARCHAR(1000)     NOT NULL
);

CREATE TABLE devices
(
    id                      NUMBER(5)         NOT NULL    unique,
    manufacturer            VARCHAR2(100),
    model_number            VARCHAR2(25),
    hospital_location       VARCHAR(100)       NOT NULL
);

CREATE TABLE physicians
(
    id                      NUMBER            NOT NULL    unique,
    first_name              VARCHAR2(20)      NOT NULL,
    last_name               VARCHAR2(20)      NOT NULL,
    specialty               VARCHAR2(100)      NOT NULL
);

CREATE TABLE users
(
    id                      NUMBER            NOT NULL    unique,
    username                VARCHAR(45)       NOT NULL    unique,
    email                   VARCHAR2(50)      NOT NULL    unique,
    first_name              VARCHAR2(20)      NOT NULL,
    last_name               VARCHAR2(20)      NOT NULL,
    hipaa_authorized        NUMBER(1)         NOT NULL CHECK  (hipaa_authorized in (0,1))
);

commit;
cl scr
