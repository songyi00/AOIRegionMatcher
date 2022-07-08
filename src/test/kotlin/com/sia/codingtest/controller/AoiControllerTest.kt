package com.sia.codingtest.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.sia.codingtest.config.DataConfig
import com.sia.codingtest.service.AoiService
import com.sia.codingtest.service.RegionService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName

@Testcontainers
@AutoConfigureMockMvc
@SpringBootTest
class AoiControllerTest() {

    @Autowired
    private lateinit var mvc : MockMvc

    @Autowired
    private lateinit var aoiService : AoiService

    @Autowired
    private lateinit var regionService: RegionService


    @Container
    val postgresqlContainer = PostgreSQLContainer<Nothing>(
        DockerImageName.parse("postgis/postgis")
            .asCompatibleSubstituteFor("postgres"))

    // [POST] 관심 지역 저장 API
    @Test
    fun saveAoiApi() {
        val createAoiDto = DataConfig.createAoiDto()

        val json = jacksonObjectMapper().writeValueAsString(createAoiDto)

        val uri = "/aois"

        mvc.perform(
            MockMvcRequestBuilders.post(uri)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
            .andDo(MockMvcResultHandlers.print())
    }

    // [GET] 행정 지역 포함 관심 지역 조회 API
    @Test
    fun findAoiInRegionApi() {
        val createAoiDto = DataConfig.createAoiDto()
        val createRegionDto = DataConfig.createRegionDto()

        aoiService.saveAoi(createAoiDto)
        regionService.saveRegion(createRegionDto)

        val json = jacksonObjectMapper().writeValueAsString(createAoiDto)

        val uri = "/regions/1/aois/intersects"

        mvc.perform(
            MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.jsonPath("$.aois").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.aois[0].id").value(1))
            .andDo(MockMvcResultHandlers.print())
    }

    // [GET] 가장 가까운 관심 지역 조회
    @Test
    fun findClosestAoiApi() {
        val createAoiDto = DataConfig.createAoiDto()
        val createRegionDto = DataConfig.createRegionDto()

        aoiService.saveAoi(createAoiDto)
        regionService.saveRegion(createRegionDto)

        val json = jacksonObjectMapper().writeValueAsString(createAoiDto)

        val uri = "/aois?lat=37.541&long=126.986"

        mvc.perform(
            MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.jsonPath("$.aois").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.aois[0].id").value(1))
            .andExpect(MockMvcResultMatchers.jsonPath("$.aois[0].name").value("북한산"))
            .andDo(MockMvcResultHandlers.print())
    }
}