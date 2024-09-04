package com.example.newsapp

import android.app.Activity
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class NewsAdapter(val activity: Activity, val news: ArrayList<News>) :
    RecyclerView.Adapter<NewsAdapter.MVH>() {
    class MVH(view: View) : ViewHolder(view) {
//        val parent : CardView =view.findViewById(R.id.crview)
//        val image : ImageView =view.findViewById(R.id.ieeemage)
//        val text : TextView =view.findViewById(R.id.title)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapter.MVH {
        val view = activity.layoutInflater.inflate(R.layout.news_list_item, parent, false)
        return MVH(view)
    }

    override fun onBindViewHolder(holder: NewsAdapter.MVH, position: Int) {
//        holder.image.setImageResource(news[position].pic)
//        holder.text.text = emergencys[position].name
//        holder.parent.setOnClickListener {
//            val i = Intent(Intent.ACTION_DIAL, "tel:${emergencys[position].number}".toUri())
//            activity.startActivity(i)
//        }
    }

    override fun getItemCount() = news.size

}