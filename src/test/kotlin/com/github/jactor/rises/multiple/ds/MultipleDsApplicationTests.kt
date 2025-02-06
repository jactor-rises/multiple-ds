package com.github.jactor.rises.multiple.ds

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

@SpringBootTest
class MultipleDsApplicationTests {

	init {
		logger.info { ">>> starting MultipleDsApplicationTests" }
	}

	@Test
	fun `should load spring-context`() {
	}
}
