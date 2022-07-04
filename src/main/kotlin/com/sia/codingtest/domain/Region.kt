package com.sia.codingtest.domain

import com.sia.codingtest.dto.CreateRegionInfoDto
import com.vividsolutions.jts.geom.Point
import javax.persistence.*

@Entity
class Region(name:String, area:Point) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "region_id", nullable = false)
    val id: Long = 0

    @Column(columnDefinition="text")
    var name: String = name

    @Column(columnDefinition = "geometry(Polygon,4326)",name="area")
    var area: Point = area

    companion object{
        private val equalsAndHashCodeProperties = arrayOf(Region::id)
        private val toStringProperties = arrayOf(
            Region::id,
            Region::name,
            Region::area
        )
        fun createRegionInfo(createRegionInfoDto: CreateRegionInfoDto) : Region {
            val region = Region(createRegionInfoDto.name, createRegionInfoDto.area).apply {
                this.name = name
                this.area = area
            }
            return region
        }
    }
}