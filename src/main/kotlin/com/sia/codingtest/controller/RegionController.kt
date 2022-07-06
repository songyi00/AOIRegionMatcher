package com.sia.codingtest.controller

import com.sia.codingtest.dto.request.CreateAoiDto
import com.sia.codingtest.dto.request.CreateRegionDto
import com.sia.codingtest.service.RegionService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class RegionController(private val regionService: RegionService) {

    // 행정 지역 등록
    @PostMapping("/regions")
    @ResponseStatus(HttpStatus.CREATED)
    fun saveRegion(@RequestBody @Validated createRegionDto: CreateRegionDto) : ResponseEntity<Long> {
        val id = regionService.saveRegion(createRegionDto)
        return ResponseEntity.ok().body(id)
    }

}