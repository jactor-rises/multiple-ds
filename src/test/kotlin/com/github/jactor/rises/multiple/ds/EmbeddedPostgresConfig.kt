package com.github.jactor.rises.multiple.ds

import javax.sql.DataSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import io.zonky.test.db.postgres.embedded.EmbeddedPostgres

@Configuration
class EmbeddedPostgresConfig {

    @Bean
    fun dataSource(): DataSource {
        return EmbeddedPostgres
            .builder()
            .setConnectConfig("user", "postgres")
            .start()
            .postgresDatabase
    }
}
