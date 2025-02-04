package com.github.jactor.rises.multiple.ds.foo

import java.time.LocalDateTime
import java.util.UUID

@JvmRecord
data class Foo(
    val fooId: UUID? = null,
    val barId: UUID? = null,
    val created: LocalDateTime = LocalDateTime.now(),
    val updated: LocalDateTime? = null,
) {
    fun toEntity() = FooEntity(
        fooId = fooId ?: UUID.randomUUID(),
    ).apply {
        barId = this@Foo.barId

        if (this@Foo.fooId == null) {
            created = this@Foo.created
        }

        if (this@Foo.updated != null) {
            updated = this@Foo.updated
        }
    }
}
