package com.sia.codingtest.service

import com.sia.codingtest.dto.CreateRegionDto
import com.vividsolutions.jts.geom.Point
import com.vividsolutions.jts.io.WKTReader
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe


class RegionServiceTest(regionService: RegionService) : ShouldSpec({

//    test("행정 지역 정보 저장") {
//        "Region".name shouldBe "서울시"
//    }
    should("행정 지역 정보 저장 ") {
        //given
        val latitude = 37.51435
        val longitude = 127.12215
        val pointWKT = String.format("POINT(%s %s)", longitude, latitude)
        val point = WKTReader().read(pointWKT) as Point

        val createRegionDto : CreateRegionDto = CreateRegionDto(
            "서울시",point
        )

        //when
        val region_id: Long = regionService.saveRegion(createRegionDto)

        //then
        region_id shouldBe 4
    }
})
