DROP TABLE users CASCADE CONSTRAINT;
DROP TABLE physicians CASCADE CONSTRAINT;
DROP TABLE devices CASCADE CONSTRAINT;
DROP TABLE treatments CASCADE CONSTRAINT;
DROP TABLE conditions CASCADE CONSTRAINT;
DROP TABLE genomes CASCADE CONSTRAINT;
DROP TABLE measurements CASCADE CONSTRAINT;
DROP TABLE regimens CASCADE CONSTRAINT;
DROP TABLE diagnoses CASCADE CONSTRAINT;
DROP TABLE mri_scans CASCADE CONSTRAINT;
DROP TABLE visits CASCADE CONSTRAINT;
DROP TABLE patients CASCADE CONSTRAINT;
DROP TABLE technicians CASCADE CONSTRAINT;

CREATE TABLE technicians
(
    id                      NUMBER(5)          NOT NULL,
    first_name              VARCHAR2(20)       NOT NULL,
    last_name               VARCHAR2(20)       NOT NULL
);

CREATE TABLE patients
(
    id                  NUMBER(9)          NOT NULL,	
    first_name          VARCHAR2(20)       NOT NULL,
    last_name           VARCHAR2(20)       NOT NULL
);

CREATE TABLE visits
(
        id                  NUMBER        NOT NULL,
        gender              NUMBER(1)     NOT NULL,
        patient_age         NUMBER(3),
        check_in            TIMESTAMP     NOT NULL,
        check_out           TIMESTAMP,
        patient_number      NUMBER        NOT NULL
);

CREATE TABLE mri_scans
(
    id                      NUMBER(12)        NOT NULL,
    technician_notes        VARCHAR2(1000),
    technician_id           NUMBER(3),
    visit_id                NUMBER(3)         NOT NULL,
    device_id               NUMBER(2)
);

CREATE TABLE diagnoses
(
    id                      NUMBER(3)       NOT NULL,
    condition_id            NUMBER(2)       NOT NULL,
    patient_number          NUMBER(3)       NOT NULL,
    physician_id            NUMBER(3)       NOT NULL,
    physician_notes         VARCHAR2(2000),
    diagnoses_date          TIMESTAMP       NOT NULL
);

CREATE TABLE regimens
(
    id                      NUMBER(10)        NOT NULL,
    patient_number          NUMBER(3)         NOT NULL,
    physician_id            NUMBER(3)         NOT NULL,
    physician_notes         VARCHAR2(4000),
    start_date              TIMESTAMP         NOT NULL,
    treatment_id            NUMBER(1)         NOT NULL
);

CREATE TABLE measurements
(
    id                      NUMBER           NOT NULL,
    label                   VARCHAR2(50)     NOT NULL,
    brain_region            VARCHAR2(20)
);

CREATE TABLE genomes
(
    id                      NUMBER(10)        NOT NULL,
    sequence                VARCHAR2(120)     NOT NULL,
    date_taken              TIMESTAMP         NOT NULL,
    visit_id                NUMBER            NOT NULL
);

CREATE TABLE conditions
(
    id                      NUMBER(5)         NOT NULL,
    name                    VARCHAR2(50)      NOT NULL    UNIQUE,
    signs                   VARCHAR2(100),
    symptoms                VARCHAR2(4000)
);

CREATE TABLE treatments
(
    id                      NUMBER(6)         NOT NULL,
    treatment_type          VARCHAR2(4000)    NOT NULL,
    description             VARCHAR2(1000)    NOT NULL
);

CREATE TABLE devices
(
    id                      NUMBER(5),
    manufacturer            VARCHAR2(100),
    model_number            VARCHAR2(25),
    hospital_location       VARCHAR2(100)     NOT NULL
);

CREATE TABLE physicians
(
    id                      NUMBER            NOT NULL,
    first_name              VARCHAR2(20)      NOT NULL,
    last_name               VARCHAR2(20)      NOT NULL,
    specialty               VARCHAR2(100)     NOT NULL
);

CREATE TABLE users
(
    id                      NUMBER            NOT NULL,
    first_name              VARCHAR2(20)      NOT NULL,
    last_name               VARCHAR2(20)      NOT NULL,
    username                VARCHAR2(45)      NOT NULL,
    email                   VARCHAR2(50)      NOT NULL,
    password                VARCHAR2(8)       NOT NULL,
    hipaa_authorized        NUMBER(1)         NOT NULL CHECK  (hipaa_authorized in (0,1))
);

commit;
exit