INSERT INTO place (numero) VALUES
    ("ZERR175G"),
    ("ARFFAF"),
    ("JHFDGGS"),
    ("EREVSVSB"),
    ("44DHHSDH");

INSERT INTO utilisateur(email, password, role) VALUES
    ("a@a.com", "12345", "ADMIN"),
    ("b@b.com", "678910", "EMPLOYE");

INSERT INTO reservation(date_debut, date_fin,date_creation, place_id, utilisateur_id) VALUES
    ("2025-03-10 14:26:01", "2025-04-10 12:00:00", "2025-03-08 10:00:00", 1, 2),
    ("2025-04-10 14:26:01", "2025-08-10 12:00:00", "2025-03-04 10:00:00", 2, 1);