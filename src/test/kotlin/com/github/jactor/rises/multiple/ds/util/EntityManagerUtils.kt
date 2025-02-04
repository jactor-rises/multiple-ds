package com.github.jactor.rises.multiple.ds.util

import jakarta.persistence.EntityManager

fun EntityManager.flushAndClearCache() {
    flush()
    clear()
}
