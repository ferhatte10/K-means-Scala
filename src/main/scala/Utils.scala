import scala.util.Random

object Utils {

  def power(x: Double, p: Double): Double = if (p == 0) 1 else x * power(x, p-1)  // methode recursive pour calculer la puissance
//-------------------------------------------------------------------------------------------------------------------------------------------------------------
  def min(x: Double, y: Double) : Double = if (x < y) x else y // methode qui calcule le min entre deux valeurs
//-------------------------------------------------------------------------------------------------------------------------------------------------------------
  def assigement(Allpts: List[Point2D], clusters: List[Cluster]): List[Cluster] = {
    val ptsByCLuster = Allpts.groupBy(point => clusters.minBy(_.centroid.distanceTo(point))) // je regroupe tout les point our pouvoir les verifier avec les points de chaque cluster et prendre la distance minimal
    clusters.map(c => c.copy(points = ptsByCLuster.getOrElse(c, Nil)))
  }
//-------------------------------------------------------------------------------------------------------------------------------------------------------------
  def initialClusters(pts: List[Point2D], n: Int): List[Cluster] = Random.shuffle(pts).take(n).map(pt => Cluster(pt)) // prendre au hazard n points pour les initialiser comme des cluster au debut on aura une liste de cluster qui auront des une liste pour chacun
//-------------------------------------------------------------------------------------------------------------------------------------------------------------
  def reCenter(clusters: List[Cluster]): List[Cluster] = clusters.map { c =>
    val avgX = c.points.map(_.x).sum / c.points.size // calcul des nouvelles coordonnées de x
    val avgY = c.points.map(_.y).sum / c.points.size // calcul des nouvelles coordonnées de y
    c.copy(centroid = Point2D(avgX, avgY)) // comme les classe sont case donc les VI sont private j'utilise copy pour creere une copie
  }

//-------------------------------------------------------------------------------------------------------------------------------------------------------------
  def kMeans(Allpts: List[Point2D], n: Int): List[ClusterBatch] ={
    val inialialBatch = ClusterBatch(initialClusters(Allpts, n))

    val steps = LazyList.iterate(inialialBatch){ clusterBatch => // premiere etape de kmeans
      val assignedClusters = assigement(Allpts, clusterBatch.clusters)
      val reCentered = reCenter(assignedClusters)
      ClusterBatch(reCentered)
    }
    steps.zipWithIndex.takeWhile{ case (cBatch,index) => // boucler le kmeans pour le converger
      var i = if (index > 0) -1 else  1 // pour contourner l'indexoutofbound
      //println("index = " + index)
      //println("i = " + i)
      var LCurrent = cBatch.clusters.map(_.centroid) //le cluster batch courant
      var LBefore = steps(index + i).clusters.map(_.centroid) // le cluster batch qui precede
      !LBefore.forall(LCurrent.contains) // condition d'arret si seuleemnt les points clusters des deux iteration ne sont pas les memes

   // cBatch.clusters.map(_.centroid) != steps(index + i).clusters.map(_.centroid)
    }.map { // je mets tous dans une lazylist[Clusterbatch]
      case (cBatch, index) => cBatch  //j'ajoute chaque etape a la liste
    }.toList // je la transforme en liste
  }

//-------------------------------------------------------------------------------------------------------------------------------------------------------------

}
