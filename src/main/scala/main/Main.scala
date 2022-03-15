package main

import cats.effect.{ExitCode, IO, IOApp}
import main.Route.crawlerRoute
import org.http4s.blaze.server.BlazeServerBuilder
import org.http4s.server.Router

object Main extends IOApp {

  override def run(args: List[String]): IO[ExitCode] = {
    val httpApp = Router("/" -> crawlerRoute)
      .orNotFound

    BlazeServerBuilder[IO]
      .bindHttp(8080, "127.0.0.1")
      .withHttpApp(httpApp)
      .resource.use(_ => IO.never)
      .as(ExitCode.Success)
  }
}
