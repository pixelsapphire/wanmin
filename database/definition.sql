CREATE TABLE wm_stanowiska
(
    nazwa  VARCHAR(30),
    pensja NUMBER(6, 2),
    CONSTRAINT pk_stanowiska_nazwa PRIMARY KEY (nazwa)
);

CREATE TABLE wm_pracownicy
(
    id          NUMBER(5) GENERATED ALWAYS AS IDENTITY,
    imie        VARCHAR(30) NOT NULL,
    nazwisko    VARCHAR(30) NOT NULL,
    stanowisko  VARCHAR(30) NOT NULL,
    numer_umowy VARCHAR(15) NOT NULL,
    CONSTRAINT pk_pracownicy_id PRIMARY KEY (id),
    CONSTRAINT fk_pracownicy_stanowiska FOREIGN KEY (stanowisko) REFERENCES wm_stanowiska (nazwa)
);

CREATE TABLE wm_umowy
(
    id        NUMBER(5) GENERATED ALWAYS AS IDENTITY,
    typ       VARCHAR(30) NOT NULL,
    zawiazana TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    zerwana   DATE        NULL,
    CONSTRAINT pk_umowy_id PRIMARY KEY (id)
);

CREATE TABLE wm_kontrahenci
(
    id      NUMBER(5) GENERATED ALWAYS AS IDENTITY,
    nazwa   VARCHAR(30) NOT NULL,
    adres   VARCHAR(30) NOT NULL,
    telefon VARCHAR(15) NULL,
    email   VARCHAR(30) NULL,
    NIP     VARCHAR(10) NULL,
    CONSTRAINT pk_kontrahenci_id PRIMARY KEY (id),
    CONSTRAINT uk_kontrahenci_nip UNIQUE (NIP)
);

CREATE TABLE wm_produkty
(
    nazwa     VARCHAR(30),
    jednostka VARCHAR(7) NULL,
    CONSTRAINT pk_produkty_nazwa PRIMARY KEY (nazwa)
);

CREATE TABLE wm_faktury_obce
(
    id         NUMBER(5) GENERATED ALWAYS AS IDENTITY,
    kontrahent NUMBER(5)   NOT NULL,
    data       DATE        NOT NULL,
    nr_obcy    VARCHAR(30) NOT NULL,
    CONSTRAINT pk_faktury_obce_id PRIMARY KEY (id),
    CONSTRAINT fk_faktury_obce_kontrahenci FOREIGN KEY (kontrahent) REFERENCES wm_kontrahenci (id),
    CONSTRAINT uk_faktury_obce_kontrahent_nr_obcy UNIQUE (kontrahent, nr_obcy)
);

CREATE TABLE wm_magazyn
(
    id            NUMBER(5) GENERATED ALWAYS AS IDENTITY,
    produkt       VARCHAR(30) NOT NULL,
    ilosc         NUMBER(5)   NOT NULL,
    data_waznosci DATE        NULL,
    faktura       NUMBER(5)   NOT NULL,
    CONSTRAINT pk_magazyn_id PRIMARY KEY (id),
    CONSTRAINT fk_magazyn_produkty FOREIGN KEY (produkt) REFERENCES wm_produkty (nazwa),
    CONSTRAINT fk_magazyn_faktury_zakupu FOREIGN KEY (faktura) REFERENCES wm_faktury_obce (id)
);

CREATE TABLE wm_faktury_obce_pozycje
(
    id            NUMBER(5) GENERATED ALWAYS AS IDENTITY,
    faktura       NUMBER(5)    NOT NULL,
    produkt       VARCHAR(30)  NOT NULL,
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
    nazwa VARCHAR(50) NOT NULL,
    CONSTRAINT pk_przepisy_id PRIMARY KEY (id),
    CONSTRAINT uk_przepisy_nazwa UNIQUE (nazwa)
);

CREATE TABLE wm_przepisy_skladniki
(
    id      NUMBER(5) GENERATED ALWAYS AS IDENTITY,
    przepis NUMBER(5)    NOT NULL,
    produkt VARCHAR(30)  NOT NULL,
    ilosc   NUMBER(8, 2) NOT NULL,
    CONSTRAINT pk_przepisy_skladniki_id PRIMARY KEY (id),
    CONSTRAINT fk_przepisy_skladniki_przepisy FOREIGN KEY (przepis) REFERENCES wm_przepisy (id),
    CONSTRAINT fk_przepisy_skladniki_produkty FOREIGN KEY (produkt) REFERENCES wm_produkty (nazwa)
);

CREATE TABLE wm_menu
(
    id    NUMBER(5) GENERATED ALWAYS AS IDENTITY,
    nazwa VARCHAR(30) NOT NULL,
    CONSTRAINT pk_menu_id PRIMARY KEY (id),
    CONSTRAINT uk_menu_nazwa UNIQUE (nazwa)
);

CREATE TABLE wm_menu_pozycje
(
    id        NUMBER(5) GENERATED ALWAYS AS IDENTITY,
    nazwa     VARCHAR(30)  NOT NULL,
    cena      NUMBER(6, 2) NOT NULL,
    przepis   NUMBER(5)    NOT NULL,
    kategoria VARCHAR(15)  NOT NULL,
    menu      NUMBER(5)    NOT NULL,
    CONSTRAINT pk_menu_pozycje_id PRIMARY KEY (id),
    CONSTRAINT fk_menu_pozycje_przepisy FOREIGN KEY (przepis) REFERENCES wm_przepisy (id),
    CONSTRAINT chk_menu_pozycje_kategoria CHECK (kategoria IN ('zupy', 'dania glowne', 'przystawki', 'desery', 'napoje')),
    CONSTRAINT fk_menu_pozycje_menu FOREIGN KEY (menu) REFERENCES wm_menu (id)
);

CREATE TABLE wm_klienci
(
    numer_karty NUMBER(5) GENERATED ALWAYS AS IDENTITY,
    imie        VARCHAR(30) NOT NULL,
    nazwisko    VARCHAR(30) NOT NULL,
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
    klient     NUMBER(5)   NOT NULL,
    data       DATE        NOT NULL,
    nr_faktury VARCHAR(30) NOT NULL,
    znizka     NUMBER(6, 2) DEFAULT 0,
    CONSTRAINT pk_faktury_id PRIMARY KEY (id),
    CONSTRAINT fk_faktury_kontrahenci FOREIGN KEY (klient) REFERENCES wm_klienci (numer_karty)
);