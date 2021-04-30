# DIAGRAM C4-Model
Buoni diagrammi di architettura software aiutano con la comunicazione
(sia all'interno che all'esterno del team di sviluppo/prodotto del software),
l'onboarding di nuovo personale, l'identificazione dei rischi (ad esempio la tempesta di rischi),
la modellazione delle minacce (ad esempio con STRIDE eLINDDUN),ecc.
Buoni diagrammi di architettura software aiutano ad allineare la comprensione di tutti del software
in fase di costruzione, contribuendo quindi a rendere il team più efficiente.

## Livello 1:
un diagramma contesto di sistema fornisce un punto di partenza,mostrando
come il sistema software nell'ambito si inserisce nel mondo che lo circonda.

## Livello 2:
un diagramma contenitore ingrandisce il sistema software nell'ambito,
mostrando gli elementi tecnici di alto livello.

## Livello 3:
un diagramma componente ingrandisce un singolo contenitore,
mostrando i componenti al suo interno.

## Livello 4:
Un diagramma di codice (ad esempio classe UML) può essere usato per ingrandire
un singolo componente, mostrando come quel componente viene implementato.

Non Docker! Nel modello C4, un contenitore rappresenta un'applicazione o un archivio dati. Un contenitore è qualcosa che deve essere in esecuzione affinché il sistema software complessivo funzioni. In termini reali, un contenitore è qualcosa come:

Applicazione Web sul lato server:un'applicazione Web Java EE in esecuzione su Apache Tomcat, un'applicazione MVC ASP.NET in esecuzione su Microsoft IIS, un'applicazione Ruby on Rails in esecuzione su WEBrick, un'applicazione Node.js e così via.
Applicazione Web sul lato client:un'applicazione JavaScript in esecuzione in un browser Web che utilizza Angular, Backbone.JS, jQuery e così via.
Applicazione desktop lato client:un'applicazione desktop Windows scritta utilizzando WPF, un'applicazione desktop OS X scritta utilizzando Objective-C, un'applicazione desktop multipiattaforma scritta con JavaFX e così via.
App per dispositivimobili: un'app Apple iOS, un'app Android, un'app di Microsoft Windows Phone e così via.
Applicazione console lato server:un'applicazione autonoma (ad esempio "public static void main") , un processo batch, ecc.
Funzione senza server:una singola funzione senza server (ad esempio Amazon Lambda, Funzione Azure, ecc.).
Database:schema o database in un sistema di gestione di database relazionali, archivio documenti, database grafico e così via, ad esempio MySQL, Microsoft SQL Server, Oracle Database, MongoDB, Riak, Cassandra, Neo4j e così via.
Blob o archivio di contenuti:un archivio BLOB (ad esempio Amazon S3, Microsoft Azure Blob Storage e così via) o una rete di distribuzione dei contenuti (ad esempio Akamai, Amazon CloudFront e così via).
File system:un file system locale completo o una parte di un file system in rete più grande (ad esempio SAN, NAS, ecc.).
Script shell:un singolo script shell scritto in Bash, ecc.
and so on
Un contenitore è essenzialmente un contesto o un limite all'interno del quale viene eseguito codice o alcuni dati vengono archiviati. E ogni contenitore è una cosa distribuibile /eseguibile separatamente o un ambiente di runtime, in genere (ma non sempre) in esecuzione nel proprio spazio di processo. Per questo motivo, la comunicazione tra contenitori assume in genere la forma di una comunicazione tra processi.

componente
La parola "componente" è un termine enormemente sovraccarico nel settore dello sviluppo software, ma in questo contesto un componente è un raggruppamento di funzionalità correlate incapsulate dietro un'interfaccia ben definita. Se usi un linguaggio come Java o C#, il modo più semplice per pensare a un componente è che si tratta di una raccolta di classi di implementazione dietro un'interfaccia. Aspetti come il modo in cui tali componenti sono confezionati (ad esempio un componente rispetto a molti componenti per file JAR, DLL, libreria condivisa e così via) sono una preoccupazione separata e ortogonale.

Un punto importante da notare qui è che tutti i componenti all'interno di un contenitore in genere vengono eseguiti nello stesso spazio di processo. Nel modello C4, i componenti non sono unità distribuibili separatamente.

System Landscape diagram
Il modello C4 offre una visione statica di un singolo sistema software ma, nel mondo reale, i sistemi software non vivono mai in isolamento. Per questo motivo, e in particolare se sei responsabile di una raccolta di sistemi software, è spesso utile capire come tutti questi sistemi software si adattano insieme entro i limiti di un'azienda. Per fare ciò, è sufficiente aggiungere un altro diagramma che si trova "in cima" ai diagrammi C4, per mostrare il panorama del sistema dal punto di vista IT. Come il diagramma Contesto di sistema, questo diagramma può mostrare il confine organizzativo, gli utenti interni/esterni e i sistemi interni/esterni.

Essenzialmente questa è una mappa di alto livello dei sistemi software a livello aziendale, con un drill-down C4 per ogni sistema software di interesse. Da un punto di vista pratico, un diagramma del panorama del sistema è in realtà solo un diagramma del contesto di sistema senza un focus specifico su un particolare sistema software.

