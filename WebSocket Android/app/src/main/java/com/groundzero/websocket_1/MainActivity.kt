package com.groundzero.websocket_1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*

class MainActivity : AppCompatActivity() {

    private lateinit var socket: WebSocket

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val client = OkHttpClient()
        val request: Request = Request.Builder().url("$SERVER_URL:$PORT").build()
        socket = client.newWebSocket(request, socketListener())

        send_message.setOnClickListener {
            socket.send("Android client message!")
        }
    }

    private fun socketListener() = object : WebSocketListener() {
        override fun onOpen(webSocket: WebSocket, response: Response) {
            super.onOpen(webSocket, response)
            println("Connection opened")
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            super.onMessage(webSocket, text)
            println(text)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        socket.close(CLOSING_STATUS, null)
    }

    companion object {
        private const val SERVER_URL = "ws://192.168.0.16"
        private const val PORT = "3000"
        private const val CLOSING_STATUS = 1
    }
}