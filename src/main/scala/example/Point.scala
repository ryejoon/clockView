package example

/**
 * Created by ryejoon on 10/1/15.
 */

case class Point(x: Int, y: Int){
  def +(p: Point) = Point(x + p.x, y + p.y)
  def -(p: Point) = Point(x - p.x, y - p.y)
  def /(d: Int) = Point(x / d, y / d)
  def dist(p: Point) = Math.sqrt(Math.pow(x - p.x, 2) + Math.pow(y - p.y, 2)).toInt
}
