package com.sia.codingtest.controller

import com.sia.codingtest.domain.Aoi
import com.sia.codingtest.domain.Point
import com.sia.codingtest.domain.Region
import com.sia.codingtest.dto.request.CreateAoiDto
import com.sia.codingtest.dto.request.CreateRegionDto
import com.sia.codingtest.repository.AoiRepository
import com.sia.codingtest.repository.RegionRepository
import com.sia.codingtest.service.AoiService
import com.sia.codingtest.service.RegionService
import com.vividsolutions.jts.geom.Polygon
import com.vividsolutions.jts.io.WKTReader
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class AoiControllerTest : FunSpec({

    lateinit var aoiRepository : AoiRepository
    lateinit var regionRepository : RegionRepository

    lateinit var regionService : RegionService
    lateinit var aoiService : AoiService
    lateinit var aoiController : AoiController

    beforeTest {
        aoiRepository = mockk()
        regionRepository = mockk()
        aoiService = AoiService(aoiRepository, regionRepository)
        aoiController = AoiController(aoiService)
        regionService = RegionService(regionRepository)
    }

    test("관심 지역 저장") {
        //given
        val aoiArea : Polygon = WKTReader().read("Polygon((127.02 37.742, 127.023 37.664, 126.945 37.605, 126.962 37.692, 127.02 37.742))") as Polygon
        val points = Point.convertPolygonToList(aoiArea)
        val createAoiDto = CreateAoiDto(
            "북한산", points
        )
        val aoiResult = Aoi("북한산",aoiArea)
        every { aoiRepository.save(any()) } returns aoiResult
        every { aoiRepository.findAOIById(any()) } returns aoiResult

        //when
        val response = aoiController.saveAoi(createAoiDto)

        //then
        response.body shouldBe 0L
    }

    test("행정 지역 포함 관심지역 조회") {
        //given
        val regionArea : Polygon = WKTReader().read("Polygon((126.835 37.688, 127.155 37.702, 127.184 37.474, 126.821 37.454, 126.835 37.688))") as Polygon
        val points = Point.convertPolygonToList(regionArea)
        val createRegionDto = CreateRegionDto(
            "서울시", points
        )

        val aoiArea : Polygon = WKTReader().read("Polygon((127.02 37.742, 127.023 37.664, 126.945 37.605, 126.962 37.692, 127.02 37.742))") as Polygon
        val points2 = Point.convertPolygonToList(aoiArea)
        val createAoiDto = CreateAoiDto(
            "북한산", points2
        )
        val regionResult = Region("서울시",regionArea)
        val aoiResult = Aoi("북한산",aoiArea)
        val aoiListResultList = listOf(aoiResult)

        every { aoiRepository.findAOISByRegion(regionResult.id) } returns aoiListResultList

        // when
        val response = aoiController.findAoisInRegion(0L)

        // then
        if (response.aois != null) {
            response.aois!![0].id shouldBe 0L
            response.aois!![0].name shouldBe "북한산"
        }
        else{
            response.aois shouldBe emptyList()
        }
        println("!!!!"+response.aois)
    }

    afterTest {
        clearMocks(aoiRepository)
    }
})
