package main

import cats.data.Validated.{Invalid, Valid}
import cats.effect.IO
import org.http4s._
import org.http4s.dsl.io._
import org.http4s.circe._
import io.circe.syntax._
import io.circe.generic.auto._
import main.Validation.validateUrls

case class ScrapResponse(result: Map[String, String], errors: Map[String, String])

object UrlsQueryParamMatcher extends OptionalMultiQueryParamDecoderMatcher[String]("urls")

object Route {

  val crawlerRoute: HttpRoutes[IO] = HttpRoutes.of[IO] {
    case GET -> Root :? UrlsQueryParamMatcher(urls) =>
      urls match {
        case Valid(urls) =>
          val res = for {
            validationResult <- IO.pure(validateUrls(urls))
            crawlerResult <- Crawler.crawl(validationResult.urls, Browser)
          } yield ScrapResponse(crawlerResult.result, validationResult.errors ++ crawlerResult.errors).asJson

          Ok(res)
        case Invalid(_) => BadRequest()
      }
  }
}
