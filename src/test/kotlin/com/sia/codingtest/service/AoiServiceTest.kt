package com.sia.codingtest.service

import com.sia.codingtest.domain.AOI
import com.sia.codingtest.dto.CreateAoiDto
import com.sia.codingtest.dto.CreateRegionDto
import com.vividsolutions.jts.geom.Polygon
import com.vividsolutions.jts.io.WKTReader
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class AoiServiceTest(val aoiService: AoiService, val regionService: RegionService) : FunSpec({

    test("관심 지역 저장") {
        //given
        val aoiArea : Polygon = WKTReader().read("Polygon((127.02 37.742, 127.023 37.664, 126.945 37.605, 126.962 37.692, 127.02 37.742))") as Polygon
        aoiArea.srid = 4326

        val createAoiDto = CreateAoiDto(
            "북한산", aoiArea
        )

        //when
        val aoiId = aoiService.saveAoi(createAoiDto)
        val aoi : AOI? = aoiService.findOne(aoiId)
        val aoiName = aoi?.name

        //then
        aoiName shouldBe "북한산"
    }

    test("행정 지역 포함 관심지역 조회") {
        //given
        val regionArea : Polygon = WKTReader().read("Polygon((126.835 37.688, 127.155 37.702, 127.184 37.474, 126.821 37.454, 126.835 37.688))") as Polygon
        val createRegionDto = CreateRegionDto(
            "서울시", regionArea
        )

        val aoiArea : Polygon = WKTReader().read("Polygon((127.02 37.742, 127.023 37.664, 126.945 37.605, 126.962 37.692, 127.02 37.742))") as Polygon
        val createAoiDto = CreateAoiDto(
            "북한산", aoiArea
        )

        // when
        val regionId = regionService.saveRegion(createRegionDto)
        val aoiId = aoiService.saveAoi(createAoiDto)

        val aois : List<AOI>? = aoiService.findAoiInRegion(regionId)

        // then
        if (aois != null) {
          aois[0].name shouldBe "북한산"
        }
    }
})
