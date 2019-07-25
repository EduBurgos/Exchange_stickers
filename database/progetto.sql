CREATE TABLE catalog(
ID  NUMERIC(6) PRIMARY KEY,
Category VARCHAR(20) NOT NULL,
Class VARCHAR(25) NOT NULL, 
Lvl NUMERIC(3) NOT NULL,
Rarity VARCHAR(15) NOT NULL,
CardType VARCHAR(20),
CardName VARCHAR(100) NOT NULL,
CardDescription VARCHAR(10000) NOT NULL,
unique(Class, Rarity, CardName, CardType)
);



CREATE TABLE users(
Username VARCHAR(20) PRIMARY KEY,
NameUser VARCHAR(15) NOT NULL,
Surname VARCHAR(20) NOT NULL,
Mail VARCHAR(55) NOT NULL,
Pass VARCHAR(20) NOT NULL,
Unique(Mail)
);

Insert into catalog VALUES(1,'Hearthstone','Druido',8,'Rara','Magia','Aiuto della Foresta','Magia Gemella. Evoca cinque Treant 2/2.');
INSERT INTO catalog VALUES(2,'Hearthstone','Druido',3,'Comune','Magia','Benedizione degli Antichi','Magia Gemella. +1/+1 ai tuoi servitori.');
INSERT INTO catalog VALUES(3,'Hearthstone','Druido',5,'Epica','Bestia','Cervo di Cristallo','Assalto. Grido di Battaglia: se hai rigenerato 5 Salute in questa partita, evoca una copia di se stesso.');
INSERT INTO catalog VALUES(4,'Hearthstone','Druido',2,'Leggendaria','Servitore','Custode Stalladris','Dopo che hai lanciato una Magia con Scegli, mette nella tua mano una copia di ogni scelta.');
INSERT INTO catalog VALUES(5,'Hearthstone','Druido',4,'Rara','Servitore', 'Guida della Foresta','Alla fine del tuo turno, entambi i giocatori pescano una carta.');   
 INSERT INTO catalog VALUES(6,'Hearthstone','Druido',4,'Leggendaria','Servitore', 'Melmo il Melmoide','Finchè resta mella tua mano, si trasforma in una copia 3/4 dell ultimo servitore che hai giocato.');  
 INSERT INTO catalog VALUES(7,'Hearthstone','Druido',8,'Leggendaria','Servitore','Scheggiatruce','Grido di Battaglia: seleziona un tuo servitore. Ne mette una copia 10/10 nella tua mano che costa 10.');
 INSERT INTO catalog VALUES(8,'Hearthstone','Druido',6,'Rara','Servitore', 'Tauren Protettiva', 'Scegli:+1/+1 agli altri tuoi servitori o evoca due Treant 2/2');   
INSERT INTO catalog VALUES(9,'Hearthstone','Druido',1,'Comune','Bestia','Portaghiande','Rantolo di Morte: mette nella tua mano due Scoiattoli 1/1.');
INSERT INTO catalog VALUES(10,'Hearthstone','Druido',10,'Leggendaria','Bestia','Tirannus','Non può essere il bersaglio di Magie o Poteri Eroe.');
INSERT INTO catalog VALUES(11,'Hearthstone','Paladino',3,'Comune','Drago','Alfiere Bronzeo','Rantolo di Morte: mette nella tua mano due Draghi 4/4.');
INSERT INTO catalog VALUES(12, 'Hearthstone', 'Paladino', 5, 'Rara', 'Drago', 'Draco Scargliardente', 'Grido di Battaglia: fornisce 5 armatura se hai un Drago in mano.');
INSERT INTO catalog VALUES(13,'Hearthstone','Paladino',5,'Epica','Magia','Duello!','Evoca un servitore dal mazzo di ogni giocatore. Combattono!');
INSERT INTO catalog VALUES(14,'Hearthstone','Paladino',3,'Rara','Magia','Favore Divino','Pesca delle carte finchè non ne hai lo stesso numero dell avversario');
INSERT INTO catalog VALUES(15,'Hearthstone','Paladino',2,'Rara','Magia','Lama Misteriosa','Grido di Battaglia: ottiene +1 Attacco se controlli un Segreto.');
INSERT INTO catalog VALUES(16,'Hearthstone','Paladino',2,'Comune','Magia', 'Lampo di Luce', 'Rigenera 4 Salute. Pesca una carta.');
INSERT INTO catalog VALUES(17,'Hearthstone','Paladino',1,'Comune','Magia','Mai Arrendersi!','Segreto: quando l''avversario lancia una Magia, +2 Salute ai tuoi servitori.');
INSERT INTO catalog VALUES(18,'Hearthstone','Paladino',10,'Leggendaria','Drago','Nozari','Grido di Battaglia: rigenera la Salute di entrambi gli eroi al massimo.');
INSERT INTO catalog VALUES(19,'Hearthstone','Paladino',1,'Comune','Magia','Ritorsione', 'Segreto: quando un tuo servitore muore fornisce +3/+2 a un servitore casuale');
INSERT INTO catalog VALUES(20,'Hearthstone','Paladino',1,'Epica','Magia','Saggezza Nascosta','Segreto: pesca 2 carte dopo che l avversario ha giocato 3 carte in un turno');


INSERT INTO catalog VALUES(21, 'Hearthstone', 'Mago',2,'Comune','Servitore','Arcanologa','Grido di Battaglia:pesca un Segreto dal tuo mazzo.');
INSERT INTO catalog VALUES(22, 'Hearthstone', 'Mago',5,'Rara','Servitore','Collezionista di Rarità','Ottieni +1/+1 quando peschi una carta');
INSERT INTO catalog VALUES(23, 'Hearthstone', 'Mago',2,'Epica', 'Magia', 'Frammento di Ghiaccio', 'Infligge 2 danni a un servitore. Se è Congelato pesca una carta');
INSERT INTO catalog VALUES(24, 'Hearthstone', 'Mago',5,'Epica','Magia','Furia del Drago','Mostra una Magia dal tuo mazzo.Infligge danni pari al suo costo a TUTTI i servitori');
INSERT INTO catalog VALUES(25, 'Hearthstone', 'Mago',7,'Leggendaria','Magia','Galassia Portatile di Luna','Imposta a (1) il costo dei servitori nel tuo mazzo.');
INSERT INTO catalog VALUES(26, 'Hearthstone', 'Mago',3,'Epica','Magia','Immagine Scissa','Segreto: quando un tuo servitore viene attaccato, ne evoca una copia');
INSERT INTO catalog VALUES(27, 'Hearthstone', 'Mago',4,'Rara','Servitore','Mago Anziano','Grido di Battaglia: +1 Danni Magici ai servitori adiacenti.');
INSERT INTO catalog VALUES(28, 'Hearthstone', 'Mago',6,'Rara','Servitore','Meteorologa','Grido di Battaglia: infligge 1 danno a un nemico casuale per ogni altra carta nella tua mano.');
INSERT INTO catalog VALUES(29, 'Hearthstone', 'Mago',10,'Leggendaria','Servitore','Kalecgos','La tua prima Magia ogni turno costa(0). Grido di Battaglia:Rinvieni una Magia.');
INSERT INTO catalog VALUES(30, 'Hearthstone', 'Mago',6,'Rara','Magia','Tormenta','Infligge 2 danni ai servitori nemici e li Congela.');

INSERT INTO catalog VALUES(31, 'Hearthstone', 'Sacerdote',2,'Comune','Servitore','Reclutatore del M.A.L.E.','Rantolo di Morte: mette un Lacchè nella tua mano.');
INSERT INTO catalog VALUES(32, 'Hearthstone', 'Sacerdote',5,'Epica','Bestia', 'Falena Splendente','Grido di Battaglia: se il tuo mazzo ha solo carte a costo dispari, raddoppia la Salute degli altri tuoi servitori.');
INSERT INTO catalog VALUES(33, 'Hearthstone', 'Sacerdote',3,'Epica','Magia','Incubo Vivido','Seleziona un tuo servitore. Ne evoca una copia con 1 Salute restante.');
INSERT INTO catalog VALUES(34, 'Hearthstone', 'Sacerdote',5,'Rara','Magia','Isteria di Massa','Costringe ogni servitore ad attaccare un altro servitore casuale.');
INSERT INTO catalog VALUES(35, 'Hearthstone', 'Sacerdote',12,'Rara','Servitore', 'Orrore della Fossa','Provocazione. Costa (1) in meno per ogni Magia che hai lanciato in questa partita.');
INSERT INTO catalog VALUES(36, 'Hearthstone', 'Sacerdote',0,'Epica','Magia','Piano di Lazul','Riduce l attacco di un servitore nemico di 1 fino al tuo turno successivo. (Si potenzia ogni turno!)');
INSERT INTO catalog VALUES(37, 'Hearthstone', 'Sacerdote',8,'Leggendaria','Servitore','Principessa Talanji','Grido di Battaglia: evica dalla tua mano i servitori non presenti nel tuo mazzo originale.');
INSERT INTO catalog VALUES(38, 'Hearthstone', 'Sacerdote',5,'Epica','Servitore','Ricercatrice Incauta','I servitori con Rantolo di Morte che giochi costano (3) in meno, ma muiono alla fine del turno');
INSERT INTO catalog VALUES(39, 'Hearthstone', 'Sacerdote',3,'Comune','Servitore', 'Sacerdote della Kabala','Grido di Battaglia: +3 salute a un tuo servitore.');
INSERT INTO catalog VALUES(40, 'Hearthstone', 'Sacerdote',2,'Comune','Magia','Seduta Spiritica','Seleziona un servitore. Ne mette una copia nella tua mano.');


INSERT INTO catalog VALUES(41,'Hearthstone','Stregone',7,'Leggendaria','Magia','Arcicattivo Rafaam','Provocazione. Grido di Battaglia: sostituisce le carte nella tua mano e nel tuo mazzo con servitori Leggendari.');
INSERT INTO catalog VALUES(42,'Hearthstone','Stregone',2,'Rara','Magia','Colpo di scena','Mette la tua mano nel tuo mazzo. Pesca lo stesso numero di carte.');
INSERT INTO catalog VALUES(43,'Hearthstone','Stregone',2,'Epica','Magia','Fiorsangue','La prossima Magia che lanci in questo turno costa Salute invece di Mana');
INSERT INTO catalog VALUES(44,'Hearthstone','Stregone',3,'Rara','Magia','Follia Incontrollata','Infligge 9 danni suddivisi casualmente tra tutti i personaggi.');
INSERT INTO catalog VALUES(45,'Hearthstone','Stregone',1,'Rara','Magia','Grido','Scarta la tua carta con il costo più basso. Infligge 2 danni a TUTTI i servitori.');
INSERT INTO catalog VALUES(46,'Hearthstone','Stregone',10,'Epica','Demone','Imp Gigante','Finche' 'resta nella tua mano, il suo costo si ridusce di (1) quando un tuo Demone muore.');
INSERT INTO catalog VALUES(47,'Hearthstone','Stregone',3,'Rara','Magia','Impferno','+1 di Attacco ai tuoi Demoni. Infligge 1 danno ai servitori nemici.');
INSERT INTO catalog VALUES(48,'Hearthstone','Stregone',4,'Rara','Magia','Imp-losione','Infligge da 2 a 4 danni a un servitore. Evoca un Imp 1/1 per ogni danno inflitto.');
INSERT INTO catalog VALUES(49,'Hearthstone','Stregone',6,'Rara','Servitore','Lacchè Posseduto','Rantolo di Morte: recluta un Demone.');
INSERT INTO catalog VALUES(50,'Hearthstone','Stregone',3,'Rara','Demone', 'Terrore del Vuoto','Grido di Battaglia: distrugge i servitori adiacenti e ne ottiene Attacco e Salute');

INSERT INTO catalog VALUES(51,'Pokemon','Fase 1',130,'Rara','Erba','Abomasnow','Abilita.Preghiera dei Ghiacci:Quando giochi questo Pokémon dalla tua mano durante il tuo turno
                                                            per far evolvere uno dei tuoi Pokémon, puoi assegnare a uno dei tuoi Pokémon una  carta Energia Grass dalla tua pila degli scarti.
												            Martello Ipnotico 80
												            Il Pokémon attivo del tuo avversario viene addormentato.');
INSERT INTO catalog VALUES(52,'Pokemon','Base',180,'Rara Holo Ex','Erba','VenusaurEX','Il tuo avversario prende due carte Premio ogni volta che uno dei tuoi Pokémon-EX viene messo K.O.
														  Velenpolvere 60
														  Il Pokémon attivo del tuo avversario viene avvelenato.
														  Martelgiungla	90
														  Cura questo Pokémon da 30 danni.');

INSERT INTO catalog VALUES(53,'Pokemon','Base',60,'Comune','Erba','Treecko','Attacco Rapido  10+
                                                      Lancia una moneta. Se esce testa, questo attacco infligge 10 danni in più.');
INSERT INTO catalog VALUES(54,'Pokemon','Base',70,'Comune','Erba','Celebi','Blocco Θ Previeni tutti gli effetti delle abilità dei Pokémon del tuo avversario inflitti a questo Pokémon.');
INSERT INTO catalog VALUES(55,'Pokemon','Fase 1',200,'Arcobaleno Rara','Erba','LeafeonGX','Abilità. Alito di Foglie:Una sola volta durante il tuo turno, se questo Pokémon è il tuo Pokémon attivo,
                                                              prima di attaccare, puoi curare da 50 danni uno dei tuoi Pokémon che ha Energie assegnate.
                                                              Il tuo avversario prende due carte Premio ogni volta che uno dei tuoi Pokémon-GX
                                                              viene messo K.O.
                                                              Solarraggio   110 Fioritura Formidabile-GX
                                                              Per ogni Pokémon Base nella tua panchina, cerca nel tuo mazzo una carta che si evolve
                                                              da quel Pokémon e metticela sopra per farlo evolvere. Poi rimischia le carte del tuo
                                                              mazzo. Non puoi usare più di un attacco GX a partita.');
INSERT INTO catalog VALUES(56,'Pokemon','Base',50,'Comune','Fuoco','Chimchar','Morso 10. Fuocopugno 20');
INSERT INTO catalog VALUES(57,'Pokemon','Base',180,'UltraRara','Fuoco','CharizardEX','Il tuo avversario prende due carte Premio ogni volta che uno dei tuoi Pokémon-EX viene messo K.O.
														 Megascensione
                                                         Cerca nel tuo mazzo un M Charizard-EX, mostralo e aggiungilo alle carte che hai in mano.
                                                         Poi rimischia le carte del tuo mazzo.
                                                         Cuorinfiamme	120
                                                         Este Pokémon se hace 30 puntos de daño a sí mismo.');
INSERT INTO catalog VALUES(58,'Pokemon','Mega',210,'Comune', 'Fuoco','MBlazikenEX','Il tuo avversario prende due carte Premio ogni volta che uno dei tuoi Pokémon-EX viene messo K.O.
													         Regola della Megaevoluzione
                                                             Quando uno dei tuoi Pokémon diventa un Pokémon Megaevoluzione, il tuo turno termina.
                                                             Fuoco Acrobatico	100
                                                             Durante il tuo prossimo turno, l''attacco Fuoco Acrobatico di questo Pokémon infligge 100 danni in più,
                                                             prima di aver applicato debolezza e resistenza.');
INSERT INTO catalog VALUES(59,'Pokemon','Fase 2',160,'Rara','Fuoco','Incineroar','Rogodenti	30
													      Il Pokémon attivo del tuo avversario viene bruciato.
													      Braccioteso  100×
                                                          Lancia due volte una moneta. Questo attacco infligge 100 danni ogni volta che esce testa.');
INSERT INTO catalog VALUES(60,'Pokemon','Base',190,'Rara Holo gx','Fuoco','HO-OhGX','Il tuo avversario prende due carte Premio ogni volta che uno dei tuoi Pokémon-GX viene messo K.O.
														      Magifuoco
                                                              Questo attacco infligge 50 danni a uno dei Pokémon del tuo avversario. Ricorda che non puoi
                                                              applicare debolezza e resistenza ai Pokémon in panchina.
                                                              Scottata della Fenice   180
                                                              Durante il tuo prossimo turno, questo Pokémon non può usare Scottata della Fenice.
                                                              Fiamma Eterna-GX
                                                              Prendi tre Pokémon-GX Fire o Pokémon-EX Fire in qualsiasi combinazione dalla tua pila
                                                              degli scarti e mettili nella tua panchina. Non puoi usare più di un attacco GX a partita.');
INSERT INTO catalog VALUES(61,'Pokemon','Base',70,'Comune','Acqua','Psyduck','Confusionda.Entrambi i Pokémon attivi vengono confusi.');
INSERT INTO catalog VALUES(62,'Pokemon','Fase 1',90,'Raro','Acqua','Glaceon','Bora	30
                                                       Questo attacco infligge 10 danni a ciascun Pokémon nella panchina del tuo avversario.
                                                       Ricorda che non puoi applicare debolezza e resistenza ai Pokémon in panchina.
												       Fendighiaccio 	60+
												       Lancia una moneta. Se esce testa, questo attacco infligge 30 danni in più.');
INSERT INTO catalog VALUES(63,'Pokemon','Fase 2',250,'Comune','Acqua','PrimarinaGX','Il tuo avversario prende due carte Premio ogni volta che uno dei tuoi Pokémon-GX viene messo K.O.
                                                              Bollabotta 10+
                                                              Questo attacco infligge 20 danni in più per ogni Energia Water assegnata ai tuoi Pokémon.
                                                              Mari Ruggenti 120
                                                              Scarta un’Energia assegnata al Pokémon attivo del tuo avversario.
                                                              Eco Formidabile-GX
                                                              Cura tutti i tuoi Pokémon da tutti i danni. Non puoi usare più di un attacco GX a partita.');
INSERT INTO catalog VALUES(64,'Pokemon','Fase 2',230,'Rara Holo gx','Acqua','GreninjaEX','Abilità.Lameturbine: Quando giochi questo Pokémon dalla tua mano per far evolvere uno dei tuoi Pokémon durante il tuo turno, puoi mettere tre segnalini danno su uno dei Pokémon del tuo avversario.
                                                                   Il tuo avversario prende due carte Premio ogni volta che uno dei tuoi Pokémon-GX viene messo K.O.
															       Sferzanube	110
                                                                   Puoi rimischiare questo Pokémon e tutte le carte a esso assegnate nel tuo mazzo.
                                                                   Cacciatore Oscuro-GX
                                                                   Questo attacco infligge 130 danni a uno dei Pokémon nella panchina del tuo avversario.
                                                                   Ricorda che non puoi applicare debolezza e resistenza ai Pokémon in panchina. Non puoi usare più di un attacco GX a partita');
INSERT INTO catalog VALUES(65,'Pokemon','Base',120,'Comune','Acqua','Palkia','Schizzi d''Onda30');
INSERT INTO catalog VALUES(66,'Pokemon','Fase 1',210,'Arcobaleno Rara','Lampo','RaichuGX','Il tuo avversario prende due carte Premio ogni volta che uno dei tuoi Pokémon-GX viene messo K.O.
															        Superscintilla	20+
                                                                    Questo attacco infligge 20 danni in più per ogni Energia Lightning assegnata ai tuoi Pokémon.
                                                                    Tuono		160
                                                                    Questo Pokémon infligge 30 danni a se stesso.
                                                                    Coda Voltaica-GX	120
                                                                    Il Pokémon attivo del tuo avversario viene paralizzato. Non puoi usare più di un attacco GX a partita.');
INSERT INTO catalog VALUES(67,'Pokemon','Fase 2',150,'Rara Holo','Lampo', 'Ampharos','Abilità.Bagliore Perduto:Una sola volta durante il tuo turno, prima di attaccare, puoi prendere due carte Energia Lightning dalla tua mano e metterle nell’area perduta.
                                                              Se lo fai, il Pokémon attivo del tuo avversario viene paralizzato.
                                                              Squarciabomba.
                                                              Questo attacco infligge 50 danni a due dei Pokémon del tuo avversario.
                                                              Ricorda che non puoi applicare debolezza e resistenza ai Pokémon in panchina' );
INSERT INTO catalog VALUES(68,'Pokemon','Base',110,'Comune','Lampo','Tapu KoKo','Svolta Volante:Questo attacco infligge 20 danni a ciascuno dei Pokémon del tuo avversario.
													     Ricorda che non puoi applicare debolezza e resistenza ai Pokémon in panchina.
                                                         Lamposfera100');
INSERT INTO catalog VALUES(69,'Pokemon','Base',70,'Comune','Lampo','Pikachu','Tuononda:Lancia una moneta. Se esce testa, il Pokémon attivo del tuo avversario viene paralizzato.
												       Energisfera');
INSERT INTO catalog VALUES(70,'Pokemon','Base',110,'Rara Holo EX','Lampo','EmolgaEX','Il tuo avversario prende due carte Premio ogni volta che uno dei tuoi Pokémon-EX viene messo K.O.
														       Energialiante: Cerca nel tuo mazzo una carta Energia Lightning e assegnala a questo Pokémon. Poi rimischia le carte del tuo mazzo. Se assegni Energia in questo modo, scambia questo Pokémon con uno della tua panchina.
                                                               Elettrostritolamento  60+
                                                               Puoi scartare un''Energia assegnata a questo Pokémon. Se lo fai, questo attacco infligge 30 danni in più.');
INSERT INTO catalog VALUES(71,'Pokemon','Base',120,'Rara Holo EX','Psico','MewEX','Abilità.Versatile:Questo Pokémon può usare gli attacchi di tutti i Pokémon in gioco,
													        sia tuoi che del tuo avversario. Devi comunque avere l''Energia necessaria per usare quegli attacchi.
                                                            Il tuo avversario prende due carte Premio ogni volta che uno dei tuoi Pokémon-EX viene messo K.O.
                                                            Sostituzione: Distribuisci a piacimento tutte le Energie assegnate ai tuoi Pokémon.');
INSERT INTO catalog VALUES(72,'Pokemon','Fase 2',130,'Rara Holo','Psico','Crobat','Abilità.Vista Notturna:Una sola volta durante il tuo turno, prima di attaccare, puoi pescare una carta.
														   Dentavvelenato 40
                                                           Il Pokémon difensore viene avvelenato. Tra un turno e l''altro, metti quattro segnalini danno invece di uno su quel Pokémon.');
INSERT INTO catalog VALUES(73,'Pokemon','Fase 2',250,'Comune','Psico','LunalaGX','Abilità.Psicotransfer:Durante il tuo turno, prima di attaccare, puoi spostare un’Energia Psychic da uno a un altro dei tuoi Pokémon tutte le volte che vuoi.
													       Il tuo avversario prende due carte Premio ogni volta che uno dei tuoi Pokémon-GX viene messo K.O.
                                                           Raggio d’Ombra  120
                                                           Durante il prossimo turno del tuo avversario, il Pokémon difensore non può essere curato.
                                                           Caduta Lunare-GX
                                                           Metti KO un Pokémon Base del tuo avversario che non è un Pokémon-GX. Non puoi usare più di un attacco GX a partita.');
INSERT INTO catalog VALUES(74,'Pokemon','Base',70,'Comune','Psico','Mimikyu','Infestare:Metti un segnalino danno sul Pokémon attivo del tuo avversario.
												       Sgomento: Scegli una carta a caso dalla mano del tuo avversario. Il tuo avversario mostra quella carta e la rimischia nel suo mazzo.');
INSERT INTO catalog VALUES(75,'Pokemon','Fase1',200,'Comune','Psico','EspeonGX','Il tuo avversario prende due carte Premio ogni volta che uno dei tuoi Pokémon-GX viene messo K.O.
														  Psicoraggio	30
                                                          Il Pokémon attivo del tuo avversario viene confuso.
                                                          Psichico		60+
                                                          Questo attacco infligge 30 danni in più per ogni Energia assegnata al Pokémon attivo del tuo avversario.
                                                          Distribuzione-GX
                                                          Distribuisci a piacimento dieci segnalini danno sui Pokémon del tuo avversario. Non puoi usare più di un attacco GX a partita.' );
INSERT INTO catalog VALUES(76,'Pokemon','Base',70,'Comune','Lotta','Riolu','Colpo Duplice 10×
									                 Lancia due volte una moneta. Questo attacco infligge 10 danno ogni volta che esce testa.');
INSERT INTO catalog VALUES(77,'Pokemon','Base',120,'Rara','Lotta','Landorus','Urlotonante  20
											          Assegna a uno dei tuoi Pokémon in panchina una carta Energia base dalla tua pila degli scarti.
												      Lazoaereo 90');
INSERT INTO catalog VALUES(78,'Pokemon','Fase1',210,'Comune','Lotta','LucarioGX','Il tuo avversario prende due carte Premio ogni volta che uno dei tuoi Pokémon-GX viene messo K.O.
													       Colpo d’Aura  30+
													       Se questo Pokémon si è evoluto da Riolu durante questo turno, questo attacco infligge 90 danni in più.
                                                           Calciovento   130
                                                           Batosta Litigiosa-GX  30×
                                                           Questo attacco infligge 30 danni per ogni segnalino danno presente su questo Pokémon. Non puoi usare più di un attacco GX a partita.');
INSERT INTO catalog VALUES(79,'Pokemon','Fase1',100,'Comune','Lotta','Pangoro','Abilità.Bravo Boss:Se questo Pokémon è il tuo Pokémon attivo, ha 20 PS in più per ogni Pokémon che hai in panchina.
												        Pugnomatto   80＋
                                                        Se questo Pokémon è influenzato da una condizione speciale, questo attacco infligge 40 danni in più.');
INSERT INTO catalog VALUES(80,'Pokemon','Base',70,'Comune','Lotta','Gligar','Colpo Doppio
												      Questo attacco infligge 10 danni a due dei Pokémon nella panchina del tuo avversario.
                                                      Ricorda che non puoi applicare debolezza e resistenza ai Pokémon in panchina.');
INSERT INTO catalog VALUES(81,'Pokemon','Fase 1',100,'Comune','Oscurità','Umbreon','Artiglio Mach  30
													        I danni di questo attacco non sono influenzati dalla resistenza.
                                                            Senso Siderale  60+
                                                            Gira a faccia in su delle tue carte Premio che sono a faccia in giù.
                                                            Se quella carta Premio è un Pokemon è un Pokemon, questo attacco infligge 60 danni in più.
                                                            La carta Premio resta a faccia in su per il resto della partita.');
INSERT INTO catalog VALUES(82,'Pokemon','Base',60,'Comune','Oscurità','Sneasel','Ventogelato
													      Il Pokémon attivo del tuo avversario viene addormentato.
                                                           Graffio20');
INSERT INTO catalog VALUES(83,'Pokemon','Base',180,'Rara Ultra','Oscurità','DarkraiGX','Abilità.Restaurazione:Una sola volta durante il tuo turno, prima di attaccare,
														        se questo Pokémon è nella tua pila degli scarti, puoi metterlo in panchina. Poi, assegnagli una carta Energia Darkness dalla tua pila degli scarti.
														         Il tuo avversario prende due carte Premio ogni volta che uno dei tuoi Pokémon-GX viene messo K.O.
                                                                 Fendente Oscuro     130
                                                                 I danni di questo attacco non sono influenzati dalla resistenza.
                                                                 Vicolo Cieco-GX
                                                                 Se il Pokémon attivo del tuo avversario è influenzato da una condizione speciale, quel Pokémon viene messo KO.
                                                                 Non puoi usare più di un attacco GX a partita.');
INSERT INTO catalog VALUES(84,'Pokemon','Fase 1',100,'Rara','Oscurità','Mightyena','Morso  30
													        Zanna Oscura  50
													        Scarta una carta a caso dalla mano del tuo avversario.');
INSERT INTO catalog VALUES(85,'Pokemon','Base',70,'Comune','Oscurità','Sandile','Fauci Imponenti  30
													      Se il Pokémon del tuo avversario viene messo KO dai danni di questo attacco, gli attacchi di questo Pokémon
                                                          infliggono 120 danni in più al Pokémon attivo del tuo avversario durante il tuo prossimo turno, prima di aver
                                                          applicato debolezza e resistenza.');
INSERT INTO catalog VALUES(86,'Pokemon','Base',70,'Comune','Metallo','Jirachi','Premonisogno: Pescare tre carte.Questo Pokemon viene addormentato.
												         Metaltestata 30x
												         Lancia una moneta finchè non esce croce.Questo attacco infligge 30 danni ogni volta che esce testa.');
INSERT INTO catalog VALUES(87,'Pokemon','Base',120,'Comune','Metallo','Dialga','Ferrartigli   30
												        Fermatempo    80
                                                        Il tuo avversario non può giocare Pokemon dalla sua mano per far evolvere il POkemon difensore durante il suo turno.');
INSERT INTO catalog VALUES(88,'Pokemon','Fase 1',100,'Rara','Metallo','Forretress','Abilità.Spintempesta:Quando giochi questo Pokémon dalla tua mano per far evolvere uno dei
													       tuoi Pokémon, puoi mettere un segnalino danno su ciascuno dei Pokémon del tuo avversario.
                                                           Schiacciaferro   20+
                                                           Questo attacco infligge 20 danni in più per ogni Energia Colorless nel costo di ritirata del Pokémon attivo del tuo avversario.');
INSERT INTO catalog VALUES(89,'Pokemon','Fase 2',160,'Rara','Metallo','Solgaleo','Freccia Lucente
													      Questo attacco infligge 50 danni a uno dei Pokémon del tuo avversario.
                                                          Ricorda che non puoi applicare debolezza e resistenza ai Pokémon in panchina.
                                                          Zanne Solari     170
                                                          Durante il tuo prossimo turno, questo Pokémon non può usare Zanne Solari.');
INSERT INTO catalog VALUES(90,'Pokemon','Fase 2',160,'Rara Holo','Metallo','Empoleon','Comando Assoluto   20×
                                                               Questo attacco infligge 20 danni per ogni Pokémon in panchina, sia tuo che del tuo avversario.
                                                               Mulinello    90
                                                               Scarta un’Energia assegnata al Pokémon attivo del tuo avversario.');
INSERT INTO catalog VALUES(91,'Pokemon','Fase 2',130,'Rara','Incolore','Pidgeot','Grinfie   40
												         Durante il prossimo turno del tuo avversario, il Pokémon difensore non può ritirarsi.
                                                         Forteraffica  60
                                                         Durante il tuo prossimo turno, l''attacco Forteraffica di questo Pokémon infligge 60 danni in più,
                                                         prima di aver applicato debolezza e resistenza.');
INSERT INTO catalog VALUES(92,'Pokemon','Base',60,'Comune','Incolore','Axew','Porzione Extra  20
                                                       Questo Pokémon infligge 10 danni a se stesso.
                                                        Dragartigli  40');
INSERT INTO catalog VALUES(93,'Pokemon','Base',180,'Rara Holo EX','Incolore','LugiaEX','Abilità.Eccedenza:Se un Pokémon del tuo avversario viene messo K.O.
														         dal danno di un attacco di questo Pokémon, prendi una carta Premio in più.
                                                                 Burrasca Plasma  120
                                                                 Scarta un''Energia Plasma assegnata a questo Pokémon. Se non puoi scartare
                                                                 un''Energia Plasma, questo attacco non ha effetto.');
INSERT INTO catalog VALUES(94,'Pokemon','Base',80,'Non Comune','Incolore','Farfetch''d','Tassa: Pesca due carte.
                                                                         Sfondaoggetto   20+
                                                                         Prima di infliggere danni, scarta tutte le carte Oggetto Pokémon assegnate al Pokémon attivo del tuo avversario.
                                                                         Se scarti una carta Oggetto Pokémon in questo modo, questo attacco infligge 70 danni in più.');
INSERT INTO catalog VALUES(95,'Pokemon','Base',130,'Rara','Incolore','Snorlax','Abilità.Lasciadormir:Se questo Pokémon è addormentato, tra un turno e l''altro, lancia due volte una moneta invece di una.
												        Se esce almeno una volta croce, questo Pokémon resta addormentato.
                                                        Sonnopressa    120
                                                        Cura questo Pokémon da 20 danni. Questo Pokémon viene addormentato.');
INSERT INTO catalog VALUES(96,'Pokemon','Base',70,'Comune','Folletto','Snubbull', 'Carica Avventata  20
                                                           Lancia una moneta. Se esce croce, questo Pokémon infligge 10 danni a se stesso.');
INSERT INTO catalog VALUES(97,'Pokemon','Base',60,'Comune','Folletto','Jigglypuff','Rotolamento  10
                                                            Cantosincero
                                                            Scarta un''Energia Darkness assegnata al Pokémon attivo del tuo avversario.');
INSERT INTO catalog VALUES(98,'Pokemon','Fase 1',200,'Rara','Folletto','SylveonGX','Il tuo avversario prende due carte Premio ogni volta che uno dei tuoi Pokémon-GX viene messo K.O.
                                                                        Fiocco Magico
                                                                        Cerca nel tuo mazzo fino a tre carte e aggiungile alle carte che hai in mano. Poi rimischia le carte del tuo mazzo.
                                                                        Vento di Fata    110
																        Istanza-GX
                                                                        Prendi due dei Pokémon in panchina del tuo avversario e tutte le carte loro assegnate e aggiungili alle carte che ha in mano. Non puoi usare più di un attacco GX a partita.');
INSERT INTO catalog VALUES(99,'Pokemon','Fase 1',90,'Rara','Folletto','Azumarill','Bollaraggio 30
                                                           Lancia una moneta. Se esce testa, il Pokémon attivo del tuo avversario viene paralizzato.
                                                           Troppoforte  60+
                                                           Puoi infliggere 30 danni in più. Se lo fai, questo Pokémon infligge 30 danni a se stesso.');
INSERT INTO catalog VALUES(100,'Pokemon','Base',40,'Comune','Folletto','Togepi','Dolcebacio  10
                                                         Il tuo avversario pesca una carta.');
INSERT INTO catalog VALUES(101,'Pokemon','Base',130,'Rara','Drago','Kyurem Nero','Doppiagrinfia 20x
                                                          Lancia due volte una moneta. Questo attacco infligge 20 danni ogni volta che esce testa.
                                                          Lampogelido   100
                                                          Scarta un''Energia assegnata a questo Pokémon.');
INSERT INTO catalog VALUES(102,'Pokemon','Base',90,'Comune','Drago','Latias','Legame Eonico
                                                        Pesca una carta. Se Latios è nella tua panchina, pesca un''altra carta.');
INSERT INTO catalog VALUES(103,'Pokemon','Base',100,'Comune','Drago','Latios','Aliante 20
												       Cielolama 70+
                                                       Se Latias è nella tua panchina, questo attacco infligge 50 danni in più.');
INSERT INTO catalog VALUES(104,'Pokemon','Fase 2',160,'Rara Holo','Drago','Dragonite','Ondadrago  130
                                                               Scarta un''Energia Grass e un''Energia Lightning assegnate a questo Pokémon.
                                                               Gigacoda   200
                                                               Lancia una moneta. Se esce croce, questo attacco non ha effetto.');
INSERT INTO catalog VALUES(105,'Pokemon','Fase 1',80,'Non Comune','Drago','Dragonair', 'Muta
                                                                Cura questo Pokémon da 30 danni.
                                                                Schianto   60×
                                                                Lancia due volte una moneta. Questo attacco infligge 60 danni ogni volta che esce testa.');

INSERT INTO catalog VALUES(106,'Yu-Gi-Oh!','N/A',0,'Continua','Trappola','Anima Immortale','Ogni "Mago Nero" nella tua Zona Mostri è immune agli effetti delle carte del tuo avversario.
                                                                                               Se questa carta scoperta lascia il Terreno: distruggi tutti i mostri che controlli. Puoi utilizzare
                                                                                                il seguente effetto di "Anima Immortale" una sola volta per turno. Puoi attivare 1 di questi effetti;
                                                                                                Evoca Specialmente 1 "Mago Nero" dalla tua mano o Cimitero.
                                                                                                Aggiungi 1 "Attacco Mago Nero" o "Mille Coltelli" dal tuo Deck alla tua mano.');

INSERT INTO catalog VALUES(107,'Yu-Gi-Oh!','Luce',6,'Comune','Mostro','Artistamico Caleidoscorpione','[INSETTO/PENDULUM/EFFETTO]
                                                                                                     Effetto Pendulum
                                                                                                     Tutti i mostri LUCE che controlli guadagnano 300 ATK.
                                                                                                     Una volta per turno: puoi scegliere come bersaglio 1 mostro scoperto che controlli; in questo turno, esso può attaccare tutti i mostri
                                                                                                     Evocati Specialmente controllati dal tuo avversario, una volta ciascuno. ');
INSERT INTO catalog VALUES(108,'Yu-Gi-Oh!','Terra',5,'Comune','Mostro','Artistamico Compagnonaga','[RETTILE/PENDULUM/EFFETTO]
                                                                                                    Effetto Pendulum
                                                                                                    Una volta per turno: puoi scegliere come bersaglio 1 mostro scoperto che controlli; esso guadagna 300 ATK
                                                                                                    per ogni carta "Artistamico" che controlli attualmente, fino alla fine di questo turno.
                                                                                                    Se questa carta viene Evocata Normalmente o Specialmente: puoi scegliere come bersaglio 1 mostro che controlli;
                                                                                                   esso guadagna 300 ATK per ogni mostro "Artistamico" che controlli attualmente. I mostri di Livello 5 o inferiore non possono attaccare.' );
INSERT INTO catalog VALUES(109,'Yu-Gi-Oh!','Oscurità',4,'Comune','Mostro','Artistamico Grinfiargento','[BESTIA/PENDULUM/EFFETTO]
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
                                                                                                    Quando Evochi Pendulum uno o più mostri: puoi scegliere come bersaglio 1 carta nella Zona Pendulum di qualsiasi giocatore; falla ritornare nella mano.
                                                                                                    Puoi utilizzare questo effetto di "Artistamico Trampolince" una sola volta per turno.
                                                                                                     Quando questa carta viene Evocata Normalmente: puoi scegliere come bersaglio 1 carta nella Zona Pendulum di qualsiasi giocatore;
                                                                                                    falla ritornare nella mano.');
INSERT INTO catalog VALUES(112,'Yu-Gi-Oh!','N/A',0,'Normale','Magia','Baratto','Scarta 1 mostro di Livello 8; pesca 2 carte.');
INSERT INTO catalog VALUES(113,'Yu-Gi-Oh!','N/A',0,'Normale','Magia','Carica della Brigata della Luce','Manda le prime 3 carte del tuo Deck al Cimitero; aggiungi 1 mostro "Fedele della Luce" di Livello 4
                                                                                                        o inferiore dal tuo Deck alla tua mano.');
INSERT INTO catalog VALUES(114,'Yu-Gi-Oh!','N/A',0,'Normale','Magia','Carte dal Cielo','Bandisci 1 mostro Fata LUCE dalla tua mano e, se lo fai, pesca 2 carte. Non puoi Evocare Specialmente o
                                                                                        effettuare la tua Battle Phase nel turno in cui attivi questa carta.');
INSERT INTO catalog VALUES(115,'Yu-Gi-Oh!','N/A',0,'Normale','Trappola','Cessate il Fuoco','Se sul Terreno c''è un mostro coperto in Posizione di Difesa o un Mostro con Effetto: metti scoperti in Posizione di Difesa tutti i mostri coperti in Posizione di Difesa sul Terreno (gli effetti dei mostri Scoperta non vengono attivati in questo caso),
                                                                                            inoltre infliggi al tuo avversario 500 danni per ogni Mostro con Effetto sul Terreno.');
INSERT INTO catalog VALUES(116,'Yu-Gi-Oh!','N/A',0,'Rapida','Magia','Circolo Salamagna','Attiva 1 di questi effetti.
                                                                                         Aggiungi 1 mostro "Salamagna" dal tuo Deck alla tua mano.
                                                                                        Scegli come bersaglio 1 Mostro Link "Salamagna" che controlli che è stato Evocato Link utilizzando un mostro con il suo stesso nome come materiale; quel Mostro Link è immune agli effetti dei mostri in questo turno, eccetto i suoi stessi.
                                                                                        Puoi attivare solo 1 "Circolo Salamagna" per turno.');
INSERT INTO catalog VALUES(117,'Yu-Gi-Oh!','Luce',4,'Comune','Mostro','Congegno d''Oro','[MACCHINA/EFFETTO]
                                                                                         Quando questa carta viene Evocata Normalmente o Specialmente: puoi Evocare Specialmente 1 mostro Macchina di Livello 4 dalla tua mano. Se questa carta viene distrutta in battaglia o dall''effetto di una carta:
                                                                                         puoi Evocare Specialmente 1 mostro "Congegno" di Livello 4 dal tuo Deck, eccetto "Congegno d''Oro". Puoi utilizzare solo 1 effetto di "Congegno d''Oro" per turno, e solo una volta in quel turno.');
INSERT INTO catalog VALUES(118,'Yu-Gi-Oh!','Fuoco',3,'Comune','Mostro','Crociatia Reclusia','[INCANTATORE/EFFETTO]
                                                                                            Puoi Evocare Specialmente questa carta (dalla tua mano) in Posizione di Difesa in una tua zona puntata da un Mostro Link. Puoi Evocare Specialmente "Crociatia Reclusia" una sola volta per turno in questo modo. Se questa carta viene Evocata Normalmente o Specialmente in una zona puntata da un Mostro Link: puoi scegliere come bersaglio 1 carta "Crociatia" che controlli e 1 carta controllata dal tuo avversario; distruggile.
                                                                                            Puoi utilizzare questo effetto di "Crociatia Reclusia" una sola volta per turno.');
INSERT INTO catalog VALUES(119,'Yu-Gi-Oh!','Luce',10,'Ultra Rara','Mostro','Drago Cyber Finale','[MACCHINA/FUSIONE/EFFETTO]
                                                                                           "Cyber Drago" + "Cyber Drago" + "Cyber Drago"
                                                                                            L''Evocazione tramite Fusione di questa carta può essere effettuata solo con i Materiali da Fusione sopra indicati.
                                                                                            Se questa carta attacca un mostro in Posizione di Difesa, infliggi danno da combattimento perforante.');
INSERT INTO catalog VALUES(120,'Yu-Gi-Oh!','Luce',8,'Rara','Mostro','Drago Cyber Gemello','[MACCHINA/FUSIONE/EFFETTO]
                                                                                            "Cyber Drago" + "Cyber Drago"
                                                                                            L''Evocazione tramite Fusione di questa carta può essere effettuata solo con i Materiali da Fusione sopra indicati.
                                                                                            Questa carta può effettuare un secondo attacco durante ogni Battle Phase');
INSERT INTO catalog VALUES(121,'Yu-Gi-Oh!','Luce',5,'Comune','Mostro','Cyber Drago','[MACCHINA/EFFETTO]
                                                                                    Se solo il tuo avversario controlla un mostro, puoi Evocare Specialmente questa carta (dalla tua mano).');
INSERT INTO catalog VALUES(122,'Yu-Gi-Oh!','Luce',2,'Comune','Mostro','Cyber Drago Nucleo','[MACCHINA/EFFETTO]
                                                                                            Quando questa carta viene Evocata Normalmente: aggiungi 1 Magia/Trappola "Cyber" o "Cibernetico" dal tuo Deck alla tua mano. Se solo il tuo avversario controlla un mostro: puoi bandire questa carta dal tuo Cimitero; Evoca Specialmente 1 mostro "Cyber Drago" dal tuo Deck. Puoi utilizzare 1 solo effetto di "Cyber Drago Nucleo" per turno, e solo una volta in quel turno.
                                                                                            Il nome di questa carta diventa "Cyber Drago" mentre è sul Terreno o nel Cimitero.');
INSERT INTO catalog VALUES(123,'Yu-Gi-Oh!','Vento',4,'Non Comune','Mostro','Cyber Lady Arpia','[BESTIA ALATA/EFFETTO]
                                                                                              (Il nome di questa carta è sempre considerato come "Lady Arpia".)');
INSERT INTO catalog VALUES(124,'Yu-Gi-Oh!','Luce',1,'Comune','Mostro','Dissimulatore di Effetto','[INCANTATORE/TUNER/EFFETTO]
                                                                                                  Durante la Main Phase del tuo avversario (Effetto Rapido): puoi mandare questa carta dalla tua mano al Cimitero, poi scegliere come bersaglio 1 Mostro con Effetto controllato dal tuo avversario;
                                                                                                  annulla gli effetti di quel mostro scoperto controllato dal tuo avversario, fino alla fine di questo turno.');
INSERT INTO catalog VALUES(125,'Yu-Gi-Oh!','N/A',0,'Contro Trappola','Trappola','Disturbo Magico','Quando viene attivata una Carta Magia: scarta 1 carta;
                                                                                                   annulla l''attivazione e, se lo fai, distruggila.');
INSERT INTO catalog VALUES(126,'Yu-Gi-Oh!','Luce',5,'Non Comune','Mostro','Disturbo Vento Solare','[MACCHINA/EFFETTO]
                                                                                                    Se non controlli nessun mostro, puoi Evocare Specialmente questa carta (dalla tua mano), ma i suoi ATK e DEF originali diventano la metà. Durante ogni tua Standby Phase: aumenta il Livello di questa carta di 1.
                                                                                                    Ci può essere solo 1 "Disturbo Vento Solare" sul Terreno.');
INSERT INTO catalog VALUES(127,'Yu-Gi-Oh!','Luce',4,'Non Comune','Mostro','Drago Antico','[DRAGO/EFFETTO]
                                                                                          Quando questa carta infligge danno da combattimento al tuo avversario con un attacco diretto:
                                                                                          puoi aumentare il Livello di questa carta di 1 e il suo ATK di 500.');
INSERT INTO catalog VALUES(128,'Yu-Gi-Oh!','Vento',8,'Rara','Mostro','Drago Polvere di Stelle','[DRAGO/SYNCHRO/EFFETTO]
                                                                                                1 Tuner + 1+ mostri non-Tuner
                                                                                                Quando viene attivata una carta o un effetto che sta per distruggere una o più carte sul Terreno (Effetto Rapido):
                                                                                                puoi offrire come Tributo questa carta; annulla l''attivazione e, se lo fai, distruggila. Durante la End Phase, se questo effetto è stato attivato in questo turno (e non è stato annullato): puoi Evocare Specialmente questa carta dal tuo Cimitero.');
INSERT INTO catalog VALUES(129,'Yu-Gi-Oh!','N/A',0,'Normale','Magia','Fusione Mondo Parallelo','Evoca tramite Fusione 1 Mostro Fusione "EROE Elementale" dal tuo Extra Deck, mischiando nel tuo Deck i tuoi Materiali da Fusione banditi indicati su di esso. Non puoi Evocare Specialmente mostri,
                                                                                                eccetto con l''effetto di questa carta, durante il turno in cui attivi questa carta.');
INSERT INTO catalog VALUES(130,'Yu-Gi-Oh!','Vento',4,'Comune','Mostro','Garuda lo Spirito del Vento','[BESTIA ALATA/EFFETTO]
                                                                                                      Questa carta non può essere Evocata Normalmente o Posizionata. Questa carta può essere Evocata solo Specialmente rimuovendo dal gioco 1 mostro VENTO nel tuo Cimitero.
                                                                                                      Durante l''End Phase del tuo avversario, puoi scegliere 1 mostro scoperto controllato dal tuo avversario, e cambiare la sua posizione.');
INSERT INTO catalog VALUES(131,'Yu-Gi-Oh!','N/A',0,'Normale','Magia','Giara dei Desideri','Bandisci 10 carte dalla cima del tuo Deck, coperte; pesca 2 carte. Puoi attivare solo 1 "Giara dei Desideri" per turno.');
INSERT INTO catalog VALUES(132,'Yu-Gi-Oh!','N/A',0,'Contro Trappola','Trappola','Giudizio Solenne','Quando uno o più mostri stanno per essere Evocati OPPURE viene attivata una Carta Magia/Trappola: paga la metà dei tuoi LP;
                                                                                                    annulla l''Evocazione oppure l''attivazione e, se lo fai, distruggi quella carta.');
INSERT INTO catalog VALUES(133,'Yu-Gi-Oh!','Terra',4,'Comune','Mostro','Guerriero Rocciapietra','[ROCCIA/EFFETTO]
                                                                                                  Non subisci danni da combattimento da battaglie in cui è coinvolta questa carta. Quando questa carta attaccante viene distrutta in battaglia e mandata al Cimitero, Evoca Specialmente 2 "Segna-Rocciapietra" (Tipo Roccia/TERRA/Livello 1/ATK 0/DEF 0).
                                                                                                  Questi Segna-Mostro non possono venire offerti come Tributo per un''Evocazione tramite Tributo.');
INSERT INTO catalog VALUES(134,'Yu-Gi-Oh!','Luce',4,'Comune','Mostro','Hecatrice','[FATA/EFFETTO]
                                                                                    Puoi scartare questa carta al Cimitero; aggiungi 1 "Valhalla, Sala dei Caduti" dal tuo Deck alla tua mano');
INSERT INTO catalog VALUES(135,'Yu-Gi-Oh!','Luce',9,'Ultra Rara','Mostro','Incrociatore Spazio Profondo IX','[MACCHINA/EFFETTO]
                                                                                                             Puoi Evocare Specialmente questa carta (dalla tua mano) mandando 1 altro mostro di Tipo Macchina dalla tua mano al Cimitero.');
INSERT INTO catalog VALUES(136,'Yu-Gi-Oh!','N/A',0,'Continua','Magia','La Fontana nel Cielo','Quando un mostro LUCE viene distrutto in battaglia e mandato al tuo Cimitero, puoi rimuovere quel mostro dal gioco per guadagnare Life Point pari al suo ATK');

INSERT INTO catalog VALUES(137,'Yu-Gi-Oh!','Luce',3,'Comune','Mostro','L''Agente della Forza - Venere','Questa carta è immune agli effetti delle Carte Magia. Mentre controlli "Il Santuario nel Cielo" scoperto, e i tuoi Life Point sono superiori a quelli del tuo avversario,
                                                                                                        questa carta guadagna ATK e DEF pari alla differenza tra i tuoi Life Point e quelli del tuo avversario.');
INSERT INTO catalog VALUES(138,'Yu-Gi-Oh!','N/A',0,'Normale','Magia','Libro di Magia dei Segreti','Aggiungi 1 carta "Libro di Magia" dal tuo Deck alla tua mano, eccetto "Libro di Magia dei Segreti".
                                                                      Puoi attivare solo 1 "Libro di Magia dei Segreti" per turno.');
INSERT INTO catalog VALUES(139,'Yu-Gi-Oh!','N/A',0,'Terreno','Magia','Mina Mistica','Se il tuo avversario controlla più mostri di te, il tuo avversario non può attivare effetti di mostri o dichiarare un attacco. Se tu controlli più mostri del tuo avversario, non puoi attivare effetti di mostri o dichiarare un attacco. Una volta per turno, durante la End Phase,
                                                                                     se entrambi i giocatori controllano lo stesso numero di mostri: distruggi questa carta.');
INSERT INTO catalog VALUES(140,'Yu-Gi-Oh!','N/A',0,'Normale','Magia','Nuovo Cast Artistamico','Rivela un qualsiasi numero di mostri "Artistamico" dalla tua mano e mischiali nel Deck, poi pesca carte pari al numero di carte che hai mischiato nel Deck +1.
                                                                                              Puoi attivare solo 1 "Nuovo Cast Artistamico" per turno.');
INSERT INTO catalog VALUES(141,'Yu-Gi-Oh!','N/A',0,'Normale','Trappola','Oh Segna-Mostro-baum!','Offri come Tributo 2 o più Segna-Mostro con lo stesso Livello, poi scegli come bersaglio un numero di mostri nel tuo Cimitero con quello stesso Livello, fino al massimo al numero di Segna-Mostro offerti come Tributo; Evoca Specialmente quei bersagli.
                                                                                                 I loro effetti sono annullati. Distruggili durante la End Phase.');
INSERT INTO catalog VALUES(142,'Yu-Gi-Oh!','Oscurità',10,'Ultra Rara','Mostro','Orichalcos Shunoros','[MACCHINA/EFFETTO]
                                                                                                      Questa carta non può essere Evocata Normalmente o Posizionata. Se un Mostro Normale che controlli viene distrutto in battaglia, puoi Evocare Specialmente questa carta dalla tua mano. Questa carta guadagna 1000 ATK per ogni mostro controllato dal tuo avversario.
                                                                                                      I Mostri Normali di Livello 4 scoperti sul Terreno non possono essere distrutti dagli effetti delle carte.');
INSERT INTO catalog VALUES(143,'Yu-Gi-Oh!','N/A',0,'Normale','Trappola','Potente Cessate il Fuoco','Scarta 1 carta dalla tua mano. Nessuna Carta Trappola può essere attivata fino alla End Phase di questo turno.');

INSERT INTO catalog VALUES(144,'Yu-Gi-Oh!','N/A',0,'Normale','Trappola','Prigione Dimensionale','Quando un mostro dell''avversario dichiara un attacco: scegli come bersaglio quel mostro attaccante; bandisci quel bersaglio.');

INSERT INTO catalog VALUES(145,'Yu-Gi-Oh!','Terra',3,'Comune','Mostro','Riciclatore di Frammenti','[MACCHINA/EFFETTO]
                                                                                                    Quando questa carta viene Evocata Normalmente o Specialmente, puoi scegliere 1 mostro di Tipo Macchina dal tuo Deck e mandarlo al Cimitero. Una volta per turno,
                                                                                                    puoi far ritornare 2 mostri TERRA di Tipo Macchina di Livello 4 dal tuo Cimitero al Deck per pescare 1 carta.');
INSERT INTO catalog VALUES(146,'Yu-Gi-Oh!','N/A',0,'Rapida','Magia','Rimozione di Limite','Raddoppia l''ATK di tutti i mostri Macchina che controlli attualmente, fino alla fine di questo turno.
                                                                                           Durante la End Phase di questo turno, distruggi quei mostri.');
INSERT INTO catalog VALUES(147,'Yu-Gi-Oh!','N/A',0,'Continua','Trappola','Risucchia Mente','Attiva questa carta pagando 1000 LP. Gli effetti dei mostri nella mano non possono essere attivati.');

INSERT INTO catalog VALUES(148,'Yu-Gi-Oh!','Luce',7,'Non Comune','Mostro','Sacerdotessa Alta della Profezia','[INCANTATORE/EFFETTO]
                                                                                                              Puoi rivelare 3 Magie "Libro di Magia" nella tua mano; Evoca Specialmente questa carta dalla tua mano. Una volta per turno: puoi bandire 1 Magia "Libro di Magia" dalla tua mano o Cimitero,
                                                                                                              poi scegliere come bersaglio 1 carta sul Terreno; distruggi quel bersaglio.');
INSERT INTO catalog VALUES(149,'Yu-Gi-Oh!','N/A',0,'Continua','Trappola','Scarica di Colpi','Una volta per turno, puoi staccare un qualsiasi numero di materiali dai Mostri Xyz Macchina che controlli, poi scegliere come bersaglio quel numero di carte sul Terreno; distruggile.
                                                                                             Se uno o più Mostri Xyz Macchina che controlli vengono distrutti in battaglia o dall''effetto di una carta dell''avversario mentre questa carta è nel tuo Cimitero: puoi bandire questa carta e 1 Mostro Xyz Macchina dal tuo Cimitero; infliggi al tuo avversario danno pari al Rango del mostro bandito x 200.');
INSERT INTO catalog VALUES(150,'Yu-Gi-Oh!','N/A',0,'Normale','Trappola','Scontro ad Armi Pari','Alla fine della Battle Phase, se il tuo avversario controlla più carte di te: puoi fare bandire al tuo avversario carte dal suo Terreno coperte in modo che controlli il tuo stesso numero di carte.
                                                                                                Se non controlli nessuna carta, puoi attivare questa carta dalla tua mano.');
INSERT INTO catalog VALUES(151,'Yu-Gi-Oh!','N/A',0,'Continua','Trappola','Seraphim Sintetico','Ogni volta che viene attivata una Carta Contro-Trappola, immediatamente dopo che si è risolta,
                                                                                               Evoca Specialmente 1 "Segna-Seraphim Sintetico" (Fata/LUCE/Livello 1/ATK 300/DEF 300).');
INSERT INTO catalog VALUES(152,'Yu-Gi-Oh!','Luce',4,'Comune','Mostro','Spada Serafino delle Stelle','[FATA/EFFETTO]
                                                                                                     Una volta per turno: puoi mandare 1 mostro "Serafino delle Stelle" dalla tua mano al Cimitero; questa carta guadagna ATK pari
                                                                                                     all''ATK originale del mostro mandato al Cimitero, fino alla End Phase.');
INSERT INTO catalog VALUES(153,'Yu-Gi-Oh!','N/A',0,'Normale','Magia','Terraformare','Aggiungi 1 Magia Terreno dal tuo Deck alla tua mano.');
INSERT INTO catalog VALUES(154,'Yu-Gi-Oh!','N/A',0,'Rapida','Magia','Turbini Gemelli','Scarta 1 carta, poi scegli come bersaglio fino a 2 Magie/Trappole sul Terreno; distruggile.');
INSERT INTO catalog VALUES(155,'Yu-Gi-Oh!','Luce',0,'Rara','Mostro','Venere Splendida','[FATA/EFFETTO]
                                                                                        Tutti i mostri non-Fata perdono 500 ATK/DEF.
                                                                                        L''attivazione e gli effetti delle Carte Magia/Trappola attivate sul tuo Terreno non possono essere annullate.');


INSERT INTO users VALUES('MRossi','Marco', 'Rossi', 'marcorossi@hotmail.it','motogp');
INSERT INTO users VALUES('StellaTheBest', 'Carlotta', 'Verde', 'carlottaverde@gmail.com', 'fiorellino');
INSERT INTO users VALUES('Aquaman', 'Luca', 'Prato', 'lucca1997@hotmail.com', 'Starwars');
INSERT INTO users VALUES('GretaMancini', 'Greta', 'Mancini', 'gretamancini@gmail.it', 'torredipisa');
INSERT INTO users VALUES('Fabiolina', 'Fabiana', 'Bianchi', 'fabiolina50@yahoo.it','natale12' );
INSERT INTO users VALUES('Lorenzo1995', 'Lorenzo', 'Remo', 'lorenzoremo@hotmail.com', 'ferrarirossa');
INSERT INTO users VALUES('AntoCere', 'Antonio', 'Cereali', 'antoniocere@gmail.it', 'biscottialcacao');
INSERT INTO users VALUES('SerenaBeuci', 'Serena', 'Beuci', 'serenabeuci@gmail.com', 'doctorwho');
INSERT INTO users VALUES('EmanueliStefano','Stefano', 'Emanueli', 'emanueli1994@hotmail.com', 'castelloverde');
INSERT INTO users VALUES('Fra1999','Francesca', 'Pellegrini', 'francescapellegrini@yahoo.com', 'piscinaolimpica22');

CREATE TABLE collections(
Username VARCHAR(20),
IDCard NUMERIC(6),
Quantity INTEGER NOT NULL,
PRIMARY KEY(Username, IDCard),
FOREIGN KEY (Username) REFERENCES Users(Username),
FOREIGN KEY (IDCard) REFERENCES Catalog(ID)
);

CREATE TABLE CardsExchanges(
ID NUMERIC(10) PRIMARY KEY,
Offerer VARCHAR(20) NOT NULL,
Receiver VARCHAR(20) NOT NULL,
Status NUMERIC(1) NOT NULL,       /* 0 se non ha ancora risposto, 1 se ha rifiutato, 2 se ha accettato */
CardOffered VARCHAR(1000) NOT NULL,
CardCounterOffered VARCHAR(1000) NOT NULL,
FOREIGN KEY (Offerer) REFERENCES Users(Username),
FOREIGN KEY (Receiver) REFERENCES Users(Username)
);


CREATE TABLE Exchanges(
ID NUMERIC(10) PRIMARY KEY,
Offerer VARCHAR(20) NOT NULL,
Receiver VARCHAR(20) NOT NULL,
FOREIGN KEY (Offerer) REFERENCES Users(Username),
FOREIGN KEY (Receiver) REFERENCES Users(Username)
);

CREATE TABLE CardExchange(
ID NUMERIC(10) NOT NULL,
IDOffer NUMERIC(6),
IDCounteroffer NUMERIC(6),
FOREIGN KEY (ID)  REFERENCES Exchanges(ID),
FOREIGN KEY (IDOffer) REFERENCES Catalog(ID),
FOREIGN KEY (IDCounteroffer) REFERENCES Catalog(ID)
);

INSERT INTO Exchanges VALUES(1,'MRossi', 'StellaTheBest', 0);
INSERT INTO CardExchange VALUES(1, 1, 2);
INSERT INTO CardExchange(ID, IDCounteroffer) VALUES(1,3);
/*
select e.ID, Offerer, Receiver, IDOffer, IDCounteroffer 
from Exchanges e join CardExchange ce on e.ID=ce.ID;
*/
/*CREATE TABLE Exchanges(
IDExchange NUMERIC(10) PRIMARY KEY,
Offerer VARCHAR(20),
Receiver VARCHAR(20),
Offer NUMERIC(6),
Counteroffer NUMERIC(6),
/*PRIMARY KEY(Offerer, Receiver, Offer, Counteroffer),
FOREIGN KEY (Offerer) REFERENCES Users(Username),
FOREIGN KEY (Receiver) REFERENCES Users(Username),
FOREIGN KEY (Offer) REFERENCES Catalog(ID),
FOREIGN KEY (Counteroffer) REFERENCES Catalog(ID)
);*/