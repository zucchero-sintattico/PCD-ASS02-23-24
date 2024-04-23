package reactiveProgramming.model.connection

import org.apache.hc.client5.http.classic.methods.HttpGet
import org.apache.hc.client5.http.config.RequestConfig
import org.apache.hc.client5.http.impl.classic.{BasicHttpClientResponseHandler, HttpClients}
import org.apache.hc.core5.util.Timeout

object HttpRequest:
  def getBody(url: String): String =
    val request = HttpGet(url)
    val requestConfig = RequestConfig.custom().setConnectionRequestTimeout(Timeout.ofSeconds(5)).setResponseTimeout(Timeout.ofSeconds(5)).build()
    HttpClients.createDefault().execute(request, BasicHttpClientResponseHandler())




@main def tryRequest: Unit =
  println(HttpRequest.getBody("https://www.google.com"))