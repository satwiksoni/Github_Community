package com.example.networking

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item.*
import kotlinx.android.synthetic.main.list_item.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        var got=intent.getStringExtra("ID")


        GlobalScope.launch(Dispatchers.Main) {
            val response= withContext(Dispatchers.IO)
            {
                Client.api.getUserById(got!!)
            }


            if(response.isSuccessful)
            {
                response.body()?.let {
                    tv1.text=it.name
                    tv2.text=it.login
                    Picasso.get().load(it.avatarUrl).into(im1)



                }
            }
        }


    }
}