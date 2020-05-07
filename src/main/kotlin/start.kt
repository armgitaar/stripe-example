package com.stripe.example

import com.stripe.Stripe
import com.stripe.model.PaymentIntent
import com.stripe.param.PaymentIntentCreateParams
import dev.alpas.Alpas
import dev.alpas.JsonSerializer

fun main(args: Array<String>) = Alpas(args).routes {

    group {

        get{ render("welcome")}

        post("create-payment-intent") {

            Stripe.apiKey = "sk_test_4eC39HqLyjWDarjtT1zdp7dc"

            val createParams = PaymentIntentCreateParams.Builder()
                    .setCurrency("usd")
                    .setAmount(1400)
                    .build()

            val intent = PaymentIntent.create(createParams)

            val paymentResponse = CreatePaymentResponse(intent.clientSecret)

            reply(JsonSerializer.serialize(paymentResponse))
        }

    }.middlewareGroup("web")

}.ignite()

class CreatePaymentResponse(val clientSecret: String)