package com.arl.localwebsocet.model

/**
 * Created by AralBenli on 10.09.2024.
 */
data class WebSocketItemResponse(
    val id: Int,
    val description: String,
    val imageUrl: String,
    val title: String,
    val booleanStatus: Boolean
) {
    override fun toString(): String {
        return "Model(id=$id, description='$description', imageUrl='$imageUrl', title='$title', booleanStatus=$booleanStatus)"
    }
}