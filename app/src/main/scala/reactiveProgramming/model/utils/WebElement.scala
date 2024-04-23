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


  /*
  def getBody(url: String): String =
    val request = HttpGet(url)
    val requestConfig = RequestConfig.custom().setConnectionRequestTimeout(Timeout.ofSeconds(5)).setResponseTimeout(Timeout.ofSeconds(5)).build()
    HttpClients.createDefault().execute(request, BasicHttpClientResponseHandler())

  def parseHTML(HTMLBody: String): Set[String] =
    val links = mutable.HashSet.empty
    ???
  */



@main def tryRequest(): Unit =
  val webElement = WebElementImpl("http://en.wikipedia.org/", "wikipedia", 2).connect()
  webElement._2.forEach(e => WebElementImpl(e, "wikipedia", 2).connect())