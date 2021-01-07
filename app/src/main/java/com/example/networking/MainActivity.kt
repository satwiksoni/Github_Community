package com.example.networking
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    val originalList= arrayListOf<User>()

    val adapter= UserAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        userRv.layoutManager=LinearLayoutManager(this)
        userRv.adapter=adapter



        searchView.isSubmitButtonEnabled =true
        searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { searchUsers(it) }
                return true
            }


            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { searchUsers(it) }
                return true
            }

        })

        searchView.setOnCloseListener {
            adapter.swapData(originalList)
            true
        }

     adapter.onItemClicked={
         val intent=Intent(this,UserActivity::class.java)
         intent.putExtra("ID",it)
         startActivity(intent)
     }

        GlobalScope.launch(Dispatchers.Main) {
            val response= withContext(Dispatchers.IO)
            {
                Client.api.getUsers()
            }


            if(response.isSuccessful)
            {
                response.body()?.let {
                    originalList.addAll(it)
                    adapter.swapData(it)
                }
            }
        }
    }
    fun searchUsers(query:String)
    {

        GlobalScope.launch(Dispatchers.Main) {
            val response= withContext(Dispatchers.IO)
            {
                Client.api.searchUser(query)
            }

            if(response.isSuccessful)
            {

                response.body()?.let {
                   it.items?.let { it1 -> adapter.swapData(it1) }
                }


            }

        }

    }
}

