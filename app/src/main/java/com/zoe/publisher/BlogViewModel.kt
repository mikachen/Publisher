package com.zoe.publisher

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class BlogViewModel : ViewModel() {
    val db = Firebase.firestore

    val artsList = mutableListOf<Articles>()
    val ref = db.collection("articles")

    private val _articles = MutableLiveData<List<Articles>>()
    val articles: LiveData<List<Articles>>
        get() = _articles

    private val _refreshStatus = MutableLiveData<Boolean>()

    val refreshStatus: LiveData<Boolean>
        get() = _refreshStatus


    init {
        getAllArticles()
    }

    fun getAllArticles() {
        artsList.clear()

        ref.orderBy("createdTime", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener {
                for (element in it) {
                    val article = Articles(
                        id = element.data["id"].toString(),
                        category = element.data["category"].toString(),
                        title = element.data["title"].toString(),
                        content = element.data["content"].toString(),
                        author = element.data["author"] as Map<String, Any>,
                        createdTime = element.data["createdTime"] as Long
                    )
                    artsList.add(article)
                }
                Log.d("SWIPE","$artsList")
                _articles.value = artsList
            }
            .addOnFailureListener { exception ->
                Log.d("Error ", exception.toString())
            }
        _refreshStatus.value = false
    }
}