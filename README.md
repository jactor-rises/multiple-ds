# multiple-ds & flyway
Test to setup multiple postgres datasources

* branch feature/one contains one datasource
* main branch contains two datasources, where one datasource is migrated with flyway

run `./gradlew clean build` to build the application

![Build Status](https://github.com/jactor-rises/multiple-ds/actions/workflows/build.yaml/badge.svg)

### Flyway
Flyway is used to migrate the database. The migration scripts are located in `src/main/resources/db/migrate` and are
executed when the application starts. In order to select which datasource to migrate, Flyway is configured manually.

See `InternalDatabaseConfig` for manually configuring of Flyway
