package main

import java.net.URL
import scala.util.Try

object Validation {

  case class ValidationResult(errors: Map[String, String], urls: List[URL])

  def validateUrl(url: String): Either[Throwable, URL] = {
    Try(new URL(url)).toEither
  }

  def validateUrls(urls: List[String]): ValidationResult = {
    val (errors, result) = urls.distinct
      .map(url => url -> validateUrl(url))
      .partitionMap { case (url, throwableOrUri) =>
        throwableOrUri.left.map(url -> _.getMessage)
      }
    ValidationResult(errors.toMap, result)
  }
}
