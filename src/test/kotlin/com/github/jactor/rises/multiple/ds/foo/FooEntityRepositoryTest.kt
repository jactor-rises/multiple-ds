package com.github.jactor.rises.multiple.ds.foo

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

@Transactional
@SpringBootTest
class FooEntityRepositoryTest {
    @Autowired
    private lateinit var entityManager: EntityManager

    @Autowired
    private lateinit var fooEntityRepository: FooEntityRepository

    @Test
    fun `should update entity`() {
        val foo = Foo()
        val fooEntity = foo.toEntity()

        assertThat(foo.fooId, "foo.fooId").isNull()
        assertThat(fooEntity.fooId, "fooEntity.fooId").isNotNull()

        val created = fooEntityRepository.save(fooEntity)
        entityManager.flushAndClearCache()

        val fetched = fooEntityRepository.findById(created.fooId).orElseThrow()

        assertThat(fetched.fooId, "fooId, fetched vs. created").isEqualTo(created.fooId)
        val newBarId = UUID.randomUUID()
        val fetchedRecord = fetched.toRecord()

        fooEntityRepository.save(FooEntity.from(fetchedRecord)).update(foo = fetchedRecord.copy(barId = newBarId))
        entityManager.flushAndClearCache()

        val updated = fooEntityRepository.findById(fetched.fooId).orElseThrow()

        assertAll {
            assertThat(updated.barId, "updated.barId").isEqualTo(newBarId)
            assertThat(updated.barId, "updated.barId vs. foo.barId").isNotEqualTo(foo.barId)
            assertThat(updated.updated, "updated.updated vs. created.updated").isNotEqualTo(created.updated)
        }
    }
}
