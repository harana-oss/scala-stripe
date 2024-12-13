package com.outr.stripe

import io.circe._
import io.circe.parser._

import scala.reflect.ClassTag

object Pickler {
  private val entryRegex = """"(.+)": (.+)""".r
  private val snakeRegex = """_([a-z])""".r
  private val camelRegex = """([A-Z])""".r

  def read[T](jsonString: String)(implicit decoder: Decoder[T], classTag: ClassTag[T]): T = {
    // Snake to Camel
    val json = entryRegex.replaceAllIn(jsonString, (regexMatch) => {
      val key = snakeRegex.replaceAllIn(regexMatch.group(1), (snakeMatch) => {
        snakeMatch.group(1).toUpperCase
      })
      s""""$key": ${regexMatch.group(2)}"""
    })
    // Use Circe to decode the JSON into a case class
    decode[T](json) match {
      case Left(error) => throw new PicklerException(s"Unable to decode $jsonString (${classTag.runtimeClass.getName})", error)
      case Right(value) => value
    }
  }
}