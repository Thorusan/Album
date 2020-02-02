package com.example.album.ui.users


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.example.album.R
import com.example.album.datamodel.AlbumData
import com.example.album.datamodel.UserData
import com.example.album.db.UserEntity


class UsersEntityListAdapter(private val userList: List<UserEntity>, private val listener: (Int) -> Unit) : RecyclerView.Adapter<UsersEntityListAdapter.UserViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view: View =
                LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(userList[position], listener)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.text_name)
        lateinit var textName: TextView
        @BindView(R.id.text_username)
        lateinit var textUsername: TextView

        init {
            ButterKnife.bind(this, itemView)
        }

        fun bind(user: UserEntity, listener: (Int) -> Unit) = with(itemView) {

            textName.text = user.name
            textUsername.text = user.username

            itemView.setOnClickListener { listener(user.id) }
        }
    }
}