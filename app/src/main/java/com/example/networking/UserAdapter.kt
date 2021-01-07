package com.example.networking

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item.view.*

class UserAdapter():RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {



        fun bind(item: User) {
            with(itemView)
            {
                tv1.text=item.login
                tv2.text=item.name
                Picasso.get().load(item.avatarUrl).into(im1)


                setOnClickListener {

                    onItemClicked?.invoke(item.login!!)
                }

            }

        }

    }
    var onItemClicked:((login:String)->Unit)?=null

    private var data:List<User> =ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        )

    }

    fun swapData(data:List<User>)
    {
        this.data=data
          notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }
}