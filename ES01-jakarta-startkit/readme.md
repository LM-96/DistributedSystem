# Sistemi Distribuiti M - Esercitazione 1

_Start Kit - JavaEE 9 (Jakarta)_

## Descrizione

Aggiornamento del codice dello Start Kit dell'esercitazione 1 del corso di Sistemi Distribuiti.
Il codice originale si trova nel repository Git del corso ed il link è reperibile nelle slide.

Il codice originale è stato aggiornato in modo da funzionare con [JEE9](https://it.wikipedia.org/wiki/Jakarta_EE) (Jakarta EE) in modo che tutto funzioni con le utile versioni dei software richiesti e di *JBoss*.

## Software Necessari
- **[Java 11](https://www.oracle.com/it/java/technologies/javase/jdk11-archive-downloads.html)**
- **[Gradle](https://gradle.org/install/)**
- **[Eclipse IDE for Enterprise Java and Web Developers](https://www.eclipse.org/downloads/download.php?file=/oomph/epp/2021-12/R/eclipse-inst-jre-win64.exe)**
- **[Jboss with JEE9 (WildFly)](https://github.com/wildfly/wildfly/releases/download/26.0.1.Final/wildfly-preview-26.0.1.Final.zip)**
- **[MySQL Community Server](https://dev.mysql.com/downloads/mysql/)**

N.B: si possono utilizzare anche altri IDE (es. *Intellij*, *NetBeans*), tuttavia si potrebbe non avere supporto per le *JSP*.

## Guida all'avvio dello Start Kit
**1. Installare Java**

Scaricare ed installare Java 11 ed [impostarla come default](https://www.java.com/it/download/help/path.html).

**2. Installare Gradle**

Scaricare ed installare l'ultima versione di Gradle.
Una volta aperto il link per il download, premere su [Installing manually](https://gradle.org/install/#manually) e seguire la guida.

**3. Installare Eclipse**

Scaricare l'installer di eclipse dal [link](https://www.eclipse.org/downloads/download.php?file=/oomph/epp/2021-12/R/eclipse-inst-jre-win64.exe) ed avviarlo. Scegliere la versione *Eclipse IDE for Enterprise Java and Web Developers*.
In alternativa è possibile scaricare direttamente lo zip della versione per sviluppatori enterprise direttamente a questo [link](https://www.eclipse.org/downloads/packages/) e poi scompattarla per usare Eclipse senza installarlo.
*Non occorre aggiungere il plugin Gradle: è già incluso!*

**4. Scaricare e configurare WildFly con JEE9**

Scaricare e decomprimere WildFly in `C:\Wildfly` o in un percorso a scelta e modificare la variabile `wildfly_home` presente nel file `build.gradle` di conseguenza.
Per avviare o configurare WildFly è sufficente andare nella cartella `C:\Wildfly\bin` ed avviare un terminale:
- digitare `add-user.bat` (Windows) o `add-user.sh` (Linux/Mac) e seguire le istruzioni per aggiungere un eventuale utente con ruolo di *Manager* in modo da poter gestire *WildFly* da console;
- digitare `standalone.bat` (Windows) o `standalone.sh` (Linux/Mac) per avviare *WildFly*.

**5. Installare MySql Community Edition**

Scaricare ed eseguire l'installer di MySql Community Edition seguendo le istruzioni ed installando *MySQL Server* come servizio di sistema con avvio automatico per evitare di dover avviare manualmente.

**6. Importazione dello Start Kit in Eclipse**

Scaricare ed installare Eclipse per sviluppatori JEE.
Aprire l'IDE e premere `File -> Import -> Gradle -> Existing Gradle Project`: il progetto verrà importato in Eclipse (si possono tranquillamente ignorare eventuali errori `'Auto share git project'`).

In caso di errori durante il *build* di Eclipse, fare tasto destro sul progetto, `Properties -> Builders` è controllare che le uniche voci selezionate siano:
- [x] Java Builder
- [x] Faceted Project Validation Builder
- [x] Validation
- [x] Gradle Project Builder

Se persistono errori sul *CDI*, rimuovere la spunta in `Properties -> CDI Settings -> CDI support`.

** Ricordarsi di aggiornare i file `src/main/application/META-INF/persistence.xml` e `src/main/resources/hibernate.cfg.xml` con URL, username e password corretti in relazione alle impostazioni di MySQL Server **

Il kit è configurato in modo tale che le tabelle siano automaticamente generate nel database *test*: se si verificano errori di tipo `database 'test' not found` creare manualmente il database (aprire una shell SQL e dare il comando `create database test`). In alternativa utilizzare software di utilità per database come [DBeaver](https://dbeaver.io/) per gestire il DBMS.

**7. Deploy e avvio dell'applicazione**

Se *WildFly* è stato correttamente avviato, allora è possibile accere alla console di gestione all'url:

<div align="center"> http://127.0.0.1:9990/console/index.html </div>

Se è anche stato configurato un superutente, allora si possono inserire *username* e *password* e gestire *WildFly* tramite interfaccia grafica. Le applicazioni che sono deployate sul server sono accessibili nel tab `Deployments`.

Per fare il deployment dello start kit si può procedere in due modi:
1. utilizzando direttamente Gradle installato nel PC
    - aprire un terminale nella cartella dello start kit
    - dare il comando `gradle build` e poi `gradle deployEar`
2. utilizzando la vista *Gradle* di Eclipse
	- da Eclipse premere su `Window -> Show View -> Other -> Gradle -> Gradle Task`
	- dai task di Gradle sul pannello a destra espandere il progetto dello start kit, poi espandere `build`, tasto destro su `build -> Run Gradle Tasks`
	- ripetere con il task `other -> deployEar`

*N.B.: L'operazione di deploy può impiegare qualche secondo per partire.*

Se il deploy è andato a buon fine, nella cartella`C:\Windfly\standalone\deployments` saranno presenti i file `distributed-systems-demo.ear` e `distributed-systems-demo.ear.deployed`; inoltre, nella schermata *Deployments* di *WildFly* sarà presente una entry con il nome dello start kit e lo stato corrente (inclusi eventuali errori reperibili nei log e nel terminale del server).

*N.B.: C'è la possibilità che compaiano delle eccezioni SQL su degli `alter table` o `already exists`: queste eccezioni possono essere ignorate e sono dovute a precedenti esecuzioni e al fatto che lo start kit è impostato in modo tale da creare automaticamente le cartelle nel DB.
Si veda la proprietà

```xml
<property name="hibernate.hbm2ddl.auto" value="create"/>```
presente nel file `src/main/application/META-INF/persistence.xml`, moficandola o rimuovendola nel caso si voglia gestire manualmente la creazione del database.

**8. Deploy sincronizzato con Eclipse**

In alternativa al punto precedente, è possibile utilizzare direttamente Eclipse per fare deploy automatico e sincronizzato dell'applicazione, agganciando *Wildfly* ad Eclipse, procedendo seguendo [questa guida](https://www.baeldung.com/eclipse-wildfly-configuration).

