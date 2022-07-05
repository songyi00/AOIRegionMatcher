package com.sia.codingtest.domain

import com.sia.codingtest.dto.CreateRegionDto
import com.vividsolutions.jts.geom.Point
import com.vividsolutions.jts.geom.Polygon
import javax.persistence.*

@Entity
class Region(name:String, area:Polygon) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "region_id", nullable = false)
    val id: Long = 0

    @Column(columnDefinition="text")
    var name: String = name

    @Column(columnDefinition = "geometry(Polygon)")
    var area: Polygon = area

    companion object{
        private val equalsAndHashCodeProperties = arrayOf(Region::id)
        private val toStringProperties = arrayOf(
            Region::id,
            Region::name,
            Region::area
        )
        fun createRegionInfo(createRegionDto: CreateRegionDto) : Region {
            return Region(createRegionDto.name, createRegionDto.area)
        }
    }
}