DROP TABLE conditions CASCADE CONSTRAINT;
DROP TABLE devices CASCADE CONSTRAINT;
DROP TABLE diagnoses CASCADE CONSTRAINT;
DROP TABLE genomes CASCADE CONSTRAINT;
DROP TABLE measurements CASCADE CONSTRAINT;
DROP TABLE mri_scans CASCADE CONSTRAINT;
DROP TABLE patients CASCADE CONSTRAINT;
DROP TABLE physicians CASCADE CONSTRAINT;
DROP TABLE regimens CASCADE CONSTRAINT;
DROP TABLE technicians CASCADE CONSTRAINT;
DROP TABLE treatments CASCADE CONSTRAINT;
DROP TABLE users CASCADE CONSTRAINT;
DROP TABLE visits CASCADE CONSTRAINT;
CREATE OR REPLACE TYPE numbers AS VARRAY(4788) of NUMBER;
/

CREATE TABLE conditions
(
    id                      NUMBER(5)         GENERATED BY DEFAULT ON NULL AS IDENTITY,
    name                    VARCHAR2(35)      NOT NULL    UNIQUE,
    signs                   VARCHAR2(70),
    symptoms                VARCHAR2(250),
    CONSTRAINT PK_Conditions PRIMARY KEY (id)
);

CREATE TABLE devices
(
    id                      NUMBER(5)         GENERATED BY DEFAULT ON NULL AS IDENTITY,
    manufacturer            VARCHAR2(70),
    model_number            VARCHAR2(20),
    hospital_location       VARCHAR2(20)      NOT NULL,
    CONSTRAINT PK_Devices PRIMARY KEY (id)
);

CREATE TABLE diagnoses
(
    id                      NUMBER(5)         GENERATED BY DEFAULT ON NULL AS IDENTITY,
    condition_id            NUMBER(1)         NOT NULL,
    patient_id              NUMBER(5)         NOT NULL,
    physician_id            NUMBER(5)         NOT NULL,
    physician_notes         VARCHAR2(150),
    diagnoses_date          TIMESTAMP(0),
    CONSTRAINT PK_Diagnoses PRIMARY KEY (id)
);

CREATE TABLE genomes
(
    id                      NUMBER(5)         GENERATED BY DEFAULT ON NULL AS IDENTITY,
    sequence                VARCHAR2(100),
    date_taken              TIMESTAMP(0),
    visit_id                NUMBER(5)         NOT NULL,
    CONSTRAINT PK_Genomes PRIMARY KEY (id)
);

CREATE TABLE measurements
(
    id                      NUMBER(5)         GENERATED BY DEFAULT ON NULL AS IDENTITY,
    hemisphere              VARCHAR2(20)      NOT NULL,
    label                   VARCHAR2(60)      NOT NULL,
    brain_region            VARCHAR2(25),
    CONSTRAINT PK_Measurements PRIMARY KEY (id)
);

CREATE TABLE mri_scans
(
    id                      NUMBER(5)         GENERATED BY DEFAULT ON NULL AS IDENTITY,
    technician_notes        VARCHAR2(150),
    technician_id           NUMBER(5),
    visit_id                NUMBER(5)         NOT NULL,
    device_id               NUMBER(5),
    vals                    numbers,
    CONSTRAINT PK_MRI_Scans PRIMARY KEY (id)
);

CREATE TABLE patients
(
    id                  NUMBER(5)             GENERATED BY DEFAULT ON NULL AS IDENTITY,	
    first_name          VARCHAR2(15)          NOT NULL,
    last_name           VARCHAR2(20)          NOT NULL,
    address             VARCHAR2(30)          NOT NULL,
    CONSTRAINT PK_Patient PRIMARY KEY(id)
);

CREATE TABLE physicians
(
    id                      NUMBER(5)         GENERATED BY DEFAULT ON NULL AS IDENTITY,
    first_name              VARCHAR2(10)      NOT NULL,
    last_name               VARCHAR2(15)      NOT NULL,
    specialty               VARCHAR2(50)      NOT NULL,
    CONSTRAINT PK_Physicians PRIMARY KEY (id)
);

CREATE TABLE regimens
(
    id                      NUMBER(5)         GENERATED BY DEFAULT ON NULL AS IDENTITY,
    patient_id              NUMBER(5)         NOT NULL,
    physician_id            NUMBER(3)         NOT NULL,
    physician_notes         VARCHAR2(150),
    start_date              TIMESTAMP(0),
    treatment_id            NUMBER(1)         NOT NULL,
    CONSTRAINT PK_Regimens PRIMARY KEY (id)
);

CREATE TABLE technicians
(
    id                      NUMBER(5)         GENERATED BY DEFAULT ON NULL AS IDENTITY,
    first_name              VARCHAR2(10)      NOT NULL,
    last_name               VARCHAR2(10)      NOT NULL,
    CONSTRAINT PK_Technicians PRIMARY KEY (id)
);

CREATE TABLE treatments
(
    id                      NUMBER(5)         GENERATED BY DEFAULT ON NULL AS IDENTITY,
    treatment_type          VARCHAR2(160),
    description             VARCHAR2(140),
    CONSTRAINT PK_Treatments PRIMARY KEY (id)
);

CREATE TABLE users
(
    id                      NUMBER(5)         GENERATED BY DEFAULT ON NULL AS IDENTITY,
    first_name              VARCHAR2(20)      NOT NULL,
    last_name               VARCHAR2(20)      NOT NULL,
    username                VARCHAR2(10)      NOT NULL    UNIQUE,
    email                   VARCHAR2(50)      NOT NULL    UNIQUE,
    password                VARCHAR2(8)       NOT NULL,
    hipaa_authorized        NUMBER(1)         NOT NULL    CHECK  (hipaa_authorized in (0,1)),
    CONSTRAINT PK_Users PRIMARY KEY (id)
);
 
CREATE TABLE visits
(
    id                  NUMBER(5)         GENERATED BY DEFAULT ON NULL AS IDENTITY,
    gender              NUMBER(1)         NOT NULL    CHECK (gender in (1,2,3)),
    dob                 TIMESTAMP(0)      NOT NULL,
    check_in            TIMESTAMP(0)      NOT NULL,
    check_out           TIMESTAMP(0),
    patient_id          NUMBER(5)         NOT NULL,
    CONSTRAINT Chk_dob CHECK (dob <= check_in),
    CONSTRAINT Chk_in CHECK (check_in <= check_out),
    CONSTRAINT Chk_out CHECK (check_out >= check_in),
    CONSTRAINT PK_Visit PRIMARY KEY (id)
);
exit