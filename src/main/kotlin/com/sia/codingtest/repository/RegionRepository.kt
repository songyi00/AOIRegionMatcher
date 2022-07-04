package com.sia.codingtest.repository

import com.sia.codingtest.domain.Region
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RegionRepository : JpaRepository<Region,Long> {

}
