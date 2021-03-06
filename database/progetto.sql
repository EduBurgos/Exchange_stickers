DROP SCHEMA IF EXISTS cardwebapplication;
CREATE SCHEMA cardwebapplication;
USE cardwebapplication;


-- Table that contains all cards
CREATE TABLE catalog(
                        ID int PRIMARY KEY,
                        Category VARCHAR(32) NOT NULL,
                        Class VARCHAR(32) NOT NULL,
                        Lvl NUMERIC(8) NOT NULL,   -- Level of the card
                        Rarity VARCHAR(16) NOT NULL,
                        CardType VARCHAR(32),
                        CardName VARCHAR(128) NOT NULL,
                        CardDescription VARCHAR(10000) NOT NULL,
                        unique(Class, Rarity, CardName, CardType)
);

-- Table that contains all the users signed up to the platform
CREATE TABLE users(
                      Username VARCHAR(32) NOT NULL ,
                      NameUser VARCHAR(32) NOT NULL,
                      Surname VARCHAR(32) NOT NULL,
                      Mail VARCHAR(64) NOT NULL,
                      pass VARCHAR(64) NOT NULL,  -- user's password
                      PRIMARY KEY (Username)
);


-- Table that contains usernames of all the users who did the daily access
CREATE TABLE accesses(

                         Username VARCHAR(32) NOT NULL ,
                         PRIMARY KEY (Username),
                         FOREIGN KEY (Username) REFERENCES users(Username)

);


-- Event that deletes table accesses content once a day
SET GLOBAL event_scheduler = 1;

CREATE EVENT cancelGifted
    ON SCHEDULE
        EVERY 1 day
    DO
    delete from
        accesses;

-- Table that contains collections of all the users signed up to the platform
CREATE TABLE collections(
                            ID_Card int not null ,             -- id of the card owned
                            USERNAME VARCHAR(32) not null ,   -- username of the owner
                           -- In_Market boolean,          -- inutile forse da cancellare
                            quantity int not null DEFAULT 1, -- quantity of the card
                            primary key (ID_Card,USERNAME),
                             FOREIGN KEY (USERNAME) REFERENCES Users(Username),
                            FOREIGN KEY (ID_Card) REFERENCES Catalog(ID)
);

-- Table that records all the exchanges occurred
create table exchanges(
                          id_trans INT not null auto_increment,  -- id of the exchange
                          username VARCHAR(32) NOT NULL ,  -- username of the user who started the exchange   */
                          username_offer VARCHAR(32) DEFAULT NULL,  -- username of the user who accepts the exchange, null if the exchange is still open
                          trans_comp boolean DEFAULT FALSE,     -- false when the exchange is created, true when it's accepted
                          notified int not null DEFAULT 0, -- false when the exchange, which is accepted, is not notified to the user who started the exchange, true otherwise
                          primary key (id_trans),
                          foreign key(username) references users(username),
                          foreign key(username_offer) references users(username)

);

-- Table that contains all the cards offered by the users who start the exchanges
create table Cards_own(
                          Id_trans INT not null, -- id of the exchange
                          cardId  int not null ,
                          quantity int(5) DEFAULT 1,  -- quantity of the card that can be exchangeable
                          primary key (Id_trans,cardId),
                          foreign key(Id_trans) references exchanges(id_trans)
                              ON DELETE CASCADE
                              ON UPDATE CASCADE
);

-- Table that contains all cards wanted by the users who start exchanges
create table Cards_wanted(
                             Id_trans INT not null,  -- id of the exchange
                             cardId  int not null ,
                             quantity int(5) DEFAULT 1, -- quantity of the card that can be exchangeable
                             primary key (Id_trans,cardId),
                             foreign key (cardId) references catalog(ID),
                             foreign key(Id_trans) references exchanges(id_trans)
                                 ON DELETE cascade
);

Insert into catalog VALUES(1,'Hearthstone','Druido',8,'Rara','Magia','Aiuto Della Foresta','Magia Gemella. Evoca cinque Treant 2/2.');
INSERT INTO catalog VALUES(2,'Hearthstone','Druido',3,'Comune','Magia','Benedizione Degli Antichi','Magia Gemella. +1/+1 ai tuoi servitori.');
INSERT INTO catalog VALUES(3,'Hearthstone','Druido',5,'Epica','Bestia','Cervo Di Cristallo','Assalto. Grido di Battaglia: se hai rigenerato 5 Salute in questa partita, evoca una copia di se stesso.');
INSERT INTO catalog VALUES(4,'Hearthstone','Druido',2,'Leggendaria','Servitore','Custode Stalladris','Dopo che hai lanciato una Magia con Scegli, mette nella tua mano una copia di ogni scelta.');
INSERT INTO catalog VALUES(5,'Hearthstone','Druido',4,'Rara','Servitore', 'Guida Della Foresta','Alla fine del tuo turno, entambi i giocatori pescano una carta.');
INSERT INTO catalog VALUES(6,'Hearthstone','Druido',4,'Leggendaria','Servitore', 'Melmo il Melmoide','Finch?? resta mella tua mano, si trasforma in una copia 3/4 dell ultimo servitore che hai giocato.');
INSERT INTO catalog VALUES(7,'Hearthstone','Druido',8,'Leggendaria','Servitore','Scheggiatruce','Grido di Battaglia: seleziona un tuo servitore. Ne mette una copia 10/10 nella tua mano che costa 10.');
INSERT INTO catalog VALUES(8,'Hearthstone','Druido',6,'Rara','Servitore', 'Tauren Protettiva', 'Scegli:+1/+1 agli altri tuoi servitori o evoca due Treant 2/2');
INSERT INTO catalog VALUES(9,'Hearthstone','Druido',1,'Comune','Bestia','Portaghiande','Rantolo di Morte: mette nella tua mano due Scoiattoli 1/1.');
INSERT INTO catalog VALUES(10,'Hearthstone','Druido',10,'Leggendaria','Bestia','Tirannus','Non pu?? essere il bersaglio di Magie o Poteri Eroe.');
INSERT INTO catalog VALUES(11,'Hearthstone','Paladino',3,'Comune','Drago','Alfiere Bronzeo','Rantolo di Morte: mette nella tua mano due Draghi 4/4.');
INSERT INTO catalog VALUES(12, 'Hearthstone', 'Paladino', 5, 'Rara', 'Drago', 'Draco Scargliardente', 'Grido di Battaglia: fornisce 5 armatura se hai un Drago in mano.');
INSERT INTO catalog VALUES(13,'Hearthstone','Paladino',5,'Epica','Magia','Duello!','Evoca un servitore dal mazzo di ogni giocatore. Combattono!');
INSERT INTO catalog VALUES(14,'Hearthstone','Paladino',3,'Rara','Magia','Favore Divino','Pesca delle carte finch?? non ne hai lo stesso numero dell avversario');
INSERT INTO catalog VALUES(15,'Hearthstone','Paladino',2,'Rara','Magia','Lama Misteriosa','Grido di Battaglia: ottiene +1 Attacco se controlli un Segreto.');
INSERT INTO catalog VALUES(16,'Hearthstone','Paladino',2,'Comune','Magia', 'Lampo Di Luce', 'Rigenera 4 Salute. Pesca una carta.');
INSERT INTO catalog VALUES(17,'Hearthstone','Paladino',1,'Comune','Magia','Mai Arrendersi!','Segreto: quando l''avversario lancia una Magia, +2 Salute ai tuoi servitori.');
INSERT INTO catalog VALUES(18,'Hearthstone','Paladino',10,'Leggendaria','Drago','Nozari','Grido di Battaglia: rigenera la Salute di entrambi gli eroi al massimo.');
INSERT INTO catalog VALUES(19,'Hearthstone','Paladino',1,'Comune','Magia','Ritorsione', 'Segreto: quando un tuo servitore muore fornisce +3/+2 a un servitore casuale');
INSERT INTO catalog VALUES(20,'Hearthstone','Paladino',1,'Epica','Magia','Saggezza Nascosta','Segreto: pesca 2 carte dopo che l avversario ha giocato 3 carte in un turno');

INSERT INTO catalog VALUES(21, 'Hearthstone', 'Mago',2,'Comune','Servitore','Arcanologa','Grido di Battaglia:pesca un Segreto dal tuo mazzo.');
INSERT INTO catalog VALUES(22, 'Hearthstone', 'Mago',5,'Rara','Servitore','Collezionista Di Rarit??','Ottieni +1/+1 quando peschi una carta');
INSERT INTO catalog VALUES(23, 'Hearthstone', 'Mago',2,'Epica', 'Magia', 'Frammento Di Ghiaccio', 'Infligge 2 danni a un servitore. Se ?? Congelato pesca una carta');
INSERT INTO catalog VALUES(24, 'Hearthstone', 'Mago',5,'Epica','Magia','Furia Del Drago','Mostra una Magia dal tuo mazzo.Infligge danni pari al suo costo a TUTTI i servitori');
INSERT INTO catalog VALUES(25, 'Hearthstone', 'Mago',7,'Leggendaria','Magia','Galassia Portatile','Imposta a (1) il costo dei servitori nel tuo mazzo.');
INSERT INTO catalog VALUES(26, 'Hearthstone', 'Mago',3,'Epica','Magia','Immagine Scissa','Segreto: quando un tuo servitore viene attaccato, ne evoca una copia');
INSERT INTO catalog VALUES(27, 'Hearthstone', 'Mago',4,'Rara','Servitore','Mago Anziano','Grido di Battaglia: +1 Danni Magici ai servitori adiacenti.');
INSERT INTO catalog VALUES(28, 'Hearthstone', 'Mago',6,'Rara','Servitore','Meteorologa','Grido di Battaglia: infligge 1 danno a un nemico casuale per ogni altra carta nella tua mano.');
INSERT INTO catalog VALUES(29, 'Hearthstone', 'Mago',10,'Leggendaria','Servitore','Kalecgos','La tua prima Magia ogni turno costa(0). Grido di Battaglia:Rinvieni una Magia.');
INSERT INTO catalog VALUES(30, 'Hearthstone', 'Mago',6,'Rara','Magia','Tormenta','Infligge 2 danni ai servitori nemici e li Congela.');

INSERT INTO catalog VALUES(31, 'Hearthstone', 'Sacerdote',2,'Comune','Servitore','Reclutatore del M.A.L.E.','Rantolo di Morte: mette un Lacch?? nella tua mano.');
INSERT INTO catalog VALUES(32, 'Hearthstone', 'Sacerdote',5,'Epica','Bestia', 'Falena Splendente','Grido di Battaglia: se il tuo mazzo ha solo carte a costo dispari, raddoppia la Salute degli altri tuoi servitori.');
INSERT INTO catalog VALUES(33, 'Hearthstone', 'Sacerdote',3,'Epica','Magia','Incubo Vivido','Seleziona un tuo servitore. Ne evoca una copia con 1 Salute restante.');
INSERT INTO catalog VALUES(34, 'Hearthstone', 'Sacerdote',5,'Rara','Magia','Isteria Di Massa','Costringe ogni servitore ad attaccare un altro servitore casuale.');
INSERT INTO catalog VALUES(35, 'Hearthstone', 'Sacerdote',12,'Rara','Servitore', 'Orrore Della Fossa','Provocazione. Costa (1) in meno per ogni Magia che hai lanciato in questa partita.');
INSERT INTO catalog VALUES(36, 'Hearthstone', 'Sacerdote',0,'Epica','Magia','Piano Di Lazul','Riduce l attacco di un servitore nemico di 1 fino al tuo turno successivo. (Si potenzia ogni turno!)');
INSERT INTO catalog VALUES(37, 'Hearthstone', 'Sacerdote',8,'Leggendaria','Servitore','Principessa Talanji','Grido di Battaglia: evica dalla tua mano i servitori non presenti nel tuo mazzo originale.');
INSERT INTO catalog VALUES(38, 'Hearthstone', 'Sacerdote',5,'Epica','Servitore','Ricercatrice Incauta','I servitori con Rantolo di Morte che giochi costano (3) in meno, ma muiono alla fine del turno');
INSERT INTO catalog VALUES(39, 'Hearthstone', 'Sacerdote',3,'Comune','Servitore', 'Sacerdote Della Kabala','Grido di Battaglia: +3 salute a un tuo servitore.');
INSERT INTO catalog VALUES(40, 'Hearthstone', 'Sacerdote',2,'Comune','Magia','Seduta Spiritica','Seleziona un servitore. Ne mette una copia nella tua mano.');

INSERT INTO catalog VALUES(41,'Hearthstone','Stregone',7,'Leggendaria','Magia','Arcicattivo Rafaam','Provocazione. Grido di Battaglia: sostituisce le carte nella tua mano e nel tuo mazzo con servitori Leggendari.');
INSERT INTO catalog VALUES(42,'Hearthstone','Stregone',2,'Rara','Magia','Colpo Di scena','Mette la tua mano nel tuo mazzo. Pesca lo stesso numero di carte.');
INSERT INTO catalog VALUES(43,'Hearthstone','Stregone',2,'Epica','Magia','Fiorsangue','La prossima Magia che lanci in questo turno costa Salute invece di Mana');
INSERT INTO catalog VALUES(44,'Hearthstone','Stregone',3,'Rara','Magia','Follia Incontrollata','Infligge 9 danni suddivisi casualmente tra tutti i personaggi.');
INSERT INTO catalog VALUES(45,'Hearthstone','Stregone',1,'Rara','Magia','Grido','Scarta la tua carta con il costo pi?? basso. Infligge 2 danni a TUTTI i servitori.');
INSERT INTO catalog VALUES(46,'Hearthstone','Stregone',10,'Epica','Demone','Imp Gigante','Finche' 'resta nella tua mano, il suo costo si ridusce di (1) quando un tuo Demone muore.');
INSERT INTO catalog VALUES(47,'Hearthstone','Stregone',3,'Rara','Magia','Impferno','+1 di Attacco ai tuoi Demoni. Infligge 1 danno ai servitori nemici.');
INSERT INTO catalog VALUES(48,'Hearthstone','Stregone',4,'Rara','Magia','Implosione','Infligge da 2 a 4 danni a un servitore. Evoca un Imp 1/1 per ogni danno inflitto.');
INSERT INTO catalog VALUES(49,'Hearthstone','Stregone',6,'Rara','Servitore','Lacch?? Posseduto','Rantolo di Morte: recluta un Demone.');
INSERT INTO catalog VALUES(50,'Hearthstone','Stregone',3,'Rara','Demone', 'Terrore Del Vuoto','Grido di Battaglia: distrugge i servitori adiacenti e ne ottiene Attacco e Salute');

INSERT INTO catalog VALUES(51,'Pokemon','Fase 1',130,'Rara','Erba','Abomasnow','Abilita.Preghiera dei Ghiacci:Quando giochi questo Pok??mon dalla tua mano durante il tuo turno
                                                            per far evolvere uno dei tuoi Pok??mon, puoi assegnare a uno dei tuoi Pok??mon una  carta Energia Grass dalla tua pila degli scarti.
												            Martello Ipnotico 80
												            Il Pok??mon attivo del tuo avversario viene addormentato.');
INSERT INTO catalog VALUES(52,'Pokemon','Base',180,'Rara Holo Ex','Erba','VenusaurEX','Il tuo avversario prende due carte Premio ogni volta che uno dei tuoi Pok??mon-EX viene messo K.O.
														  Velenpolvere 60
														  Il Pok??mon attivo del tuo avversario viene avvelenato.
														  Martelgiungla	90
														  Cura questo Pok??mon da 30 danni.');

INSERT INTO catalog VALUES(53,'Pokemon','Base',60,'Comune','Erba','Treecko','Attacco Rapido  10+
                                                      Lancia una moneta. Se esce testa, questo attacco infligge 10 danni in pi??.');
INSERT INTO catalog VALUES(54,'Pokemon','Base',70,'Comune','Erba','Celebi','Blocco ?? Previeni tutti gli effetti delle abilit?? dei Pok??mon del tuo avversario inflitti a questo Pok??mon.');
INSERT INTO catalog VALUES(55,'Pokemon','Fase 1',200,'Arcobaleno Rara','Erba','LeafeonGX','Abilit??. Alito di Foglie:Una sola volta durante il tuo turno, se questo Pok??mon ?? il tuo Pok??mon attivo,
                                                              prima di attaccare, puoi curare da 50 danni uno dei tuoi Pok??mon che ha Energie assegnate.
                                                              Il tuo avversario prende due carte Premio ogni volta che uno dei tuoi Pok??mon-GX
                                                              viene messo K.O.
                                                              Solarraggio   110 Fioritura Formidabile-GX
                                                              Per ogni Pok??mon Base nella tua panchina, cerca nel tuo mazzo una carta che si evolve
                                                              da quel Pok??mon e metticela sopra per farlo evolvere. Poi rimischia le carte del tuo
                                                              mazzo. Non puoi usare pi?? di un attacco GX a partita.');
INSERT INTO catalog VALUES(56,'Pokemon','Base',50,'Comune','Fuoco','Chimchar','Morso 10. Fuocopugno 20');
INSERT INTO catalog VALUES(57,'Pokemon','Base',180,'UltraRara','Fuoco','CharizardEX','Il tuo avversario prende due carte Premio ogni volta che uno dei tuoi Pok??mon-EX viene messo K.O.
														 Megascensione
                                                         Cerca nel tuo mazzo un M Charizard-EX, mostralo e aggiungilo alle carte che hai in mano.
                                                         Poi rimischia le carte del tuo mazzo.
                                                         Cuorinfiamme	120
                                                         Este Pok??mon se hace 30 puntos de da??o a s?? mismo.');
INSERT INTO catalog VALUES(58,'Pokemon','Mega',210,'Comune', 'Fuoco','MBlazikenEX','Il tuo avversario prende due carte Premio ogni volta che uno dei tuoi Pok??mon-EX viene messo K.O.
													         Regola della Megaevoluzione
                                                             Quando uno dei tuoi Pok??mon diventa un Pok??mon Megaevoluzione, il tuo turno termina.
                                                             Fuoco Acrobatico	100
                                                             Durante il tuo prossimo turno, l''attacco Fuoco Acrobatico di questo Pok??mon infligge 100 danni in pi??,
                                                             prima di aver applicato debolezza e resistenza.');
INSERT INTO catalog VALUES(59,'Pokemon','Fase 2',160,'Rara','Fuoco','Incineroar','Rogodenti	30
													      Il Pok??mon attivo del tuo avversario viene bruciato.
													      Braccioteso  100??
                                                          Lancia due volte una moneta. Questo attacco infligge 100 danni ogni volta che esce testa.');
INSERT INTO catalog VALUES(60,'Pokemon','Base',190,'Rara Holo gx','Fuoco','Ho-OhGX','Il tuo avversario prende due carte Premio ogni volta che uno dei tuoi Pok??mon-GX viene messo K.O.
														      Magifuoco
                                                              Questo attacco infligge 50 danni a uno dei Pok??mon del tuo avversario. Ricorda che non puoi
                                                              applicare debolezza e resistenza ai Pok??mon in panchina.
                                                              Scottata della Fenice   180
                                                              Durante il tuo prossimo turno, questo Pok??mon non pu?? usare Scottata della Fenice.
                                                              Fiamma Eterna-GX
                                                              Prendi tre Pok??mon-GX Fire o Pok??mon-EX Fire in qualsiasi combinazione dalla tua pila
                                                              degli scarti e mettili nella tua panchina. Non puoi usare pi?? di un attacco GX a partita.');
INSERT INTO catalog VALUES(61,'Pokemon','Base',70,'Comune','Acqua','Psyduck','Confusionda.Entrambi i Pok??mon attivi vengono confusi.');
INSERT INTO catalog VALUES(62,'Pokemon','Fase 1',90,'Raro','Acqua','Glaceon','Bora	30
                                                       Questo attacco infligge 10 danni a ciascun Pok??mon nella panchina del tuo avversario.
                                                       Ricorda che non puoi applicare debolezza e resistenza ai Pok??mon in panchina.
												       Fendighiaccio 	60+
												       Lancia una moneta. Se esce testa, questo attacco infligge 30 danni in pi??.');
INSERT INTO catalog VALUES(63,'Pokemon','Fase 2',250,'Comune','Acqua','PrimarinaGX','Il tuo avversario prende due carte Premio ogni volta che uno dei tuoi Pok??mon-GX viene messo K.O.
                                                              Bollabotta 10+
                                                              Questo attacco infligge 20 danni in pi?? per ogni Energia Water assegnata ai tuoi Pok??mon.
                                                              Mari Ruggenti 120
                                                              Scarta un???Energia assegnata al Pok??mon attivo del tuo avversario.
                                                              Eco Formidabile-GX
                                                              Cura tutti i tuoi Pok??mon da tutti i danni. Non puoi usare pi?? di un attacco GX a partita.');
INSERT INTO catalog VALUES(64,'Pokemon','Fase 2',230,'Rara Holo gx','Acqua','GreninjaGX','Abilit??.Lameturbine: Quando giochi questo Pok??mon dalla tua mano per far evolvere uno dei tuoi Pok??mon durante il tuo turno, puoi mettere tre segnalini danno su uno dei Pok??mon del tuo avversario.
                                                                   Il tuo avversario prende due carte Premio ogni volta che uno dei tuoi Pok??mon-GX viene messo K.O.
															       Sferzanube	110
                                                                   Puoi rimischiare questo Pok??mon e tutte le carte a esso assegnate nel tuo mazzo.
                                                                   Cacciatore Oscuro-GX
                                                                   Questo attacco infligge 130 danni a uno dei Pok??mon nella panchina del tuo avversario.
                                                                   Ricorda che non puoi applicare debolezza e resistenza ai Pok??mon in panchina. Non puoi usare pi?? di un attacco GX a partita');
INSERT INTO catalog VALUES(65,'Pokemon','Base',120,'Comune','Acqua','Palkia','Schizzi d''Onda30');
INSERT INTO catalog VALUES(66,'Pokemon','Fase 1',210,'Arcobaleno Rara','Lampo','RaichuGX','Il tuo avversario prende due carte Premio ogni volta che uno dei tuoi Pok??mon-GX viene messo K.O.
															        Superscintilla	20+
                                                                    Questo attacco infligge 20 danni in pi?? per ogni Energia Lightning assegnata ai tuoi Pok??mon.
                                                                    Tuono		160
                                                                    Questo Pok??mon infligge 30 danni a se stesso.
                                                                    Coda Voltaica-GX	120
                                                                    Il Pok??mon attivo del tuo avversario viene paralizzato. Non puoi usare pi?? di un attacco GX a partita.');
INSERT INTO catalog VALUES(67,'Pokemon','Fase 2',150,'Rara Holo','Lampo', 'Ampharos','Abilit??.Bagliore Perduto:Una sola volta durante il tuo turno, prima di attaccare, puoi prendere due carte Energia Lightning dalla tua mano e metterle nell???area perduta.
                                                              Se lo fai, il Pok??mon attivo del tuo avversario viene paralizzato.
                                                              Squarciabomba.
                                                              Questo attacco infligge 50 danni a due dei Pok??mon del tuo avversario.
                                                              Ricorda che non puoi applicare debolezza e resistenza ai Pok??mon in panchina' );
INSERT INTO catalog VALUES(68,'Pokemon','Base',110,'Comune','Lampo','Tapu Koko','Svolta Volante:Questo attacco infligge 20 danni a ciascuno dei Pok??mon del tuo avversario.
													     Ricorda che non puoi applicare debolezza e resistenza ai Pok??mon in panchina.
                                                         Lamposfera100');
INSERT INTO catalog VALUES(69,'Pokemon','Base',70,'Comune','Lampo','Pikachu','Tuononda:Lancia una moneta. Se esce testa, il Pok??mon attivo del tuo avversario viene paralizzato.
												       Energisfera');
INSERT INTO catalog VALUES(70,'Pokemon','Base',110,'Rara Holo EX','Lampo','EmolgaEX','Il tuo avversario prende due carte Premio ogni volta che uno dei tuoi Pok??mon-EX viene messo K.O.
														       Energialiante: Cerca nel tuo mazzo una carta Energia Lightning e assegnala a questo Pok??mon. Poi rimischia le carte del tuo mazzo. Se assegni Energia in questo modo, scambia questo Pok??mon con uno della tua panchina.
                                                               Elettrostritolamento  60+
                                                               Puoi scartare un''Energia assegnata a questo Pok??mon. Se lo fai, questo attacco infligge 30 danni in pi??.');
INSERT INTO catalog VALUES(71,'Pokemon','Base',120,'Rara Holo EX','Psico','MewEX','Abilit??.Versatile:Questo Pok??mon pu?? usare gli attacchi di tutti i Pok??mon in gioco,
													        sia tuoi che del tuo avversario. Devi comunque avere l''Energia necessaria per usare quegli attacchi.
                                                            Il tuo avversario prende due carte Premio ogni volta che uno dei tuoi Pok??mon-EX viene messo K.O.
                                                            Sostituzione: Distribuisci a piacimento tutte le Energie assegnate ai tuoi Pok??mon.');
INSERT INTO catalog VALUES(72,'Pokemon','Fase 2',130,'Rara Holo','Psico','Crobat','Abilit??.Vista Notturna:Una sola volta durante il tuo turno, prima di attaccare, puoi pescare una carta.
														   Dentavvelenato 40
                                                           Il Pok??mon difensore viene avvelenato. Tra un turno e l''altro, metti quattro segnalini danno invece di uno su quel Pok??mon.');
INSERT INTO catalog VALUES(73,'Pokemon','Fase 2',250,'Comune','Psico','LunalaGX','Abilit??.Psicotransfer:Durante il tuo turno, prima di attaccare, puoi spostare un???Energia Psychic da uno a un altro dei tuoi Pok??mon tutte le volte che vuoi.
													       Il tuo avversario prende due carte Premio ogni volta che uno dei tuoi Pok??mon-GX viene messo K.O.
                                                           Raggio d???Ombra  120
                                                           Durante il prossimo turno del tuo avversario, il Pok??mon difensore non pu?? essere curato.
                                                           Caduta Lunare-GX
                                                           Metti KO un Pok??mon Base del tuo avversario che non ?? un Pok??mon-GX. Non puoi usare pi?? di un attacco GX a partita.');
INSERT INTO catalog VALUES(74,'Pokemon','Base',70,'Comune','Psico','Mimikyu','Infestare:Metti un segnalino danno sul Pok??mon attivo del tuo avversario.
												       Sgomento: Scegli una carta a caso dalla mano del tuo avversario. Il tuo avversario mostra quella carta e la rimischia nel suo mazzo.');
INSERT INTO catalog VALUES(75,'Pokemon','Fase1',200,'Comune','Psico','EspeonGX','Il tuo avversario prende due carte Premio ogni volta che uno dei tuoi Pok??mon-GX viene messo K.O.
														  Psicoraggio	30
                                                          Il Pok??mon attivo del tuo avversario viene confuso.
                                                          Psichico		60+
                                                          Questo attacco infligge 30 danni in pi?? per ogni Energia assegnata al Pok??mon attivo del tuo avversario.
                                                          Distribuzione-GX
                                                          Distribuisci a piacimento dieci segnalini danno sui Pok??mon del tuo avversario. Non puoi usare pi?? di un attacco GX a partita.' );
INSERT INTO catalog VALUES(76,'Pokemon','Base',70,'Comune','Lotta','Riolu','Colpo Duplice 10??
									                 Lancia due volte una moneta. Questo attacco infligge 10 danno ogni volta che esce testa.');
INSERT INTO catalog VALUES(77,'Pokemon','Base',120,'Rara','Lotta','Landorus','Urlotonante  20
											          Assegna a uno dei tuoi Pok??mon in panchina una carta Energia base dalla tua pila degli scarti.
												      Lazoaereo 90');
INSERT INTO catalog VALUES(78,'Pokemon','Fase1',210,'Comune','Lotta','LucarioGX','Il tuo avversario prende due carte Premio ogni volta che uno dei tuoi Pok??mon-GX viene messo K.O.
													       Colpo d???Aura  30+
													       Se questo Pok??mon si ?? evoluto da Riolu durante questo turno, questo attacco infligge 90 danni in pi??.
                                                           Calciovento   130
                                                           Batosta Litigiosa-GX  30??
                                                           Questo attacco infligge 30 danni per ogni segnalino danno presente su questo Pok??mon. Non puoi usare pi?? di un attacco GX a partita.');
INSERT INTO catalog VALUES(79,'Pokemon','Fase1',100,'Comune','Lotta','Pangoro','Abilit??.Bravo Boss:Se questo Pok??mon ?? il tuo Pok??mon attivo, ha 20 PS in pi?? per ogni Pok??mon che hai in panchina.
												        Pugnomatto   80???
                                                        Se questo Pok??mon ?? influenzato da una condizione speciale, questo attacco infligge 40 danni in pi??.');
INSERT INTO catalog VALUES(80,'Pokemon','Base',70,'Comune','Lotta','Gligar','Colpo Doppio
												      Questo attacco infligge 10 danni a due dei Pok??mon nella panchina del tuo avversario.
                                                      Ricorda che non puoi applicare debolezza e resistenza ai Pok??mon in panchina.');
INSERT INTO catalog VALUES(81,'Pokemon','Fase 1',100,'Comune','Oscurit??','Umbreon','Artiglio Mach  30
													        I danni di questo attacco non sono influenzati dalla resistenza.
                                                            Senso Siderale  60+
                                                            Gira a faccia in su delle tue carte Premio che sono a faccia in gi??.
                                                            Se quella carta Premio ?? un Pokemon ?? un Pokemon, questo attacco infligge 60 danni in pi??.
                                                            La carta Premio resta a faccia in su per il resto della partita.');
INSERT INTO catalog VALUES(82,'Pokemon','Base',60,'Comune','Oscurit??','Sneasel','Ventogelato
													      Il Pok??mon attivo del tuo avversario viene addormentato.
                                                           Graffio20');
INSERT INTO catalog VALUES(83,'Pokemon','Base',180,'Rara Ultra','Oscurit??','DarkraiGX','Abilit??.Restaurazione:Una sola volta durante il tuo turno, prima di attaccare,
														        se questo Pok??mon ?? nella tua pila degli scarti, puoi metterlo in panchina. Poi, assegnagli una carta Energia Darkness dalla tua pila degli scarti.
														         Il tuo avversario prende due carte Premio ogni volta che uno dei tuoi Pok??mon-GX viene messo K.O.
                                                                 Fendente Oscuro     130
                                                                 I danni di questo attacco non sono influenzati dalla resistenza.
                                                                 Vicolo Cieco-GX
                                                                 Se il Pok??mon attivo del tuo avversario ?? influenzato da una condizione speciale, quel Pok??mon viene messo KO.
                                                                 Non puoi usare pi?? di un attacco GX a partita.');
INSERT INTO catalog VALUES(84,'Pokemon','Fase 1',100,'Rara','Oscurit??','Mightyena','Morso  30
													        Zanna Oscura  50
													        Scarta una carta a caso dalla mano del tuo avversario.');
INSERT INTO catalog VALUES(85,'Pokemon','Base',70,'Comune','Oscurit??','Sandile','Fauci Imponenti  30
													      Se il Pok??mon del tuo avversario viene messo KO dai danni di questo attacco, gli attacchi di questo Pok??mon
                                                          infliggono 120 danni in pi?? al Pok??mon attivo del tuo avversario durante il tuo prossimo turno, prima di aver
                                                          applicato debolezza e resistenza.');
INSERT INTO catalog VALUES(86,'Pokemon','Base',70,'Comune','Metallo','Jirachi','Premonisogno: Pescare tre carte.Questo Pokemon viene addormentato.
												         Metaltestata 30x
												         Lancia una moneta finch?? non esce croce.Questo attacco infligge 30 danni ogni volta che esce testa.');
INSERT INTO catalog VALUES(87,'Pokemon','Base',120,'Comune','Metallo','Dialga','Ferrartigli   30
												        Fermatempo    80
                                                        Il tuo avversario non pu?? giocare Pokemon dalla sua mano per far evolvere il POkemon difensore durante il suo turno.');
INSERT INTO catalog VALUES(88,'Pokemon','Fase 1',100,'Rara','Metallo','Forretress','Abilit??.Spintempesta:Quando giochi questo Pok??mon dalla tua mano per far evolvere uno dei
													       tuoi Pok??mon, puoi mettere un segnalino danno su ciascuno dei Pok??mon del tuo avversario.
                                                           Schiacciaferro   20+
                                                           Questo attacco infligge 20 danni in pi?? per ogni Energia Colorless nel costo di ritirata del Pok??mon attivo del tuo avversario.');
INSERT INTO catalog VALUES(89,'Pokemon','Fase 2',160,'Rara','Metallo','Solgaleo','Freccia Lucente
													      Questo attacco infligge 50 danni a uno dei Pok??mon del tuo avversario.
                                                          Ricorda che non puoi applicare debolezza e resistenza ai Pok??mon in panchina.
                                                          Zanne Solari     170
                                                          Durante il tuo prossimo turno, questo Pok??mon non pu?? usare Zanne Solari.');
INSERT INTO catalog VALUES(90,'Pokemon','Fase 2',160,'Rara Holo','Metallo','Empoleon','Comando Assoluto   20??
                                                               Questo attacco infligge 20 danni per ogni Pok??mon in panchina, sia tuo che del tuo avversario.
                                                               Mulinello    90
                                                               Scarta un???Energia assegnata al Pok??mon attivo del tuo avversario.');
INSERT INTO catalog VALUES(91,'Pokemon','Fase 2',130,'Rara','Incolore','Pidgeot','Grinfie   40
												         Durante il prossimo turno del tuo avversario, il Pok??mon difensore non pu?? ritirarsi.
                                                         Forteraffica  60
                                                         Durante il tuo prossimo turno, l''attacco Forteraffica di questo Pok??mon infligge 60 danni in pi??,
                                                         prima di aver applicato debolezza e resistenza.');
INSERT INTO catalog VALUES(92,'Pokemon','Base',60,'Comune','Incolore','Axew','Porzione Extra  20
                                                       Questo Pok??mon infligge 10 danni a se stesso.
                                                        Dragartigli  40');
INSERT INTO catalog VALUES(93,'Pokemon','Base',180,'Rara Holo EX','Incolore','LugiaEx','Abilit??.Eccedenza:Se un Pok??mon del tuo avversario viene messo K.O.
														         dal danno di un attacco di questo Pok??mon, prendi una carta Premio in pi??.
                                                                 Burrasca Plasma  120
                                                                 Scarta un''Energia Plasma assegnata a questo Pok??mon. Se non puoi scartare
                                                                 un''Energia Plasma, questo attacco non ha effetto.');
INSERT INTO catalog VALUES(94,'Pokemon','Base',80,'Non Comune','Incolore','Farfetch''d','Tassa: Pesca due carte.
                                                                         Sfondaoggetto   20+
                                                                         Prima di infliggere danni, scarta tutte le carte Oggetto Pok??mon assegnate al Pok??mon attivo del tuo avversario.
                                                                         Se scarti una carta Oggetto Pok??mon in questo modo, questo attacco infligge 70 danni in pi??.');
INSERT INTO catalog VALUES(95,'Pokemon','Base',130,'Rara','Incolore','Snorlax','Abilit??.Lasciadormir:Se questo Pok??mon ?? addormentato, tra un turno e l''altro, lancia due volte una moneta invece di una.
												        Se esce almeno una volta croce, questo Pok??mon resta addormentato.
                                                        Sonnopressa    120
                                                        Cura questo Pok??mon da 20 danni. Questo Pok??mon viene addormentato.');
INSERT INTO catalog VALUES(96,'Pokemon','Base',70,'Comune','Folletto','Snubull', 'Carica Avventata  20
                                                           Lancia una moneta. Se esce croce, questo Pok??mon infligge 10 danni a se stesso.');
INSERT INTO catalog VALUES(97,'Pokemon','Base',60,'Comune','Folletto','Jigglypuff','Rotolamento  10
                                                            Cantosincero
                                                            Scarta un''Energia Darkness assegnata al Pok??mon attivo del tuo avversario.');
INSERT INTO catalog VALUES(98,'Pokemon','Fase 1',200,'Rara','Folletto','SylveonGX','Il tuo avversario prende due carte Premio ogni volta che uno dei tuoi Pok??mon-GX viene messo K.O.
                                                                        Fiocco Magico
                                                                        Cerca nel tuo mazzo fino a tre carte e aggiungile alle carte che hai in mano. Poi rimischia le carte del tuo mazzo.
                                                                        Vento di Fata    110
																        Istanza-GX
                                                                        Prendi due dei Pok??mon in panchina del tuo avversario e tutte le carte loro assegnate e aggiungili alle carte che ha in mano. Non puoi usare pi?? di un attacco GX a partita.');
INSERT INTO catalog VALUES(99,'Pokemon','Fase 1',90,'Rara','Folletto','Azumarill','Bollaraggio 30
                                                           Lancia una moneta. Se esce testa, il Pok??mon attivo del tuo avversario viene paralizzato.
                                                           Troppoforte  60+
                                                           Puoi infliggere 30 danni in pi??. Se lo fai, questo Pok??mon infligge 30 danni a se stesso.');
INSERT INTO catalog VALUES(100,'Pokemon','Base',40,'Comune','Folletto','Togepi','Dolcebacio  10
                                                         Il tuo avversario pesca una carta.');
INSERT INTO catalog VALUES(101,'Pokemon','Base',130,'Rara','Drago','Kyurem Nero','Doppiagrinfia 20x
                                                          Lancia due volte una moneta. Questo attacco infligge 20 danni ogni volta che esce testa.
                                                          Lampogelido   100
                                                          Scarta un''Energia assegnata a questo Pok??mon.');
INSERT INTO catalog VALUES(102,'Pokemon','Base',90,'Comune','Drago','Latias','Legame Eonico
                                                        Pesca una carta. Se Latios ?? nella tua panchina, pesca un''altra carta.');
INSERT INTO catalog VALUES(103,'Pokemon','Base',100,'Comune','Drago','Latios','Aliante 20
												       Cielolama 70+
                                                       Se Latias ?? nella tua panchina, questo attacco infligge 50 danni in pi??.');
INSERT INTO catalog VALUES(104,'Pokemon','Fase 2',160,'Rara Holo','Drago','Dragonite','Ondadrago  130
                                                               Scarta un''Energia Grass e un''Energia Lightning assegnate a questo Pok??mon.
                                                               Gigacoda   200
                                                               Lancia una moneta. Se esce croce, questo attacco non ha effetto.');
INSERT INTO catalog VALUES(105,'Pokemon','Fase 1',80,'Non Comune','Drago','Dragonair', 'Muta
                                                                Cura questo Pok??mon da 30 danni.
                                                                Schianto   60??
                                                                Lancia due volte una moneta. Questo attacco infligge 60 danni ogni volta che esce testa.');

INSERT INTO catalog VALUES(106,'Yu-Gi-Oh!','N/A',0,'Continua','Trappola','Anima Immortale','Ogni "Mago Nero" nella tua Zona Mostri ?? immune agli effetti delle carte del tuo avversario.
                                                                                               Se questa carta scoperta lascia il Terreno: distruggi tutti i mostri che controlli. Puoi utilizzare
                                                                                                il seguente effetto di "Anima Immortale" una sola volta per turno. Puoi attivare 1 di questi effetti;
                                                                                                Evoca Specialmente 1 "Mago Nero" dalla tua mano o Cimitero.
                                                                                                Aggiungi 1 "Attacco Mago Nero" o "Mille Coltelli" dal tuo Deck alla tua mano.');

INSERT INTO catalog VALUES(107,'Yu-Gi-Oh!','Luce',6,'Comune','Mostro','Artistamico CaleidoScorpione','[INSETTO/PENDULUM/EFFETTO]
                                                                                                     Effetto Pendulum
                                                                                                     Tutti i mostri LUCE che controlli guadagnano 300 ATK.
                                                                                                     Una volta per turno: puoi scegliere come bersaglio 1 mostro scoperto che controlli; in questo turno, esso pu?? attaccare tutti i mostri
                                                                                                     Evocati Specialmente controllati dal tuo avversario, una volta ciascuno. ');
INSERT INTO catalog VALUES(108,'Yu-Gi-Oh!','Terra',5,'Comune','Mostro','Artistamico Compagnonaga','[RETTILE/PENDULUM/EFFETTO]
                                                                                                    Effetto Pendulum
                                                                                                    Una volta per turno: puoi scegliere come bersaglio 1 mostro scoperto che controlli; esso guadagna 300 ATK
                                                                                                    per ogni carta "Artistamico" che controlli attualmente, fino alla fine di questo turno.
                                                                                                    Se questa carta viene Evocata Normalmente o Specialmente: puoi scegliere come bersaglio 1 mostro che controlli;
                                                                                                   esso guadagna 300 ATK per ogni mostro "Artistamico" che controlli attualmente. I mostri di Livello 5 o inferiore non possono attaccare.' );
INSERT INTO catalog VALUES(109,'Yu-Gi-Oh!','Oscurit??',4,'Comune','Mostro','Artistamico Grinfiargento','[BESTIA/PENDULUM/EFFETTO]
                                                                                                        Effetto Pendulum
                                                                                                       Tutti i mostri "Artistamico" che controlli guadagnano 300 ATK.
                                                                                                        Quando questa carta dichiara un attacco: tutti i mostri "Artistamico" che controlli attualmente guadagnano 300 ATK,
                                                                                                        fino alla fine della Battle Phase.');
INSERT INTO catalog VALUES(110,'Yu-Gi-Oh!','Acqua',2,'Comune','Mostro','Artistamico Rospo Giratore','[ACQUA/PENDULUM/EFFETTO]
                                                                                                       Effetto Pendulum
                                                                                                       Una volta per turno: puoi scegliere come bersaglio 1 mostro scoperto sul Terreno;
                                                                                                       scambia i suoi ATK e DEF attuali fino alla fine di questo turno.
                                                                                                      Una volta per turno, durante la tua Battle Phase (eccetto durante il Damage Step): puoi scegliere come bersaglio 1 mostro che controlli; cambia la sua posizione e,
                                                                                                      se lo fai, scambia i suoi ATK e DEF attuali fino alla fine di questo turno.');
INSERT INTO catalog VALUES(111,'Yu-Gi-Oh!','Terra',2,'Comune','Mostro','Artistamico Trampolince','[BESTIA/PENDULUM/EFFETTO]
                                                                                                    Effetto Pendulum
                                                                                                    Quando Evochi Pendulum uno o pi?? mostri: puoi scegliere come bersaglio 1 carta nella Zona Pendulum di qualsiasi giocatore; falla ritornare nella mano.
                                                                                                    Puoi utilizzare questo effetto di "Artistamico Trampolince" una sola volta per turno.
                                                                                                     Quando questa carta viene Evocata Normalmente: puoi scegliere come bersaglio 1 carta nella Zona Pendulum di qualsiasi giocatore;
                                                                                                    falla ritornare nella mano.');
INSERT INTO catalog VALUES(112,'Yu-Gi-Oh!','N/A',0,'Normale','Magia','Baratto','Scarta 1 mostro di Livello 8; pesca 2 carte.');
INSERT INTO catalog VALUES(113,'Yu-Gi-Oh!','N/A',0,'Normale','Magia','Carica Della Brigata Della Luce','Manda le prime 3 carte del tuo Deck al Cimitero; aggiungi 1 mostro "Fedele della Luce" di Livello 4
                                                                                                        o inferiore dal tuo Deck alla tua mano.');
INSERT INTO catalog VALUES(114,'Yu-Gi-Oh!','N/A',0,'Normale','Magia','Carte Dal Cielo','Bandisci 1 mostro Fata LUCE dalla tua mano e, se lo fai, pesca 2 carte. Non puoi Evocare Specialmente o
                                                                                        effettuare la tua Battle Phase nel turno in cui attivi questa carta.');
INSERT INTO catalog VALUES(115,'Yu-Gi-Oh!','N/A',0,'Normale','Trappola','Cessate Il Fuoco','Se sul Terreno c''?? un mostro coperto in Posizione di Difesa o un Mostro con Effetto: metti scoperti in Posizione di Difesa tutti i mostri coperti in Posizione di Difesa sul Terreno (gli effetti dei mostri Scoperta non vengono attivati in questo caso),
                                                                                            inoltre infliggi al tuo avversario 500 danni per ogni Mostro con Effetto sul Terreno.');
INSERT INTO catalog VALUES(116,'Yu-Gi-Oh!','N/A',0,'Rapida','Magia','Circolo Salamagna','Attiva 1 di questi effetti.
                                                                                         Aggiungi 1 mostro "Salamagna" dal tuo Deck alla tua mano.
                                                                                        Scegli come bersaglio 1 Mostro Link "Salamagna" che controlli che ?? stato Evocato Link utilizzando un mostro con il suo stesso nome come materiale; quel Mostro Link ?? immune agli effetti dei mostri in questo turno, eccetto i suoi stessi.
                                                                                        Puoi attivare solo 1 "Circolo Salamagna" per turno.');
INSERT INTO catalog VALUES(117,'Yu-Gi-Oh!','Luce',4,'Comune','Mostro','Congegno DOro','[MACCHINA/EFFETTO]
                                                                                         Quando questa carta viene Evocata Normalmente o Specialmente: puoi Evocare Specialmente 1 mostro Macchina di Livello 4 dalla tua mano. Se questa carta viene distrutta in battaglia o dall''effetto di una carta:
                                                                                         puoi Evocare Specialmente 1 mostro "Congegno" di Livello 4 dal tuo Deck, eccetto "Congegno d''Oro". Puoi utilizzare solo 1 effetto di "Congegno d''Oro" per turno, e solo una volta in quel turno.');
INSERT INTO catalog VALUES(118,'Yu-Gi-Oh!','Fuoco',3,'Comune','Mostro','Crociatia Reclusia','[INCANTATORE/EFFETTO]
                                                                                            Puoi Evocare Specialmente questa carta (dalla tua mano) in Posizione di Difesa in una tua zona puntata da un Mostro Link. Puoi Evocare Specialmente "Crociatia Reclusia" una sola volta per turno in questo modo. Se questa carta viene Evocata Normalmente o Specialmente in una zona puntata da un Mostro Link: puoi scegliere come bersaglio 1 carta "Crociatia" che controlli e 1 carta controllata dal tuo avversario; distruggile.
                                                                                            Puoi utilizzare questo effetto di "Crociatia Reclusia" una sola volta per turno.');
INSERT INTO catalog VALUES(119,'Yu-Gi-Oh!','Luce',10,'Ultra Rara','Mostro','Cyber Drago Finale','[MACCHINA/FUSIONE/EFFETTO]
                                                                                           "Cyber Drago" + "Cyber Drago" + "Cyber Drago"
                                                                                            L''Evocazione tramite Fusione di questa carta pu?? essere effettuata solo con i Materiali da Fusione sopra indicati.
                                                                                            Se questa carta attacca un mostro in Posizione di Difesa, infliggi danno da combattimento perforante.');
INSERT INTO catalog VALUES(120,'Yu-Gi-Oh!','Luce',8,'Rara','Mostro','Cyber Drago Gemello','[MACCHINA/FUSIONE/EFFETTO]
                                                                                            "Cyber Drago" + "Cyber Drago"
                                                                                            L''Evocazione tramite Fusione di questa carta pu?? essere effettuata solo con i Materiali da Fusione sopra indicati.
                                                                                            Questa carta pu?? effettuare un secondo attacco durante ogni Battle Phase');
INSERT INTO catalog VALUES(121,'Yu-Gi-Oh!','Luce',5,'Comune','Mostro','Cyber Drago','[MACCHINA/EFFETTO]
                                                                                    Se solo il tuo avversario controlla un mostro, puoi Evocare Specialmente questa carta (dalla tua mano).');
INSERT INTO catalog VALUES(122,'Yu-Gi-Oh!','Luce',2,'Comune','Mostro','Cyber Drago Nucleo','[MACCHINA/EFFETTO]
                                                                                            Quando questa carta viene Evocata Normalmente: aggiungi 1 Magia/Trappola "Cyber" o "Cibernetico" dal tuo Deck alla tua mano. Se solo il tuo avversario controlla un mostro: puoi bandire questa carta dal tuo Cimitero; Evoca Specialmente 1 mostro "Cyber Drago" dal tuo Deck. Puoi utilizzare 1 solo effetto di "Cyber Drago Nucleo" per turno, e solo una volta in quel turno.
                                                                                            Il nome di questa carta diventa "Cyber Drago" mentre ?? sul Terreno o nel Cimitero.');
INSERT INTO catalog VALUES(123,'Yu-Gi-Oh!','Vento',4,'Non Comune','Mostro','Cyber Lady Arpia','[BESTIA ALATA/EFFETTO]
                                                                                              (Il nome di questa carta ?? sempre considerato come "Lady Arpia".)');
INSERT INTO catalog VALUES(124,'Yu-Gi-Oh!','Luce',1,'Comune','Mostro','Dissimulatore Di Effetto','[INCANTATORE/TUNER/EFFETTO]
                                                                                                  Durante la Main Phase del tuo avversario (Effetto Rapido): puoi mandare questa carta dalla tua mano al Cimitero, poi scegliere come bersaglio 1 Mostro con Effetto controllato dal tuo avversario;
                                                                                                  annulla gli effetti di quel mostro scoperto controllato dal tuo avversario, fino alla fine di questo turno.');
INSERT INTO catalog VALUES(125,'Yu-Gi-Oh!','N/A',0,'Contro Trappola','Trappola','Disturbo Magico','Quando viene attivata una Carta Magia: scarta 1 carta;
                                                                                                   annulla l''attivazione e, se lo fai, distruggila.');
INSERT INTO catalog VALUES(126,'Yu-Gi-Oh!','Luce',5,'Non Comune','Mostro','Disturbo Vento Solare','[MACCHINA/EFFETTO]
                                                                                                    Se non controlli nessun mostro, puoi Evocare Specialmente questa carta (dalla tua mano), ma i suoi ATK e DEF originali diventano la met??. Durante ogni tua Standby Phase: aumenta il Livello di questa carta di 1.
                                                                                                    Ci pu?? essere solo 1 "Disturbo Vento Solare" sul Terreno.');
INSERT INTO catalog VALUES(127,'Yu-Gi-Oh!','Luce',4,'Non Comune','Mostro','Drago Antico','[DRAGO/EFFETTO]
                                                                                          Quando questa carta infligge danno da combattimento al tuo avversario con un attacco diretto:
                                                                                          puoi aumentare il Livello di questa carta di 1 e il suo ATK di 500.');
INSERT INTO catalog VALUES(128,'Yu-Gi-Oh!','Vento',8,'Rara','Mostro','Drago Polvere Di Stelle','[DRAGO/SYNCHRO/EFFETTO]
                                                                                                1 Tuner + 1+ mostri non-Tuner
                                                                                                Quando viene attivata una carta o un effetto che sta per distruggere una o pi?? carte sul Terreno (Effetto Rapido):
                                                                                                puoi offrire come Tributo questa carta; annulla l''attivazione e, se lo fai, distruggila. Durante la End Phase, se questo effetto ?? stato attivato in questo turno (e non ?? stato annullato): puoi Evocare Specialmente questa carta dal tuo Cimitero.');
INSERT INTO catalog VALUES(129,'Yu-Gi-Oh!','N/A',0,'Normale','Magia','Fusione Mondo Parallelo','Evoca tramite Fusione 1 Mostro Fusione "EROE Elementale" dal tuo Extra Deck, mischiando nel tuo Deck i tuoi Materiali da Fusione banditi indicati su di esso. Non puoi Evocare Specialmente mostri,
                                                                                                eccetto con l''effetto di questa carta, durante il turno in cui attivi questa carta.');
INSERT INTO catalog VALUES(130,'Yu-Gi-Oh!','Vento',4,'Comune','Mostro','Garuda Lo Spirito Del Vento','[BESTIA ALATA/EFFETTO]
                                                                                                      Questa carta non pu?? essere Evocata Normalmente o Posizionata. Questa carta pu?? essere Evocata solo Specialmente rimuovendo dal gioco 1 mostro VENTO nel tuo Cimitero.
                                                                                                      Durante l''End Phase del tuo avversario, puoi scegliere 1 mostro scoperto controllato dal tuo avversario, e cambiare la sua posizione.');
INSERT INTO catalog VALUES(131,'Yu-Gi-Oh!','N/A',0,'Normale','Magia','Giara Dei Desideri','Bandisci 10 carte dalla cima del tuo Deck, coperte; pesca 2 carte. Puoi attivare solo 1 "Giara dei Desideri" per turno.');
INSERT INTO catalog VALUES(132,'Yu-Gi-Oh!','N/A',0,'Contro Trappola','Trappola','Giudizio Solenne','Quando uno o pi?? mostri stanno per essere Evocati OPPURE viene attivata una Carta Magia/Trappola: paga la met?? dei tuoi LP;
                                                                                                    annulla l''Evocazione oppure l''attivazione e, se lo fai, distruggi quella carta.');
INSERT INTO catalog VALUES(133,'Yu-Gi-Oh!','Terra',4,'Comune','Mostro','Guerriero Rocciapietra','[ROCCIA/EFFETTO]
                                                                                                  Non subisci danni da combattimento da battaglie in cui ?? coinvolta questa carta. Quando questa carta attaccante viene distrutta in battaglia e mandata al Cimitero, Evoca Specialmente 2 "Segna-Rocciapietra" (Tipo Roccia/TERRA/Livello 1/ATK 0/DEF 0).
                                                                                                  Questi Segna-Mostro non possono venire offerti come Tributo per un''Evocazione tramite Tributo.');
INSERT INTO catalog VALUES(134,'Yu-Gi-Oh!','Luce',4,'Comune','Mostro','Hecatrice','[FATA/EFFETTO]
                                                                                    Puoi scartare questa carta al Cimitero; aggiungi 1 "Valhalla, Sala dei Caduti" dal tuo Deck alla tua mano');
INSERT INTO catalog VALUES(135,'Yu-Gi-Oh!','Luce',9,'Ultra Rara','Mostro','Incrociatore Spazio Profondo IX','[MACCHINA/EFFETTO]
                                                                                                             Puoi Evocare Specialmente questa carta (dalla tua mano) mandando 1 altro mostro di Tipo Macchina dalla tua mano al Cimitero.');
INSERT INTO catalog VALUES(136,'Yu-Gi-Oh!','N/A',0,'Continua','Magia','La Fontana Nel Cielo','Quando un mostro LUCE viene distrutto in battaglia e mandato al tuo Cimitero, puoi rimuovere quel mostro dal gioco per guadagnare Life Point pari al suo ATK');

INSERT INTO catalog VALUES(137,'Yu-Gi-Oh!','Luce',3,'Comune','Mostro','L''Agente della Forza - Venere','Questa carta ?? immune agli effetti delle Carte Magia. Mentre controlli "Il Santuario nel Cielo" scoperto, e i tuoi Life Point sono superiori a quelli del tuo avversario,
                                                                                                        questa carta guadagna ATK e DEF pari alla differenza tra i tuoi Life Point e quelli del tuo avversario.');
INSERT INTO catalog VALUES(138,'Yu-Gi-Oh!','N/A',0,'Normale','Magia','Libro Di Magia Dei Segreti','Aggiungi 1 carta "Libro di Magia" dal tuo Deck alla tua mano, eccetto "Libro di Magia dei Segreti".
                                                                      Puoi attivare solo 1 "Libro di Magia dei Segreti" per turno.');
INSERT INTO catalog VALUES(139,'Yu-Gi-Oh!','N/A',0,'Terreno','Magia','Mina Mistica','Se il tuo avversario controlla pi?? mostri di te, il tuo avversario non pu?? attivare effetti di mostri o dichiarare un attacco. Se tu controlli pi?? mostri del tuo avversario, non puoi attivare effetti di mostri o dichiarare un attacco. Una volta per turno, durante la End Phase,
                                                                                     se entrambi i giocatori controllano lo stesso numero di mostri: distruggi questa carta.');
INSERT INTO catalog VALUES(140,'Yu-Gi-Oh!','N/A',0,'Normale','Magia','Nuovo Cast Artistamico','Rivela un qualsiasi numero di mostri "Artistamico" dalla tua mano e mischiali nel Deck, poi pesca carte pari al numero di carte che hai mischiato nel Deck +1.
                                                                                              Puoi attivare solo 1 "Nuovo Cast Artistamico" per turno.');
INSERT INTO catalog VALUES(141,'Yu-Gi-Oh!','N/A',0,'Normale','Trappola','Oh Segna-Mostro-baum!','Offri come Tributo 2 o pi?? Segna-Mostro con lo stesso Livello, poi scegli come bersaglio un numero di mostri nel tuo Cimitero con quello stesso Livello, fino al massimo al numero di Segna-Mostro offerti come Tributo; Evoca Specialmente quei bersagli.
                                                                                                 I loro effetti sono annullati. Distruggili durante la End Phase.');
INSERT INTO catalog VALUES(142,'Yu-Gi-Oh!','Oscurit??',10,'Ultra Rara','Mostro','Orichalcos Shunoros','[MACCHINA/EFFETTO]
                                                                                                      Questa carta non pu?? essere Evocata Normalmente o Posizionata. Se un Mostro Normale che controlli viene distrutto in battaglia, puoi Evocare Specialmente questa carta dalla tua mano. Questa carta guadagna 1000 ATK per ogni mostro controllato dal tuo avversario.
                                                                                                      I Mostri Normali di Livello 4 scoperti sul Terreno non possono essere distrutti dagli effetti delle carte.');
INSERT INTO catalog VALUES(143,'Yu-Gi-Oh!','N/A',0,'Normale','Trappola','Potente Cessate Il Fuoco','Scarta 1 carta dalla tua mano. Nessuna Carta Trappola pu?? essere attivata fino alla End Phase di questo turno.');

INSERT INTO catalog VALUES(144,'Yu-Gi-Oh!','N/A',0,'Normale','Trappola','Prigione Dimensionale','Quando un mostro dell''avversario dichiara un attacco: scegli come bersaglio quel mostro attaccante; bandisci quel bersaglio.');

INSERT INTO catalog VALUES(145,'Yu-Gi-Oh!','Terra',3,'Comune','Mostro','Riciclatore Di Frammenti','[MACCHINA/EFFETTO]
                                                                                                    Quando questa carta viene Evocata Normalmente o Specialmente, puoi scegliere 1 mostro di Tipo Macchina dal tuo Deck e mandarlo al Cimitero. Una volta per turno,
                                                                                                    puoi far ritornare 2 mostri TERRA di Tipo Macchina di Livello 4 dal tuo Cimitero al Deck per pescare 1 carta.');
INSERT INTO catalog VALUES(146,'Yu-Gi-Oh!','N/A',0,'Rapida','Magia','Rimozione Di Limite','Raddoppia l''ATK di tutti i mostri Macchina che controlli attualmente, fino alla fine di questo turno.
                                                                                           Durante la End Phase di questo turno, distruggi quei mostri.');
INSERT INTO catalog VALUES(147,'Yu-Gi-Oh!','N/A',0,'Continua','Trappola','Risucchia Mente','Attiva questa carta pagando 1000 LP. Gli effetti dei mostri nella mano non possono essere attivati.');

INSERT INTO catalog VALUES(148,'Yu-Gi-Oh!','Luce',7,'Non Comune','Mostro','Sacerdotessa Alta Della Profezia','[INCANTATORE/EFFETTO]
                                                                                                              Puoi rivelare 3 Magie "Libro di Magia" nella tua mano; Evoca Specialmente questa carta dalla tua mano. Una volta per turno: puoi bandire 1 Magia "Libro di Magia" dalla tua mano o Cimitero,
                                                                                                              poi scegliere come bersaglio 1 carta sul Terreno; distruggi quel bersaglio.');
INSERT INTO catalog VALUES(149,'Yu-Gi-Oh!','N/A',0,'Continua','Trappola','Scarica Di Colpi','Una volta per turno, puoi staccare un qualsiasi numero di materiali dai Mostri Xyz Macchina che controlli, poi scegliere come bersaglio quel numero di carte sul Terreno; distruggile.
                                                                                             Se uno o pi?? Mostri Xyz Macchina che controlli vengono distrutti in battaglia o dall''effetto di una carta dell''avversario mentre questa carta ?? nel tuo Cimitero: puoi bandire questa carta e 1 Mostro Xyz Macchina dal tuo Cimitero; infliggi al tuo avversario danno pari al Rango del mostro bandito x 200.');
INSERT INTO catalog VALUES(150,'Yu-Gi-Oh!','N/A',0,'Normale','Trappola','Scontro Ad Armi Pari','Alla fine della Battle Phase, se il tuo avversario controlla pi?? carte di te: puoi fare bandire al tuo avversario carte dal suo Terreno coperte in modo che controlli il tuo stesso numero di carte.
                                                                                                Se non controlli nessuna carta, puoi attivare questa carta dalla tua mano.');
INSERT INTO catalog VALUES(151,'Yu-Gi-Oh!','N/A',0,'Continua','Trappola','Seraphim Sintetico','Ogni volta che viene attivata una Carta Contro-Trappola, immediatamente dopo che si ?? risolta,
                                                                                               Evoca Specialmente 1 "Segna-Seraphim Sintetico" (Fata/LUCE/Livello 1/ATK 300/DEF 300).');
INSERT INTO catalog VALUES(152,'Yu-Gi-Oh!','Luce',4,'Comune','Mostro','Spada Serafino Delle Stelle','[FATA/EFFETTO]
                                                                                                     Una volta per turno: puoi mandare 1 mostro "Serafino delle Stelle" dalla tua mano al Cimitero; questa carta guadagna ATK pari
                                                                                                     all''ATK originale del mostro mandato al Cimitero, fino alla End Phase.');
INSERT INTO catalog VALUES(153,'Yu-Gi-Oh!','N/A',0,'Normale','Magia','Terraformare','Aggiungi 1 Magia Terreno dal tuo Deck alla tua mano.');
INSERT INTO catalog VALUES(154,'Yu-Gi-Oh!','N/A',0,'Rapida','Magia','Turbini Gemelli','Scarta 1 carta, poi scegli come bersaglio fino a 2 Magie/Trappole sul Terreno; distruggile.');
INSERT INTO catalog VALUES(155,'Yu-Gi-Oh!','Luce',0,'Rara','Mostro','Venere Splendida','[FATA/EFFETTO]
                                                                                        Tutti i mostri non-Fata perdono 500 ATK/DEF.
                                                                                        L''attivazione e gli effetti delle Carte Magia/Trappola attivate sul tuo Terreno non possono essere annullate.');


INSERT INTO users VALUES('Obe','Edu', 'Bur', 'edu@hotmail.it','GbEZsnkEipLEhXM/lV4C8A==');
INSERT INTO users VALUES( 'Sau', 'Dan', 'Her', 'dan@gmail.com', 'GbEZsnkEipLEhXM/lV4C8A==');
INSERT INTO users VALUES( 'Zay', 'Mar', 'Her', 'mar@hotmail.com', 'GbEZsnkEipLEhXM/lV4C8A==');
INSERT INTO users VALUES( 'Elge', 'Dav', 'Bur', 'dav@gmail.it', 'GbEZsnkEipLEhXM/lV4C8A==');
INSERT INTO users VALUES( 'Pol', 'Pao', 'Gre', 'pao@yahoo.it','GbEZsnkEipLEhXM/lV4C8A==' );
INSERT INTO users VALUES( 'Lorenzo1995', 'Lorenzo', 'Remo', 'lorenzoremo@hotmail.com', 'GbEZsnkEipLEhXM/lV4C8A==');
INSERT INTO users VALUES( 'AntoCere', 'Antonio', 'Cereali', 'antoniocere@gmail.it', 'GbEZsnkEipLEhXM/lV4C8A==');
INSERT INTO users VALUES( 'SerenaBeuci', 'Serena', 'Beuci', 'serenabeuci@gmail.com', 'GbEZsnkEipLEhXM/lV4C8A==');
INSERT INTO users VALUES( 'EmanueliStefano','Stefano', 'Emanueli', 'emanueli1994@hotmail.com', 'GbEZsnkEipLEhXM/lV4C8A==');
INSERT INTO users VALUES( 'Fra1999','Francesca', 'Pellegrini', 'francescapellegrini@yahoo.com', 'GbEZsnkEipLEhXM/lV4C8A==');



/*Obe*/
INSERT INTO collections VALUES(1 ,'Obe',2);
INSERT INTO collections VALUES(2 ,'Obe',2);
INSERT INTO collections VALUES(3 ,'Obe',2);
INSERT INTO collections VALUES(4 ,'Obe',2);
INSERT INTO collections VALUES(5 ,'Obe',2);

/*Sau*/
INSERT INTO collections VALUES(6 ,'Sau',2);
INSERT INTO collections VALUES(7 ,'Sau',2);
INSERT INTO collections VALUES(8 ,'Sau',2);
INSERT INTO collections VALUES(9 ,'Sau',2);
INSERT INTO collections VALUES(10 ,'Sau',2);

/*Zay*/
INSERT INTO collections VALUES(10 ,'Zay',2);
INSERT INTO collections VALUES(11 ,'Zay',2);
INSERT INTO collections VALUES(12 ,'Zay',2);
INSERT INTO collections VALUES(13 ,'Zay',2);
INSERT INTO collections VALUES(14 ,'Zay',2);

/*elge*/
INSERT INTO collections VALUES(14 ,'elge',2);
INSERT INTO collections VALUES(50 ,'elge',2);
INSERT INTO collections VALUES(55 ,'elge',2);
INSERT INTO collections VALUES(33 ,'elge',2);
INSERT INTO collections VALUES(20 ,'elge',2);

/*Pol*/
INSERT INTO collections VALUES(100 ,'Pol',2);
INSERT INTO collections VALUES(90 ,'Pol',2);
INSERT INTO collections VALUES(30 ,'Pol',2);
INSERT INTO collections VALUES(45 ,'Pol',2);
INSERT INTO collections VALUES(19 ,'Pol',2);
INSERT INTO collections VALUES(15 ,'Pol',2);
INSERT INTO collections VALUES(5 ,'Pol',2);



