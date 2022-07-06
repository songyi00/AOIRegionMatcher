package com.sia.codingtest.controller

import com.sia.codingtest.domain.Point
import com.sia.codingtest.dto.request.CreateRegionDto
import com.sia.codingtest.repository.RegionRepository
import com.sia.codingtest.service.RegionService
import com.vividsolutions.jts.geom.Polygon
import com.vividsolutions.jts.io.WKTReader
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.springframework.boot.test.context.SpringBootTest
import javax.swing.plaf.synth.Region

@SpringBootTest
class RegionControllerTest : FunSpec({

    lateinit var regionRepository : RegionRepository
    lateinit var regionService : RegionService
    lateinit var regionController : RegionController

    beforeTest {
        regionRepository = mockk()
        regionService = RegionService(regionRepository)
        regionController = RegionController(regionService)
    }

    test("행정 지역 저장") {
        //given
        val regionArea : Polygon = WKTReader().read("Polygon((126.835 37.688, 127.155 37.702, 127.184 37.474, 126.821 37.454, 126.835 37.688))") as Polygon
        val points = Point.convertPolygonToList(regionArea)
        val createRegionDto = CreateRegionDto(
            "서울시", points
        )

        val regionResult = com.sia.codingtest.domain.Region("서울시", regionArea)
        every { regionRepository.save(any()) } returns regionResult
        every { regionRepository.findRegionById(any()) } returns regionResult

        //when
        val response = regionController.saveRegion(createRegionDto)

        //then
        response.body shouldBe 0L
    }


})
