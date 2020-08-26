package com.outr.stripe.price

case class Price(id: String,
                 `object`: String,
                 active: Boolean,
                 billingScheme: Option[String],
                 created: Long,
                 currency: String,
                 livemode: Boolean,
                 lookupKey: Option[String],
                 metadata: Map[String, String],
                 nickname: Option[String],
                 product: String,
                 recurring: Option[Recurring],
                 transformQuantity: Option[TransformQuantity],
                 unitAmount: Int,
                 unitAmountDecimal: Option[BigDecimal])