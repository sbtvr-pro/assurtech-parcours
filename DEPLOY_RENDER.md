# Guide de déploiement sur Render

But: déployer les backends `quote-service` et `rule-engine-service` sur Render en utilisant les Dockerfiles multistage ajoutés.

Prérequis

- Compte GitHub connecté à Render
- Le repo `assurtech-parcours` poussé sur GitHub (branche `master`)

Étapes rapides

1. Dans Render, cliquez sur "New" → "Web Service" → choisissez GitHub et sélectionnez ce repo.
2. Render détectera `render.yaml` à la racine et appliquera la configuration. Vérifiez que les services `quote-service` et `rule-engine-service` sont listés.
3. Si votre application nécessite une base de données en production, créez un service "Postgres" via Render et notez les variables : `DATABASE_URL`, `DB_USER`, `DB_PASSWORD`, `DB_HOST`, `DB_PORT`.
4. Dans chaque service → Settings → Environment, ajoutez les variables d'environnement nécessaires :
   - `SPRING_PROFILES_ACTIVE=prod`
   - `SPRING_DATASOURCE_URL` (ex: `jdbc:postgresql://<host>:<port>/<db>`)
   - `SPRING_DATASOURCE_USERNAME` et `SPRING_DATASOURCE_PASSWORD`

Notes techniques

- Les nouveaux `Dockerfile` dans `quote-service` et `rule-engine-service` sont maintenant multistage : Render construira le jar puis l'image.

Recommandation

- Si vous avez besoin d'une base de données dédiée pour chaque service, créez les services DB via Render et connectez-les via les variables d'environnement.

Exemple rapide (Dockerfile multistage utilisé ici):

```dockerfile
FROM maven:3.10.1-jdk-21 AS build
WORKDIR /build
COPY quote-service ./quote-service
RUN mvn -f quote-service/pom.xml -DskipTests package

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /build/quote-service/target/quote-service-0.0.1-SNAPSHOT.jar app.jar
ENV SERVER_PORT=8082
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]
```

Le même principe est appliqué à `rule-engine-service` (port par défaut `8083`).

Après déploiement

- Vérifier les logs dans Render (Service → Logs).
- Vérifier l'endpoint public fourni par Render.
