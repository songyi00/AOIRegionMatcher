package com.sia.codingtest.dto

import com.vividsolutions.jts.geom.Point

data class CreateAoiDto(val name: String, val area: Point) {
}