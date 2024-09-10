package com.arl.localwebsocet

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.arl.localwebsocet.databinding.ActivityMainBinding
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

/**
 * Created by AralBenli on 9.09.2024.
 */
class LocalWebSocketListener(private val activity: AppCompatActivity, private val binding: ActivityMainBinding) : WebSocketListener() {

    private lateinit var webSocket: WebSocket

    override fun onOpen(webSocket: WebSocket, response: Response) {
        this.webSocket = webSocket
        Log.e("WebSocket", "Connected to server")
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        Log.d("WebSocket", "Message received: $text")
        activity.runOnUiThread {
            binding.textView.text = "Websocket Message: $text"
        }
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        Log.e("WebSocket", "Connection failed: ${t.message}")
        activity.runOnUiThread {
            binding.textView.text = "Connection failed: ${t.message}"
        }
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        Log.e("WebSocket", "Connection closing: Code $code, Reason $reason")
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        Log.e("WebSocket", "Connection closed: Code $code, Reason $reason")
    }


    companion object {
        const val CONNECTION_CONSTANT = 1000
    }
}