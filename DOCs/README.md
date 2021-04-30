# TOOLs
https://medium.com/developers-stacks/microservice-application-with-service-discovery-using-jhipster-8c18c99d6cc5
https://www.youtube.com/watch?v=I2czvj3V1AQ

## PLANT UML
docker run -d -p 8079:8080 plantuml/plantuml-server:jetty

> mvn verify -Pdev jib:dockerBuild

# Microservice Patterns
https://microservice-api-patterns.org/

# Configurazione di base

La configurazione di base è composta da:
- Un server JHipster UAA (server di autenticazione as a code)
- Almeno un altro microservizio (utilizzando l'autenticazione UAA)
- Un gateway JHipster (utilizzando l'autenticazione UAA)

## UAA
Poiché il servizio UAA generato non ha un'interfaccia utente
è necessario utilizzare chiamate API dirette per verificarne il funzionamento.

Ci sono due funzionalità che dobbiamo assicurarci che funzionino:
Generazione di token OAuth2	 
$ curl -X POST --data "username=user&password=user&grant_type=password&scope=openid" http://web_app:changeit@localhost:9999/oauth/token

Ora possiamo utilizzare access_token restituito per ottenere informazioni per l'account associato utilizzando la  risorsa account, disponibile nel servizio UAA:
$ curl -H "Authorization: Bearer eyJh...(access token omitted)" http://localhost:9999/api/account

Per impostazione predefinita, il servizio UAA emette token validi per cinque minuti, che è un valore ragionevole per la produzione.
È possibile modificare facilmente la durata dei token validi modificando il file application-<profile>.yml corrispondente al profilo in cui stiamo eseguendo l'app e impostando la chiave uaa.web-client-configuration.access-token-validity-in-seconds. I file delle impostazioni risiedono nella directory src/main/resources/config del nostro progetto UAA.

## GATEWAY
Il gateway è il servizio che negozierà con UAA per l'autenticazione.
Ospiterà la nostra applicazione front-end e instrada le richieste API ad altri microservizi.

Il gateway ha inviato le nostre credenziali all'endpoint token OAuth2 di UAA, che le ha convalidate e ha generato una risposta contenente un token JWT di accesso e aggiornamento. Il gateway ha quindi preso quei token e li ha rispediti al browser come cookie.

Successivamente, il front-end angolare ha chiamato l'API /uaa/api/account, che ancora una volta il gateway ha inoltrato a UAA. In questo processo, il gateway accetta il cookie contenente il token di accesso e utilizza il relativo valore per aggiungere un'intestazione di autorizzazione alla richiesta.


La necessità di integrazione: una storia infinita
Indipendentemente dall'azienda in cui lavori, indipendentemente da quando è stata fondata la tua azienda, avrai il requisito di integrare le tue applicazioni tra loro per implementare i tuoi processi aziendali.

Ciò include molti fattori diversi:

Tecnologie (standard come SOAP, REST, JMS, MQTT, formati di dati come JSON, XML, Apache Avro o Protocol Buffers, framework aperti come Nginx o Kubernetes e interfacce proprietarie come EDIFACT o SAP BAPI)
Linguaggi e piattaforme di programmazione come Cobol, Java, .NET, Go o Python
Architetture di applicazioni come Monolith, Client Server, Service-oriented Architecture (SOA), Microservices o Serverless
Paradigmi di comunicazione come l'elaborazione batch, (vicino) in tempo reale, la richiesta-risposta, il fire-and-forget, la pubblicazione di abbonarsi, le query continue e il riavvolgimento
REF --> Architettura-Spaghetti.png

Ogni azienda ha bisogno di risolvere queste architetture di spaghetti. A seconda del decennio, hai acquistato qualcosa come uno strumento ETL per costruire pipeline batch o un ESB per progettare un SOA. Alcuni prodotti hanno anche cambiato nome. Oggi, ti vengono offerti cose come la messaggistica middleware, una piattaforma di integrazione, un gateway di microservizi o la gestione delle API. Il marchio e il nome del prodotto non contano. Vedi sempre la stessa immagine di una soluzione per passare dalla tua architettura di spaghetti a una scatola integrale centrale al centro, come questa:
REF --> Architettura-ESB.png

Per monoliti e gateway, JHipster genera diversi dashboard per monitorare ogni applicazione. Questi dashboard sono disponibili in fase di esecuzione e sono il modo più semplice per eseguire un monitoraggio.
Dashboard metriche
Il dashboard delle metriche utilizza Micrometro per fornire una visualizzazione dettagliata delle prestazioni dell'applicazione.

Fornisce metriche su:

JVM
Richieste HTTP
utilizzo della cache
pool di connessioni database
Facendo clic sul pulsante Espandi accanto alle metriche del thread JVM, si oserà un dump del thread dell'applicazione in esecuzione, che è molto utile per scoprire i thread bloccati.

Fornisce principalmente gli stessi dashboard di monitoraggio della sezione precedente, ma funziona su un server separato. Pertanto, è un po 'più complesso da configurare, ma si consiglia vivamente di avere dashboard in esecuzione al di fuori dell'applicazione in esecuzione: altrimenti, non saranno disponibili quando si verifica un errore dell'applicazione.
https://www.jhipster.tech/monitoring/#elk

Pila ELK (Elasticsearch, Logstash, Kibana)
Lo stack ELK viene spesso utilizzato per l'aggregazione e la ricerca dei log, è costituito dai componenti seguenti:

Ricerca elastica per l'indicizzazione dei dati (registri e metriche)
Logstash per gestire ed elaborare i log ricevuti dalle applicazioni
Kibana per visualizzare i registri con una bella interfaccia

Inoltrare metriche a un sistema di monitoraggio di terze parti supportato (JMX, Prometheus)

Zipkin
Le applicazioni JHipster possono integrarsi con Zipkin tramite Spring Cloud Sleuth per fornire la traccia distribuita per l'architettura dei microservizi. Per abilitare la traccia Zipkin, impacca l'applicazione con il profilo maven/gradle e imposta la proprietà su true. In questo modo verrà attivato il report span al server Zipkin e verranno inoltre aggiunta ID di correlazione (TraceId, SpanId e ParentId) per richiedere intestazioni e registri.zipkinspring.zipkin.enabled

Zipkin fornisce anche una funzionalità del grafico delle dipendenze del servizio che consente di visualizzare le dipendenze tra microservizi nel tempo.

## Microservice Supplier
Creeremo un microservizio "supplier", che esporrà un'API REST completa che ci consente di:
creare, interrogare, modificare ed eliminare [CRUD] un set di supplier.

La supplier entity conterrà le seguenti proprietà:
