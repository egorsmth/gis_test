package main

import cats.Parallel.parTraverse
import cats.effect.IO

import java.net.URL

object Crawler {
  case class CrawlResult(errors: Map[String, String], result: Map[String, String])

  def crawlTitle(url: URL, browser: Browser): IO[(String, Either[Throwable, String])] = {
    browser
      .get(url)
      .map(_.map(_.title))
      .map(url.toString -> _)
  }

  def crawl(urls: List[URL], browser: Browser): IO[CrawlResult] = {
    parTraverse(urls)(crawlTitle(_, browser))
      .map(_.partitionMap {
        case (url, res) => res.map(url -> _).left.map(url -> _.getMessage)
      })
      .map { case (errors, res) => CrawlResult(errors.toMap, res.toMap)}
  }
}
