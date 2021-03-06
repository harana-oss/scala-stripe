package com.outr.stripe.connect

import com.outr.stripe.charge.Address

case class LegalEntity(address: Option[Address] = None,
                       addressKana: Option[AddressKana] = None,
                       addressKanji: Option[AddressKanji] = None,
                       businessName: Option[String] = None,
                       businessNameKana: Option[String] = None,
                       businessNameKanji: Option[String] = None,
                       businessTaxIdProvided: Option[Boolean] = None,
                       businessVatIdProvided: Option[Boolean] = None,
                       dob: Date,
                       firstName: Option[String] = None,
                       firstNameKana: Option[String] = None,
                       firstNameKanji: Option[String] = None,
                       gender: Option[String] = None,
                       lastName: Option[String] = None,
                       lastNameKana: Option[String] = None,
                       lastNameKanji: Option[String] = None,
                       maidenName: Option[String] = None,
                       personalAddress: Option[Address] = None,
                       personalAddressKana: Option[AddressKana] = None,
                       personalAddressKanji: Option[AddressKanji] = None,
                       personalIdNumberProvided: Option[Boolean] = None,
                       phoneNumber: Option[String] = None,
                       ssnLast4Provided: Option[Boolean] = None,
                       ssnLast4: Option[String] = None,
                       `type`: String,
                       verification: Verification)
