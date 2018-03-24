GRANT SELECT, INSERT, UPDATE, DELETE ON conditions TO x2008ofj, x2009jpe, x2014gve, x2016mou;

GRANT SELECT, INSERT, UPDATE, DELETE ON devices TO x2008ofj, x2009jpe, x2014gve, x2016mou;

GRANT SELECT, INSERT, UPDATE, DELETE ON patients TO x2008ofj, x2009jpe, x2014gve, x2016mou;
    
GRANT SELECT, INSERT, UPDATE, DELETE ON physicians TO x2008ofj, x2009jpe, x2014gve, x2016mou:
    
GRANT SELECT, INSERT, UPDATE, DELETE ON technicians TO x2008ofj, x2009jpe, x2014gve, x2016mou;

ALTER TABLE diagnoses
    ADD CONSTRAINT FK_Condition_Id FOREIGN KEY(condition_id) REFERENCES conditions(id)
    ADD CONSTRAINT FK_Patient_ID FOREIGN KEY(patient_id) REFERENCES patients(id)
    ADD CONSTRAINT FK_Physician_id FOREIGN KEY(physician_id) REFERENCES physicians(id);
GRANT SELECT, INSERT, UPDATE, DELETE ON diagnoses TO x2008ofj, x2009jpe, x2014gve, x2016mou;
    
ALTER TABLE visits
    ADD CONSTRAINT FK_Visits_Patient_iD FOREIGN KEY(patient_id) REFERENCES patients(id);
GRANT SELECT, INSERT, UPDATE, DELETE ON visits TO x2008ofj, x2009jpe, x2014gve, x2016mou;

ALTER TABLE genomes
    ADD CONSTRAINT FK_VisitId FOREIGN KEY(visit_id) REFERENCES visits(id);
GRANT SELECT, INSERT, UPDATE, DELETE ON genomes TO x2008ofj, x2009jpe, x2014gve, x2016mou;

GRANT SELECT, INSERT, UPDATE, DELETE ON measurements TO x2008ofj, x2009jpe, x2014gve, x2016mou;

ALTER TABLE mri_scans
    ADD CONSTRAINT FK_Visit_Id FOREIGN KEY(visit_id) REFERENCES visits(id)
    ADD CONSTRAINT FK_Technician_Id FOREIGN KEY(technician_id) REFERENCES technicians(id)
    ADD CONSTRAINT FK_Device_Id FOREIGN KEY(device_id) REFERENCES devices(id);
GRANT SELECT, INSERT, UPDATE, DELETE ON mri_scans TO x2008ofj, x2009jpe, x2014gve, x2016mou;

GRANT SELECT, INSERT, UPDATE, DELETE ON treatments TO x2008ofj, x2009jpe, x2014gve, x2016mou;

ALTER TABLE regimens    
    ADD CONSTRAINT FK_Regimens_Patient_id FOREIGN KEY(patient_id) REFERENCES patients(id)
    ADD CONSTRAINT FK_physicianid FOREIGN KEY(physician_id) REFERENCES physicians(id)
    ADD CONSTRAINT FK_Treatment_Id FOREIGN KEY(treatment_id) REFERENCES treatments(id);
GRANT SELECT, INSERT, UPDATE, DELETE ON regimens TO x2008ofj, x2009jpe, x2014gve, x2016mou;

GRANT SELECT, INSERT, UPDATE, DELETE ON users TO x2008ofj, x2009jpe, x2014gve, x2016mou;

exit