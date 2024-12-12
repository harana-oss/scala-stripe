package com.outr.stripe

import com.outr.stripe.support._
import sttp.model.Uri

class Stripe(val apiKey: String) extends Restful {

  override protected def url(endPoint: String): Uri = Uri.parse(s"https://api.stripe.com/v1/$endPoint").toOption.get

  lazy val balance: BalanceSupport = new BalanceSupport(this)
  lazy val charges: ChargesSupport = new ChargesSupport(this)
  lazy val customers: CustomersSupport = new CustomersSupport(this)
  lazy val disputes: DisputesSupport = new DisputesSupport(this)
  lazy val events: EventsSupport = new EventsSupport(this)
  lazy val refunds: RefundsSupport = new RefundsSupport(this)
  lazy val tokens: TokensSupport = new TokensSupport(this)
  lazy val transfers: TransfersSupport = new TransfersSupport(this)
  lazy val accounts: AccountsSupport = new AccountsSupport(this)
  lazy val applicationFees: ApplicationFeesSupport = new ApplicationFeesSupport(this)
  lazy val countrySpecs: CountrySpecsSupport = new CountrySpecsSupport(this)
  lazy val coupons: CouponsSupport = new CouponsSupport(this)
  lazy val discounts: DiscountSupport = new DiscountSupport(this)
  lazy val invoices: InvoicesSupport = new InvoicesSupport(this)
  lazy val invoiceItems: InvoiceItemsSupport = new InvoiceItemsSupport(this)
  lazy val plans: PlansSupport = new PlansSupport(this)
  lazy val prices: PricesSupport = new PricesSupport(this)
  lazy val products: ProductsSupport = new ProductsSupport(this)
  lazy val subscriptions: SubscriptionsSupport = new SubscriptionsSupport(this)
  lazy val subscriptionItems: SubscriptionItemsSupport = new SubscriptionItemsSupport(this)
}

object Stripe {
  val Version = "2016-07-06"
}