package com.sia.codingtest.service

import com.sia.codingtest.domain.Point
import com.sia.codingtest.domain.Region
import com.sia.codingtest.dto.request.CreateRegionDto
import com.vividsolutions.jts.geom.Polygon
import com.vividsolutions.jts.io.WKTReader
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class RegionServiceTest(val regionService: RegionService) : FunSpec({

     test("행정 지역 정보 저장") {
          //given
          val regionArea : Polygon = WKTReader().read("Polygon((126.835 37.688, 127.155 37.702, 127.184 37.474, 126.821 37.454, 126.835 37.688))") as Polygon
          val points : MutableList<Point> = mutableListOf()
          val coordinates = regionArea.coordinates
          for (c in coordinates){
               points.add(Point(c.x,c.y))
          }

          val createRegionDto = CreateRegionDto(
               "서울시", points
          )

          println(createRegionDto.area)
          println("geometry: "+ regionArea.geometryType)

          //when
          val regionId = regionService.saveRegion(createRegionDto)
          val region : Region? = regionService.findOne(regionId)
          val regionName = region?.name

          //then
          regionName shouldBe "서울시"
     }

})
