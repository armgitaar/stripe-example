package com.stripe.example.controllers

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.stripe.Stripe
import com.stripe.model.PaymentIntent
import com.stripe.param.PaymentIntentCreateParams
import dev.alpas.http.HttpCall
import dev.alpas.routing.Controller

// https://alpas.dev/docs/controllers
class WelcomeController : Controller() {
    fun index(call: HttpCall) {
        call.render("welcome")
    }

    fun payment(call: HttpCall) {

        // This is a sample test API key. Sign in to see examples pre-filled with your key.
        Stripe.apiKey = "sk_test_4eC39HqLyjWDarjtT1zdp7dc"

       // val request = call.jsonBody
        val postBody: CreatePayment = gson.fromJson(call.body, CreatePayment::class.java)
        val createParams = PaymentIntentCreateParams.Builder()
            .setCurrency("usd")
            .setAmount(calculateOrderAmount(postBody.items).toLong())
            .build()
        // Create a PaymentIntent with the order amount and currency
        val intent = PaymentIntent.create(createParams)
        val paymentResponse = CreatePaymentResponse(intent.clientSecret)

        call.reply(gson.toJson(paymentResponse)).asJson()
    }

    private val gson: Gson = Gson()
    fun calculateOrderAmount(items: Array<Any?>?): Int {
        // Replace this constant with a calculation of the order's amount
        // Calculate the order total on the server to prevent
        // users from directly manipulating the amount on the client
        return 1400
    }

    internal class CreatePayment {
        @SerializedName("items")
        lateinit var items: Array<Any?>
    }

    internal class CreatePaymentResponse(private val clientSecret: String)

}


