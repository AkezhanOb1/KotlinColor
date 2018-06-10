import org.otfried.cs109ui.*
import org.otfried.cs109ui.ImageCanvas
import org.otfried.cs109.Color
import org.otfried.cs109.DrawStyle

import java.awt.image.BufferedImage

fun draw(image: BufferedImage, inp: Int) {
  // get ImageCanvas for the image

  val w = image.width
  val h = image.height
  val g = ImageCanvas(image)

  // clear background
  
  
  for (x in 0 until w) {
    for (y in 0 until h) {
      val ak = hsvtorgb(x, y, inp)
      
       val color = (ak.first * 65536) + (ak.second * 256) + ak.third
      image.setRGB(x, y, color)
    }
  }

  // done with drawing
  g.done()
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
  setTitle("Rainbow for v = ${args[0]}")
  val image = BufferedImage(360, 256, BufferedImage.TYPE_INT_RGB)
  val res = args[0].toInt()
  draw(image, res)
  show(image)
}