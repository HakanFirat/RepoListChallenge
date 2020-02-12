package com.example.repolistchallenge.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.repolistchallenge.R
import com.example.repolistchallenge.model.RepoModel

class RepoListAdapter(private val repoList: List<RepoModel>) :
    RecyclerView.Adapter<RepoListAdapter.ViewHolder>() {

    private var onItemClickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.row_repo, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return repoList.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.name?.text = repoList[position].name
        viewHolder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(viewHolder.itemView, position)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.name)
    }

    fun setItemClickListener(clickListener: ItemClickListener) {
        onItemClickListener = clickListener
    }

    interface ItemClickListener {
        fun onItemClick(view: View, position: Int)
    }
}