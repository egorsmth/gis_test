import cats.effect._
import cats.effect.testing.scalatest.AsyncIOSpec
import main.{Browser, Crawler}
import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.model.Document
import org.scalatest.matchers.should.Matchers
import org.scalatest.freespec.AsyncFreeSpec

import java.net.URL

class TestCrawl extends AsyncFreeSpec with AsyncIOSpec with Matchers {

  val googleUrl = "http://google.com"
  object MockBrowser extends Browser {
    val browser = new JsoupBrowser()

    override def get(url: URL): IO[Either[Throwable, Document]] = {
      url.toString match {
        case googleUrl => IO.delay(browser.parseString("<!DOCTYPE html>\n<html lang=\"en\">\n<head>\n    <meta charset=\"UTF-8\">\n    <title>google title</title>\n</head>\n<body>\n\n</body>\n</html>")).attempt
      }
    }
  }

  "Crawl" - {
    "test get titles" in {
      Crawler.crawl(List(new URL(googleUrl)), MockBrowser)
        .asserting(res =>  res.result shouldBe Map(googleUrl -> "google title"))
    }
  }
}
