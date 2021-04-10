import Utils._
import breeze.plot.Figure

import java.util
import scala.collection.JavaConverters._
import scala.io.Source
import scala.util.Random

object Main {

  def main(args: Array[String]): Unit = {

    val colors = List("[255,0,0]",
      "[0,255,0]", "[0,0,255]",
      "[204,0,204]", "[128,102,51]",
      "[5,50,0]", "[25,12,5]",
      "[0,25,56]", "[4,52,65]",
      "[0,4,6]", "[0,14,90]",
      "[0,102,51]", "[0,150,0]",
      "[0,50,0]", "[255,255,0]",
      "[180,0,255]", "[255,106,0]",
      "[255,0,255]", "[88,88,88]",
      "[0,0,255]", "[0,0,180]",
      "[170,0,100]", "[100,50,0]",
      "[180,180,50]", "[0,250,100]",
      "[0,255,200]", "[100,100,255]",
      "[0,255,0]",      "[255, 0, 0]",
      "[145, 40, 59]",    "[109, 7, 26]",
      "[132, 46, 27]",    "[187, 11, 11]",
      "[231, 62, 1]",    "[237, 0, 0]",
      "[191, 48, 48]",    "[164, 36, 36]",
      "[199, 44, 72]",    "[253, 63, 146]",
      "[233, 56, 63]",    "[110, 11, 20]",
      "[254, 150, 160]",    "[255, 111, 125]",
      "[255, 0, 255]",    "[128, 0, 128]",
      "[219, 0, 115]",    "[212, 115, 212]",
      "[252, 93, 93]",    "[221, 152, 92]",
      "[145, 40, 59]",    "[158, 14, 64]",
      "[129, 20, 83]",    "[255, 0, 127]",
      "[217, 1, 21]",    "[247, 35, 12]",
      "[165, 38, 10]",    "[107, 13, 13]",
      "[255, 94, 77]",    "[184, 32, 16]",
      "[150, 0, 24]",    "[219, 23, 2]",
      "[253, 70, 38]",    "[198, 8, 0]",
      "[150, 0, 24]",    "[220, 20, 60]",
      "[169, 17, 1]",    "[235, 0, 0]",
      "[128, 24, 24]",    "[247, 35, 12]",
      "[188, 32, 1]",    "[254, 27, 0]",
      "[255, 73, 1]",    "[238, 16, 16]",
      "[207, 10, 29]",    "[198, 8, 0]",
      "[224, 17, 95]",    "[133, 6, 6]",
      "[222, 41, 22]",    "[174, 74, 52]",
      "[169, 17, 1]",    "[219, 23, 2]",
      "[253, 70, 38]",    "[199, 21, 133]",
      "[152, 87, 23]",    "[115, 8, 0]",
      "[141, 64, 36]",    "[204, 78, 92]",
      "[255, 9, 33]",    "[108, 2, 119]"
    )



    var i = 0
    val filename = "./iris.data"
    var irisSepal = new util.ArrayList[Point2D]()
    var irisPetal = new util.ArrayList[Point2D]()

    Source.fromFile(filename).getLines
      .withFilter(line => line != "")
      .foreach(f = line => {
        var t = line.split(",")

        irisPetal.add( Point2D(t(2).toDouble, t(3).toDouble))
        irisSepal.add( Point2D(t(0).toDouble, t(1).toDouble))

        i += 1
      })


    val PointsSepal: List[Point2D] = irisSepal.asScala.toList
    val PointsPetal : List[Point2D] = irisPetal.asScala.toList



    var fin = kMeans(PointsSepal,3)
    fin.last.clusters.foreach(_.centroid.C = Random.shuffle(colors).take(1).head)
  
    var t = kMeans(PointsPetal,3)
    t.last.clusters.foreach(_.centroid.C = Random.shuffle(colors).take(1).head)


//-------------------------------------------------------------------------------------------------------------------------------------------------------------
    val F2 = Figure("Data visualization k-Means")
    val p6 = F2.subplot(0)
    p6.title = "Sepal data Before clustering"
    p6.xlabel = "Sepallength"
    p6.ylabel = "Sepalwidth"

    for (i <- fin.head.clusters.indices){
      p6+=breeze.plot.plot(List(fin.head.clusters(i).centroid.x),List(fin.head.clusters(i).centroid.y),'+',"[0,0,0]")
    }
    for (k <- PointsSepal.indices){
      p6+=breeze.plot.plot(List(PointsSepal(k).x),List(PointsSepal(k).y),'.',PointsSepal(k).C)
    }
/*
//-------------------------------------------------------------------------------------------------------------------------------------------------------------
    val F1 = Figure("Data visualization k-Means")
    val p3 = F1.subplot(0)
    p3.title = "Petal data before clustering"
    p3.xlabel = "Petallength"
    p3.ylabel = "Petalwidth"

    for (i <- t.head.clusters.indices){
      p3+=breeze.plot.plot(List(t.head.clusters(i).centroid.x),List(t.head.clusters(i).centroid.y),'+',"[0,0,0]")
    }
    for (u <- PointsPetal.indices){
      p3+=breeze.plot.plot(List(PointsPetal(u).x),List(PointsPetal(u).y),'.',PointsPetal(u).C)
    }
*/

//-------------------------------------------------------------------------------------------------------------------------------------------------------------
    val F = Figure("Data visualization k-Means")
    val p1 = F.subplot(0)
    p1.title = "Sepal data after clustering"
    p1.xlabel = "Sepallength"
    p1.ylabel = "Sepalwidth"

    for (i <- fin.last.clusters.indices) {
      p1+=breeze.plot.plot(List(fin.last.clusters(i).centroid.x),List(fin.last.clusters(i).centroid.y),'+',fin.last.clusters(i).centroid.C)
      val pts = fin.last.clusters(i).points
      for (j <- pts.indices){
          p1+=breeze.plot.plot(List(pts(j).x),List(pts(j).y),'.',fin.last.clusters(i).centroid.C)
      }
    }
/*
//-------------------------------------------------------------------------------------------------------------------------------------------------------------
 
    val F5 = Figure("Data visualization k-Means")
    val p = F5.subplot(0)
    p.title = "Petal data after clustering"
    p.xlabel = "Petallength"
    p.ylabel = "Petalwidth"
    val taille = t.last.clusters
    for (i <- taille.indices) {
      p+=breeze.plot.plot(List(taille(i).centroid.x),List(taille(i).centroid.y),'+',taille(i).centroid.C)
      val pts = taille(i).points
      for (j <- pts.indices){
        p+=breeze.plot.plot(List(pts(j).x),List(pts(j).y),'.',taille(i).centroid.C)
      }
    }
    */

  }

}
