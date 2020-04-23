package com.stripe.example

import dev.alpas.Alpas

fun main(args: Array<String>) = Alpas(args).routes { addRoutes() }.ignite()
