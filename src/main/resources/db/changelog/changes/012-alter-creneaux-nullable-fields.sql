-- liquibase formatted sql

-- changeset planning:005-alter-creneaux-nullable-fields

-- Rendre les ressources optionnelles
ALTER TABLE creneaux_planning
    ALTER COLUMN id_class_voyage DROP NOT NULL;

ALTER TABLE creneaux_planning
    ALTER COLUMN id_vehicule DROP NOT NULL;

ALTER TABLE creneaux_planning
    ALTER COLUMN id_chauffeur DROP NOT NULL;
