package com.stripe.example

import com.stripe.Stripe
import com.stripe.model.PaymentIntent
import com.stripe.param.PaymentIntentCreateParams
import dev.alpas.http.HttpCall
import dev.alpas.routing.Router
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.annotations.SerializedName
import dev.alpas.routing.Controller

fun Router.addRoutes() = apply {
    group {

        get("/", StripeController::index)

        post("/create-payment-intent", StripeController::post)

    }.middlewareGroup("web")
}

class StripeController : Controller(){
    fun index(call: HttpCall){
        call.render("welcome")
    }

    fun post(call: HttpCall){

        Stripe.apiKey = "sk_test_4eC39HqLyjWDarjtT1zdp7dc"

        val postBody: CreatePayment = objectMapper.readValue(call.body, CreatePayment::class.java)
        val createParams = PaymentIntentCreateParams.Builder()
                .setCurrency("usd")
                .setAmount(calculateOrderAmount(postBody.items).toLong())
                .build()
        val intent = PaymentIntent.create(createParams)
        val paymentResponse = CreatePaymentResponse(intent.clientSecret)

        call.reply(objectMapper.writeValueAsString(paymentResponse))
    }
}

private val objectMapper: ObjectMapper = ObjectMapper()

fun calculateOrderAmount(items: Array<Any?>?): Int {
    return 1400
}

internal class CreatePayment {
    @SerializedName("items")
    lateinit var items: Array<Any?>
}

internal class CreatePaymentResponse(private val clientSecret: String)

