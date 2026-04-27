-- liquibase formatted sql

-- changeset yowyob:10

-- ============================================================
-- SEED — Données de référence Bus Station
-- Mot de passe : Password123 (BCrypt hash)
-- ============================================================

-- ─────────────────────────────────────────────────────────────
-- 1. USERS
-- Colonnes réelles : user_id, nom, prenom, genre, username,
--                   email, password, tel_number, roles
-- ─────────────────────────────────────────────────────────────
INSERT INTO users (user_id, nom, prenom, genre, username, email, password, tel_number, roles)
VALUES
  ('aa000001-0000-0000-0000-000000000001','FOUDA NKOLO','Émile','MALE','bsm_mvan','e.fouda@gare-mvan.cm','$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.','222305060','BUS_STATION_MANAGER'),
  ('aa000001-0000-0000-0000-000000000002','ABENA MANGA','Cécile','FEMALE','bsm_lac','c.abena@gare-lac.cm','$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.','222312020','BUS_STATION_MANAGER'),
  ('aa000001-0000-0000-0000-000000000011','NKONGO','Théodore','MALE','nkongo_theo','t.nkongo@generalexpress.cm','$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.','655001122','AGENCE_VOYAGE'),
  ('aa000001-0000-0000-0000-000000000012','MOUKOURI','Sandrine','FEMALE','sandrine_te','s.moukouri@touristiqueexpress.cm','$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.','655778899','AGENCE_VOYAGE'),
  ('aa000001-0000-0000-0000-000000000013','FOTSO','Boniface','MALE','fotso_btu','b.fotso@btu-transport.cm','$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.','656445566','AGENCE_VOYAGE'),
  ('aa000001-0000-0000-0000-000000000014','OWONA','Patricia','FEMALE','patricia_cl','p.owona@confortlines.cm','$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.','654223344','AGENCE_VOYAGE'),
  ('aa000001-0000-0000-0000-000000000021','MVONDO','Paul','MALE','paul_mvondo','paul.mvondo@gmail.com','$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.','677112233','USAGER'),
  ('aa000001-0000-0000-0000-000000000022','NGONO','Marie-Claire','FEMALE','mc_ngono','mc.ngono@yahoo.fr','$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.','699887766','USAGER'),
  ('aa000001-0000-0000-0000-000000000023','BELLO','Ibrahim','MALE','ibrahim_b','ibrahim.bello@gmail.com','$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.','676543210','USAGER'),
  ('aa000001-0000-0000-0000-000000000031','MBARGA','Jean-Pierre','MALE','chauffeur_jp','jp.mbarga@generalexpress.cm','$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.','655341122','EMPLOYE'),
  ('aa000001-0000-0000-0000-000000000032','ESSOMBA','Hervé','MALE','chauffeur_herve','h.essomba@generalexpress.cm','$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.','655560033','EMPLOYE'),
  ('aa000001-0000-0000-0000-000000000033','ATEBA','Serge','MALE','chauffeur_serge','s.ateba@generalexpress.cm','$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.','655789900','EMPLOYE'),
  ('aa000001-0000-0000-0000-000000000034','BELIBI','Christelle','FEMALE','belibi_christelle','c.belibi@generalexpress.cm','$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.','655110022','EMPLOYE'),
  ('aa000001-0000-0000-0000-000000000035','ABENA','Pierre','MALE','chauffeur_pierre','p.abena@touristiqueexpress.cm','$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.','655001199','EMPLOYE'),
  ('aa000001-0000-0000-0000-000000000036','NGUEMA','Paul','MALE','chauffeur_paul','p.nguema@touristiqueexpress.cm','$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.','655223300','EMPLOYE'),
  ('aa000001-0000-0000-0000-000000000037','KAMGA','Aristide','MALE','chauffeur_aristide','a.kamga@btu-transport.cm','$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.','656112233','EMPLOYE'),
  ('aa000001-0000-0000-0000-000000000038','TIENTCHEU','Roger','MALE','chauffeur_roger','r.tientcheu@btu-transport.cm','$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.','656445577','EMPLOYE'),
  ('aa000001-0000-0000-0000-000000000040','ZAMBO','Eric','MALE','chauffeur_eric','e.zambo@confortlines.cm','$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.','654334455','EMPLOYE')
ON CONFLICT (user_id) DO NOTHING;

-- ─────────────────────────────────────────────────────────────
-- 2. GARES ROUTIÈRES
-- ─────────────────────────────────────────────────────────────
INSERT INTO gare_routiere (id_gare_routiere, nom_gare_routiere, ville, quartier, description, horaires, services, manager_id, version)
VALUES
  ('bb000002-0000-0000-0000-000000000001','Gare Routière de Mvan','Yaoundé','Mvan','Principal terminus Sud de Yaoundé.','Lun–Dim : 04h00–23h00','Salle d''attente climatisée,Consigne bagages,Restauration,Wi-Fi gratuit,Toilettes,Parking sécurisé','aa000001-0000-0000-0000-000000000001',0),
  ('bb000002-0000-0000-0000-000000000002','Gare Routière du Lac Municipal','Yaoundé','Centre-ville','Gare centrale de Yaoundé.','Lun–Dim : 05h00–22h00','Salle d''attente,Restauration rapide,Toilettes,Parking,Billetterie électronique','aa000001-0000-0000-0000-000000000002',0),
  ('bb000002-0000-0000-0000-000000000003','Gare Routière de Bonabéri','Douala','Bonabéri','Gare de Bonabéri, porte d''entrée Ouest de Douala.','Lun–Dim : 04h30–22h30','Salle d''attente,Restauration,Toilettes,Parking,Guichet Mobile Money','aa000001-0000-0000-0000-000000000001',0),
  ('bb000002-0000-0000-0000-000000000004','Gare Routière de Bessengué','Douala','Bessengué','Gare centrale de Douala, au cœur du quartier Akwa.','Lun–Dim : 04h00–23h59','Climatisation,Wi-Fi,Consigne,Restauration,Infirmerie,Parking sécurisé 24h/24','aa000001-0000-0000-0000-000000000002',0),
  ('bb000002-0000-0000-0000-000000000005','Gare Routière de Bafoussam','Bafoussam','Banengo','Principale gare de la capitale régionale de l''Ouest.','Lun–Dim : 05h00–21h00','Salle d''attente,Restauration traditionnelle,Toilettes,Parking,Boutiques souvenirs','aa000001-0000-0000-0000-000000000001',0)
ON CONFLICT (id_gare_routiere) DO NOTHING;

-- ─────────────────────────────────────────────────────────────
-- 3. ORGANISATIONS
-- ─────────────────────────────────────────────────────────────
INSERT INTO organization (id, long_name, short_name, is_active)
VALUES
  ('cc000003-0000-0000-0000-000000000001','General Express Cameroun SARL','GEC',true),
  ('cc000003-0000-0000-0000-000000000002','Touristique Express du Cameroun SA','TEC',true),
  ('cc000003-0000-0000-0000-000000000003','Bamiléké Transport Unité SARL','BTU',true),
  ('cc000003-0000-0000-0000-000000000004','Confort Lines Cameroun SARL','CLC',true)
ON CONFLICT (id) DO NOTHING;

-- ─────────────────────────────────────────────────────────────
-- 4. VÉHICULES (sans id_agence_voyage d'abord → FK circulaire)
-- Colonnes réelles : id_vehicule, nom, modele, description,
--                   nbr_places, plaque_matricule, lien_photo, id_agence_voyage
-- ─────────────────────────────────────────────────────────────
INSERT INTO vehicules (id_vehicule, nom, modele, description, nbr_places, plaque_matricule, lien_photo, id_agence_voyage)
VALUES
  ('ee000005-0000-0000-0000-000000000001','Grand Bus GE-01','Yutong ZK6122H9','Grand bus 70 places, climatisé, USB, écrans individuels.',70,'LT-7823-CM','https://images.unsplash.com/photo-1544620347-c4fd4a3d5957?w=600',NULL),
  ('ee000005-0000-0000-0000-000000000002','Bus GE-02 Classique','King Long XMQ6127J','Bus 55 places, climatisé, idéal pour l''axe Yaoundé–Douala.',55,'LT-4456-CM','https://images.unsplash.com/photo-1570125909232-eb263c188f7e?w=600',NULL),
  ('ee000005-0000-0000-0000-000000000003','Minibus GE-03 Express','Toyota Hiace GL','Minibus 18 places, express Yaoundé–Douala en 3h.',18,'LT-1102-CM','https://images.unsplash.com/photo-1558618666-fcd25c85cd64?w=600',NULL),
  ('ee000005-0000-0000-0000-000000000004','Bus GE-04 Nuit','Higer KLQ6119Q','Bus nuit 45 places, sièges inclinables 160°, couvertures fournies.',45,'LT-9908-CM','https://images.unsplash.com/photo-1494515843206-f3117d3f51b7?w=600',NULL),
  ('ee000005-0000-0000-0000-000000000005','VIP Coach TE-01','Mercedes-Benz Travego','Coach grand tourisme 30 places VIP, sièges cuir, Wi-Fi.',30,'LT-0055-CM','https://images.unsplash.com/photo-1557223562-6c77ef16210f?w=600',NULL),
  ('ee000005-0000-0000-0000-000000000006','VIP Premium TE-02','Volvo 9700','Bus premium 35 places, double deck, panoramique.',35,'LT-2277-CM','https://images.unsplash.com/photo-1544620347-c4fd4a3d5957?w=600',NULL),
  ('ee000005-0000-0000-0000-000000000007','Minibus TE-03 Affaires','Mercedes-Benz Sprinter 519','Minibus affaires 16 places, sièges cuir, tablettes, Wi-Fi.',16,'LT-6643-CM','https://images.unsplash.com/photo-1570125909232-eb263c188f7e?w=600',NULL),
  ('ee000005-0000-0000-0000-000000000008','Touristic TE-04 Tour','Irizar i8','Coach de luxe 40 places, vitres panoramiques.',40,'LT-3381-CM','https://images.unsplash.com/photo-1449824913935-59a10b8d2000?w=600',NULL),
  ('ee000005-0000-0000-0000-000000000009','BTU Grand Bus-01','Yutong ZK6112HG','Bus 50 places, climatisé, liaisons Bafoussam–Douala.',50,'OU-1134-CM','https://images.unsplash.com/photo-1544620347-c4fd4a3d5957?w=600',NULL),
  ('ee000005-0000-0000-0000-000000000010','BTU Express-02','King Long XMQ6100','Bus 40 places, rapidité et fiabilité sur l''axe Ouest.',40,'OU-5567-CM','https://images.unsplash.com/photo-1570125909232-eb263c188f7e?w=600',NULL),
  ('ee000005-0000-0000-0000-000000000011','BTU Minibus-03','Toyota Coaster HZB50','Minibus 30 places, desserte des villes secondaires de l''Ouest.',30,'OU-8890-CM','https://images.unsplash.com/photo-1558618666-fcd25c85cd64?w=600',NULL),
  ('ee000005-0000-0000-0000-000000000013','CL Minibus-01 Sud','Toyota HiAce Commuter','Minibus 18 places, axe Yaoundé–Mbalmayo–Ebolowa.',18,'CE-2201-CM','https://images.unsplash.com/photo-1544620347-c4fd4a3d5957?w=600',NULL),
  ('ee000005-0000-0000-0000-000000000014','CL Minibus-02 Kribi','Nissan Urvan NV350','Minibus 15 places, liaison directe Yaoundé–Kribi.',15,'CE-4423-CM','https://images.unsplash.com/photo-1570125909232-eb263c188f7e?w=600',NULL)
ON CONFLICT (id_vehicule) DO NOTHING;

-- ─────────────────────────────────────────────────────────────
-- 5. CHAUFFEURS (table dédiée — id référencé par lignes_voyage)
-- ─────────────────────────────────────────────────────────────
INSERT INTO chauffeurs (id, user_id, agence_id, statut, version, is_active, annee_experience, numero_permis)
VALUES
  ('cf000006-0000-0000-0000-000000000001','aa000001-0000-0000-0000-000000000031',NULL,'ACTIF',0,true,15,'PERM-D-CM-001'),
  ('cf000006-0000-0000-0000-000000000002','aa000001-0000-0000-0000-000000000032',NULL,'ACTIF',0,true,10,'PERM-D-CM-002'),
  ('cf000006-0000-0000-0000-000000000003','aa000001-0000-0000-0000-000000000033',NULL,'ACTIF',0,true,12,'PERM-D-CM-003'),
  ('cf000006-0000-0000-0000-000000000004','aa000001-0000-0000-0000-000000000035',NULL,'ACTIF',0,true,20,'PERM-DE-CM-004'),
  ('cf000006-0000-0000-0000-000000000005','aa000001-0000-0000-0000-000000000036',NULL,'ACTIF',0,true,14,'PERM-D-CM-005'),
  ('cf000006-0000-0000-0000-000000000006','aa000001-0000-0000-0000-000000000037',NULL,'ACTIF',0,true,13,'PERM-D-CM-006'),
  ('cf000006-0000-0000-0000-000000000007','aa000001-0000-0000-0000-000000000038',NULL,'ACTIF',0,true,11,'PERM-D-CM-007'),
  ('cf000006-0000-0000-0000-000000000008','aa000001-0000-0000-0000-000000000040',NULL,'ACTIF',0,true,8,'PERM-D-CM-008')
ON CONFLICT (id) DO NOTHING;

-- ─────────────────────────────────────────────────────────────
-- 6. AGENCES
-- chauffeur_id_defaut référence chauffeurs(id) !
-- ─────────────────────────────────────────────────────────────
INSERT INTO agences_voyage (agency_id, name, short_name, is_active, organisation_id, user_id, location, gare_routiere_id, moyens_paiement, vehicule_id_defaut, chauffeur_id_defaut, version)
VALUES
  ('dd000004-0000-0000-0000-000000000001','General Express Yaoundé - Agence Principale','General Express',true,'cc000003-0000-0000-0000-000000000001','aa000001-0000-0000-0000-000000000011','Gare de Mvan, Yaoundé','bb000002-0000-0000-0000-000000000001','["ORANGE_MONEY","MTN_MOMO","CASH"]','ee000005-0000-0000-0000-000000000001','cf000006-0000-0000-0000-000000000001',0),
  ('dd000004-0000-0000-0000-000000000002','Touristique Express VIP Douala','Touristique Express',true,'cc000003-0000-0000-0000-000000000002','aa000001-0000-0000-0000-000000000012','Gare de Bessengué, Douala','bb000002-0000-0000-0000-000000000004','["ORANGE_MONEY","MTN_MOMO","EXPRESS_UNION"]','ee000005-0000-0000-0000-000000000005','cf000006-0000-0000-0000-000000000004',0),
  ('dd000004-0000-0000-0000-000000000003','BTU - Bafoussam Transit Unité','BTU Transport',true,'cc000003-0000-0000-0000-000000000003','aa000001-0000-0000-0000-000000000013','Gare de Banengo, Bafoussam','bb000002-0000-0000-0000-000000000005','["ORANGE_MONEY","MTN_MOMO"]','ee000005-0000-0000-0000-000000000009','cf000006-0000-0000-0000-000000000006',0),
  ('dd000004-0000-0000-0000-000000000004','Confort Lines - Axe Centre-Sud','Confort Lines',true,'cc000003-0000-0000-0000-000000000004','aa000001-0000-0000-0000-000000000014','Gare du Lac Municipal, Yaoundé','bb000002-0000-0000-0000-000000000002','["ORANGE_MONEY","CASH"]','ee000005-0000-0000-0000-000000000013','cf000006-0000-0000-0000-000000000008',0)
ON CONFLICT (agency_id) DO NOTHING;

-- Mettre à jour l'agence des véhicules
UPDATE vehicules SET id_agence_voyage = 'dd000004-0000-0000-0000-000000000001' WHERE id_vehicule IN ('ee000005-0000-0000-0000-000000000001','ee000005-0000-0000-0000-000000000002','ee000005-0000-0000-0000-000000000003','ee000005-0000-0000-0000-000000000004');
UPDATE vehicules SET id_agence_voyage = 'dd000004-0000-0000-0000-000000000002' WHERE id_vehicule IN ('ee000005-0000-0000-0000-000000000005','ee000005-0000-0000-0000-000000000006','ee000005-0000-0000-0000-000000000007','ee000005-0000-0000-0000-000000000008');
UPDATE vehicules SET id_agence_voyage = 'dd000004-0000-0000-0000-000000000003' WHERE id_vehicule IN ('ee000005-0000-0000-0000-000000000009','ee000005-0000-0000-0000-000000000010','ee000005-0000-0000-0000-000000000011');
UPDATE vehicules SET id_agence_voyage = 'dd000004-0000-0000-0000-000000000004' WHERE id_vehicule IN ('ee000005-0000-0000-0000-000000000013','ee000005-0000-0000-0000-000000000014');

-- Mettre à jour l'agence des chauffeurs
UPDATE chauffeurs SET agence_id = 'dd000004-0000-0000-0000-000000000001' WHERE id IN ('cf000006-0000-0000-0000-000000000001','cf000006-0000-0000-0000-000000000002','cf000006-0000-0000-0000-000000000003');
UPDATE chauffeurs SET agence_id = 'dd000004-0000-0000-0000-000000000002' WHERE id IN ('cf000006-0000-0000-0000-000000000004','cf000006-0000-0000-0000-000000000005');
UPDATE chauffeurs SET agence_id = 'dd000004-0000-0000-0000-000000000003' WHERE id IN ('cf000006-0000-0000-0000-000000000006','cf000006-0000-0000-0000-000000000007');
UPDATE chauffeurs SET agence_id = 'dd000004-0000-0000-0000-000000000004' WHERE id = 'cf000006-0000-0000-0000-000000000008';

-- ─────────────────────────────────────────────────────────────
-- 7. CLASSES VOYAGE
-- ─────────────────────────────────────────────────────────────
INSERT INTO class_voyage (id, label, price, is_active, version)
VALUES
  ('ff000007-0000-0000-0000-000000000001','Classique GE',6000.0,true,0),
  ('ff000007-0000-0000-0000-000000000002','Confort GE',8500.0,true,0),
  ('ff000007-0000-0000-0000-000000000003','Express GE',7500.0,true,0),
  ('ff000007-0000-0000-0000-000000000004','Nuit Confort GE',9000.0,true,0),
  ('ff000007-0000-0000-0000-000000000005','VIP Standard TE',15000.0,true,0),
  ('ff000007-0000-0000-0000-000000000006','VIP Premium TE',22000.0,true,0),
  ('ff000007-0000-0000-0000-000000000007','Business Class TE',35000.0,true,0),
  ('ff000007-0000-0000-0000-000000000008','Classique BTU',5000.0,true,0),
  ('ff000007-0000-0000-0000-000000000009','Rapide BTU',6500.0,true,0),
  ('ff000007-0000-0000-0000-000000000010','Standard CL',4500.0,true,0),
  ('ff000007-0000-0000-0000-000000000011','Confort CL',6000.0,true,0)
ON CONFLICT (id) DO NOTHING;

-- ─────────────────────────────────────────────────────────────
-- 8. EMPLOYÉS
-- Colonnes réelles : id, user_id, agence_id, is_active, version,
--                   poste, date_embauche, departement, statut_employe, salaire
-- ─────────────────────────────────────────────────────────────
INSERT INTO employes (id, user_id, agence_id, is_active, version, poste, date_embauche, departement, statut_employe, salaire)
VALUES
  ('ee100001-0000-0000-0000-000000000001','aa000001-0000-0000-0000-000000000031','dd000004-0000-0000-0000-000000000001',true,0,'Chauffeur Principal','2015-03-01','Exploitation','ACTIF',180000),
  ('ee100001-0000-0000-0000-000000000002','aa000001-0000-0000-0000-000000000032','dd000004-0000-0000-0000-000000000001',true,0,'Chauffeur','2018-07-15','Exploitation','ACTIF',150000),
  ('ee100001-0000-0000-0000-000000000003','aa000001-0000-0000-0000-000000000033','dd000004-0000-0000-0000-000000000001',true,0,'Chauffeur','2016-11-20','Exploitation','ACTIF',160000),
  ('ee100001-0000-0000-0000-000000000004','aa000001-0000-0000-0000-000000000034','dd000004-0000-0000-0000-000000000001',true,0,'Caissière','2020-01-06','Finance','ACTIF',110000),
  ('ee100001-0000-0000-0000-000000000005','aa000001-0000-0000-0000-000000000035','dd000004-0000-0000-0000-000000000002',true,0,'Chauffeur VIP Senior','2012-05-10','Exploitation VIP','ACTIF',250000),
  ('ee100001-0000-0000-0000-000000000006','aa000001-0000-0000-0000-000000000036','dd000004-0000-0000-0000-000000000002',true,0,'Chauffeur VIP','2017-09-01','Exploitation VIP','ACTIF',220000),
  ('ee100001-0000-0000-0000-000000000007','aa000001-0000-0000-0000-000000000037','dd000004-0000-0000-0000-000000000003',true,0,'Chauffeur Principal','2014-02-14','Exploitation','ACTIF',170000),
  ('ee100001-0000-0000-0000-000000000008','aa000001-0000-0000-0000-000000000038','dd000004-0000-0000-0000-000000000003',true,0,'Chauffeur','2016-04-01','Exploitation','ACTIF',155000),
  ('ee100001-0000-0000-0000-000000000009','aa000001-0000-0000-0000-000000000040','dd000004-0000-0000-0000-000000000004',true,0,'Chauffeur','2019-06-01','Exploitation','ACTIF',140000)
ON CONFLICT (id) DO NOTHING;

-- ─────────────────────────────────────────────────────────────
-- 9. VOYAGES
-- ─────────────────────────────────────────────────────────────
INSERT INTO voyages (id_voyage, titre, description, lieu_depart, lieu_arrive, point_de_depart, point_arrivee, status_voyage, date_publication, date_depart_prev, date_limite_reservation, date_limite_confirmation, nbr_place_reservable, nbr_place_restante, nbr_place_reserve, nbr_place_confirm, duree_voyage, amenities, small_image, big_image)
VALUES
  ('11000008-0000-0000-0000-000000000001','Yaoundé → Douala — Classique Express','Départ de Mvan à 07h00. Voyage direct, climatisé.','Yaoundé','Douala','Gare Routière de Mvan','Gare de Bessengué','PUBLIE','2026-04-25 08:00:00+00','2026-05-10 07:00:00+00','2026-05-09 23:59:00+00','2026-05-09 23:59:00+00',53,23,30,28,14400,'AC,USB,COMFORTABLE_SEATS,LUGGAGE_STORAGE','https://images.unsplash.com/photo-1544620347-c4fd4a3d5957?w=400','https://images.unsplash.com/photo-1544620347-c4fd4a3d5957?w=1200'),
  ('11000008-0000-0000-0000-000000000002','Yaoundé → Douala — Confort Midi','Départ de Mvan à 12h00. Voyage express sans arrêt.','Yaoundé','Douala','Gare Routière de Mvan','Gare de Bessengué','PUBLIE','2026-04-25 08:00:00+00','2026-05-10 12:00:00+00','2026-05-09 23:59:00+00','2026-05-09 23:59:00+00',53,23,30,28,13500,'AC,USB,COMFORTABLE_SEATS,WIFI','https://images.unsplash.com/photo-1570125909232-eb263c188f7e?w=400','https://images.unsplash.com/photo-1570125909232-eb263c188f7e?w=1200'),
  ('11000008-0000-0000-0000-000000000003','Yaoundé → Douala — VIP Standard','Service VIP direct Yaoundé–Douala. Sièges cuir, Wi-Fi, collation offerte.','Yaoundé','Douala','Gare du Lac Municipal','Gare de Bessengué','PUBLIE','2026-04-24 10:00:00+00','2026-05-10 08:00:00+00','2026-05-09 23:59:00+00','2026-05-09 23:59:00+00',28,6,22,20,12600,'AC,WIFI,USB,COMFORTABLE_SEATS,MEAL_SERVICE,ENTERTAINMENT,POWER_OUTLETS','https://images.unsplash.com/photo-1557223562-6c77ef16210f?w=400','https://images.unsplash.com/photo-1557223562-6c77ef16210f?w=1200'),
  ('11000008-0000-0000-0000-000000000004','Yaoundé → Douala — Nuit Confort','Voyage de nuit climatisé. Couvertures et oreiller fournis.','Yaoundé','Douala','Gare Routière de Mvan','Gare de Bonabéri','PUBLIE','2026-04-25 08:00:00+00','2026-05-10 22:00:00+00','2026-05-10 18:00:00+00','2026-05-10 18:00:00+00',44,6,38,35,16200,'AC,COMFORTABLE_SEATS,LUGGAGE_STORAGE','https://images.unsplash.com/photo-1494515843206-f3117d3f51b7?w=400','https://images.unsplash.com/photo-1494515843206-f3117d3f51b7?w=1200'),
  ('11000008-0000-0000-0000-000000000005','Bafoussam → Douala — Classique','Liaison directe Bafoussam–Douala via Bafang.','Bafoussam','Douala','Gare de Banengo','Gare de Bonabéri','PUBLIE','2026-04-26 07:00:00+00','2026-05-11 06:00:00+00','2026-05-10 23:59:00+00','2026-05-10 23:59:00+00',48,23,25,20,18000,'AC,COMFORTABLE_SEATS,LUGGAGE_STORAGE','https://images.unsplash.com/photo-1544620347-c4fd4a3d5957?w=400','https://images.unsplash.com/photo-1544620347-c4fd4a3d5957?w=1200'),
  ('11000008-0000-0000-0000-000000000006','Yaoundé → Douala — Classique Matin (12 mai)','Départ matinal de la Gare de Mvan. Arrêt à Edéa.','Yaoundé','Douala','Gare Routière de Mvan','Gare de Bonabéri','PUBLIE','2026-04-27 08:00:00+00','2026-05-12 06:00:00+00','2026-05-11 23:59:00+00','2026-05-11 23:59:00+00',68,56,12,10,15300,'AC,USB,COMFORTABLE_SEATS','https://images.unsplash.com/photo-1544620347-c4fd4a3d5957?w=400','https://images.unsplash.com/photo-1544620347-c4fd4a3d5957?w=1200'),
  ('11000008-0000-0000-0000-000000000007','Douala → Yaoundé — VIP Premium','Retour VIP Douala–Yaoundé. Plateau dînatoire inclus. Bus panoramique.','Douala','Yaoundé','Gare de Bessengué','Gare du Lac Municipal','PUBLIE','2026-04-26 09:00:00+00','2026-05-11 17:00:00+00','2026-05-11 12:00:00+00','2026-05-11 12:00:00+00',33,13,20,18,12600,'AC,WIFI,USB,MEAL_SERVICE,ENTERTAINMENT,POWER_OUTLETS,COMFORTABLE_SEATS','https://images.unsplash.com/photo-1557223562-6c77ef16210f?w=400','https://images.unsplash.com/photo-1557223562-6c77ef16210f?w=1200'),
  ('11000008-0000-0000-0000-000000000008','Yaoundé → Mbalmayo — Standard Matin','Liaison rapide Yaoundé–Mbalmayo. Départ de la Gare du Lac Municipal.','Yaoundé','Mbalmayo','Gare du Lac Municipal','Gare de Mbalmayo','PUBLIE','2026-04-25 08:00:00+00','2026-05-10 07:30:00+00','2026-05-09 23:59:00+00','2026-05-09 23:59:00+00',16,6,10,9,5400,'AC,COMFORTABLE_SEATS','https://images.unsplash.com/photo-1449824913935-59a10b8d2000?w=400','https://images.unsplash.com/photo-1449824913935-59a10b8d2000?w=1200')
ON CONFLICT (id_voyage) DO NOTHING;

-- ─────────────────────────────────────────────────────────────
-- 10. LIGNES VOYAGE
-- PK réelle : id_ligne_voyage
-- id_chauffeur référence chauffeurs(id) pas users(user_id)
-- ─────────────────────────────────────────────────────────────
INSERT INTO lignes_voyage (id_ligne_voyage, id_voyage, id_agence_voyage, id_vehicule, id_chauffeur, id_class_voyage)
VALUES
  ('22000009-0000-0000-0000-000000000001','11000008-0000-0000-0000-000000000001','dd000004-0000-0000-0000-000000000001','ee000005-0000-0000-0000-000000000001','cf000006-0000-0000-0000-000000000001','ff000007-0000-0000-0000-000000000001'),
  ('22000009-0000-0000-0000-000000000002','11000008-0000-0000-0000-000000000002','dd000004-0000-0000-0000-000000000001','ee000005-0000-0000-0000-000000000002','cf000006-0000-0000-0000-000000000002','ff000007-0000-0000-0000-000000000002'),
  ('22000009-0000-0000-0000-000000000003','11000008-0000-0000-0000-000000000003','dd000004-0000-0000-0000-000000000002','ee000005-0000-0000-0000-000000000005','cf000006-0000-0000-0000-000000000004','ff000007-0000-0000-0000-000000000005'),
  ('22000009-0000-0000-0000-000000000004','11000008-0000-0000-0000-000000000004','dd000004-0000-0000-0000-000000000001','ee000005-0000-0000-0000-000000000004','cf000006-0000-0000-0000-000000000003','ff000007-0000-0000-0000-000000000004'),
  ('22000009-0000-0000-0000-000000000005','11000008-0000-0000-0000-000000000005','dd000004-0000-0000-0000-000000000003','ee000005-0000-0000-0000-000000000009','cf000006-0000-0000-0000-000000000006','ff000007-0000-0000-0000-000000000008'),
  ('22000009-0000-0000-0000-000000000006','11000008-0000-0000-0000-000000000006','dd000004-0000-0000-0000-000000000001','ee000005-0000-0000-0000-000000000001','cf000006-0000-0000-0000-000000000001','ff000007-0000-0000-0000-000000000001'),
  ('22000009-0000-0000-0000-000000000007','11000008-0000-0000-0000-000000000007','dd000004-0000-0000-0000-000000000002','ee000005-0000-0000-0000-000000000006','cf000006-0000-0000-0000-000000000005','ff000007-0000-0000-0000-000000000006'),
  ('22000009-0000-0000-0000-000000000008','11000008-0000-0000-0000-000000000008','dd000004-0000-0000-0000-000000000004','ee000005-0000-0000-0000-000000000013','cf000006-0000-0000-0000-000000000008','ff000007-0000-0000-0000-000000000010')
ON CONFLICT (id_ligne_voyage) DO NOTHING;

-- ─────────────────────────────────────────────────────────────
-- 11. AFFILIATIONS
-- Colonnes réelles : id, gare_routiere_id, agency_id, agency_name,
--                   statut, echeance, montant_affiliation
-- ─────────────────────────────────────────────────────────────
INSERT INTO affiliation_agence_voyage (id, gare_routiere_id, agency_id, agency_name, statut, echeance, montant_affiliation)
VALUES
  ('33000010-0000-0000-0000-000000000001','bb000002-0000-0000-0000-000000000001','dd000004-0000-0000-0000-000000000001','General Express Yaoundé','ACTIF','2026-05-01',75000.00),
  ('33000010-0000-0000-0000-000000000002','bb000002-0000-0000-0000-000000000004','dd000004-0000-0000-0000-000000000002','Touristique Express VIP Douala','ACTIF','2026-05-01',90000.00),
  ('33000010-0000-0000-0000-000000000003','bb000002-0000-0000-0000-000000000005','dd000004-0000-0000-0000-000000000003','BTU - Bafoussam Transit Unité','ACTIF','2026-05-01',55000.00),
  ('33000010-0000-0000-0000-000000000004','bb000002-0000-0000-0000-000000000002','dd000004-0000-0000-0000-000000000004','Confort Lines - Axe Centre-Sud','ACTIF','2026-05-01',65000.00),
  ('33000010-0000-0000-0000-000000000005','bb000002-0000-0000-0000-000000000004','dd000004-0000-0000-0000-000000000001','General Express Yaoundé','ACTIF','2026-05-01',80000.00)
ON CONFLICT (id) DO NOTHING;

-- ─────────────────────────────────────────────────────────────
-- 12. POLITIQUES ET TAXES
-- ─────────────────────────────────────────────────────────────
INSERT INTO politique_et_taxes (id_politique, gare_routiere_id, nom_politique, description, montant_fixe, date_effet, type)
VALUES
  ('44000011-0000-0000-0000-000000000001','bb000002-0000-0000-0000-000000000001','Règlement intérieur — Gare de Mvan','Toute agence affiliée s''engage à respecter les horaires de départ affichés.',NULL,'2024-01-01','POLITIQUE'),
  ('44000011-0000-0000-0000-000000000002','bb000002-0000-0000-0000-000000000001','Taxe d''affiliation mensuelle — Mvan','Chaque agence affiliée à la Gare de Mvan s''acquitte d''une taxe mensuelle de 75 000 FCFA.',75000.0,'2024-01-01','TAXE'),
  ('44000011-0000-0000-0000-000000000003','bb000002-0000-0000-0000-000000000001','Frais de quai par départ — Mvan','Un frais de quai de 500 FCFA est prélevé par départ effectif.',500.0,'2024-01-01','TAXE'),
  ('44000011-0000-0000-0000-000000000004','bb000002-0000-0000-0000-000000000002','Politique de sécurité — Gare du Lac','Identification obligatoire des passagers à l''embarquement.',NULL,'2023-06-01','POLITIQUE'),
  ('44000011-0000-0000-0000-000000000005','bb000002-0000-0000-0000-000000000002','Taxe d''affiliation — Gare du Lac','Taxe mensuelle d''affiliation fixée à 65 000 FCFA.',65000.0,'2023-06-01','TAXE')
ON CONFLICT (id_politique) DO NOTHING;

-- ─────────────────────────────────────────────────────────────
-- 13. ALERTES AGENCE
-- ─────────────────────────────────────────────────────────────
INSERT INTO alertes_agence (id_alerte, gare_id, agence_id, bsm_id, type, message, is_lu, created_at)
VALUES
  ('55000012-0000-0000-0000-000000000001','bb000002-0000-0000-0000-000000000002','dd000004-0000-0000-0000-000000000004','aa000001-0000-0000-0000-000000000002','TAX_REMINDER','Rappel : votre taxe d''affiliation du mois de mars 2026 (65 000 FCFA) est en retard. Merci de régulariser avant le 10 mai 2026.',true,'2026-04-15 10:00:00'),
  ('55000012-0000-0000-0000-000000000002','bb000002-0000-0000-0000-000000000002','dd000004-0000-0000-0000-000000000004','aa000001-0000-0000-0000-000000000002','ALERTE_GENERALE','Votre agence a été signalée pour un départ non déclaré le 22 avril 2026. Un deuxième manquement entraînera une suspension.',false,'2026-04-23 09:00:00'),
  ('55000012-0000-0000-0000-000000000003','bb000002-0000-0000-0000-000000000001','dd000004-0000-0000-0000-000000000001','aa000001-0000-0000-0000-000000000001','ALERTE_GENERALE','La Gare de Mvan procédera à des travaux de rénovation du 5 au 10 juin 2026. Les départs seront relocalisés vers la plateforme Est.',true,'2026-04-20 11:00:00')
ON CONFLICT (id_alerte) DO NOTHING;