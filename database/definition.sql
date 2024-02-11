CREATE TABLE wm_stanowiska
(
    id     NUMBER(5) GENERATED ALWAYS AS IDENTITY,
    nazwa  VARCHAR2(30),
    pensja NUMBER(6, 2),
    CONSTRAINT pk_stanowiska_nazwa PRIMARY KEY (id)
);

INSERT INTO wm_stanowiska (nazwa, pensja)
VALUES ('kucharz', 4500.00);
INSERT INTO wm_stanowiska (nazwa, pensja)
VALUES ('kelner', 3800.00);
INSERT INTO wm_stanowiska (nazwa, pensja)
VALUES ('zmywacz', 2800.00);
INSERT INTO wm_stanowiska (nazwa, pensja)
VALUES ('barman', 4000.00);
INSERT INTO wm_stanowiska (nazwa, pensja)
VALUES ('szef kuchni', 7000.00);
INSERT INTO wm_stanowiska (nazwa, pensja)
VALUES ('pomoc kuchenna', 3000.00);
INSERT INTO wm_stanowiska (nazwa, pensja)
VALUES ('host', 3500.00);

CREATE TABLE wm_pracownicy
(
    id       NUMBER(5) GENERATED ALWAYS AS IDENTITY,
    imie     VARCHAR2(30) NOT NULL,
    nazwisko VARCHAR2(30) NOT NULL,
    login    VARCHAR2(30) NULL,
    CONSTRAINT pk_pracownicy_id PRIMARY KEY (id),
    CONSTRAINT uk_pracownicy_username UNIQUE (login)
);

INSERT INTO wm_pracownicy (imie, nazwisko)
VALUES ('Jan', 'Kowalski');
INSERT INTO wm_pracownicy (imie, nazwisko)
VALUES ('Anna', 'Nowak');
INSERT INTO wm_pracownicy (imie, nazwisko)
VALUES ('Piotr', 'Wiśniewski');
INSERT INTO wm_pracownicy (imie, nazwisko)
VALUES ('Agnieszka', 'Kowalczyk');
INSERT INTO wm_pracownicy (imie, nazwisko)
VALUES ('Tomasz', 'Duda');
INSERT INTO wm_pracownicy (imie, nazwisko)
VALUES ('Maria', 'Lewandowska');
INSERT INTO wm_pracownicy (imie, nazwisko)
VALUES ('Michał', 'Wójcik');
INSERT INTO wm_pracownicy (imie, nazwisko)
VALUES ('Katarzyna', 'Zielińska');
INSERT INTO wm_pracownicy (imie, nazwisko)
VALUES ('Paweł', 'Kamiński');
INSERT INTO wm_pracownicy (imie, nazwisko)
VALUES ('Magdalena', 'Kwiatkowska');
INSERT INTO wm_pracownicy (imie, nazwisko)
VALUES ('Marcin', 'Król');
INSERT INTO wm_pracownicy (imie, nazwisko)
VALUES ('Barbara', 'Bąk');
INSERT INTO wm_pracownicy (imie, nazwisko)
VALUES ('Jakub', 'Woźniak');
INSERT INTO wm_pracownicy (imie, nazwisko)
VALUES ('Zofia', 'Szymczak');
INSERT INTO wm_pracownicy (imie, nazwisko)
VALUES ('Stanisław', 'Wróbel');
INSERT INTO wm_pracownicy (imie, nazwisko)
VALUES ('Elżbieta', 'Kozłowska');
INSERT INTO wm_pracownicy (imie, nazwisko)
VALUES ('Andrzej', 'Kruk');
INSERT INTO wm_pracownicy (imie, nazwisko)
VALUES ('Alicja', 'Stępień');
INSERT INTO wm_pracownicy (imie, nazwisko)
VALUES ('Adam', 'Malinowski');
INSERT INTO wm_pracownicy (imie, nazwisko)
VALUES ('Ewa', 'Pawlak');
INSERT INTO wm_pracownicy (imie, nazwisko, login)
VALUES ('Stanisław', 'Puzio', 'SBD151886');
INSERT INTO wm_pracownicy (imie, nazwisko, login)
VALUES ('Alex', 'Pawelski', 'SBD147412');
SELECT *
FROM wm_pracownicy;

CREATE TABLE wm_umowy
(
    id         NUMBER(5) GENERATED ALWAYS AS IDENTITY,
    numer      VARCHAR2(15) NOT NULL,
    typ        VARCHAR2(30) NOT NULL,
    pracownik  NUMBER(5)    NOT NULL,
    stanowisko NUMBER(5)    NOT NULL,
    zawiazana  DATE DEFAULT CURRENT_TIMESTAMP,
    zerwana    DATE         NULL,
    CONSTRAINT pk_umowy_id PRIMARY KEY (id),
    CONSTRAINT fk_umowy_pracownicy FOREIGN KEY (pracownik) REFERENCES wm_pracownicy (id),
    CONSTRAINT fk_umowy_stanowiska FOREIGN KEY (stanowisko) REFERENCES wm_stanowiska (id)
);

INSERT INTO wm_umowy (numer, typ, pracownik, stanowisko)
VALUES ('UM/2024/0001', 'umowa o pracę', (SELECT id FROM wm_pracownicy WHERE nazwisko = 'Kowalski'),
        (SELECT id FROM wm_stanowiska WHERE nazwa = 'kucharz'));
INSERT INTO wm_umowy (numer, typ, pracownik, stanowisko)
VALUES ('UM/2024/0002', 'umowa o pracę', (SELECT id FROM wm_pracownicy WHERE nazwisko = 'Nowak'),
        (SELECT id FROM wm_stanowiska WHERE nazwa = 'kelner'));
INSERT INTO wm_umowy (numer, typ, pracownik, stanowisko)
VALUES ('UM/2024/0003', 'umowa o pracę', (SELECT id FROM wm_pracownicy WHERE nazwisko = 'Wiśniewski'),
        (SELECT id FROM wm_stanowiska WHERE nazwa = 'zmywacz'));
INSERT INTO wm_umowy (numer, typ, pracownik, stanowisko)
VALUES ('UM/2024/0004', 'umowa o pracę', (SELECT id FROM wm_pracownicy WHERE nazwisko = 'Kowalczyk'),
        (SELECT id FROM wm_stanowiska WHERE nazwa = 'barman'));
INSERT INTO wm_umowy (numer, typ, pracownik, stanowisko)
VALUES ('UM/2024/0005', 'umowa o pracę', (SELECT id FROM wm_pracownicy WHERE nazwisko = 'Duda'),
        (SELECT id FROM wm_stanowiska WHERE nazwa = 'pomoc kuchenna'));
INSERT INTO wm_umowy (numer, typ, pracownik, stanowisko)
VALUES ('UM/2024/0006', 'umowa o pracę', (SELECT id FROM wm_pracownicy WHERE nazwisko = 'Lewandowska'),
        (SELECT id FROM wm_stanowiska WHERE nazwa = 'host'));
INSERT INTO wm_umowy (numer, typ, pracownik, stanowisko)
VALUES ('UM/2024/0007', 'umowa o pracę', (SELECT id FROM wm_pracownicy WHERE nazwisko = 'Wójcik'),
        (SELECT id FROM wm_stanowiska WHERE nazwa = 'kucharz'));
INSERT INTO wm_umowy (numer, typ, pracownik, stanowisko)
VALUES ('UM/2024/0008', 'umowa o pracę', (SELECT id FROM wm_pracownicy WHERE nazwisko = 'Zielińska'),
        (SELECT id FROM wm_stanowiska WHERE nazwa = 'kelner'));
INSERT INTO wm_umowy (numer, typ, pracownik, stanowisko)
VALUES ('UM/2024/0009', 'umowa o pracę', (SELECT id FROM wm_pracownicy WHERE nazwisko = 'Kamiński'),
        (SELECT id FROM wm_stanowiska WHERE nazwa = 'zmywacz'));
INSERT INTO wm_umowy (numer, typ, pracownik, stanowisko)
VALUES ('UM/2024/0010', 'umowa o pracę', (SELECT id FROM wm_pracownicy WHERE nazwisko = 'Kwiatkowska'),
        (SELECT id FROM wm_stanowiska WHERE nazwa = 'barman'));
INSERT INTO wm_umowy (numer, typ, pracownik, stanowisko)
VALUES ('UM/2024/0011', 'umowa o pracę', (SELECT id FROM wm_pracownicy WHERE nazwisko = 'Król'),
        (SELECT id FROM wm_stanowiska WHERE nazwa = 'pomoc kuchenna'));
INSERT INTO wm_umowy (numer, typ, pracownik, stanowisko)
VALUES ('UM/2024/0012', 'umowa o pracę', (SELECT id FROM wm_pracownicy WHERE nazwisko = 'Bąk'),
        (SELECT id FROM wm_stanowiska WHERE nazwa = 'host'));
INSERT INTO wm_umowy (numer, typ, pracownik, stanowisko)
VALUES ('UM/2024/0013', 'umowa o pracę', (SELECT id FROM wm_pracownicy WHERE nazwisko = 'Woźniak'),
        (SELECT id FROM wm_stanowiska WHERE nazwa = 'kucharz'));
INSERT INTO wm_umowy (numer, typ, pracownik, stanowisko)
VALUES ('UM/2024/0014', 'umowa o pracę', (SELECT id FROM wm_pracownicy WHERE nazwisko = 'Szymczak'),
        (SELECT id FROM wm_stanowiska WHERE nazwa = 'kelner'));
INSERT INTO wm_umowy (numer, typ, pracownik, stanowisko)
VALUES ('UM/2024/0015', 'umowa o pracę', (SELECT id FROM wm_pracownicy WHERE nazwisko = 'Wróbel'),
        (SELECT id FROM wm_stanowiska WHERE nazwa = 'zmywacz'));
INSERT INTO wm_umowy (numer, typ, pracownik, stanowisko)
VALUES ('UM/2024/0016', 'umowa o pracę', (SELECT id FROM wm_pracownicy WHERE nazwisko = 'Kozłowska'),
        (SELECT id FROM wm_stanowiska WHERE nazwa = 'barman'));
INSERT INTO wm_umowy (numer, typ, pracownik, stanowisko)
VALUES ('UM/2024/0017', 'umowa o pracę', (SELECT id FROM wm_pracownicy WHERE nazwisko = 'Kruk'),
        (SELECT id FROM wm_stanowiska WHERE nazwa = 'pomoc kuchenna'));
INSERT INTO wm_umowy (numer, typ, pracownik, stanowisko)
VALUES ('UM/2024/0018', 'umowa o pracę', (SELECT id FROM wm_pracownicy WHERE nazwisko = 'Stępień'),
        (SELECT id FROM wm_stanowiska WHERE nazwa = 'host'));
INSERT INTO wm_umowy (numer, typ, pracownik, stanowisko)
VALUES ('UM/2024/0019', 'umowa o pracę', (SELECT id FROM wm_pracownicy WHERE nazwisko = 'Malinowski'),
        (SELECT id FROM wm_stanowiska WHERE nazwa = 'kucharz'));
INSERT INTO wm_umowy (numer, typ, pracownik, stanowisko)
VALUES ('UM/2024/0020', 'umowa o pracę', (SELECT id FROM wm_pracownicy WHERE nazwisko = 'Pawlak'),
        (SELECT id FROM wm_stanowiska WHERE nazwa = 'kelner'));
INSERT INTO wm_umowy (numer, typ, pracownik, stanowisko)
VALUES ('UM/2024/0019', 'umowa o pracę', (SELECT id FROM wm_pracownicy WHERE nazwisko = 'Puzio' AND login = 'SBD151886'),
        (SELECT id FROM wm_stanowiska WHERE nazwa = 'szef kuchni'));

CREATE TABLE wm_kontrahenci
(
    id      NUMBER(5) GENERATED ALWAYS AS IDENTITY,
    nazwa   VARCHAR2(30) NOT NULL,
    adres   VARCHAR2(30) NOT NULL,
    telefon VARCHAR2(15) NULL,
    email   VARCHAR2(30) NULL,
    NIP     VARCHAR2(10) NULL,
    CONSTRAINT pk_kontrahenci_id PRIMARY KEY (id),
    CONSTRAINT uk_kontrahenci_nip UNIQUE (NIP)
);

INSERT INTO wm_kontrahenci (nazwa, adres, telefon, email, NIP)
VALUES ('Smakosz Express', 'ul. Przyjemności 42', '111-222-333', 'smakosz@email.com', '9876543210');
INSERT INTO wm_kontrahenci (nazwa, adres, telefon, email, NIP)
VALUES ('Kulinarny Raj', 'ul. Pyszności 13', '555-123-789', 'raj_kuchenny@email.com', '4567890123');
INSERT INTO wm_kontrahenci (nazwa, adres, telefon, email, NIP)
VALUES ('Sztuka Smaku Sp. z o.o.', 'ul. Dekadencji 7', '777-888-999', 'kontakt@sztukasmaku.com', '7890123456');
INSERT INTO wm_kontrahenci (nazwa, adres, telefon, email, NIP)
VALUES ('Aromatyczne Kąski', 'ul. Zapachów 21', '333-456-789', 'aromat@email.com', '6543210987');
INSERT INTO wm_kontrahenci (nazwa, adres, telefon, email, NIP)
VALUES ('Przysmakowa Kraina', 'ul. Uczty 55', '222-333-444', 'kontakt@kraina-przysmakow.com', '3210987654');

CREATE TABLE wm_produkty
(
    id        NUMBER(5) GENERATED ALWAYS AS IDENTITY,
    nazwa     VARCHAR2(30),
    jednostka VARCHAR2(7) NULL,
    CONSTRAINT pk_produkty_id PRIMARY KEY (id),
    CONSTRAINT uk_produkty_nazwa UNIQUE (nazwa)
);

INSERT INTO wm_produkty (nazwa, jednostka)
VALUES ('Woda', 'l');
INSERT INTO wm_produkty (nazwa, jednostka)
VALUES ('Ryż', 'kg');
INSERT INTO wm_produkty (nazwa, jednostka)
VALUES ('Makaron ryżowy', 'kg');
INSERT INTO wm_produkty (nazwa, jednostka)
VALUES ('Tofu', 'kg');
INSERT INTO wm_produkty (nazwa, jednostka)
VALUES ('Soja', 'kg');
INSERT INTO wm_produkty (nazwa, jednostka)
VALUES ('Sos sojowy', 'l');
INSERT INTO wm_produkty (nazwa, jednostka)
VALUES ('Kurczak', 'kg');
INSERT INTO wm_produkty (nazwa, jednostka)
VALUES ('Wołowina', 'kg');
INSERT INTO wm_produkty (nazwa, jednostka)
VALUES ('Krewetki', 'kg');
INSERT INTO wm_produkty (nazwa, jednostka)
VALUES ('Wodorosty nori', 'szt.');
INSERT INTO wm_produkty (nazwa, jednostka)
VALUES ('Imbir', 'kg');
INSERT INTO wm_produkty (nazwa, jednostka)
VALUES ('Sos teriyaki', 'l');
INSERT INTO wm_produkty (nazwa, jednostka)
VALUES ('Wasabi', 'kg');
INSERT INTO wm_produkty (nazwa, jednostka)
VALUES ('Sos rybny', 'l');
INSERT INTO wm_produkty (nazwa, jednostka)
VALUES ('Sezam', 'kg');
INSERT INTO wm_produkty (nazwa, jednostka)
VALUES ('Pasta chili', 'kg');
INSERT INTO wm_produkty (nazwa, jednostka)
VALUES ('Kurkuma', 'kg');
INSERT INTO wm_produkty (nazwa, jednostka)
VALUES ('Mleko kokosowe', 'l');
INSERT INTO wm_produkty (nazwa, jednostka)
VALUES ('Ostre papryczki', 'kg');
INSERT INTO wm_produkty (nazwa, jednostka)
VALUES ('Ocet ryżowy', 'l');
INSERT INTO wm_produkty (nazwa, jednostka)
VALUES ('Mirin', 'l');
INSERT INTO wm_produkty (nazwa, jednostka)
VALUES ('Sake', 'l');
INSERT INTO wm_produkty (nazwa, jednostka)
VALUES ('Orzeszki ziemne', 'kg');
INSERT INTO wm_produkty (nazwa, jednostka)
VALUES ('Sos curry', 'l');
INSERT INTO wm_produkty (nazwa, jednostka)
VALUES ('Wodorosty wakame', 'kg');
INSERT INTO wm_produkty (nazwa, jednostka)
VALUES ('Jajko', 'szt.');
INSERT INTO wm_produkty (nazwa, jednostka)
VALUES ('Kiełki fasoli mung', 'kg');
INSERT INTO wm_produkty (nazwa, jednostka)
VALUES ('Wodorosty kombu', 'kg');
INSERT INTO wm_produkty (nazwa, jednostka)
VALUES ('Makaron ramen', 'kg');
INSERT INTO wm_produkty (nazwa, jednostka)
VALUES ('Szczypiorek', 'kg');


CREATE TABLE wm_faktury_obce
(
    id         NUMBER(5) GENERATED ALWAYS AS IDENTITY,
    kontrahent NUMBER(5)    NOT NULL,
    data       DATE         NOT NULL,
    nr_obcy    VARCHAR2(30) NOT NULL,
    CONSTRAINT pk_faktury_obce_id PRIMARY KEY (id),
    CONSTRAINT fk_faktury_obce_kontrahenci FOREIGN KEY (kontrahent) REFERENCES wm_kontrahenci (id),
    CONSTRAINT uk_faktury_obce_kontrahent_nr_obcy UNIQUE (kontrahent, nr_obcy)
);

INSERT INTO wm_faktury_obce (kontrahent, data, nr_obcy)
VALUES (1, '2024-02-06', 'FV/2024/0001');
INSERT INTO wm_faktury_obce (kontrahent, data, nr_obcy)
VALUES (2, '2024-02-07', 'FV/2024/0002');
INSERT INTO wm_faktury_obce (kontrahent, data, nr_obcy)
VALUES (3, '2024-02-08', 'FV/2024/0003');
INSERT INTO wm_faktury_obce (kontrahent, data, nr_obcy)
VALUES (4, '2024-02-09', 'FV/2024/0004');
INSERT INTO wm_faktury_obce (kontrahent, data, nr_obcy)
VALUES (5, '2024-02-10', 'FV/2024/0005');
INSERT INTO wm_faktury_obce (kontrahent, data, nr_obcy)
VALUES (1, '2024-02-11', 'FV/2024/0006');
INSERT INTO wm_faktury_obce (kontrahent, data, nr_obcy)
VALUES (2, '2024-02-12', 'FV/2024/0007');
INSERT INTO wm_faktury_obce (kontrahent, data, nr_obcy)
VALUES (3, '2024-02-13', 'FV/2024/0008');
INSERT INTO wm_faktury_obce (kontrahent, data, nr_obcy)
VALUES (4, '2024-02-14', 'FV/2024/0009');
INSERT INTO wm_faktury_obce (kontrahent, data, nr_obcy)
VALUES (5, '2024-02-15', 'FV/2024/0010');

CREATE TABLE wm_faktury_obce_pozycje
(
    id            NUMBER(5) GENERATED ALWAYS AS IDENTITY,
    faktura       NUMBER(5)    NOT NULL,
    produkt       NUMBER(5)    NOT NULL,
    cena          NUMBER(6, 2) NOT NULL,
    ilosc         NUMBER(8, 2) NOT NULL,
    data_waznosci DATE         NULL,
    CONSTRAINT pk_faktury_obce_pozycje_id PRIMARY KEY (id),
    CONSTRAINT fk_faktury_obce_pozycje_faktury_obce FOREIGN KEY (faktura) REFERENCES wm_faktury_obce (id),
    CONSTRAINT fk_faktury_obce_pozycje_produkty FOREIGN KEY (produkt) REFERENCES wm_produkty (id)
);

INSERT INTO wm_faktury_obce_pozycje (faktura, produkt, cena, ilosc, data_waznosci)
VALUES (1, 1, 2.50, 10, '2024-03-01');
INSERT INTO wm_faktury_obce_pozycje (faktura, produkt, cena, ilosc, data_waznosci)
VALUES (2, 2, 3.75, 8, '2024-03-02');
INSERT INTO wm_faktury_obce_pozycje (faktura, produkt, cena, ilosc, data_waznosci)
VALUES (3, 3, 5.20, 5, '2024-03-03');
INSERT INTO wm_faktury_obce_pozycje (faktura, produkt, cena, ilosc, data_waznosci)
VALUES (4, 4, 4.80, 7, '2024-03-04');
INSERT INTO wm_faktury_obce_pozycje (faktura, produkt, cena, ilosc, data_waznosci)
VALUES (5, 5, 6.90, 4, '2024-03-05');
INSERT INTO wm_faktury_obce_pozycje (faktura, produkt, cena, ilosc, data_waznosci)
VALUES (6, 6, 2.20, 12, '2024-03-06');
INSERT INTO wm_faktury_obce_pozycje (faktura, produkt, cena, ilosc, data_waznosci)
VALUES (7, 7, 8.50, 6, '2024-03-07');
INSERT INTO wm_faktury_obce_pozycje (faktura, produkt, cena, ilosc, data_waznosci)
VALUES (8, 8, 9.75, 3, '2024-03-08');
INSERT INTO wm_faktury_obce_pozycje (faktura, produkt, cena, ilosc, data_waznosci)
VALUES (9, 9, 12.30, 2, '2024-03-09');
INSERT INTO wm_faktury_obce_pozycje (faktura, produkt, cena, ilosc, data_waznosci)
VALUES (10, 10, 7.60, 9, '2024-03-10');
INSERT INTO wm_faktury_obce_pozycje (faktura, produkt, cena, ilosc, data_waznosci)
VALUES (1, 11, 3.40, 11, '2024-03-11');
INSERT INTO wm_faktury_obce_pozycje (faktura, produkt, cena, ilosc, data_waznosci)
VALUES (2, 12, 5.90, 6, '2024-03-12');
INSERT INTO wm_faktury_obce_pozycje (faktura, produkt, cena, ilosc, data_waznosci)
VALUES (3, 13, 1.80, 15, '2024-03-13');
INSERT INTO wm_faktury_obce_pozycje (faktura, produkt, cena, ilosc, data_waznosci)
VALUES (4, 14, 4.50, 8, '2024-03-14');
INSERT INTO wm_faktury_obce_pozycje (faktura, produkt, cena, ilosc, data_waznosci)
VALUES (5, 15, 6.20, 5, '2024-03-15');
INSERT INTO wm_faktury_obce_pozycje (faktura, produkt, cena, ilosc, data_waznosci)
VALUES (6, 16, 2.90, 10, '2024-03-16');
INSERT INTO wm_faktury_obce_pozycje (faktura, produkt, cena, ilosc, data_waznosci)
VALUES (7, 17, 4.10, 7, '2024-03-17');
INSERT INTO wm_faktury_obce_pozycje (faktura, produkt, cena, ilosc, data_waznosci)
VALUES (8, 18, 8.80, 4, '2024-03-18');
INSERT INTO wm_faktury_obce_pozycje (faktura, produkt, cena, ilosc, data_waznosci)
VALUES (9, 19, 5.40, 6, '2024-03-19');
INSERT INTO wm_faktury_obce_pozycje (faktura, produkt, cena, ilosc, data_waznosci)
VALUES (10, 20, 10.75, 3, '2024-03-20');
INSERT INTO wm_faktury_obce_pozycje (faktura, produkt, cena, ilosc, data_waznosci)
VALUES (1, 21, 6.90, 8, '2024-03-21');
INSERT INTO wm_faktury_obce_pozycje (faktura, produkt, cena, ilosc, data_waznosci)
VALUES (2, 22, 3.20, 12, '2024-03-22');
INSERT INTO wm_faktury_obce_pozycje (faktura, produkt, cena, ilosc, data_waznosci)
VALUES (3, 24, 9.40, 4, '2024-03-23');
INSERT INTO wm_faktury_obce_pozycje (faktura, produkt, cena, ilosc, data_waznosci)
VALUES (4, 25, 7.60, 6, '2024-03-24');
INSERT INTO wm_faktury_obce_pozycje (faktura, produkt, cena, ilosc, data_waznosci)
VALUES (6, 1, 3.25, 15, '2024-03-26');
INSERT INTO wm_faktury_obce_pozycje (faktura, produkt, cena, ilosc, data_waznosci)
VALUES (7, 2, 7.10, 8, '2024-03-27');
INSERT INTO wm_faktury_obce_pozycje (faktura, produkt, cena, ilosc, data_waznosci)
VALUES (8, 3, 5.80, 6, '2024-03-28');
INSERT INTO wm_faktury_obce_pozycje (faktura, produkt, cena, ilosc, data_waznosci)
VALUES (9, 4, 8.50, 4, '2024-03-29');
INSERT INTO wm_faktury_obce_pozycje (faktura, produkt, cena, ilosc, data_waznosci)
VALUES (10, 5, 2.40, 12, '2024-03-30');
INSERT INTO wm_faktury_obce_pozycje (faktura, produkt, cena, ilosc, data_waznosci)
VALUES (1, 6, 9.20, 5, '2024-03-31');
INSERT INTO wm_faktury_obce_pozycje (faktura, produkt, cena, ilosc, data_waznosci)
VALUES (2, 7, 4.75, 9, '2024-04-01');
INSERT INTO wm_faktury_obce_pozycje (faktura, produkt, cena, ilosc, data_waznosci)
VALUES (3, 8, 6.90, 7, '2024-04-02');
INSERT INTO wm_faktury_obce_pozycje (faktura, produkt, cena, ilosc, data_waznosci)
VALUES (4, 9, 11.40, 3, '2024-04-03');
INSERT INTO wm_faktury_obce_pozycje (faktura, produkt, cena, ilosc, data_waznosci)
VALUES (5, 10, 7.20, 6, '2024-04-04');
INSERT INTO wm_faktury_obce_pozycje (faktura, produkt, cena, ilosc, data_waznosci)
VALUES (6, 11, 3.90, 11, '2024-04-05');
INSERT INTO wm_faktury_obce_pozycje (faktura, produkt, cena, ilosc, data_waznosci)
VALUES (7, 12, 5.60, 8, '2024-04-06');
INSERT INTO wm_faktury_obce_pozycje (faktura, produkt, cena, ilosc, data_waznosci)
VALUES (8, 13, 2.30, 14, '2024-04-07');
INSERT INTO wm_faktury_obce_pozycje (faktura, produkt, cena, ilosc, data_waznosci)
VALUES (9, 14, 6.80, 7, '2024-04-08');
INSERT INTO wm_faktury_obce_pozycje (faktura, produkt, cena, ilosc, data_waznosci)
VALUES (10, 15, 8.90, 5, '2024-04-09');
INSERT INTO wm_faktury_obce_pozycje (faktura, produkt, cena, ilosc, data_waznosci)
VALUES (1, 16, 4.50, 9, '2024-04-10');
INSERT INTO wm_faktury_obce_pozycje (faktura, produkt, cena, ilosc, data_waznosci)
VALUES (2, 17, 9.20, 4, '2024-04-11');
INSERT INTO wm_faktury_obce_pozycje (faktura, produkt, cena, ilosc, data_waznosci)
VALUES (3, 18, 5.40, 10, '2024-04-12');
INSERT INTO wm_faktury_obce_pozycje (faktura, produkt, cena, ilosc, data_waznosci)
VALUES (4, 19, 3.70, 13, '2024-04-13');
INSERT INTO wm_faktury_obce_pozycje (faktura, produkt, cena, ilosc, data_waznosci)
VALUES (5, 20, 2.90, 15, '2024-04-14');
INSERT INTO wm_faktury_obce_pozycje (faktura, produkt, cena, ilosc, data_waznosci)
VALUES (6, 21, 7.60, 6, '2024-04-15');
INSERT INTO wm_faktury_obce_pozycje (faktura, produkt, cena, ilosc, data_waznosci)
VALUES (7, 22, 4.20, 12, '2024-04-16');
INSERT INTO wm_faktury_obce_pozycje (faktura, produkt, cena, ilosc, data_waznosci)
VALUES (8, 24, 6.90, 8, '2024-04-17');
INSERT INTO wm_faktury_obce_pozycje (faktura, produkt, cena, ilosc, data_waznosci)
VALUES (9, 25, 8.30, 5, '2024-04-18');
INSERT INTO wm_faktury_obce_pozycje (faktura, produkt, cena, ilosc, data_waznosci)
VALUES (10, 26, 3.50, 11, '2024-04-19');

CREATE TABLE wm_magazyn
(
    id            NUMBER(5) GENERATED ALWAYS AS IDENTITY,
    produkt       NUMBER(5) NOT NULL,
    ilosc         NUMBER(5) NOT NULL,
    data_waznosci DATE      NULL,
    faktura       NUMBER(5) NOT NULL,
    CONSTRAINT pk_magazyn_id PRIMARY KEY (id),
    CONSTRAINT fk_magazyn_produkty FOREIGN KEY (produkt) REFERENCES wm_produkty (id),
    CONSTRAINT fk_magazyn_faktury_zakupu FOREIGN KEY (faktura) REFERENCES wm_faktury_obce (id)
);

CREATE TABLE wm_przepisy
(
    id    NUMBER(5) GENERATED ALWAYS AS IDENTITY,
    nazwa VARCHAR2(50) NOT NULL,
    CONSTRAINT pk_przepisy_id PRIMARY KEY (id),
    CONSTRAINT uk_przepisy_nazwa UNIQUE (nazwa)
);

INSERT INTO wm_przepisy (nazwa)
VALUES ('Pad Thai z Kurczakiem');
INSERT INTO wm_przepisy (nazwa)
VALUES ('Ramen z Wołowiną');

CREATE TABLE wm_przepisy_skladniki
(
    id      NUMBER(5) GENERATED ALWAYS AS IDENTITY,
    przepis NUMBER(5)    NOT NULL,
    produkt NUMBER(5)    NOT NULL,
    ilosc   NUMBER(8, 2) NOT NULL,
    CONSTRAINT pk_przepisy_skladniki_id PRIMARY KEY (id),
    CONSTRAINT fk_przepisy_skladniki_przepisy FOREIGN KEY (przepis) REFERENCES wm_przepisy (id),
    CONSTRAINT fk_przepisy_skladniki_produkty FOREIGN KEY (produkt) REFERENCES wm_produkty (id)
);

SELECT *
FROM wm_produkty;
INSERT INTO wm_przepisy_skladniki (przepis, produkt, ilosc)
VALUES ((SELECT id FROM wm_przepisy WHERE nazwa = 'Pad Thai z Kurczakiem'),
        (SELECT id FROM wm_produkty WHERE nazwa = 'Makaron ryżowy'), 200);
INSERT INTO wm_przepisy_skladniki (przepis, produkt, ilosc)
VALUES ((SELECT id FROM wm_przepisy WHERE nazwa = 'Pad Thai z Kurczakiem'),
        (SELECT id FROM wm_produkty WHERE nazwa = 'Kurczak'), 300);
INSERT INTO wm_przepisy_skladniki (przepis, produkt, ilosc)
VALUES ((SELECT id FROM wm_przepisy WHERE nazwa = 'Pad Thai z Kurczakiem'),
        (SELECT id FROM wm_produkty WHERE nazwa = 'Jajko'), 1);
INSERT INTO wm_przepisy_skladniki (przepis, produkt, ilosc)
VALUES ((SELECT id FROM wm_przepisy WHERE nazwa = 'Pad Thai z Kurczakiem'),
        (SELECT id FROM wm_produkty WHERE nazwa = 'Sos sojowy'), 0.03);
INSERT INTO wm_przepisy_skladniki (przepis, produkt, ilosc)
VALUES ((SELECT id FROM wm_przepisy WHERE nazwa = 'Pad Thai z Kurczakiem'),
        (SELECT id FROM wm_produkty WHERE nazwa = 'Kiełki fasoli mung'), 50);
INSERT INTO wm_przepisy_skladniki (przepis, produkt, ilosc)
VALUES ((SELECT id FROM wm_przepisy WHERE nazwa = 'Pad Thai z Kurczakiem'),
        (SELECT id FROM wm_produkty WHERE nazwa = 'Orzeszki ziemne'), 50);
INSERT INTO wm_przepisy_skladniki (przepis, produkt, ilosc)
VALUES ((SELECT id FROM wm_przepisy WHERE nazwa = 'Ramen z Wołowiną'),
        (SELECT id FROM wm_produkty WHERE nazwa = 'Woda'), 2);
INSERT INTO wm_przepisy_skladniki (przepis, produkt, ilosc)
VALUES ((SELECT id FROM wm_przepisy WHERE nazwa = 'Ramen z Wołowiną'),
        (SELECT id FROM wm_produkty WHERE nazwa = 'Sos sojowy'), 0.06);
INSERT INTO wm_przepisy_skladniki (przepis, produkt, ilosc)
VALUES ((SELECT id FROM wm_przepisy WHERE nazwa = 'Ramen z Wołowiną'),
        (SELECT id FROM wm_produkty WHERE nazwa = 'Mirin'), 0.03);
INSERT INTO wm_przepisy_skladniki (przepis, produkt, ilosc)
VALUES ((SELECT id FROM wm_przepisy WHERE nazwa = 'Ramen z Wołowiną'),
        (SELECT id FROM wm_produkty WHERE nazwa = 'Sake'), 0.03);
INSERT INTO wm_przepisy_skladniki (przepis, produkt, ilosc)
VALUES ((SELECT id FROM wm_przepisy WHERE nazwa = 'Ramen z Wołowiną'),
        (SELECT id FROM wm_produkty WHERE nazwa = 'Wodorosty kombu'), 0.01);
INSERT INTO wm_przepisy_skladniki (przepis, produkt, ilosc)
VALUES ((SELECT id FROM wm_przepisy WHERE nazwa = 'Ramen z Wołowiną'),
        (SELECT id FROM wm_produkty WHERE nazwa = 'Wodorosty wakame'), 0.015);
INSERT INTO wm_przepisy_skladniki (przepis, produkt, ilosc)
VALUES ((SELECT id FROM wm_przepisy WHERE nazwa = 'Ramen z Wołowiną'),
        (SELECT id FROM wm_produkty WHERE nazwa = 'Wołowina'), 0.3);
INSERT INTO wm_przepisy_skladniki (przepis, produkt, ilosc)
VALUES ((SELECT id FROM wm_przepisy WHERE nazwa = 'Ramen z Wołowiną'),
        (SELECT id FROM wm_produkty WHERE nazwa = 'Makaron ramen'), 0.2);
INSERT INTO wm_przepisy_skladniki (przepis, produkt, ilosc)
VALUES ((SELECT id FROM wm_przepisy WHERE nazwa = 'Ramen z Wołowiną'),
        (SELECT id FROM wm_produkty WHERE nazwa = 'Jajko'), 1);
INSERT INTO wm_przepisy_skladniki (przepis, produkt, ilosc)
VALUES ((SELECT id FROM wm_przepisy WHERE nazwa = 'Ramen z Wołowiną'),
        (SELECT id FROM wm_produkty WHERE nazwa = 'Szczypiorek'), 0.015);

CREATE TABLE wm_menu
(
    id    NUMBER(5) GENERATED ALWAYS AS IDENTITY,
    nazwa VARCHAR2(30) NOT NULL,
    CONSTRAINT pk_menu_id PRIMARY KEY (id),
    CONSTRAINT uk_menu_nazwa UNIQUE (nazwa)
);

INSERT INTO wm_menu (nazwa)
VALUES ('Menu japońskie');
INSERT INTO wm_menu (nazwa)
VALUES ('Menu tajskie');
INSERT INTO wm_menu (nazwa)
VALUES ('Menu chińskie');

CREATE TABLE wm_menu_pozycje
(
    id        NUMBER(5) GENERATED ALWAYS AS IDENTITY,
    nazwa     VARCHAR2(30) NOT NULL,
    cena      NUMBER(6, 2) NOT NULL,
    przepis   NUMBER(5)    NOT NULL,
    kategoria VARCHAR2(15) NOT NULL,
    menu      NUMBER(5)    NOT NULL,
    CONSTRAINT pk_menu_pozycje_id PRIMARY KEY (id),
    CONSTRAINT fk_menu_pozycje_przepisy FOREIGN KEY (przepis) REFERENCES wm_przepisy (id),
    CONSTRAINT chk_menu_pozycje_kategoria CHECK (kategoria IN ('zupy', 'dania glowne', 'przystawki', 'desery', 'napoje')),
    CONSTRAINT fk_menu_pozycje_menu FOREIGN KEY (menu) REFERENCES wm_menu (id)
);

INSERT INTO wm_menu_pozycje (nazwa, cena, przepis, kategoria, menu)
VALUES ('Pad Thai z Kurczakiem', 25.00, (SELECT id FROM wm_przepisy WHERE nazwa = 'Pad Thai z Kurczakiem'), 'dania glowne',
        (SELECT id FROM wm_menu WHERE nazwa = 'Menu tajskie'));
INSERT INTO wm_menu_pozycje (nazwa, cena, przepis, kategoria, menu)
VALUES ('Ramen z Wołowiną', 30.00, (SELECT id FROM wm_przepisy WHERE nazwa = 'Ramen z Wołowiną'), 'dania glowne',
        (SELECT id FROM wm_menu WHERE nazwa = 'Menu japońskie'));

CREATE TABLE wm_klienci
(
    id       NUMBER(5) GENERATED ALWAYS AS IDENTITY,
    imie     VARCHAR2(30) NOT NULL,
    nazwisko VARCHAR2(30) NOT NULL,
    punkty   NUMBER(5) DEFAULT 0,
    CONSTRAINT pk_klienci_numer_karty PRIMARY KEY (id)
);

INSERT INTO wm_klienci (imie, nazwisko, punkty)
VALUES ('Gall', 'Anonim', -1);

CREATE TABLE wm_zamowienia
(
    id            NUMBER(5) GENERATED ALWAYS AS IDENTITY,
    kelner        NUMBER(5) NOT NULL,
    stolik        NUMBER(5) NOT NULL,
    czas          TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    klient        NUMBER(5) NOT NULL,
    czy_zaplacone NUMBER(1) DEFAULT 0,
    CONSTRAINT pk_zamowienia_id PRIMARY KEY (id),
    CONSTRAINT fk_zamowienia_pracownicy FOREIGN KEY (kelner) REFERENCES wm_pracownicy (id),
    CONSTRAINT fk_zamowienia_klienci FOREIGN KEY (klient) REFERENCES wm_klienci (id),
    CONSTRAINT chk_zamowienia_czy_zaplacone CHECK (czy_zaplacone IN (0, 1))
);

CREATE OR REPLACE TRIGGER wm_trig_zamowienia_czy_zaplacone
    BEFORE INSERT OR UPDATE OF czy_zaplacone
    ON wm_zamowienia
BEGIN
    IF (SELECT COUNT(czy_zaplacone)
        FROM wm_zamowienia
        WHERE czy_zaplacone = 0
          AND stolik = :NEW.stolik
        GROUP BY stolik) > 0 THEN
        RAISE_APPLICATION_ERROR(-20001, 'Stolik jest już zajęty');
    END IF;
END;

INSERT INTO wm_zamowienia (kelner, stolik, klient)
VALUES ((SELECT id FROM wm_pracownicy WHERE login = 'SBD147412'), 1,
        (SELECT id FROM wm_klienci WHERE punkty = -1));
INSERT INTO wm_zamowienia_pozycje (zamowienie, pozycja, ilosc)
VALUES ((SELECT id FROM wm_zamowienia WHERE stolik = 1 AND czy_zaplacone = 0),
        (SELECT id FROM wm_menu_pozycje WHERE nazwa = 'Pad Thai z Kurczakiem'), 1);
INSERT INTO wm_zamowienia_pozycje (zamowienie, pozycja, ilosc)
VALUES ((SELECT id FROM wm_zamowienia WHERE stolik = 1 AND czy_zaplacone = 0),
        (SELECT id FROM wm_menu_pozycje WHERE nazwa = 'Ramen z Wołowiną'), 1);

INSERT INTO wm_zamowienia (kelner, stolik, klient)
VALUES ((SELECT id FROM wm_pracownicy WHERE login = 'SBD147412'), 2,
        (SELECT id FROM wm_klienci WHERE punkty = -1));
INSERT INTO wm_zamowienia_pozycje (zamowienie, pozycja, ilosc)
VALUES ((SELECT id FROM wm_zamowienia WHERE stolik = 2 AND czy_zaplacone = 0),
        (SELECT id FROM wm_menu_pozycje WHERE nazwa = 'Ramen z Wołowiną'), 2);

INSERT INTO wm_zamowienia (kelner, stolik, klient)
VALUES ((SELECT id FROM wm_pracownicy WHERE login = 'SBD151886'), 3,
        (SELECT id FROM wm_klienci WHERE punkty = -1));
INSERT INTO wm_zamowienia_pozycje (zamowienie, pozycja, ilosc)
VALUES ((SELECT id FROM wm_zamowienia WHERE stolik = 3 AND czy_zaplacone = 0),
        (SELECT id FROM wm_menu_pozycje WHERE nazwa = 'Pad Thai z Kurczakiem'), 3);

INSERT INTO wm_zamowienia (kelner, stolik, klient)
VALUES ((SELECT id FROM wm_pracownicy WHERE login = 'SBD151886'), 4,
        (SELECT id FROM wm_klienci WHERE punkty = -1));
INSERT INTO wm_zamowienia_pozycje (zamowienie, pozycja, ilosc)
VALUES ((SELECT id FROM wm_zamowienia WHERE stolik = 4 AND czy_zaplacone = 0),
        (SELECT id FROM wm_menu_pozycje WHERE nazwa = 'Ramen z Wołowiną'), 1);
INSERT INTO wm_zamowienia_pozycje (zamowienie, pozycja, ilosc)
VALUES ((SELECT id FROM wm_zamowienia WHERE stolik = 4 AND czy_zaplacone = 0),
        (SELECT id FROM wm_menu_pozycje WHERE nazwa = 'Pad Thai z Kurczakiem'), 2);

CREATE TABLE wm_zamowienia_pozycje
(
    id         NUMBER(5) GENERATED ALWAYS AS IDENTITY,
    zamowienie NUMBER(5) NOT NULL,
    pozycja    NUMBER(5) NOT NULL,
    ilosc      NUMBER(5) NOT NULL,
    CONSTRAINT pk_zamowienia_pozycje_id PRIMARY KEY (id),
    CONSTRAINT fk_zamowienia_pozycje_zamowienia FOREIGN KEY (zamowienie) REFERENCES wm_zamowienia (id),
    CONSTRAINT fk_zamowienia_pozycje_menu_pozycje FOREIGN KEY (pozycja) REFERENCES wm_menu_pozycje (id)
);

CREATE TABLE wm_faktury
(
    id         NUMBER(5) GENERATED ALWAYS AS IDENTITY,
    nr_faktury VARCHAR2(30) NOT NULL,
    data       DATE         NOT NULL,
    klient     NUMBER(5)    NOT NULL,
    zamowiene  NUMBER(5)    NOT NULL,
    znizka     NUMBER(6, 2) DEFAULT 0,
    CONSTRAINT pk_faktury_id PRIMARY KEY (id),
    CONSTRAINT fk_faktury_klienci FOREIGN KEY (klient) REFERENCES wm_klienci (id),
    CONSTRAINT fk_faktury_zamowienia FOREIGN KEY (zamowiene) REFERENCES wm_zamowienia (id)
);

CREATE OR REPLACE FUNCTION wm_rola_przyznana(rola IN VARCHAR2) RETURN NUMBER AUTHID CURRENT_USER IS
    v_result NUMBER;
BEGIN
    SELECT COUNT(*)
    INTO v_result
    FROM user_role_privs
    WHERE granted_role = rola;
    RETURN CASE WHEN v_result > 0 THEN 1 ELSE 0 END;
END;
GRANT EXECUTE ON wm_rola_przyznana TO PUBLIC;

CREATE OR REPLACE FUNCTION wm_my_id(username IN VARCHAR2) RETURN NUMBER IS
    v_id NUMBER;
BEGIN
    SELECT id
    INTO v_id
    FROM wm_pracownicy
    WHERE login = username;
    RETURN v_id;
END;
GRANT EXECUTE ON wm_my_id TO PUBLIC;


CREATE ROLE wm_kucharz;
GRANT SELECT ON wm_przepisy TO wm_kucharz;
GRANT SELECT ON wm_przepisy_skladniki TO wm_kucharz;
GRANT SELECT ON wm_menu TO wm_kucharz;
GRANT SELECT ON wm_menu_pozycje TO wm_kucharz;

CREATE ROLE wm_kasjer;
GRANT SELECT, INSERT, UPDATE ON wm_klienci TO wm_kasjer;
GRANT SELECT, INSERT, UPDATE, DELETE ON wm_zamowienia TO wm_kasjer;
GRANT SELECT, INSERT, UPDATE, DELETE ON wm_zamowienia_pozycje TO wm_kasjer;
GRANT SELECT, INSERT, UPDATE, DELETE ON wm_faktury TO wm_kasjer;

CREATE ROLE wm_kelner;
GRANT wm_kucharz TO wm_kelner;
GRANT wm_kasjer TO wm_kelner;
GRANT SELECT ON wm_pracownicy TO wm_kelner;

CREATE ROLE wm_szef_kuchni;
GRANT wm_kucharz TO wm_szef_kuchni;
GRANT INSERT, UPDATE, DELETE ON wm_przepisy TO wm_szef_kuchni;
GRANT INSERT, UPDATE, DELETE ON wm_przepisy_skladniki TO wm_szef_kuchni;
GRANT INSERT, UPDATE, DELETE ON wm_menu TO wm_szef_kuchni;
GRANT INSERT, UPDATE, DELETE ON wm_menu_pozycje TO wm_szef_kuchni;

CREATE ROLE wm_menadzer_hr;
GRANT SELECT ON wm_stanowiska TO wm_menadzer_hr;
GRANT SELECT, INSERT, UPDATE ON wm_pracownicy TO wm_menadzer_hr;
GRANT SELECT, INSERT, UPDATE ON wm_umowy TO wm_menadzer_hr;

CREATE ROLE wm_zaopatrzenie;
GRANT SELECT, INSERT, UPDATE ON wm_kontrahenci TO wm_zaopatrzenie;
GRANT SELECT, INSERT, UPDATE, DELETE ON wm_produkty TO wm_zaopatrzenie;
GRANT SELECT, INSERT, UPDATE ON wm_faktury_obce TO wm_zaopatrzenie;
GRANT SELECT, INSERT, UPDATE ON wm_faktury_obce_pozycje TO wm_zaopatrzenie;
GRANT SELECT, INSERT, UPDATE, DELETE ON wm_magazyn TO wm_zaopatrzenie;

CREATE ROLE wm_administrator;
BEGIN
    FOR t IN (SELECT table_name FROM user_tables WHERE table_name LIKE 'WM_%')
        LOOP
            EXECUTE IMMEDIATE 'GRANT ALL PRIVILEGES
         ON ' || t.table_name || ' TO wm_administrator ';
        END LOOP;
END;

SELECT *
FROM wm_pracownicy;

SELECT *
FROM wm_stanowiska;


GRANT wm_kelner TO sbd151886;
