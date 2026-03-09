package com.example.feedbackcollectorsystem.activities

import android.R.attr.category
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.feedbackcollectorsystem.R
import com.example.feedbackcollectorsystem.databinding.ActivityAddFeedbackBinding
import com.example.feedbackcollectorsystem.models.FeedbackData
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.firestore.firestore
import java.time.Instant
import java.time.LocalDateTime
import java.util.Date

class AddFeedbackActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddFeedbackBinding
    private var feedbackDB = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddFeedbackBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.exit.setOnClickListener {
            finish()
        }

        val title = binding.editTitle.text
        val description = binding.editDescription.text
        var category = ""

        binding.selectCategory.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                category = p0?.getItemAtPosition(p2).toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        binding.submit.setOnClickListener {
            val data = FeedbackData(title.toString(), description.toString(), category, Timestamp(Date.from(Instant.now())))

            feedbackDB.collection("feedbacks").add(data).addOnSuccessListener {
                binding.coordinatorLayout.visibility = View.VISIBLE
                val snackbar = Snackbar
                    .make(binding.coordinatorLayout, "✅ Feedback submitted successfully!",
                        Snackbar.LENGTH_LONG)
                    .setBackgroundTint(Color.GREEN)
                    .setTextColor(Color.BLACK)

                snackbar.show()
            }.addOnFailureListener { exception ->
                Log.e("AddFeedback", "Error: $exception")
                val snackbar = Snackbar
                    .make(binding.coordinatorLayout, "❌ Failed to submit feedback",
                        Snackbar.LENGTH_LONG)
                    .setBackgroundTint(Color.RED)
                    .setTextColor(Color.BLACK)

                snackbar.show()
            }

        }
    }
}