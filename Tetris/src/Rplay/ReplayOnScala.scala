package Rplay
import java.io.PrintWriter
class ReplayOnScala {
  var fileForNotation = new PrintWriter("ScalaNatation.txt")
  
  def createNewReplay(){
   fileForNotation.println("NewGame") 
  }
  
  def printComplexity(complexity: String) = {
    fileForNotation.println("Complexity this game:" + complexity)
    
  }
  
  def printBoard(i:String, j: String, part: String) ={
    part match {
      case "N" => fileForNotation.println("Coord [" + i + "] [" + j + "] = " + "NoShape")
      case "L" => fileForNotation.println("Coord [" + i + "] [" + j + "] = " + "LSahpe")
      case "Z" => fileForNotation.println("Coord [" + i + "] [" + j + "] = " + "ZSahpe")
      case "S" => fileForNotation.println("Coord [" + i + "] [" + j + "] = " + "SSahpe")
      case "I" => fileForNotation.println("Coord [" + i + "] [" + j + "] = " + "ISahpe")
      case "T" => fileForNotation.println("Coord [" + i + "] [" + j + "] = " + "TSahpe")
      case "Q" => fileForNotation.println("Coord [" + i + "] [" + j + "] = " + "QSahpe")
      case "M" => fileForNotation.println("Coord [" + i + "] [" + j + "] = " + "MSahpe")
    }
  }
  
  def printNewLine() = {
    fileForNotation.println()
  }
  
  def printScore(score:String) = {
    fileForNotation.println("Score on that moment: " + score)
  }
  
  def printNewShape(newShape: String) = {
    fileForNotation.println("Next new shape: " + newShape)
  }
  
  def printNextShape(nextShape: String) = {
    fileForNotation.println("New shape for next step: " + nextShape)
  }
  
  def endOfWrite() = {
    fileForNotation.close()
  }
}