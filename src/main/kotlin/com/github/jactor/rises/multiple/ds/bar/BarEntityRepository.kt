package com.github.jactor.rises.multiple.ds.bar

import java.time.LocalDateTime
import java.util.UUID
import org.springframework.data.jpa.repository.JpaRepository
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

interface BarEntityRepository : JpaRepository<BarEntity, UUID>

@Entity
@Table(name = "BAR")
class BarEntity(
    @Id @Column(name = "bar_id") internal var barId: UUID = UUID.randomUUID(),
) {
    @Column(name = "foo_id")
    internal var fooId: UUID? = null

    @Column(name = "created", updatable = false)
    internal var created: LocalDateTime = LocalDateTime.now()

    @Column(name = "updated")
    internal var updated: LocalDateTime = LocalDateTime.now()

    fun update(bar: Bar) {
        fooId = bar.fooId
        created = bar.created
        updated = LocalDateTime.now()
    }

    fun toRecord() = Bar(
        barId = barId,
        fooId = fooId,
        created = created,
        updated = updated,
    )

    companion object {
        fun from(bar: Bar) = BarEntity(
            barId = bar.barId ?: UUID.randomUUID(),
        ).apply {
            fooId = bar.fooId
            created = bar.created

            if (bar.updated != null) {
                updated = bar.updated
            }
        }
    }
}
