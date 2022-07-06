package com.sia.codingtest.dto.request

import com.sia.codingtest.domain.Point
import com.vividsolutions.jts.geom.Polygon

data class CreateAoiDto(val name: String, val area: List<Point>) {

    //toString 재정의
    override fun toString(): String {
        return "AoiDto(name=" + this.name +")"
    }

}