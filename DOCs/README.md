# TOOLs
https://medium.com/developers-stacks/microservice-application-with-service-discovery-using-jhipster-8c18c99d6cc5
https://www.youtube.com/watch?v=I2czvj3V1AQ
https://docs.microsoft.com/en-us/dotnet/architecture/microservices/multi-container-microservice-net-applications/integration-event-based-microservice-communications

Informazioni sull'orchestrazione di microservizi mediante Kubernetes
I microservizi in esecuzione nei contenitori devono essere in grado di interagire e integrare per fornire le funzionalità di applicazione richieste. Questa integrazione può essere eseguita mediante un'orchestrazione contenitore.

L'orchestrazione contenitore consente di avviare, arrestare e raggruppare i contenitori in cluster. Abilita inoltre l'alta disponibilità e la scala. Kubernetes è una delle piattaforme di orchestrazione contenitore che è possibile utilizzare per gestire i contenitori.

Dopo aver containerizzato i microservizi, è possibile distribuirli in Oracle Cloud Infrastructure Container Engine for Kubernetes.

Prima di distribuire l'applicazione dei microservizi containerizzati nel cloud, è necessario distribuirla e sottoporla a test in un motore Kubernetes locale, come descritto di seguito.

Creare l'applicazione dei microservizi.
Creare immagini di Docker per gestire in container i microservizi.
Eseguire i microservizi nel motore locale di Docker.
Eseguire il PUSH delle immagini del contenitore in un registro contenitore.
Distribuire ed eseguire i microservizi in un modulo di gestione Kubernetes locale, ad esempio Minikube.
Dopo aver eseguito il test dell'applicazione in un motore Kubernetes locale, distribuirla in Oracle Cloud Infrastructure Container Engine for Kubernetes nel modo seguente:

Creare un cluster.
Scaricare il file kubeconfig.
Installare lo strumento kubectl su un dispositivo locale.
Preparare il file deployment.yaml.
Distribuire il microservizio al cluster.
Eseguire il test del microservizio.

Implementazione della comunicazione basata su eventi tra microservizi (eventi di integrazione)
Gli eventi di integrazione vengono utilizzati per sincronizzare lo stato del dominio tra più microservizi o sistemi esterni. Questa funzionalità viene eseguita pubblicando eventi di integrazione all'esterno del microservizio. Quando un evento viene pubblicato in più microservizi ricevitore (a tutti i microservizi sottoscritti all'evento di integrazione), il gestore eventi appropriato in ogni microservizio ricevitore gestisce l'evento.

Un evento di integrazione è fondamentalmente una classe di partecipazione dati, come nell'esempio seguente:

public class ProductPriceChangedIntegrationEvent : IntegrationEvent
{
public int ProductId { get; private set; }
public decimal NewPrice { get; private set; }
public decimal OldPrice { get; private set; }

    public ProductPriceChangedIntegrationEvent(int productId, decimal newPrice,
        decimal oldPrice)
    {
        ProductId = productId;
        NewPrice = newPrice;
        OldPrice = oldPrice;
    }
}

Gli eventi di integrazione possono essere definiti a livello di applicazione di ogni microservizio,
in modo che siano disaccoppiati da altri microservizi, in modo paragonabile al modo in cui i ViewModel
vengono definiti nel server e nel client. Ciò che non è consigliabile è condividere una libreria di eventi
di integrazione comune tra più microservizi; ciò significherebbe associare quei microservizi a una singola
libreria di dati per la definizione di eventi. Non si desidera farlo per gli stessi motivi per cui non si
desidera condividere un modello di dominio comune tra più microservizi: i microservizi devono essere
completamente autonomi.

Un bus eventi consente la comunicazione in stile pubblicazione/sottoscrizione tra microservizi senza
richiedere che i componenti siano esplicitamente consapevoli l'uno dell'altro
il microservizio A pubblica su Event Bus, che distribuisce ai microservizi di sottoscrizione B e C,
senza che l'editore edi di conoscere i sottoscrittori. Il bus eventi è correlato al modello Observer e
al modello publish-subscribe.

Modello di osservatore
Nel modello Observer, l'oggetto primario (noto come Observable) notifica ad altri oggetti interessati
(noti come Osservatori) informazioni rilevanti (eventi).

Modello Pubblica/Sottoscrivi (Pub/Sub)
Lo scopo del modello Pubblica/Sottoscrivi è lo stesso del modello Observer:
si desidera notificare ad altri servizi quando si svolgono determinati eventi.
Ma c'è un'importante differenza tra i modelli Observer e Pub / Sub. Nel modello di osservatore,
la trasmissione viene eseguita direttamente dall'osservabile agli osservatori, in modo che si "conoscano".
Ma quando si utilizza un modello Pub /Sub, esiste un terzo componente, chiamato broker,
o broker di messaggi o bus di eventi, noto sia dall'editore che dal sottoscrittore.
Pertanto, quando si utilizza il modello Pub /Sub, l'editore e i sottoscrittori vengono disaccoppiati
con precisione grazie al bus eventi o al broker di messaggi menzionato.

È bene definire il bus eventi tramite un'interfaccia in modo che possa essere implementato con diverse tecnologie,
come il bus RabbitMQ o Azure Service o altri.

Iniziamo con un codice di implementazione per l'interfaccia del bus eventi e possibili implementazioni a scopo
di esplorazione. L'interfaccia dovrebbe essere generica e semplice, come nella seguente interfaccia.

Il bus di servizio di Microsoft Azure gestisce i messaggi.
I messaggi trasportano un payload e metadati.
I metadati sono sotto forma di proprietà della coppia chiave-valore e descrivono il payload e fornisce istruzioni di
gestione al bus di servizio e alle applicazioni. In alcuni casi i soli metadati sono sufficienti per includere
le informazioni che il mittente vuole comunicare ai ricevitori e il payload rimane vuoto.

Le proprietà del broker sono predefinite dal sistema.
Queste proprietà predefinite controllano le funzionalità a livello di messaggio all'interno del broker oppure sono
mappate a elementi comuni e standardizzati dei metadati. Le proprietà utente sono una raccolta di coppie chiave-valore
che possono essere definite e configurate dall'applicazione.

Routing e correlazione dei messaggi
Semplice richiesta/risposta
Richiesta/Risposta multicast: come variazione rispetto al modello precedente, un'entità di pubblicazione invia il messaggio in un argomento e più sottoscrittori risultano idonei all'utilizzo del messaggio. Ogni sottoscrittore può rispondere come illustrato in precedenza. Questo modello viene usato in scenari di individuazione o di verifica di presenze e l'intervistato si identifica in genere con una proprietà utente o all'interno del payload. Se ReplyTo fa riferimento a un argomento, tale set di risposte di individuazione può essere distribuito a un gruppo di destinatari.

Serializzazione del payload
Durante il transito o l'archiviazione nel bus di servizio, il payload è sempre un blocco binario opaco. La proprietà consente alle applicazioni di descrivere il payload, con il formato suggerito per i valori della proprietà come descrizione del tipo di contenuto MIME in base ContentType a IETF RFC2045, ad esempio application/json;charset=utf-8 .

pubblicazione e sottoscrizione Pub/Sub
Ogni messaggio pubblicato viene reso disponibile per ogni sottoscrizione registrata con l'argomento. Il server di pubblicazione invia un messaggio a un argomento e uno o più Sottoscrittori ricevono una copia del messaggio, a seconda delle regole di filtro impostate per queste sottoscrizioni. Per limitare i messaggi da ricevere, le sottoscrizioni possono usare filtri aggiuntivi. Gli autori inviano messaggi a un argomento nello stesso modo in cui inviano messaggi a una coda. Tuttavia, gli utenti non ricevono messaggi direttamente dall'argomento. I consumer ricevono invece messaggi dalle sottoscrizioni dell'argomento. La sottoscrizione di un argomento è simile a una coda virtuale che riceve copie dei messaggi inviati all'argomento. I consumer ricevono messaggi da una sottoscrizione in modo identico al modo in cui ricevono messaggi da una coda.
La funzionalità di invio di messaggi di una coda viene mappata direttamente a un argomento e la funzionalità di ricezione dei messaggi viene mappata a una sottoscrizione. Questa funzione implica anche che le sottoscrizioni supportano gli stessi modelli descritti prima in questa sezione in merito alle code: consumer concorrente, disaccoppiamento temporale, livellamento del carico e bilanciamento del carico.

usa le funzionalità di messaggistica del bus di servizio di Azure (code e argomenti di pubblicazione/sottoscrizione) delle applicazioni Java usando il popolare standard API JMS (Java Message Service) con AMQP 1.0.
Advanced Message Queuing Protocol (AMQP) 1.0 è un protocollo di messaggistica efficiente, affidabile e a livello di filo che è possibile utilizzare per creare applicazioni di messaggistica robuste e multipiattaforma.
Il supporto per AMQP 1.0 nel bus di servizio di Azure significa che è possibile usare le funzionalità di messaggistica mediata di accodamento e pubblicazione/sottoscrizione da una vasta gamma di piattaforme utilizzando un protocollo binario efficiente. Inoltre, è possibile creare applicazioni composte da componenti costruiti utilizzando un mix di linguaggi, framework e sistemi operativi.



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
