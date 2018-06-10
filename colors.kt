import org.otfried.cs109ui.*
import org.otfried.cs109ui.ImageCanvas
import org.otfried.cs109.Color
import org.otfried.cs109.DrawStyle
import java.util.Random

import java.awt.image.BufferedImage



fun draw(image: BufferedImage, delta: Int): Pair<Boolean, Int> {

 var rows = 4 
 var cols = 4
 var x = 70.0
 var y = 90.0
 var itX = 0
 var itY = 0
 val difX = Random().nextInt(rows)
 val difY = Random().nextInt(cols)
 val randNum = Random().nextInt(2)
 var ak = 1
 var letterY = 160.0
 var letterX = 95.0
 val chars = arrayOf("A", "B", "C", "D")
 val g = ImageCanvas(image)
 var counter = 0
 var answer = ""
 g.clear(Color.WHITE)




 var colorHSV = randomHSV()
 var colorRGB = hsvtorgb(colorHSV.first, colorHSV.second, colorHSV.third)

 while(itX < rows)  {

    while(itY < cols) {

     if(itX == difX && itY == difY) {
     if(randNum == 1 ) {
        colorRGB = hsvtorgb((colorHSV.first + delta) % 360, colorHSV.second, colorHSV.third)
     }else {
        colorRGB = hsvtorgb((colorHSV.first + 360 - delta) % 360, colorHSV.second, colorHSV.third)
     }

      g.setColor(Color(colorRGB.first, colorRGB.second, colorRGB.third))

      colorRGB = hsvtorgb(colorHSV.first, colorHSV.second, colorHSV.third)
    }else {
     
      g.setColor(Color(colorRGB.first, colorRGB.second, colorRGB.third))
    }

    g.drawRectangle(x, y, 100.0, 100.0)
    x = x + 115.0
    itY ++
  }
    g.setColor(Color(colorRGB.first, colorRGB.second, colorRGB.third))
    g.setFont(75.0, "Batang")
    g.drawText("${chars[itX]}", letterX, 80.0)
    g.drawText("${ak}", 10.0, letterY)
    letterY = letterY + 120
    letterX = letterX + 115.0
    ak ++

  itY = 0
  itX ++
  y = y + 115.0
  x = x - 115.0 *4

}

show(image)
  
 do {
  
    println("Which square has a different color? (x to exit)")
    answer = readLine()!!
    if(answer == "x") {
     g.done()
     return Pair(false,counter)
    }else {
      var(asd, number, letter) = answer.split("")
      letter = letter.toUpperCase()
      var intNumber = number.toInt()
      counter ++
      if(intNumber == difX + 1 ) {
        var colNum = chars.indexOf(letter) + 1
        if(difY + 1 ==  colNum) {
         println("That is correct")
         return Pair(true,counter)
        }
      }else {
        println("That is not correct.  Square ${number}${letter} has a different color.")
        println("press enter to contunue")
        var ent = "a"
        while(ent != ""){
         ent = readLine()!!
        }

      }
    
    }
  }while(true)


  g.done()
  return Pair(true, counter)


}

fun randomHSV(): Triple<Int, Int, Int> {
  return Triple(Random().nextInt(360),
            128 + Random().nextInt(128),
    128 + Random().nextInt(128))
}


fun hsvtorgb(h: Int, s: Int, v: Int): Triple<Int, Int, Int> {
  if (s == 0) {
    // no color, just grey
    return Triple(v, v, v)
  } else {
    val sector = h / 60
    val f = (h % 60) 
    val p = v * ( 255 - s ) / 255
    val q = v * ( 15300 - s * f ) / 15300
    val t = v * ( 15300 - s * ( 60 - f )) / 15300
    return when(sector) {
      0 -> Triple(v, t, p)
      1 -> Triple(q, v, p)
      2 -> Triple(p, v, t)
      3 -> Triple(p, q, v)
      4 -> Triple(t, p, v)
      else -> Triple(v, p, q)
    }
  }
}

fun main(args: Array<String>) {
  setTitle("How good is your color vision")
  var delta = 20
  if(args.size > 0) {
    delta = args[0].toInt()
  }
   var counter = 0
   var rightAnswer = 0

   var aki = true
  val image = BufferedImage(600, 600, BufferedImage.TYPE_INT_RGB)
 do {

    println("You answered ${rightAnswer} of ${counter} tests correctly")

    var aki =  draw(image, delta)
    
    counter++
    rightAnswer = rightAnswer + aki.second

  }while(aki.first)
  close()
 
}
