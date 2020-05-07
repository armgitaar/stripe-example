package com.stripe.example

import dev.alpas.Alpas
import dev.alpas.http.HttpCall

fun main(args: Array<String>) = Alpas(args).routes { addRoutes() }.ignite()
