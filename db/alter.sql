GRANT SELECT ON conditions TO PUBLIC;

GRANT SELECT ON devices TO PUBLIC;
ALTER TABLE patients
	DROP CONSTRAINT PK_Patient;
	ADD CONSTRAINT PK_Patient PRIMARY KEY(id) ON DELETE RESTRICT ON UPDATE CASCADE
GRANT SELECT ON patients TO PUBLIC;
    
GRANT SELECT ON physicians TO PUBLIC;
    
GRANT SELECT ON technicians TO PUBLIC;

ALTER TABLE diagnoses
    ADD CONSTRAINT FK_Condition_Id FOREIGN KEY(condition_id) REFERENCES conditions(id)
    ADD CONSTRAINT FK_Patient_Num FOREIGN KEY(patient_id) REFERENCES patients(id)
    ADD CONSTRAINT FK_Physician_id FOREIGN KEY(physician_id) REFERENCES physicians(id);
GRANT SELECT ON diagnoses TO PUBLIC;
    
ALTER TABLE visits
    ADD CONSTRAINT FK_Patient_Number FOREIGN KEY(patient_id) REFERENCES patients(id);
GRANT SELECT ON visits TO PUBLIC;

ALTER TABLE genomes
    ADD CONSTRAINT FK_VisitId FOREIGN KEY(visit_id) REFERENCES visits(id);
GRANT SELECT ON genomes TO PUBLIC;

GRANT SELECT ON measurements TO PUBLIC;

ALTER TABLE mri_scans
    ADD CONSTRAINT FK_Visit_Id FOREIGN KEY(visit_id) REFERENCES visits(id)
    ADD CONSTRAINT FK_Technician_Id FOREIGN KEY(technician_id) REFERENCES technicians(id)
    ADD CONSTRAINT FK_Device_Id FOREIGN KEY(device_id) REFERENCES devices(id);
GRANT SELECT ON mri_scans TO PUBLIC;

GRANT SELECT ON treatments TO PUBLIC;    

ALTER TABLE regimens    
    ADD CONSTRAINT FK_Patient_numb FOREIGN KEY(patient_number) REFERENCES patients(id)
    ADD CONSTRAINT FK_physicianid FOREIGN KEY(physician_id) REFERENCES physicians(id)
    ADD CONSTRAINT FK_Treatment_Id FOREIGN KEY(treatment_id) REFERENCES treatments(id);
GRANT SELECT ON regimens TO PUBLIC;

GRANT SELECT ON users TO PUBLIC;

exit