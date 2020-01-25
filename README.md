# Progetto-F19
Piattaforma che consente agli utenti che si registrano ad essa di effettuare scambi di carte online. Gli utenti possono avere una propria collezione delle carte nel loro profilo, in cui possono anche consultare quelli degli altri utenti. Inoltre possono creare le proprie trattative scegliendo le carte che vogliono offrire e quelle con cui sono disposti a scambiare.

## Set up del sistema
### Requisiti
*	Ambiente di sviluppo Java
*	MySQL Workbench
*	Java jdk versione 12.0.1
*	mysql-connector-java-8.0.16
*	Tomcat versione 8.5.42
## Come avviare il programma

Scaricare e installare nel proprio dispositivo il web server open source Tomcat.
Realizzare un clone del progetto F19 da GitHub a IntelliJIDEA.
Per avviare il programma è necessario prima di tutto avere il database a disposizione e funzionante. Per evitare di dover modificare parametri all'interno del codice,prima di tutto settare il server del db con username:"root" , password:"abcd" e numero di porta:3306, poi creare nuova connessione al server con le credenziali sopra indicate,creare un nuovo schema e chiamarlo "cardwebapplication".Inserire come script iniziale quello scritto nel file “progetto.sql” del package “database” che si trova all’interno del progetto F19. 

Una volta creato il database, ritornare sul progetto F19, e configurare il web server tomcat.
Cliccare sul comando “Run” in alto a sinistra e poi di nuovo sul comando “Edit Configuration”. Si aprirà  una finestra, cliccare sul comando”+” e scegliere “tomcat server” e poi “Local”. A questo punto si dovrà cliccare il pulsante ”Configure” ,che aprirà una finestra nella quale si dovrà inserire il percorso della directory in cui si trova la web server.
Una volta che si è in possesso di tutti i requisiti del sistema è possibile avviare il programma. In una futura versione online può essere eseguito su uno stesso dispositivo o su più dispositivi.
