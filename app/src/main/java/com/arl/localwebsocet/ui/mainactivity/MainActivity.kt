package com.arl.localwebsocet.ui.mainactivity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.arl.localwebsocet.LocalWebSocketListener
import com.arl.localwebsocet.R
import com.arl.localwebsocet.ui.adapter.WebSocketItemResponseAdapter
import com.arl.localwebsocet.databinding.ActivityMainBinding
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var ws: WebSocket
    private lateinit var client: OkHttpClient
    private lateinit var messageAdapter: WebSocketItemResponseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        client =  OkHttpClient()

        messageAdapter = WebSocketItemResponseAdapter()

        binding.recyclerView.adapter = messageAdapter
        messageAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                super.onChanged()
                updateItemCount()
            }

            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                updateItemCount()
            }

            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                super.onItemRangeRemoved(positionStart, itemCount)
                updateItemCount()
            }
        })

        with(binding) {
            connect.setOnClickListener {
                Log.d("LocalWebSocket", "Connecting...")
                val request: Request = Request
                    .Builder()
                    .url("ws://10.0.2.2:8080")
                    .build()
                val listener = LocalWebSocketListener(this@MainActivity , this@MainActivity , binding ,messageAdapter)
                ws = client.newWebSocket(request, listener)
            }

            disconnect.setOnClickListener {
                Log.d("LocalWebSocket", "Disconnecting")
                ws.close(LocalWebSocketListener.CONNECTION_CONSTANT, "Goodbye!...")
            }
        }
    }

    private fun updateItemCount() {
        val itemCount = messageAdapter.itemCount
        binding.itemSize.text = getString(R.string.item_count, itemCount)
    }

    override fun onDestroy() {
        super.onDestroy()
        client.dispatcher.executorService.shutdown()
    }
}