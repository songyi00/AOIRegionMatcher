package com.sia.codingtest.dto.request

import com.sia.codingtest.domain.Point
import com.vividsolutions.jts.geom.Polygon

data class CreateRegionDto(val name: String, val area: List<Point>) {

    override fun toString(): String {
        return "RegionDto(name=" + this.name +")"
    }
}