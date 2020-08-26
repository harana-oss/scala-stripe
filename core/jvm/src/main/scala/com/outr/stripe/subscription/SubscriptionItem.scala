package com.outr.stripe.subscription

import com.outr.stripe.price.Price

case class SubscriptionItem(id: String,
                            `object`: String,
                            billingThresholds: Option[BillingThresholds],
                            created: Long,
                            metadata: Map[String, String],
                            paymentBehavior: Option[String],
                            plan: Plan,
                            price: Price,
                            prorationBehavior: Option[String],
                            prorationDate: Option[Long],
                            quantity: Option[Int],
                            subscription: String,
                            taxRates: List[String])