package com.sia.codingtest.controller

import com.sia.codingtest.dto.request.CreateRegionDto
import com.sia.codingtest.dto.response.IdResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController {
    @PostMapping("/test")
    @ResponseStatus(HttpStatus.CREATED)
    fun saveRegion() : IdResponse{
        return IdResponse(1)
    }
}