package com.sia.codingtest.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.sia.codingtest.DataConfig
import com.sia.codingtest.service.RegionService

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.TestConstructor
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers


@AutoConfigureMockMvc
@SpringBootTest
class RegionControllerTest(){

    @Autowired
    private lateinit var mvc : MockMvc

    @Autowired
    private lateinit var regionService : RegionService

    @Test
    fun saveRegionAPi() {
        val createRegionDto = DataConfig.createRegionDto()
        val json = jacksonObjectMapper().writeValueAsString(createRegionDto)
//        println("확인!!!"+ json)

        val uri = "/regions"

        mvc.perform(
            MockMvcRequestBuilders.post(uri)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
            .andDo(MockMvcResultHandlers.print())

    }
}
