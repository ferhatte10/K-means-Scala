case class Point2D(x: Double, y: Double, var C : String ="[0,0,0]") { // un point est une donnée initialisé avec une couleur noir

  def distanceTo(pt2: Point2D) :Double = math.sqrt( Utils.power(this.x - pt2.x, 2) + Utils.power(this.y - pt2.y, 2)) // Une methode qui calcule la distance entre 2 points

}