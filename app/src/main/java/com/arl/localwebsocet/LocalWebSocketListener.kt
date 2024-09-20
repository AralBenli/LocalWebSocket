package com.arl.localwebsocet

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.arl.localwebsocet.databinding.ActivityMainBinding
import com.arl.localwebsocet.model.WebSocketItemResponse
import com.arl.localwebsocet.ui.adapter.WebSocketItemResponseAdapter
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import org.json.JSONObject

/**
 * Created by AralBenli on 9.09.2024.
 */
class LocalWebSocketListener(
    private val context: Context,
    private val activity: AppCompatActivity,
    private val binding: ActivityMainBinding,
    private val messageAdapter: WebSocketItemResponseAdapter
) : WebSocketListener() {

    private lateinit var webSocket: WebSocket

    override fun onOpen(webSocket: WebSocket, response: Response) {
        this.webSocket = webSocket
        binding.textView.text = context.getString(R.string.connected_to_server)
        Log.e("WebSocket", "Connected to server")

        binding.interactionSwitch.setOnCheckedChangeListener { _, isChecked ->
            val message = if (isChecked) "true" else "false"
            webSocket.send(message)
            Log.d("WebSocket", "Sent message: $message")
        }
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        Log.d("WebSocket", "Message received: $text")
        activity.runOnUiThread {
            try {
                val jsonObject = JSONObject(text)
                val id = jsonObject.getInt("id")
                val description = jsonObject.getString("description")
                val imageUrl = jsonObject.getString("imageUrl")
                val title = jsonObject.getString("title")
                val booleanStatus = jsonObject.getBoolean("booleanStatus")

                val webSocketItem = WebSocketItemResponse(id, description, imageUrl, title, booleanStatus)
                val updatedList = listOf(webSocketItem) + messageAdapter.currentList
                messageAdapter.submitList(updatedList) {
                    binding.recyclerView.scrollToPosition(0)
                }
                Log.d(
                    "WebSocket",
                    "Received model: id=$id, description=$description, imageUrl=$imageUrl, title=$title, booleanStatus=$booleanStatus"
                )
            } catch (e: Exception) {
                Log.d("WebSocket", "Received plain message: $text")
                binding.socketResponse.text = text
            }
        }
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        Log.e("WebSocket", "Connection failed: ${t.message}")
        activity.runOnUiThread {
            binding.textView.text = context.getString(R.string.disconnect_from_server)
        }
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        activity.runOnUiThread {
            binding.textView.text = context.getString(R.string.disconnect_from_server)
        }
        Log.e("WebSocket", "Connection closing: Code $code, Reason $reason")
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        activity.runOnUiThread {
            binding.textView.text = context.getString(R.string.disconnect_from_server)
        }
        Log.e("WebSocket", "Connection closed: Code $code, Reason $reason")
    }

    companion object {
        const val CONNECTION_CONSTANT = 1000
    }
}



//1 , BEST GAME EVER , https://assets.rockpapershotgun.com/images/2018/10/armor-1212x682.jpg ,  Elder Scrolls Of Skyrim , false

//2 , OPEN WORLD GAME , https://image.api.playstation.com/vulcan/ap/rnd/202211/0711/kh4MUIuMmHlktOHar3lVl6rY.png , Witcher III , true

//3 , FALLOUT IV , https://cdn1.epicgames.com/offer/9c1a74145a9145ec803d7452e80819a0/EGS_Fallout4_BethesdaGameStudios_S1_2560x1440-fb0e82fa71a74e750c95b57b91fc558d , Fallout 4 , true