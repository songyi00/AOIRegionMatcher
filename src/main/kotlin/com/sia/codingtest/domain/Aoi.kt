package com.sia.codingtest.domain


import com.sia.codingtest.dto.request.CreateAoiDto
import com.vividsolutions.jts.geom.Coordinate
import com.vividsolutions.jts.geom.GeometryFactory
import com.vividsolutions.jts.geom.Polygon
import javax.persistence.*


@Entity
class Aoi(name: String, area: Polygon){

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "aoi_id", nullable = false)
    val id: Long = 0

    @Column(columnDefinition="text")
    var name: String = name


    @Column(columnDefinition = "geometry(Polygon)")
    var area: Polygon = area

    companion object{
        private val equalsAndHashCodeProperties = arrayOf(Aoi::id)
        private val toStringProperties = arrayOf(
            Aoi::id,
            Aoi::name,
            Aoi::area
        )
        fun createAoi(createAoiDto: CreateAoiDto): Aoi {
            val area: Polygon = Point.convertListToPolygon(createAoiDto.area)
            return Aoi(createAoiDto.name, area)
        }
    }
}