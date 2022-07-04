package com.sia.codingtest.repository

import com.sia.codingtest.domain.AOI
import org.springframework.data.jpa.repository.JpaRepository

interface AoiRepository : JpaRepository<AOI,Long> {

}