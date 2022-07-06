package com.sia.codingtest.config

import io.kotest.core.config.AbstractProjectConfig
import io.kotest.core.extensions.Extension
import io.kotest.extensions.spring.SpringExtension

object ProjectConfig : AbstractProjectConfig() {

        override fun extensions(): List<Extension> {
            return listOf(SpringExtension)
        }

}