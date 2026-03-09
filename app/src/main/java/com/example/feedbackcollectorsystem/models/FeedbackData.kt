package com.example.feedbackcollectorsystem.models

import com.google.firebase.Timestamp

data class FeedbackData(
    val title: String = "",
    val description: String = "",
    val category: String = "",
    val timestamp: Timestamp? = null
)