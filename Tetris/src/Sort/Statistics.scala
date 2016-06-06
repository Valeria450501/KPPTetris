package Sort
import java.util.ArrayList
class Statistics {
 var temp:IndexedSeq[Int] = IndexedSeq[Int]()
  
 def setArrayList(tempList: ArrayList[Int]) = {
   temp = for (i <-0 to tempList.size-1) yield tempList.get(i)
 }
 
 def foundResult() :Int =  {
   temp.sum/temp.size
 }
}