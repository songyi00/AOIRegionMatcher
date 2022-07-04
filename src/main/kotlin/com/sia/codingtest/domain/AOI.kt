package com.sia.codingtest.domain


import com.sia.codingtest.dto.CreateAoiDto
import com.vividsolutions.jts.geom.Point;
import javax.persistence.*

@Entity
class AOI(name: String, area: Point){

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "aoi_id", nullable = false)
    val id: Long = 0

    @Column(columnDefinition="text")
    var name: String = name

    @Column(columnDefinition = "geometry(Polygon,4326)")
    var area: Point = area

    companion object{
        private val equalsAndHashCodeProperties = arrayOf(AOI::id)
        private val toStringProperties = arrayOf(
            AOI::id,
            AOI::name,
            AOI::area
        )
        fun createRegionInfo(createAoiDto: CreateAoiDto): AOI {
            return AOI(createAoiDto.name, createAoiDto.area)
        }
    }
}