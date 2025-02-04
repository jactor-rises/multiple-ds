package com.github.jactor.rises.multiple.ds.bar

import java.time.LocalDateTime
import java.util.UUID

@JvmRecord
data class Bar(
    val barId: UUID? = null,
    val fooId: UUID? = null,
    val created: LocalDateTime = LocalDateTime.now(),
    val updated: LocalDateTime? = null,
) {
    fun toEntity() = BarEntity(
        barId = barId ?: UUID.randomUUID(),
    ).apply {
        fooId = this@Bar.fooId

        if (this@Bar.barId == null) {
            created = this@Bar.created
        }

        if (this@Bar.updated != null) {
            updated = this@Bar.updated
        }
    }
}
