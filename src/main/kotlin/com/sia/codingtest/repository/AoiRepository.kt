package com.sia.codingtest.repository

import com.sia.codingtest.domain.Aoi
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface AoiRepository : JpaRepository<Aoi,Long> {
    fun findAoiById(id: Long): Aoi?

    @Query(value = "select a.* from AOI a , Region r where r.region_id = :regionId and st_intersects(a.area , r.area)",nativeQuery=true)
    fun findAoisByRegion(@Param("regionId") regionId: Long): List<Aoi>?

    @Query(value =  "select * from aoi order by ST_Distance(area,ST_SetSRID(ST_Point(:lat, :long), 4326)) limit 1", nativeQuery = true)
    fun findClosestAoi(@Param("lat") lat:Double, @Param("long") long:Double) : Aoi?
 }