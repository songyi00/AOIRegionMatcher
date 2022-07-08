package com.sia.codingtest

import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName

class DatabaseContainer {
    companion object{

        @JvmStatic
        val postgresqlContainer = PostgreSQLContainer<Nothing>(
            DockerImageName.parse("postgis/postgis")
            .asCompatibleSubstituteFor("postgres"))
    }
}