package com.sia.codingtest.dto

import com.vividsolutions.jts.geom.Point

data class CreateRegionDto(val name: String, val area: Point) {

}