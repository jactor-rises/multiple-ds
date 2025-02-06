package com.github.jactor.rises.multiple.ds

import javax.sql.DataSource
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import io.github.oshai.kotlinlogging.KotlinLogging
import io.zonky.test.db.postgres.embedded.EmbeddedPostgres

private val logger = KotlinLogging.logger {}

@Configuration
class EmbeddedPostgresConfig {

    @Bean
    @Primary
    @Qualifier("internalDataSource")
    fun internalEmbeddableDataSource(): DataSource = EmbeddedPostgres
        .builder()
        .setConnectConfig("user", "postgres")
        .start()
        .postgresDatabase.also { logger.info { ">>> internalEmbeddableDataSource: ${it.connection.metaData.url}" } }
}
