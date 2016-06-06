package Sort
import java.io.File
import scala.io.Source
import scala.io.Source._
import java.util.ArrayList
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.HashMap


class ScalaSort {
  var bestOfScore = ""
  var bestOfTimes = ""
  var worstOfScore = ""
  var worstOfTimes = ""
  val timesList: ArrayList[Int] = new ArrayList[Int]()
  def sort(dir:String){
    creatingMaps(getListOfLogs(dir))
  }

  def getBestOfScore() = 
    bestOfScore

  def getBestOfTimes() = 
    bestOfTimes

  def getWorstOfScore() = 
    worstOfScore

  def getWorstOfTimes() = 
    worstOfTimes

  def getListOfLogs(dir:String):ArrayBuffer[File] = {
    val source = new File(dir)
    val list = source.listFiles()
    val listNew = ArrayBuffer[File]()
    val i =0
    for( i<-0 to list.length-1)     
     if(list.apply(i).toString().contains("Game")) {
       val temp = list.apply(i)
       if(list.apply(i).toString().contains(".txt")){
         listNew += temp
       }
     }
    listNew
  }

  def creatingMaps(files:ArrayBuffer[File]) = {
    val mapOfScore = scala.collection.mutable.Map[String, Int]()
    val mapOfTimes = scala.collection.mutable.Map[String, Int]()
    for(i<-0 to files.length-1){
      val source = Source.fromFile(files.apply(i))
      val lines = source.getLines().toArray
      
      if(lines.apply(0).toString().equals("NewGame")){
        val fileName = files.apply(i).toString()
        val fileScore = lines.apply(lines.length-2).toInt
        val fileTimes = (lines.length - 2)/3
        
        timesList.add(fileTimes)
        mapOfScore += fileName->fileScore
        mapOfTimes += fileName->fileTimes
      }
    }
    
    val ListScore = mergeSort(mapOfScore.toList)
    val ListTimes = mergeSort(mapOfTimes.toList)
    
    val stringBestScore = ListScore(ListScore.length-1).toString()
    val stringWorstScore = ListScore(0).toString()
    val stringBestTimes = ListTimes(ListTimes.length-1).toString()
    val stringWorstTimes = ListTimes(0).toString()
    bestOfScore = stringBestScore.substring(1, stringBestScore.indexOf(','))
    worstOfScore = stringWorstScore.substring(1, stringWorstScore.indexOf(','))
    bestOfTimes = stringBestTimes.substring(1, stringBestTimes.indexOf(','))
    worstOfTimes = stringWorstTimes.substring(1, stringWorstTimes.indexOf(','))
  }
  
  def getListTimes():ArrayList[Int] = {
    timesList
  }

  def mergeSort(xs: List[(String, Int)]): List[(String, Int)] = {
    val n = xs.length / 2
    if (n == 0) xs
    else {
      def merge(xs: List[(String, Int)], ys: List[(String, Int)]): List[(String, Int)] =
        (xs, ys) match {
          case (Nil, ys) => ys
          case (xs, Nil) => xs
          case (x :: xs1, y :: ys1) =>
            if (x._2 < y._2) x :: merge(xs1, ys)
            else y :: merge(xs, ys1)
        }
      val (left, right) = xs splitAt (n)
      merge(mergeSort(left), mergeSort(right))
    }
  }
}