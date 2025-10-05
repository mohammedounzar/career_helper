DROP SEQUENCE resume_seq CASCADE;

ALTER TABLE job_description
DROP
COLUMN description;

ALTER TABLE job_description
    ADD description TEXT;