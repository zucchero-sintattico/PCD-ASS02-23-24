package reactiveProgramming.model.utils

import org.jsoup.Jsoup
import reactiveProgramming.model.utils.WebElement.WebElementImpl

object WebElement:
  case class WebElementImpl(private val url: String, private val wordToFind: String, private val depth: Int):
    def connect(): (Int, java.util.List[String]) =
      val doc = Jsoup.connect(url).get()
      val word = doc.html().trim.replaceAll("<[^>]*>", "").lines().map(_.toLowerCase).filter(_.contains(wordToFind)).count().toInt
      val links = doc.select("a").stream().map(_.attr("href")).filter(_.contains("https://")).skip(1).toList
      println(s"occorrenze trovate: $word\nin: $url")
      (word, links)



@main def tryRequest(): Unit =
  val url = "http://en.wikipedia.org/"
  val word = "wikipedia"
  val depth = 2
  val webElement = WebElementImpl(url, word, depth).connect()
  webElement._2.forEach(WebElementImpl(_, "wikipedia", depth - 1).connect())