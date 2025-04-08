INSERT INTO place (numero) VALUES
    ("ZERR175G"),
    ("ARFFAF"),
    ("JHFDGGS"),
    ("EREVSVSB"),
    ("44DHHSDH");

INSERT INTO utilisateur (email, password, role) VALUES
    ("a@a.com", "$2a$10$nLVdBRYM4XiE5kZ6rAS3K.PHdrjLz9kJ0WfsV2b2X9bxG5/WzCBlC", "ADMIN"),
    ("b@b.com", "$2a$10$nLVdBRYM4XiE5kZ6rAS3K.PHdrjLz9kJ0WfsV2b2X9bxG5/WzCBlC", "EMPLOYE"),
    ("sonikast@hotmail.com", "$2a$10$Cc5cg6VKHhjCum62Wk1hpudlhiM6Oix2XlCcdM.XmDlvivD/44upG", "EMPLOYE"),
    ("c@c.com", "$2a$10$kuvZOI5QOB48igTviJ4puuNHw9OZoQRglThElxb60yAmCwobzS1k2", "ADMIN");

INSERT INTO reservation (date_debut, date_fin, date_creation, place_id, utilisateur_id) VALUES
    ("2025-03-10 14:26:01", "2025-04-10 12:00:00", "2025-03-08 10:00:00", 1, 2),
    ("2025-04-10 14:26:01", "2025-08-10 12:00:00", "2025-03-04 10:00:00", 2, 1);