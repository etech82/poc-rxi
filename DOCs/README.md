# TOOLs
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

## Microservice Supplier
Creeremo un microservizio "supplier", che esporrà un'API REST completa che ci consente di:
creare, interrogare, modificare ed eliminare [CRUD] un set di supplier.

La supplier entity conterrà le seguenti proprietà:
