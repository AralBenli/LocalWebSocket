package com.arl.localwebsocet

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.arl.localwebsocet.databinding.ActivityMainBinding
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import org.json.JSONObject

/**
 * Created by AralBenli on 9.09.2024.
 */
class LocalWebSocketListener(
    private val activity: AppCompatActivity,
    private val binding: ActivityMainBinding
) : WebSocketListener() {

    private lateinit var webSocket: WebSocket

    override fun onOpen(webSocket: WebSocket, response: Response) {
        this.webSocket = webSocket
        binding.textView.text = "Connected to server"
        Log.e("WebSocket", "Connected to server")
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        Log.d("WebSocket", "Message received: $text")
        activity.runOnUiThread {
            val jsonObject = JSONObject(text)
            val id = jsonObject.getInt("id")
            val description = jsonObject.getString("description")
            val imageUrl = jsonObject.getString("imageUrl")
            val title = jsonObject.getString("title")
            val booleanStatus = jsonObject.getBoolean("booleanStatus")

            binding.tvId.text = "Id: $id"
            binding.tvDescription.text = "Description: $description"
            binding.tvTitle.text = "Title: $title"
            binding.switchStatus.isChecked = booleanStatus
            binding.ivImage.setImageUrl(imageUrl)

            Log.d(
                "WebSocket",
                "Received model: id=$id, description=$description, imageUrl=$imageUrl, title=$title, booleanStatus=$booleanStatus"
            )

        }
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        Log.e("WebSocket", "Connection failed: ${t.message}")
            binding.textView.text = "Disconnected from server"

    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        binding.textView.text = "Disconnected from server"
        Log.e("WebSocket", "Connection closing: Code $code, Reason $reason")
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        binding.textView.text = "Disconnected from server"
        Log.e("WebSocket", "Connection closed: Code $code, Reason $reason")
    }


    companion object {
        const val CONNECTION_CONSTANT = 1000
    }
}