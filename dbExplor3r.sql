CREATE TABLE ft_UTENTE (
    id_utente INT AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    cognome VARCHAR(100) NOT NULL,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
	PRIMARY KEY(id_utente)
);

CREATE TABLE ft_PAESE (
    Stato VARCHAR(100) PRIMARY KEY,
    Continente VARCHAR(100) NOT NULL
);

CREATE TABLE ft_VIAGGIO (
    id_viaggio INT AUTO_INCREMENT,
    id_utente INT,
    Prezzo DECIMAL(10, 2) NOT NULL,
    Data_Partenza DATE NOT NULL,
    Data_Arrivo DATE NOT NULL,
    Continente VARCHAR(100) NOT NULL,
    Stato VARCHAR(100) NOT NULL,
    Descrizione TEXT,
    Itinerario TEXT,
    Difficolta ENUM('facile', 'intermedio', 'difficile') NOT NULL,
	PRIMARY KEY(id_viaggio)
    CONSTRAINT FK_vi_ut FOREIGN KEY (id_utente) REFERENCES UTENTE(id_utente),
    CONSTRAINT FK_vi_st FOREIGN KEY (Stato, Continente) REFERENCES PAESE(Stato, Continente)
);

CREATE TABLE df_PREFERITI (
    id_utente INT,
    id_viaggio INT,
    PRIMARY KEY (id_utente, id_viaggio),
    CONSTRAINT FK_pf_ut FOREIGN KEY (id_utente) REFERENCES UTENTE(id_utente),
    CONSTRAINT FK_pf_vi FOREIGN KEY (id_viaggio) REFERENCES VIAGGIO(id_viaggio)
);

CREATE TABLE df_PRENOTAZIONE (
    id_utente INT,
    id_viaggio INT,
    Data datetime DEFAULT CURRENT_TIMESTAMP NOT NULL,
    PRIMARY KEY (id_utente, id_viaggio),
    CONSTRAINT FK_pr_ut FOREIGN KEY (id_utente) REFERENCES UTENTE(id_utente),
    CONSTRAINT FK_pr_vi FOREIGN KEY (id_viaggio) REFERENCES VIAGGIO(id_viaggio)
);

INSERT INTO ft_utente (nome, cognome, username, password) VALUES ('Mario', 'Rossi', 'mrossi', 'password1');
INSERT INTO ft_utente (nome, cognome, username, password) VALUES ('Luigi', 'Verdi', 'lverdi', 'password2');
INSERT INTO ft_utente (nome, cognome, username, password) VALUES ('Giulia', 'Bianchi', 'gbianchi', 'password3');
INSERT INTO ft_utente (nome, cognome, username, password) VALUES ('Francesca', 'Neri', 'fneri', 'password4');
INSERT INTO ft_utente (nome, cognome, username, password) VALUES ('Alessandro', 'Russo', 'arusso', 'password5');
INSERT INTO ft_utente (nome, cognome, username, password) VALUES ('Sofia', 'Ferrari', 'sferrari', 'password6');
INSERT INTO ft_utente (nome, cognome, username, password) VALUES ('Lorenzo', 'Romano', 'lromano', 'password7');
INSERT INTO ft_utente (nome, cognome, username, password) VALUES ('Martina', 'Gallo', 'mgallo', 'password8');
INSERT INTO ft_utente (nome, cognome, username, password) VALUES ('Davide', 'Conti', 'dconti', 'password9');
INSERT INTO ft_utente (nome, cognome, username, password) VALUES ('Chiara', 'De Luca', 'cde_luca', 'password10');
INSERT INTO ft_utente (nome, cognome, username, password) VALUES ('Matteo', 'Marino', 'mmarino', 'password11');
INSERT INTO ft_utente (nome, cognome, username, password) VALUES ('Elena', 'Greco', 'egreco', 'password12');
INSERT INTO ft_utente (nome, cognome, username, password) VALUES ('Federico', 'Lombardi', 'flombardi', 'password13');
INSERT INTO ft_utente (nome, cognome, username, password) VALUES ('Sara', 'Barbieri', 'sbarbieri', 'password14');
INSERT INTO ft_utente (nome, cognome, username, password) VALUES ('Andrea', 'Fontana', 'afontana', 'password15');
INSERT INTO ft_utente (nome, cognome, username, password) VALUES ('Valentina', 'Rizzo', 'vrizzo', 'password16');
INSERT INTO ft_utente (nome, cognome, username, password) VALUES ('Simone', 'Moretti', 'smoretti', 'password17');
INSERT INTO ft_utente (nome, cognome, username, password) VALUES ('Alice', 'Santoro', 'asantoro', 'password18');
INSERT INTO ft_utente (nome, cognome, username, password) VALUES ('Giorgio', 'Costa', 'gcosta', 'password19');
INSERT INTO ft_utente (nome, cognome, username, password) VALUES ('Ilaria', 'Fabbri', 'ifabbri', 'password20');
INSERT INTO ft_utente (nome, cognome, username, password) VALUES ('Riccardo', 'Rinaldi', 'rrinaldi', 'password21');
INSERT INTO ft_utente (nome, cognome, username, password) VALUES ('Beatrice', 'Sartori', 'bsartori', 'password22');
INSERT INTO ft_utente (nome, cognome, username, password) VALUES ('Tommaso', 'Martini', 'tmartini', 'password23');
INSERT INTO ft_utente (nome, cognome, username, password) VALUES ('Giovanni', 'Leone', 'gleone', 'password24');
INSERT INTO ft_utente (nome, cognome, username, password) VALUES ('Silvia', 'Orlando', 'sorlando', 'password25');
INSERT INTO ft_utente (nome, cognome, username, password) VALUES ('Emanuele', 'Ferraro', 'eferraro', 'password26');
INSERT INTO ft_utente (nome, cognome, username, password) VALUES ('Marta', 'Serra', 'mserra', 'password27');
INSERT INTO ft_utente (nome, cognome, username, password) VALUES ('Nicola', 'Pellegrini', 'npellegrini', 'password28');
INSERT INTO ft_utente (nome, cognome, username, password) VALUES ('Veronica', 'Gatti', 'vgatti', 'password29');
INSERT INTO ft_utente (nome, cognome, username, password) VALUES ('Stefano', 'Riva', 'sriva', 'password30');
INSERT INTO ft_utente (nome, cognome, username, password) VALUES ('Claudia', 'Monti', 'cmonti', 'password31');
INSERT INTO ft_utente (nome, cognome, username, password) VALUES ('Filippo', 'Bianco', 'fbianco', 'password32');
INSERT INTO ft_utente (nome, cognome, username, password) VALUES ('Elisa', 'Grasso', 'egrasso', 'password33');
INSERT INTO ft_utente (nome, cognome, username, password) VALUES ('Marco', 'Sanna', 'msanna', 'password34');
INSERT INTO ft_utente (nome, cognome, username, password) VALUES ('Laura', 'Mazza', 'lmazza', 'password35');
INSERT INTO ft_utente (nome, cognome, username, password) VALUES ('Pietro', 'Villa', 'pvilla', 'password36');
INSERT INTO ft_utente (nome, cognome, username, password) VALUES ('Anna', 'Ruggiero', 'aruggiero', 'password37');
INSERT INTO ft_utente (nome, cognome, username, password) VALUES ('Gabriele', 'Ferrari', 'gferrari', 'password38');
INSERT INTO ft_utente (nome, cognome, username, password) VALUES ('Francesco', 'Marchetti', 'fmarchetti', 'password39');
INSERT INTO ft_utente (nome, cognome, username, password) VALUES ('Roberta', 'Lombardo', 'rlombardo', 'password40');
INSERT INTO ft_utente (nome, cognome, username, password) VALUES ('Alberto', 'Sorrentino', 'asorrentino', 'password41');
INSERT INTO ft_utente (nome, cognome, username, password) VALUES ('Serena', 'De Santis', 'sdesantis', 'password42');
INSERT INTO ft_utente (nome, cognome, username, password) VALUES ('Daniele', 'Piras', 'dpiras', 'password43');
INSERT INTO ft_utente (nome, cognome, username, password) VALUES ('Federica', 'Mancini', 'fmancini', 'password44');
INSERT INTO ft_utente (nome, cognome, username, password) VALUES ('Luca', 'Rossi', 'lrossi', 'password45');
INSERT INTO ft_utente (nome, cognome, username, password) VALUES ('Giada', 'Colombo', 'gcolombo', 'password46');
INSERT INTO ft_utente (nome, cognome, username, password) VALUES ('Massimo', 'Gatti', 'mgatti', 'password47');
INSERT INTO ft_utente (nome, cognome, username, password) VALUES ('Paola', 'Ricci', 'pricci', 'password48');
INSERT INTO ft_utente (nome, cognome, username, password) VALUES ('Vincenzo', 'Marino', 'vmarino', 'password49');
INSERT INTO ft_utente (nome, cognome, username, password) VALUES ('Eleonora', 'Ferrara', 'eferrara', 'password50');

INSERT INTO ft_viaggio (data_arrivo, data_partenza, prezzo, id_utente, itinerario, descrizione, difficolta, id_paese) VALUES ('2024-01-01', '2024-01-10', 500.00, 1, 'Roma-Milano', 'Viaggio di lavoro', 'Facile', 1);
INSERT INTO ft_viaggio (data_arrivo, data_partenza, prezzo, id_utente, itinerario, descrizione, difficolta, id_paese) VALUES ('2024-02-15', '2024-02-20', 750.00, 2, 'Milano-Venezia', 'Vacanza', 'Media', 2);
INSERT INTO ft_viaggio (data_arrivo, data_partenza, prezzo, id_utente, itinerario, descrizione, difficolta, id_paese) VALUES ('2024-03-10', '2024-03-15', 600.00, 3, 'Firenze-Roma', 'Visita culturale', 'Facile', 3);
INSERT INTO ft_viaggio (data_arrivo, data_partenza, prezzo, id_utente, itinerario, descrizione, difficolta, id_paese) VALUES ('2024-04-05', '2024-04-12', 800.00, 4, 'Napoli-Palermo', 'Vacanza', 'Media', 4);
INSERT INTO ft_viaggio (data_arrivo, data_partenza, prezzo, id_utente, itinerario, descrizione, difficolta, id_paese) VALUES ('2024-05-01', '2024-05-07', 450.00, 5, 'Torino-Genova', 'Viaggio di lavoro', 'Facile', 5);
INSERT INTO ft_viaggio (data_arrivo, data_partenza, prezzo, id_utente, itinerario, descrizione, difficolta, id_paese) VALUES ('2024-06-10', '2024-06-17', 900.00, 6, 'Bologna-Firenze', 'Vacanza', 'Media', 6);
INSERT INTO ft_viaggio (data_arrivo, data_partenza, prezzo, id_utente, itinerario, descrizione, difficolta, id_paese) VALUES ('2024-07-20', '2024-07-25', 700.00, 7, 'Roma-Napoli', 'Visita culturale', 'Facile', 7);
INSERT INTO ft_viaggio (data_arrivo, data_partenza, prezzo, id_utente, itinerario, descrizione, difficolta, id_paese) VALUES ('2024-08-05', '2024-08-12', 850.00, 8, 'Milano-Torino', 'Vacanza', 'Media', 8);
INSERT INTO ft_viaggio (data_arrivo, data_partenza, prezzo, id_utente, itinerario, descrizione, difficolta, id_paese) VALUES ('2024-09-01', '2024-09-07', 500.00, 9, 'Venezia-Bologna', 'Viaggio di lavoro', 'Facile', 9);
INSERT INTO ft_viaggio (data_arrivo, data_partenza, prezzo, id_utente, itinerario, descrizione, difficolta, id_paese) VALUES ('2024-10-10', '2024-10-15', 750.00, 10, 'Genova-Firenze', 'Vacanza', 'Media', 10);
INSERT INTO ft_viaggio (data_arrivo, data_partenza, prezzo, id_utente, itinerario, descrizione, difficolta, id_paese) VALUES ('2024-11-05', '2024-11-12', 600.00, 11, 'Palermo-Napoli', 'Visita culturale', 'Facile', 11);
INSERT INTO ft_viaggio (data_arrivo, data_partenza, prezzo, id_utente, itinerario, descrizione, difficolta, id_paese) VALUES ('2024-12-01', '2024-12-07', 800.00, 12, 'Roma-Milano', 'Vacanza', 'Media', 12);
INSERT INTO ft_viaggio (data_arrivo, data_partenza, prezzo, id_utente, itinerario, descrizione, difficolta, id_paese) VALUES ('2025-01-10', '2025-01-15', 450.00, 13, 'Milano-Venezia', 'Viaggio di lavoro', 'Facile', 13);
INSERT INTO ft_viaggio (data_arrivo, data_partenza, prezzo, id_utente, itinerario, descrizione, difficolta, id_paese) VALUES ('2025-02-05', '2025-02-12', 900.00, 14, 'Firenze-Roma', 'Vacanza', 'Media', 14);
INSERT INTO ft_viaggio (data_arrivo, data_partenza, prezzo, id_utente, itinerario, descrizione, difficolta, id_paese) VALUES ('2025-03-01', '2025-03-07', 700.00, 15, 'Napoli-Palermo', 'Visita culturale', 'Facile', 15);
INSERT INTO ft_viaggio (data_arrivo, data_partenza, prezzo, id_utente, itinerario, descrizione, difficolta, id_paese) VALUES ('2025-04-10', '2025-04-15', 850.00, 16, 'Torino-Genova', 'Vacanza', 'Media', 16);
INSERT INTO ft_viaggio (data_arrivo, data_partenza, prezzo, id_utente, itinerario, descrizione, difficolta, id_paese) VALUES ('2025-05-05', '2025-05-12', 500.00, 17, 'Bologna-Firenze', 'Viaggio di lavoro', 'Facile', 17);
INSERT INTO ft_viaggio (data_arrivo, data_partenza, prezzo, id_utente, itinerario, descrizione, difficolta, id_paese) VALUES ('2025-06-01', '2025-06-07', 750.00, 18, 'Roma-Napoli', 'Vacanza', 'Media', 18);
INSERT INTO ft_viaggio (data_arrivo, data_partenza, prezzo, id_utente, itinerario, descrizione, difficolta, id_paese) VALUES ('2025-07-10', '2025-07-15', 600.00, 19, 'Milano-Torino', 'Visita culturale', 'Facile', 19);
INSERT INTO ft_viaggio (data_arrivo, data_partenza, prezzo, id_utente, itinerario, descrizione, difficolta, id_paese) VALUES ('2025-08-05', '2025-08-12', 800.00, 20, 'Venezia-Bologna', 'Vacanza', 'Media', 20);
INSERT INTO ft_viaggio (data_arrivo, data_partenza, prezzo, id_utente, itinerario, descrizione, difficolta, id_paese) VALUES ('2025-09-01', '2025-09-07', 450.00, 21, 'Genova-Firenze', 'Viaggio di lavoro', 'Facile', 21);
INSERT INTO ft_viaggio (data_arrivo, data_partenza, prezzo, id_utente, itinerario, descrizione, difficolta, id_paese) VALUES ('2025-10-10', '2025-10-15', 900.00, 22, 'Palermo-Napoli', 'Vacanza', 'Media', 22);
INSERT INTO ft_viaggio (data_arrivo, data_partenza, prezzo, id_utente, itinerario, descrizione, difficolta, id_paese) VALUES ('2025-11-05', '2025-11-12', 700.00, 23, 'Roma-Milano', 'Visita culturale', 'Facile', 23);
INSERT INTO ft_viaggio (data_arrivo, data_partenza, prezzo, id_utente, itinerario, descrizione, difficolta, id_paese) VALUES ('2025-12-01', '2025-12-07', 850.00, 24, 'Milano-Venezia', 'Vacanza', 'Media', 24);
INSERT INTO ft_viaggio (data_arrivo, data_partenza, prezzo, id_utente, itinerario, descrizione, difficolta, id_paese) VALUES ('2026-01-10', '2026-01-15', 500.00, 25, 'Firenze-Roma', 'Viaggio di lavoro', 'Facile', 25);
INSERT INTO ft_viaggio (data_arrivo, data_partenza, prezzo, id_utente, itinerario, descrizione, difficolta, id_paese) VALUES ('2026-02-05', '2026-02-12', 750.00, 26, 'Napoli-Palermo', 'Vacanza', 'Media', 26);
