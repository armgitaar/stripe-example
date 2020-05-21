package com.stripe.example.drivers

import com.sparkpost.Client
import dev.alpas.Environment
import dev.alpas.mailing.MailMessage
import dev.alpas.mailing.drivers.MailDriver

class SparkPostDriver(private val env: Environment) : MailDriver {
    override fun send(mail: MailMessage) {
        val apiKey = env("SPARKPOST_API_KEY")
        val defaultFromAddress = env("MAIL_FROM_ADDRESS")

        val client = Client(apiKey)
        client.sendMessage(defaultFromAddress, mail.to, mail.subject, mail.message, mail.message)
    }
}