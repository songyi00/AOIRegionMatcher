package com.sia.codingtest.domain

import com.vividsolutions.jts.geom.Coordinate
import com.vividsolutions.jts.geom.GeometryFactory
import com.vividsolutions.jts.geom.Polygon


class Point(val x:Double,val y:Double) {

    companion object {

        // list(point) -> polygon
        fun convertListToPolygon(points: List<Point>): Polygon {
            val geometryFactory: GeometryFactory = GeometryFactory()
            val coordinates: MutableList<Coordinate> = mutableListOf()

            points.forEach { point ->
                coordinates.add(Coordinate(point.x, point.y))
            }
            return geometryFactory.createPolygon(coordinates.toTypedArray())
        }

        // polygon -> list(point)
        fun convertPolygonToList(area: Polygon) : List<Point> {
            val points : MutableList<Point> = mutableListOf()
            val coordinates = area.coordinates

            coordinates.forEach { c ->
                points.add(Point(c.x, c.y))
            }
            return points
        }
    }
}
