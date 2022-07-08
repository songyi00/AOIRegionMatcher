package com.sia.codingtest.service

import com.sia.codingtest.DatabaseContainer
import com.sia.codingtest.config.DataConfig
import com.sia.codingtest.domain.Aoi
import com.sia.codingtest.domain.Point
import com.sia.codingtest.domain.Region
import com.sia.codingtest.repository.AoiRepository
import com.sia.codingtest.repository.RegionRepository
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@ExtendWith(MockKExtension::class)
class AoiServiceTest(): FunSpec({

    lateinit var aoiRepository : AoiRepository
    lateinit var regionRepository : RegionRepository

    lateinit var regionService : RegionService
    lateinit var aoiService : AoiService

    @BeforeAll
    fun beforeAll() {
        DatabaseContainer.postgresqlContainer.start()
    }

    @AfterAll
    fun afterAll() {
        DatabaseContainer.postgresqlContainer.stop()
    }

    beforeTest {
        aoiRepository = mockk()
        regionRepository = mockk()
        aoiService = AoiService(aoiRepository, regionRepository)
        regionService = RegionService(regionRepository)
    }

    test("관심 지역 저장") {
        //given
        val createAoiDto = DataConfig.createAoiDto()
        val aoiResult = Aoi("북한산",Point.convertListToPolygon(createAoiDto.area))

        every { aoiRepository.save(any()) } returns aoiResult
        every { aoiRepository.findAoiById(any()) } returns aoiResult

        //when
        val aoiId = aoiService.saveAoi(createAoiDto)
        val aoi : Aoi? = aoiService.findOne(aoiId)
        val aoiName = aoi?.name

        //then
        aoiId shouldBe 1L
        aoiName shouldBe "북한산"
    }

    test("행정 지역 포함 관심 지역 존재") {
        //given
        val createRegionDto = DataConfig.createRegionDto()
        val createAoiDto = DataConfig.createAoiDto()

        val regionResult = Region("서울시",Point.convertListToPolygon(createRegionDto.area))
        val aoiResult = Aoi("북한산",Point.convertListToPolygon(createAoiDto.area))

        val aoiListResultList = listOf(aoiResult)

        every { regionRepository.save(any()) } returns regionResult
        every { aoiRepository.save(any()) } returns aoiResult
        every { aoiRepository.findAoisByRegion(regionResult.id) } returns aoiListResultList

        // when
        val regionId = regionService.saveRegion(createRegionDto)
        val aoiId = aoiService.saveAoi(createAoiDto)
        val aois : List<Aoi>? = aoiService.findAoiInRegion(regionId)

        // then
        if (aois != null) {
            aois[0].id shouldBe 1L
            aois[0].name shouldBe "북한산"
        }
    }

    test("행정 지역 포함 관심 지역 없음") {
        //given
        val createRegionDto = DataConfig.createRegionDto()
        val createAoiDto = DataConfig.createAoiDto()

        val regionResult = Region("서울시",Point.convertListToPolygon(createRegionDto.area))
        val aoiResult = Aoi("북한산",Point.convertListToPolygon(createAoiDto.area))

        every { regionRepository.save(any()) } returns regionResult
        every { aoiRepository.save(any()) } returns aoiResult
        every { aoiRepository.findAoisByRegion(regionResult.id) } returns listOf()

        //when
        val aois : List<Aoi>? = aoiService.findAoiInRegion(1)

        //then
        aois shouldBe emptyList()
    }

    test("가장 가까운 관심 지역 존재") {
        //given
        val lat = 37.541
        val long = 126.986

        val createRegionDto = DataConfig.createRegionDto()
        val createAoiDto = DataConfig.createAoiDto()

        val regionResult = Region("서울시",Point.convertListToPolygon(createRegionDto.area))
        val aoiResult = Aoi("북한산",Point.convertListToPolygon(createAoiDto.area))

        every { aoiRepository.findClosestAoi(lat,long) } returns aoiResult

        //when
        val aoi = aoiService.findClosestAoi(lat,long)

        // then
        if (aoi != null) {
            val name = aoi.name
            name shouldBe "북한산"
        }
        else {
            aoi shouldBe null
        }
    }

    test("가장 가까운 관심 지역 없음") {
        //given
        val lat = 37.541
        val long = 126.986

        val createRegionDto = DataConfig.createRegionDto()
        val createAoiDto = DataConfig.createAoiDto()

        val regionResult = Region("서울시",Point.convertListToPolygon(createRegionDto.area))
        val aoiResult = Aoi("북한산",Point.convertListToPolygon(createAoiDto.area))

        every { aoiRepository.findClosestAoi(lat,long) } returns null

        //when
        val aoi = aoiService.findClosestAoi(lat,long)

        // then
        aoi shouldBe null

    }
})
