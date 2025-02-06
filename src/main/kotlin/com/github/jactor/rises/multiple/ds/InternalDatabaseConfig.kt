package com.github.jactor.rises.multiple.ds

import javax.sql.DataSource
import org.flywaydb.core.Flyway
import org.hibernate.jpa.HibernatePersistenceProvider
import org.hibernate.jpa.boot.spi.EntityManagerFactoryBuilder
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import com.github.jactor.rises.multiple.ds.bar.Bar
import com.github.jactor.rises.multiple.ds.foo.Foo
import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackageClasses = [Foo::class, Bar::class],
    entityManagerFactoryRef = "internalEntityManagerFactory",
    transactionManagerRef = "internalTransactionManager",
)
class InternalDatabaseConfig {
    val packageNameFoo: String = Foo::class.let {
        it.qualifiedName?.replace(".${it.simpleName}", "") ?: error("No package name for ${it.simpleName}")
    }.also { logger.info { ">>> Package Foo: $it" } }

    val packageNameBar: String = Bar::class.let {
        it.qualifiedName?.replace(".${it.simpleName}", "") ?: error("No package name for ${it.simpleName}")
    }.also { logger.info { ">>> Package Bar: $it" } }

    @Bean
    @Lazy
    fun internalEntityManagerFactory(
        @Qualifier("internalDataSource") dataSource: DataSource,

    ): LocalContainerEntityManagerFactoryBean {
        Flyway.configure()
            .dataSource(dataSource)
            .locations("classpath:db/migrate")
            .load()
            .migrate()
            .also { logger.info { ">>> Flyway is running migration on ${dataSource.connection.metaData.url}" } }

        return LocalContainerEntityManagerFactoryBean().apply {
            this.dataSource = dataSource
            this.persistenceProvider = HibernatePersistenceProvider()
            this.setPackagesToScan(packageNameFoo, packageNameBar)
        }.also {
            logger.info { ">>> internalEntityManagerFactory: ${it.dataSource?.connection?.metaData?.url}" }
        }
    }

    @Bean
    fun internalTransactionManager(
        @Qualifier("internalEntityManagerFactory") entityManagerFactory: LocalContainerEntityManagerFactoryBean,
    ): PlatformTransactionManager = JpaTransactionManager(
        entityManagerFactory.getObject() ?: error("No internal EntityManager"),
    ).also {
        logger.info { ">>> internalTransactionManager: ${it.entityManagerFactory?.properties?.keys}" }
    }
}
