package com.example.feedbackcollectorsystem

import com.google.firebase.Timestamp

data class FeedbackData(
    val title: String = "",
    val description: String = "",
    val category: String = "",
    val timestamp: com.google.firebase.Timestamp? = null
)
