ALTER TABLE patients
    ADD CONSTRAINT PK_Patient PRIMARY KEY(number);
    GRANT SELECT ON patients TO PUBLIC;

ALTER TABLE visits
    ADD CONSTRAINT PK_Visit PRIMARY KEY(id);
    ADD CONSTRAINT FK_Patient_Number FOREIGN KEY(patient_number); REFERENCES patients(number),
    GRANT SELECT ON visits TO PUBLIC;

ALTER TABLE mri_scans
    ADD CONSTRAINT PK_MRI_Scans PRIMARY KEY(id);
    ADD CONSTRAINT FK_Visit_Id FOREIGN KEY(visit_id) REFERENCES visits(id),
    ADD CONSTRAINT FK_Technician_Id FOREIGN KEY(technician_id); REFERENCES technicians(id),
    ADD CONSTRAINT FK_Device_Id FOREIGN KEY(device_id); REFERENCES devices(id),
    GRANT SELECT ON mri_scans TO PUBLIC;

ALTER TABLE diagnoses
    ADD CONSTRAINT PK_Diagnoses PRIMARY KEY (id);
    ADD CONSTRAINT FK_Condition_Id FOREIGN KEY(condition_id); REFERENCES conditions(id),
    ADD CONSTRAINT FK_Patient_Number FOREIGN KEY(patient_number); REFERENCES patients(number),
    ADD CONSTRAINT FK_Physician_Id FOREIGN KEY(physician_id); REFERENCES physicians(id),
    GRANT SELECT ON diagnoses TO PUBLIC;

ALTER TABLE regimens
    ADD CONSTRAINT PK_Regimens PRIMARY KEY (id);
    ADD CONSTRAINT FK_Patient_number FOREIGN KEY(patient_number) REFERENCES patients(number)
    ADD CONSTRAINT FK_Physician_Id FOREIGN KEY(physician_id) REFERENCES physicians(id),
    ADD CONSTRAINT FK_Treatment_Id FOREIGN KEY(treatment_id) REFERENCES treatments(id),
    GRANT SELECT ON regimens TO PUBLIC;

ALTER TABLE measurements
    ADD CONSTRAINT PK_Measurements PRIMARY KEY (id);
    ADD CONSTRAINT FK_Physician_Id FOREIGN KEY(physician_id) REFERENCES physicians(id)
    GRANT SELECT ON regimens TO PUBLIC;

ALTER TABLE genomes
    ADD CONSTRAINT PK_Genomes PRIMARY KEY (id);
    ADD CONSTRAINT FK_Visit_Id FOREIGN KEY(visit_id) REFERENCES visits(id),
    GRANT SELECT ON genomes TO PUBLIC;

ALTER TABLE conditions
    ADD CONSTRAINT PK_Conditions PRIMARY KEY (id);
    GRANT SELECT ON conditions TO PUBLIC;

ALTER TABLE treatments
    ADD CONSTRAINT PK_Treatments PRIMARY KEY (id);
    GRANT SELECT ON treatments TO PUBLIC;

ALTER TABLE devices
    ADD CONSTRAINT PK_Devices PRIMARY KEY (id);
    GRANT SELECT ON patients TO PUBLIC;

ALTER TABLE physicians
    ADD CONSTRAINT PK_Physicians PRIMARY KEY (id);
    GRANT SELECT ON physicians TO PUBLIC;

ALTER TABLE users
    ADD CONSTRAINT PK_Users PRIMARY KEY (id);
    GRANT SELECT ON users TO PUBLIC;