package com.outr.stripe

import io.circe.Decoder
import sttp.client4.*
import sttp.client4.circe.*
import sttp.client4.httpclient.HttpClientFutureBackend
import sttp.model.{Header, HeaderNames, Method, StatusCode, Uri}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.reflect.ClassTag

trait Restful extends Implicits {

  def apiKey: String

  protected def url(endPoint: String): Uri

  private lazy val backend = HttpClientFutureBackend()

  private[stripe] def get[R](endPoint: String,
                             config: QueryConfig,
                             data: (String, String)*)
                            (implicit decoder: Decoder[R], classTag: ClassTag[R]): Future[Either[ResponseError, R]] = {
    process[R](Method.GET, endPoint = endPoint, config = config, data = data)
  }

  private[stripe] def post[R](endPoint: String,
                              config: QueryConfig,
                              data: (String, String)*)
                             (implicit decoder: Decoder[R], classTag: ClassTag[R]): Future[Either[ResponseError, R]] = {
    process[R](Method.POST, endPoint = endPoint, config = config, data = data)
  }

  private[stripe] def delete[R](endPoint: String,
                                config: QueryConfig,
                                data: (String, String)*)
                               (implicit decoder: Decoder[R], ct: ClassTag[R]): Future[Either[ResponseError, R]] = {
    process[R](Method.DELETE, endPoint = endPoint, config = config, data = data)
  }

  private[stripe] def process[R](method: Method,
                                 endPoint: String,
                                 config: QueryConfig,
                                 data: Seq[(String, String)])
                                (implicit decoder: Decoder[R], ct: ClassTag[R]): Future[Either[ResponseError, R]] = {

    val headers = List(
      Header("Stripe-Version", Stripe.Version),
      Header(HeaderNames.Authorization, s"Bearer $apiKey")
    ) ++ config.idempotencyKey.map(key => Header("Idempotency-Key", key))

    val queryParams = data ++ {
      if (config.limit != QueryConfig.default.limit) Seq("limit" -> config.limit.toString)
      else Nil
    } ++ {
      config.startingAfter.map("starting_after" -> _).toSeq ++
        config.endingBefore.map("ending_before" -> _).toSeq
    }

    val baseUri = this.url(endPoint)

    val request = method match {
      case Method.POST =>
        basicRequest
        .headers(headers*)
        .body(queryParams, "utf-8")
        .post(baseUri)
      case _ =>
        basicRequest
          .headers(headers*)
          .method(method, baseUri.addParams(queryParams*))
    }

    request.send(backend).map(response => {
      if (response.code == StatusCode.Ok)
        Right(Pickler.read[R](response.body.toOption.get))
      else {
        val error = response.body match {
          case Left(l) => l
          case Right(r) => r
        }
        val wrapper = Pickler.read[ErrorMessageWrapper](error)
        Left(ResponseError(response.statusText, response.code.code, wrapper.error))
      }
    })
  }

  def dispose(): Unit = {
    backend.close()
  }
}

case class ResponseError(text: String, code: Int, error: ErrorMessage)

case class ErrorMessageWrapper(error: ErrorMessage)

case class ErrorMessage(message: String, `type`: String, param: Option[String], code: Option[String])