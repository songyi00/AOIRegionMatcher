package com.sia.codingtest.repository

import com.sia.codingtest.domain.Aoi
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface AoiRepository : JpaRepository<Aoi,Long> {
    fun findAOIById(id: Long): Aoi?

    @Query(value = "select a.* from AOI a , Region r where r.region_id = :regionId and st_intersects(a.area , r.area)",nativeQuery=true)
    fun findAOISByRegion(@Param("regionId") regionId: Long): List<Aoi>?

 }