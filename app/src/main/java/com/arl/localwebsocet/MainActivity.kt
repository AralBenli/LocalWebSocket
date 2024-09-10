package com.arl.localwebsocet

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.arl.localwebsocet.databinding.ActivityMainBinding
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var ws: WebSocket
    private lateinit var client: OkHttpClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        client =  OkHttpClient()

        with(binding) {
            connect.setOnClickListener {
                Log.d("LocalWebSocket", "Connecting...")
                val request: Request = Request
                    .Builder()
                    .url("ws://10.0.2.2:8080")
                    .build()
                val listener = LocalWebSocketListener(this@MainActivity , binding)
                ws = client.newWebSocket(request, listener)
            }

            disconnect.setOnClickListener {
                Log.d("LocalWebSocket", "Disconnecting")
                ws.close(LocalWebSocketListener.CONNECTION_CONSTANT, "Goodbye!...")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        client.dispatcher.executorService.shutdown()
    }
}