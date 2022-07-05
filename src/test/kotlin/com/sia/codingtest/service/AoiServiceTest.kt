package com.sia.codingtest.service

import com.sia.codingtest.domain.AOI
import com.sia.codingtest.dto.CreateAoiDto
import com.vividsolutions.jts.geom.Polygon
import com.vividsolutions.jts.io.WKTReader
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED)
@SpringBootTest
class AoiServiceTest(val aoiService: AoiService) : FunSpec({

    test("관심 지역 저장") {
        //given
        val geometry : Polygon = WKTReader().read("Polygon((127.02 37.742, 127.023 37.664, 126.945 37.605, 126.962 37.692, 127.02 37.742))") as Polygon

        val createAoiDto = CreateAoiDto(
            "북한산", geometry
        )
        println(createAoiDto.toString())
        println("geometry: "+ geometry.geometryType)

        //when
        val aoiId = aoiService.saveAoi(createAoiDto)
        val aoi : AOI? = aoiService.findOne(aoiId)
        val aoiName = aoi?.name

        //then
        aoiName shouldBe "북한산"
    }

    test("행정 지역 포함 관심지역 조회") {
        val regionId : Long = 1
        val aois : List<AOI>? = aoiService.findAoiInRegion(regionId)

        println("결과: "+aois)
    }
})
