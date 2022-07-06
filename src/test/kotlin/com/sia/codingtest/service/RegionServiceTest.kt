package com.sia.codingtest.service

import com.sia.codingtest.DataConfig
import com.sia.codingtest.domain.Aoi
import com.sia.codingtest.domain.Point
import com.sia.codingtest.domain.Region
import com.sia.codingtest.dto.request.CreateRegionDto
import com.sia.codingtest.repository.AoiRepository
import com.sia.codingtest.repository.RegionRepository
import com.vividsolutions.jts.geom.Polygon
import com.vividsolutions.jts.io.WKTReader
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@ExtendWith(MockKExtension::class)
class RegionServiceTest() : FunSpec({

     lateinit var regionRepository : RegionRepository
     lateinit var regionService : RegionService

     beforeTest {
          regionRepository = mockk()
          regionService = RegionService(regionRepository)
     }

     test("행정 지역 정보 저장") {
          //given
          val createRegionDto = DataConfig.createRegionDto()
          val regionResult = Region("서울시",Point.convertListToPolygon(createRegionDto.area))

          every { regionRepository.save(any())} returns regionResult
          every { regionRepository.findRegionById(any())} returns regionResult

          //when
          val regionId = regionService.saveRegion(createRegionDto)
          val region : Region? = regionService.findOne(regionId)
          val regionName = region?.name

          //then
          regionId shouldBe 0L
          regionName shouldBe "서울시"
     }

})
