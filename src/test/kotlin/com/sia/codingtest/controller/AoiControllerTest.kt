package com.sia.codingtest.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.sia.codingtest.DataConfig
import com.sia.codingtest.service.AoiService
import com.sia.codingtest.service.RegionService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@AutoConfigureMockMvc
@SpringBootTest
class AoiControllerTest() {

    @Autowired
    private lateinit var mvc : MockMvc

    @Autowired
    private lateinit var aoiService : AoiService

    @Autowired
    private lateinit var regionService: RegionService

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
}