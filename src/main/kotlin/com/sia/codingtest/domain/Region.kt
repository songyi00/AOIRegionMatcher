package com.sia.codingtest.domain

import com.sia.codingtest.dto.request.CreateRegionDto
import com.vividsolutions.jts.geom.Coordinate
import com.vividsolutions.jts.geom.GeometryFactory
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

        fun createRegion(createRegionDto: CreateRegionDto) : Region {
            val area : Polygon = Point.convertListToPolygon(createRegionDto.area)
            return Region(createRegionDto.name, area)
        }

    }
}