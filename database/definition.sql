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
    id          NUMBER(5) GENERATED ALWAYS AS IDENTITY,
    imie        VARCHAR2(30) NOT NULL,
    nazwisko    VARCHAR2(30) NOT NULL,
    stanowisko  NUMBER(5)    NOT NULL,
    numer_umowy VARCHAR2(15) NOT NULL,
    CONSTRAINT pk_pracownicy_id PRIMARY KEY (id),
    CONSTRAINT fk_pracownicy_stanowiska FOREIGN KEY (stanowisko) REFERENCES wm_stanowiska (id)
);

INSERT INTO wm_pracownicy (imie, nazwisko, stanowisko, numer_umowy)
VALUES ('Jan', 'Kowalski', (SELECT id FROM wm_stanowiska WHERE nazwa = 'kucharz'), 'UM-0001');
INSERT INTO wm_pracownicy (imie, nazwisko, stanowisko, numer_umowy)
VALUES ('Anna', 'Nowak', (SELECT id FROM wm_stanowiska WHERE nazwa = 'kelner'), 'UM-0002');
INSERT INTO wm_pracownicy (imie, nazwisko, stanowisko, numer_umowy)
VALUES ('Piotr', 'Wiśniewski', (SELECT id FROM wm_stanowiska WHERE nazwa = 'zmywacz'), 'UM-0003');
INSERT INTO wm_pracownicy (imie, nazwisko, stanowisko, numer_umowy)
VALUES ('Agnieszka', 'Kowalczyk', (SELECT id FROM wm_stanowiska WHERE nazwa = 'barman'), 'UM-0004');
INSERT INTO wm_pracownicy (imie, nazwisko, stanowisko, numer_umowy)
VALUES ('Tomasz', 'Duda', (SELECT id FROM wm_stanowiska WHERE nazwa = 'szef kuchni'), 'UM-0005');
INSERT INTO wm_pracownicy (imie, nazwisko, stanowisko, numer_umowy)
VALUES ('Maria', 'Lewandowska', (SELECT id FROM wm_stanowiska WHERE nazwa = 'pomoc kuchenna'), 'UM-0006');
INSERT INTO wm_pracownicy (imie, nazwisko, stanowisko, numer_umowy)
VALUES ('Michał', 'Wójcik', (SELECT id FROM wm_stanowiska WHERE nazwa = 'kelner'), 'UM-0007');
INSERT INTO wm_pracownicy (imie, nazwisko, stanowisko, numer_umowy)
VALUES ('Katarzyna', 'Zielinska', (SELECT id FROM wm_stanowiska WHERE nazwa = 'host'), 'UM-0008');
INSERT INTO wm_pracownicy (imie, nazwisko, stanowisko, numer_umowy)
VALUES ('Paweł', 'Kamiński', (SELECT id FROM wm_stanowiska WHERE nazwa = 'barman'), 'UM-0009');
INSERT INTO wm_pracownicy (imie, nazwisko, stanowisko, numer_umowy)
VALUES ('Magdalena', 'Kwiatkowska', (SELECT id FROM wm_stanowiska WHERE nazwa = 'zmywacz'), 'UM-0010');
INSERT INTO wm_pracownicy (imie, nazwisko, stanowisko, numer_umowy)
VALUES ('Marcin', 'Król', (SELECT id FROM wm_stanowiska WHERE nazwa = 'kucharz'), 'UM-0011');
INSERT INTO wm_pracownicy (imie, nazwisko, stanowisko, numer_umowy)
VALUES ('Barbara', 'Bąk', (SELECT id FROM wm_stanowiska WHERE nazwa = 'kelner'), 'UM-0012');
INSERT INTO wm_pracownicy (imie, nazwisko, stanowisko, numer_umowy)
VALUES ('Jakub', 'Woźniak', (SELECT id FROM wm_stanowiska WHERE nazwa = 'pomoc kuchenna'), 'UM-0013');
INSERT INTO wm_pracownicy (imie, nazwisko, stanowisko, numer_umowy)
VALUES ('Zofia', 'Szymczak', (SELECT id FROM wm_stanowiska WHERE nazwa = 'barman'), 'UM-0014');
INSERT INTO wm_pracownicy (imie, nazwisko, stanowisko, numer_umowy)
VALUES ('Stanisław', 'Wróbel', (SELECT id FROM wm_stanowiska WHERE nazwa = 'zmywacz'), 'UM-0015');
INSERT INTO wm_pracownicy (imie, nazwisko, stanowisko, numer_umowy)
VALUES ('Elżbieta', 'Kozłowska', (SELECT id FROM wm_stanowiska WHERE nazwa = 'kelner'), 'UM-0016');
INSERT INTO wm_pracownicy (imie, nazwisko, stanowisko, numer_umowy)
VALUES ('Andrzej', 'Kruk', (SELECT id FROM wm_stanowiska WHERE nazwa = 'szef kuchni'), 'UM-0017');
INSERT INTO wm_pracownicy (imie, nazwisko, stanowisko, numer_umowy)
VALUES ('Alicja', 'Stępień', (SELECT id FROM wm_stanowiska WHERE nazwa = 'pomoc kuchenna'), 'UM-0018');
INSERT INTO wm_pracownicy (imie, nazwisko, stanowisko, numer_umowy)
VALUES ('Adam', 'Malinowski', (SELECT id FROM wm_stanowiska WHERE nazwa = 'kelner'), 'UM-0019');
INSERT INTO wm_pracownicy (imie, nazwisko, stanowisko, numer_umowy)
VALUES ('Ewa', 'Pawlak', (SELECT id FROM wm_stanowiska WHERE nazwa = 'barman'), 'UM-0020');

CREATE TABLE wm_umowy
(
    id        NUMBER(5) GENERATED ALWAYS AS IDENTITY,
    typ       VARCHAR2(30) NOT NULL,
    zawiazana DATE DEFAULT CURRENT_TIMESTAMP,
    zerwana   DATE         NULL,
    CONSTRAINT pk_umowy_id PRIMARY KEY (id)
);

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

CREATE TABLE wm_produkty
(
    nazwa     VARCHAR2(30),
    jednostka VARCHAR2(7) NULL,
    CONSTRAINT pk_produkty_nazwa PRIMARY KEY (nazwa)
);

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

CREATE TABLE wm_magazyn
(
    id            NUMBER(5) GENERATED ALWAYS AS IDENTITY,
    produkt       VARCHAR2(30) NOT NULL,
    ilosc         NUMBER(5)    NOT NULL,
    data_waznosci DATE         NULL,
    faktura       NUMBER(5)    NOT NULL,
    CONSTRAINT pk_magazyn_id PRIMARY KEY (id),
    CONSTRAINT fk_magazyn_produkty FOREIGN KEY (produkt) REFERENCES wm_produkty (nazwa),
    CONSTRAINT fk_magazyn_faktury_zakupu FOREIGN KEY (faktura) REFERENCES wm_faktury_obce (id)
);

CREATE TABLE wm_faktury_obce_pozycje
(
    id            NUMBER(5) GENERATED ALWAYS AS IDENTITY,
    faktura       NUMBER(5)    NOT NULL,
    produkt       VARCHAR2(30) NOT NULL,
    cena          NUMBER(6, 2) NOT NULL,
    ilosc         NUMBER(8, 2) NOT NULL,
    data_waznosci DATE         NULL,
    CONSTRAINT pk_faktury_obce_pozycje_id PRIMARY KEY (id),
    CONSTRAINT fk_faktury_obce_pozycje_faktury_obce FOREIGN KEY (faktura) REFERENCES wm_faktury_obce (id),
    CONSTRAINT fk_faktury_obce_pozycje_produkty FOREIGN KEY (produkt) REFERENCES wm_produkty (nazwa)
);

CREATE TABLE wm_przepisy
(
    id    NUMBER(5) GENERATED ALWAYS AS IDENTITY,
    nazwa VARCHAR2(50) NOT NULL,
    CONSTRAINT pk_przepisy_id PRIMARY KEY (id),
    CONSTRAINT uk_przepisy_nazwa UNIQUE (nazwa)
);

CREATE TABLE wm_przepisy_skladniki
(
    id      NUMBER(5) GENERATED ALWAYS AS IDENTITY,
    przepis NUMBER(5)    NOT NULL,
    produkt VARCHAR2(30) NOT NULL,
    ilosc   NUMBER(8, 2) NOT NULL,
    CONSTRAINT pk_przepisy_skladniki_id PRIMARY KEY (id),
    CONSTRAINT fk_przepisy_skladniki_przepisy FOREIGN KEY (przepis) REFERENCES wm_przepisy (id),
    CONSTRAINT fk_przepisy_skladniki_produkty FOREIGN KEY (produkt) REFERENCES wm_produkty (nazwa)
);

CREATE TABLE wm_menu
(
    id    NUMBER(5) GENERATED ALWAYS AS IDENTITY,
    nazwa VARCHAR2(30) NOT NULL,
    CONSTRAINT pk_menu_id PRIMARY KEY (id),
    CONSTRAINT uk_menu_nazwa UNIQUE (nazwa)
);

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

CREATE TABLE wm_klienci
(
    numer_karty NUMBER(5) GENERATED ALWAYS AS IDENTITY,
    imie        VARCHAR2(30) NOT NULL,
    nazwisko    VARCHAR2(30) NOT NULL,
    punkty      NUMBER(5) DEFAULT 0,
    CONSTRAINT pk_klienci_numer_karty PRIMARY KEY (numer_karty)
);

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
    CONSTRAINT fk_zamowienia_klienci FOREIGN KEY (klient) REFERENCES wm_klienci (numer_karty),
    CONSTRAINT chk_zamowienia_czy_zaplacone CHECK (czy_zaplacone IN (0, 1))
);

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
    CONSTRAINT fk_faktury_klienci FOREIGN KEY (klient) REFERENCES wm_klienci (numer_karty),
    CONSTRAINT fk_faktury_zamowienia FOREIGN KEY (zamowiene) REFERENCES wm_zamowienia (id)
);

CREATE ROLE wm_kuchnia;
GRANT SELECT ON wm_przepisy TO wm_kucharz;
GRANT SELECT ON wm_przepisy_skladniki TO wm_kucharz;
GRANT SELECT ON wm_menu TO wm_kucharz;
GRANT SELECT ON wm_menu_pozycje TO wm_kucharz;

CREATE ROLE wm_kasa;
GRANT SELECT, INSERT, UPDATE ON wm_klienci TO wm_kelner;
GRANT SELECT, INSERT, UPDATE, DELETE ON wm_zamowienia TO wm_kelner;
GRANT SELECT, INSERT, UPDATE, DELETE ON wm_zamowienia_pozycje TO wm_kelner;
GRANT SELECT, INSERT, UPDATE, DELETE ON wm_faktury TO wm_kelner;

CREATE ROLE wm_kelner;
GRANT wm_kuchnia TO wm_kelner;
GRANT wm_kasa TO wm_kelner;

CREATE ROLE wm_szef_kuchni;
GRANT wm_kuchnia TO wm_szef_kuchni;
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
FOR t IN (SELECT table_name FROM user_tables WHERE table_name LIKE 'WM_%') LOOP
    EXECUTE IMMEDIATE 'GRANT ALL PRIVILEGES ON ' || t.table_name || ' TO wm_administrator';
END LOOP;
END;

SELECT *
FROM wm_pracownicy;

SELECT *
FROM wm_stanowiska;