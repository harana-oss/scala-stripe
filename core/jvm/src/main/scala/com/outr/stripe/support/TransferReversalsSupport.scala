package com.outr.stripe.support

import com.outr.stripe.transfer.TransferReversal
import com.outr.stripe.{Implicits, Money, QueryConfig, ResponseError, Stripe, StripeList}

import scala.concurrent.Future

class TransferReversalsSupport(stripe: Stripe) extends Implicits {
  def create(transferId: String,
             amount: Option[Money] = None,
             description: Option[String] = None,
             metadata: Map[String, String] = Map.empty,
             refundApplicationFee: Boolean = false): Future[Either[ResponseError, TransferReversal]] = {
    val data = List(
      write("amount", amount),
      write("description", description),
      write("metadata", metadata),
      write("refund_application_fee", if (refundApplicationFee) Some(refundApplicationFee) else None)
    ).flatten
    stripe.post[TransferReversal](s"transfers/$transferId/reversals", QueryConfig.default, data*)
  }

  def byId(transferId: String, transferReversalId: String): Future[Either[ResponseError, TransferReversal]] = {
    stripe.get[TransferReversal](s"transfers/$transferId/reversals/$transferReversalId", QueryConfig.default)
  }

  def update(transferId: String,
             transferReversalId: String,
             description: Option[String] = None,
             metadata: Map[String, String] = Map.empty): Future[Either[ResponseError, TransferReversal]] = {
    val data = List(
      write("description", description),
      write("metadata", metadata)
    ).flatten
    stripe.post[TransferReversal](s"transfers/$transferId/reversals/$transferReversalId", QueryConfig.default, data*)
  }

  def list(transferId: String,
           config: QueryConfig = QueryConfig.default): Future[Either[ResponseError, StripeList[TransferReversal]]] = {
    stripe.get[StripeList[TransferReversal]](s"transfers/$transferId/reversals", config)
  }
}
