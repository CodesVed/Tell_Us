package com.example.feedbackcollectorsystem.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.feedbackcollectorsystem.FeedbackData
import com.example.feedbackcollectorsystem.R
import com.google.android.material.chip.Chip
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class FeedbackAdapter(val context: Activity, val arrayList: ArrayList<FeedbackData>): RecyclerView.Adapter<FeedbackAdapter.FeedbackViewHolder>() {
    class FeedbackViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val title = itemView.findViewById<TextView>(R.id.fbTitle)
        val description = itemView.findViewById<TextView>(R.id.fbDesc)
        val category = itemView.findViewById<Chip>(R.id.fbCategory)
        val timestamp = itemView.findViewById<TextView>(R.id.fbDate)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FeedbackViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.feedback_item, parent, false)
        return FeedbackViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: FeedbackViewHolder,
        position: Int
    ) {
        val currentItem = arrayList[position]
        holder.title.text = currentItem.title
        holder.description.text = currentItem.description
        holder.category.text = currentItem.category

        val timestamp = currentItem.timestamp
        if (timestamp != null){
            val millis = timestamp.seconds*1000 + timestamp.nanoseconds/1000000
            val sdf = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
            val netDate = Date(millis)
            holder.timestamp.text = sdf.format(netDate)
        } else {
            holder.timestamp.text = "No date"
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }
}