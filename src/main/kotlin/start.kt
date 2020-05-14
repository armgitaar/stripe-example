package com.stripe.example

import com.stripe.Stripe
import com.stripe.example.configs.MailConfig
import com.stripe.model.PaymentIntent
import com.stripe.param.PaymentIntentCreateParams
import dev.alpas.Alpas
import dev.alpas.JsonSerializer
import dev.alpas.config
import dev.alpas.http.HttpCall
import dev.alpas.mailing.MailMessage

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

        post("payment-success") {
            send(this)
        }

    }.middlewareGroup("web")

}.ignite()

class CreatePaymentResponse(val clientSecret: String)

fun send(call: HttpCall) {
    val mail = MailMessage().apply {
        to = call.body
        subject = "Order Confirmed"
        message = "Here is your order receipt!"
    }
    call.config<MailConfig>().driver().send(mail)
}