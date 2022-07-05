package com.sia.codingtest.repository

import com.sia.codingtest.domain.AOI
import com.sia.codingtest.domain.Region
import org.springframework.data.jpa.repository.JpaRepository

interface AoiRepository : JpaRepository<AOI,Long> {
    fun findAOIById(id: Long): AOI?
}