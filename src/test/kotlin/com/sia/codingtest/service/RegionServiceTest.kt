package com.sia.codingtest.service

import com.sia.codingtest.domain.Region
import com.sia.codingtest.dto.CreateRegionDto
import com.vividsolutions.jts.geom.Point
import com.vividsolutions.jts.geom.Polygon
import com.vividsolutions.jts.io.WKTReader
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.json.JSONObject
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED)
@SpringBootTest
class RegionServiceTest(val regionService: RegionService) : FunSpec({

     test("행정 지역 정보 저장") {
          //given
          val latitude = 37.51435 // 위도
          val longitude = 127.12215 // 경도

          val geometry : Polygon = WKTReader().read("Polygon((126.835 37.688, 127.155 37.702, 127.184 37.474, 126.821 37.454, 126.835 37.688))") as Polygon

          val createRegionDto = CreateRegionDto(
               "테스트", geometry
          )
          println(createRegionDto.toString())
          println("geometry: "+ geometry.geometryType)

          //when
          val regionId = regionService.saveRegion(createRegionDto)
          val region : Region? = regionService.findOne(regionId)
          val regionName = region?.name

          //then
          regionName shouldBe "테스트"
     }
})
