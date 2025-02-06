package com.github.jactor.rises.multiple.ds.foo

import java.time.LocalDateTime
import java.util.UUID
import org.springframework.data.jpa.repository.JpaRepository
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

interface FooEntityRepository : JpaRepository<FooEntity, UUID>

@Entity
@Table(name = "FOO")
class FooEntity(
    @Id @Column(name = "foo_id") internal var fooId: UUID = UUID.randomUUID(),
) {
    @Column(name = "bar_id")
    internal var barId: UUID? = null

    @Column(name = "created", updatable = false)
    internal var created: LocalDateTime = LocalDateTime.now()

    @Column(name = "updated")
    internal var updated: LocalDateTime = LocalDateTime.now()

    fun update(foo: Foo) {
        barId = foo.barId
        created = foo.created
        updated = LocalDateTime.now()
    }

    fun toRecord() = Foo(
        fooId = fooId,
        barId = barId,
        created = created,
        updated = updated,
    )

    companion object {
        fun from(foo: Foo) = FooEntity(
            fooId = foo.fooId ?: UUID.randomUUID(),
        ).apply {
            barId = foo.barId
            created = foo.created

            if (foo.updated != null) {
                updated = foo.updated
            }
        }
    }
}
