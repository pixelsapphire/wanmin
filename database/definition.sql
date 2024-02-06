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
    id         NUMBER(5) GENERATED ALWAYS AS IDENTITY,
    imie       VARCHAR2(30) NOT NULL,
    nazwisko   VARCHAR2(30) NOT NULL,
    stanowisko NUMBER(5)    NOT NULL,
    CONSTRAINT pk_pracownicy_id PRIMARY KEY (id),
    CONSTRAINT fk_pracownicy_stanowiska FOREIGN KEY (stanowisko) REFERENCES wm_stanowiska (id)
);

INSERT INTO wm_pracownicy (imie, nazwisko, stanowisko)
VALUES ('Jan', 'Kowalski', (SELECT id FROM wm_stanowiska WHERE nazwa = 'kucharz'));
INSERT INTO wm_pracownicy (imie, nazwisko, stanowisko)
VALUES ('Anna', 'Nowak', (SELECT id FROM wm_stanowiska WHERE nazwa = 'kelner'));
INSERT INTO wm_pracownicy (imie, nazwisko, stanowisko)
VALUES ('Piotr', 'Wiśniewski', (SELECT id FROM wm_stanowiska WHERE nazwa = 'zmywacz'));
INSERT INTO wm_pracownicy (imie, nazwisko, stanowisko)
VALUES ('Agnieszka', 'Kowalczyk', (SELECT id FROM wm_stanowiska WHERE nazwa = 'barman'));
INSERT INTO wm_pracownicy (imie, nazwisko, stanowisko)
VALUES ('Tomasz', 'Duda', (SELECT id FROM wm_stanowiska WHERE nazwa = 'szef kuchni'));
INSERT INTO wm_pracownicy (imie, nazwisko, stanowisko)
VALUES ('Maria', 'Lewandowska', (SELECT id FROM wm_stanowiska WHERE nazwa = 'pomoc kuchenna'));
INSERT INTO wm_pracownicy (imie, nazwisko, stanowisko)
VALUES ('Michał', 'Wójcik', (SELECT id FROM wm_stanowiska WHERE nazwa = 'kelner'));
INSERT INTO wm_pracownicy (imie, nazwisko, stanowisko)
VALUES ('Katarzyna', 'Zielinska', (SELECT id FROM wm_stanowiska WHERE nazwa = 'host'));
INSERT INTO wm_pracownicy (imie, nazwisko, stanowisko)
VALUES ('Paweł', 'Kamiński', (SELECT id FROM wm_stanowiska WHERE nazwa = 'barman'));
INSERT INTO wm_pracownicy (imie, nazwisko, stanowisko)
VALUES ('Magdalena', 'Kwiatkowska', (SELECT id FROM wm_stanowiska WHERE nazwa = 'zmywacz'));
INSERT INTO wm_pracownicy (imie, nazwisko, stanowisko)
VALUES ('Marcin', 'Król', (SELECT id FROM wm_stanowiska WHERE nazwa = 'kucharz'));
INSERT INTO wm_pracownicy (imie, nazwisko, stanowisko)
VALUES ('Barbara', 'Bąk', (SELECT id FROM wm_stanowiska WHERE nazwa = 'kelner'));
INSERT INTO wm_pracownicy (imie, nazwisko, stanowisko)
VALUES ('Jakub', 'Woźniak', (SELECT id FROM wm_stanowiska WHERE nazwa = 'pomoc kuchenna'));
INSERT INTO wm_pracownicy (imie, nazwisko, stanowisko)
VALUES ('Zofia', 'Szymczak', (SELECT id FROM wm_stanowiska WHERE nazwa = 'barman'));
INSERT INTO wm_pracownicy (imie, nazwisko, stanowisko)
VALUES ('Stanisław', 'Wróbel', (SELECT id FROM wm_stanowiska WHERE nazwa = 'zmywacz'));
INSERT INTO wm_pracownicy (imie, nazwisko, stanowisko)
VALUES ('Elżbieta', 'Kozłowska', (SELECT id FROM wm_stanowiska WHERE nazwa = 'kelner'));
INSERT INTO wm_pracownicy (imie, nazwisko, stanowisko)
VALUES ('Andrzej', 'Kruk', (SELECT id FROM wm_stanowiska WHERE nazwa = 'szef kuchni'));
INSERT INTO wm_pracownicy (imie, nazwisko, stanowisko)
VALUES ('Alicja', 'Stępień', (SELECT id FROM wm_stanowiska WHERE nazwa = 'pomoc kuchenna'));
INSERT INTO wm_pracownicy (imie, nazwisko, stanowisko)
VALUES ('Adam', 'Malinowski', (SELECT id FROM wm_stanowiska WHERE nazwa = 'kelner'));
INSERT INTO wm_pracownicy (imie, nazwisko, stanowisko)
VALUES ('Ewa', 'Pawlak', (SELECT id FROM wm_stanowiska WHERE nazwa = 'barman'));

CREATE TABLE wm_umowy
(
    id        NUMBER(5) GENERATED ALWAYS AS IDENTITY,
    numer     VARCHAR2(15) NOT NULL,
    typ       VARCHAR2(30) NOT NULL,
    pracownik NUMBER(5)    NOT NULL,
    zawiazana DATE DEFAULT CURRENT_TIMESTAMP,
    zerwana   DATE         NULL,
    CONSTRAINT pk_umowy_id PRIMARY KEY (id),
    CONSTRAINT fk_umowy_pracownicy FOREIGN KEY (pracownik) REFERENCES wm_pracownicy (id)
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

SELECT wm_rola_przyznana('WM_ADMINISTRATOR')
FROM dual;

SELECT *
FROM session_roles;

SELECT *
FROM session_roles;

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
            EXECUTE IMMEDIATE 'GRANT ALL PRIVILEGES ON ' || t.table_name || ' TO wm_administrator';
        END LOOP;
END;

SELECT *
FROM wm_pracownicy;

SELECT *
FROM wm_stanowiska;


GRANT wm_kelner TO sbd151886;
