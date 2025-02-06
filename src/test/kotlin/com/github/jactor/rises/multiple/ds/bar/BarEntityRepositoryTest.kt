package com.github.jactor.rises.multiple.ds.bar

import java.util.UUID
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import assertk.assertAll
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotEqualTo
import com.github.jactor.rises.multiple.ds.util.flushAndClearCache
import jakarta.persistence.EntityManager
import jakarta.transaction.Transactional
import org.springframework.boot.test.context.SpringBootTest
import assertk.assertions.isNotNull
import assertk.assertions.isNull
import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

@Transactional
@SpringBootTest
class BarEntityRepositoryTest {
    @Autowired
    private lateinit var entityManager: EntityManager

    @Autowired
    private lateinit var barEntityRepository: BarEntityRepository

    init {
        logger.info { ">>> starting BarEntityRepositoryTest" }
    }

    @Test
    fun `should update entity`() {
        val bar = Bar()
        val barEntity = bar.toEntity()

        assertThat(bar.barId, "bar.barId").isNull()
        assertThat(barEntity.barId, "barEntity.barId").isNotNull()

        val created = barEntityRepository.save(barEntity)
        entityManager.flushAndClearCache()

        val fetched = barEntityRepository.findById(created.barId).orElseThrow()

        assertThat(fetched.barId, "barId, fetched vs. created").isEqualTo(created.barId)
        val newFooId = UUID.randomUUID()
        val fetchedRecord = fetched.toRecord()

        barEntityRepository.save(BarEntity.from(fetchedRecord)).update(bar = fetchedRecord.copy(fooId = newFooId))
        entityManager.flushAndClearCache()

        val updated = barEntityRepository.findById(fetched.barId).orElseThrow()

        assertAll {
            assertThat(updated.fooId, "updated.fooId").isEqualTo(newFooId)
            assertThat(updated.fooId, "updateed.fooId vs. bar.fooId").isNotEqualTo(bar.fooId)
            assertThat(updated.created, "updated.created vs. created.created").isEqualTo(created.created)
            assertThat(updated.updated, "updated.updated vs. created.updated").isNotEqualTo(created.updated)
        }
    }
}
