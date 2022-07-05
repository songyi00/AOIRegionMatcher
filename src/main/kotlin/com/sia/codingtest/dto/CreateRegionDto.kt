package com.sia.codingtest.dto

import com.vividsolutions.jts.geom.Point
import com.vividsolutions.jts.geom.Polygon

data class CreateRegionDto(val name: String, val area: Polygon) {

}