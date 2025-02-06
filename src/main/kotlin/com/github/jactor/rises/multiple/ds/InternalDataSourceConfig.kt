package com.github.jactor.rises.multiple.ds

import javax.sql.DataSource
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

@Configuration
class InternalDataSourceConfig {

    @Bean
    @ConfigurationProperties("spring.datasource")
    fun internalDataSourceProperties() = DataSourceProperties()

    @Bean
    fun internalDataSource(): DataSource = internalDataSourceProperties().also { log(it) }
        .initializeDataSourceBuilder()
        .build().also { logger.info { ">>> internalDataSource: ${it.connection.metaData.url}" } }

    private fun log(dataSourceProperties: DataSourceProperties) {
        logger.info { ">>> dataSourceProperties: ${dataSourceProperties.url}" }
    }
}
