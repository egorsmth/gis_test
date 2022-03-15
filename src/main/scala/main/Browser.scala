package main

import cats.effect.IO
import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.model.Document

import java.net.URL

trait Browser {
  def get(url: URL): IO[Either[Throwable, Document]]
}

object Browser extends Browser {
  private val browser = JsoupBrowser()

  override def get(url: URL): IO[Either[Throwable, Document]] = {
    IO.blocking(browser.get(url.toString)).attempt
  }
}