package com.example.feedbackcollectorsystem.activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isEmpty
import androidx.recyclerview.widget.RecyclerView
import com.example.feedbackcollectorsystem.models.FeedbackData
import com.example.feedbackcollectorsystem.R
import com.example.feedbackcollectorsystem.adapters.FeedbackAdapter
import com.example.feedbackcollectorsystem.databinding.ActivityMainBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var feedbackDB = Firebase.firestore
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FeedbackAdapter
    private val dataList = arrayListOf<FeedbackData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recyclerView = binding.feedbackRecyclerview
        adapter = FeedbackAdapter(this, dataList)
        recyclerView.adapter = adapter

        fetchData()

        binding.addFeedback.setOnClickListener {
            val intent = Intent(this, AddFeedbackActivity::class.java)
            startActivity(intent)
        }

        binding.swipeRefresh.setOnRefreshListener {
            dataList.clear()
            fetchData()
            binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun fetchData() {
        binding.emptyStateLayout.visibility = View.GONE

        feedbackDB.collection("feedbacks").get()
            .addOnSuccessListener { result ->
                if (result.isEmpty){
                    recyclerView.visibility = View.GONE
                    binding.emptyStateLayout.visibility = View.VISIBLE
                } else {
                    dataList.clear()
                    for (document in result){
                        val feedback = document.toObject(FeedbackData::class.java)
                        dataList.add(feedback)
                    }
                    adapter.notifyDataSetChanged()

                    recyclerView.visibility = View.VISIBLE
                    binding.emptyStateLayout.visibility = View.GONE
                }
            }
            .addOnFailureListener { exception ->
                Log.e("MainActivity", "Error getting documents: ", exception)
            }
    }
}