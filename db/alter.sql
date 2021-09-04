ALTER TABLE diagnoses
    ADD CONSTRAINT FK_Condition_Id FOREIGN KEY(condition_id) REFERENCES conditions(id)
    ADD CONSTRAINT FK_Patient_ID FOREIGN KEY(patient_id) REFERENCES patients(id)
    ADD CONSTRAINT FK_Physician_id FOREIGN KEY(physician_id) REFERENCES physicians(id);

ALTER TABLE visits
    ADD CONSTRAINT FK_Visits_Patient_iD FOREIGN KEY(patient_id) REFERENCES patients(id);

ALTER TABLE genomes
    ADD CONSTRAINT FK_VisitId FOREIGN KEY(visit_id) REFERENCES visits(id);

ALTER TABLE mri_scans
    ADD CONSTRAINT FK_Visit_Id FOREIGN KEY(visit_id) REFERENCES visits(id)
    ADD CONSTRAINT FK_Technician_Id FOREIGN KEY(technician_id) REFERENCES technicians(id)
    ADD CONSTRAINT FK_Device_Id FOREIGN KEY(device_id) REFERENCES devices(id);

ALTER TABLE regimens    
    ADD CONSTRAINT FK_Regimens_Patient_id FOREIGN KEY(patient_id) REFERENCES patients(id)
    ADD CONSTRAINT FK_physicianid FOREIGN KEY(physician_id) REFERENCES physicians(id)
    ADD CONSTRAINT FK_Treatment_Id FOREIGN KEY(treatment_id) REFERENCES treatments(id);

exit
