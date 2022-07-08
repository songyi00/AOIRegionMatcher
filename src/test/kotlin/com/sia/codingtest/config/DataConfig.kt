package com.sia.codingtest.config

import com.sia.codingtest.domain.Point
import com.sia.codingtest.dto.request.CreateAoiDto
import com.sia.codingtest.dto.request.CreateRegionDto
import com.vividsolutions.jts.geom.Polygon
import com.vividsolutions.jts.io.WKTReader

class DataConfig {
    companion object {

        fun createRegionDto() : CreateRegionDto {
            val regionArea : Polygon = WKTReader().read("Polygon((126.835 37.688, 127.155 37.702, 127.184 37.474, 126.821 37.454, 126.835 37.688))") as Polygon
            regionArea.srid = 4326
            val points = Point.convertPolygonToList(regionArea)
            val createRegionDto = CreateRegionDto(
                "서울시", points
            )
            return createRegionDto
        }

        fun createAoiDto() : CreateAoiDto {
            val aoiArea : Polygon = WKTReader().read("Polygon((127.02 37.742, 127.023 37.664, 126.945 37.605, 126.962 37.692, 127.02 37.742))") as Polygon
            aoiArea.srid = 4326
            val points = Point.convertPolygonToList(aoiArea)
            val createAoiDto = CreateAoiDto(
                "북한산", points
            )
            return createAoiDto
        }
    }
}