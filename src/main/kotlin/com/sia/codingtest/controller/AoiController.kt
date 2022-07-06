package com.sia.codingtest.controller

import com.sia.codingtest.domain.Aoi
import com.sia.codingtest.domain.Point
import com.sia.codingtest.dto.response.AoiResponseDto
import com.sia.codingtest.dto.response.AoisDto
import com.sia.codingtest.dto.request.CreateAoiDto

import com.sia.codingtest.service.AoiService
import com.vividsolutions.jts.geom.Polygon
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class AoiController(private val aoiService: AoiService) {

    // 관심 지역 등록
    @PostMapping("/aois")
    @ResponseStatus(HttpStatus.CREATED)
    fun saveAoi(@RequestBody @Validated createAoiDto: CreateAoiDto) : ResponseEntity<Long> {
        val id = aoiService.saveAoi(createAoiDto)
        return ResponseEntity.ok().body(id)
    }

    // 행정 지역에 포함되는 관심 지역 조회
    @GetMapping("/regions/{regionId}/aois/intersects")
    @ResponseStatus(HttpStatus.OK)
    fun findAoisInRegion(@PathVariable regionId: Long) : AoiResponseDto<List<AoisDto>> {
        val aois : List<Aoi>? = aoiService.findAoiInRegion(regionId)
        val aoisDto : ArrayList<AoisDto> = ArrayList()

        if (aois != null) {
            aois.forEach{ aoi ->
                aoisDto.add(AoisDto(aoi.id,aoi.name,polygonToPoint(aoi.area)))
            }
            return AoiResponseDto(aoisDto)
        }
        else{
            return AoiResponseDto(null)
        }
    }

    companion object{
        fun polygonToPoint(area : Polygon) : List<Point> {
            val points : ArrayList<Point> = ArrayList()
            val coordinates = area.coordinates
            for (c in coordinates){
                points.add(Point(c.x,c.y))
            }
            return points
        }
    }
}