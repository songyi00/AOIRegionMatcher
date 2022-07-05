package com.sia.codingtest.repository

import com.sia.codingtest.domain.AOI
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import javax.persistence.EntityManager

 interface AoiRepository : JpaRepository<AOI,Long> {
    fun findAOIById(id: Long): AOI?

    @Query(value = "select a.*, r.* from AOI a , Region r where r.region_id = :regionId and st_intersects(a.area , r.area)",nativeQuery=true)
    fun findAOISByRegion(@Param("regionId") regionId: Long): List<AOI>?

 }