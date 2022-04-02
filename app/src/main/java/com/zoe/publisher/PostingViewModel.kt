package com.zoe.publisher

import android.annotation.SuppressLint
import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.zoe.publisher.Util.serializeToMap
import java.util.*

class PostingViewModel(application: Application) : AndroidViewModel(application) {

    @SuppressLint("StaticFieldLeak")
    val context = getApplication<Application>().applicationContext

    private val db = Firebase.firestore
    private val ref = db.collection("articles")

    private val _postSuccess = MutableLiveData<Boolean>()
    val postSuccess: LiveData<Boolean>
        get() = _postSuccess


    lateinit var author: Author
    var category: String = ""


    init {
        getAuthorInfo()
    }


    private fun getAuthorInfo() {
        db.collection("users").document("${UserManager.userToken}")
            .get()
            .addOnSuccessListener {
                author = Author(
                    email = it["email"].toString(),
                    id = it["userId"].toString(),
                    name = it["nickName"].toString()
                )
            }
    }

    fun addNewPost(title: String, content: String) {

        val docId = ref.document().id
        val authorAsMap: Map<String, Any> = author.serializeToMap()

        if (content.isNotEmpty() && title.isNotEmpty()) {
            val newPost = Articles(
                id = docId,
                title = title,
                content = content,
                category = category,
                createdTime = Calendar.getInstance().timeInMillis,
                author = authorAsMap
            )

            ref.document(docId)
                .set(newPost)
                .addOnSuccessListener {
                    Toast.makeText(context, "Success added ", Toast.LENGTH_SHORT).show()
                    _postSuccess.value = true
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Fail added ", Toast.LENGTH_SHORT).show()
                    _postSuccess.value = false
                }
        } else {
            Toast.makeText(context, "Please fill in title & content ", Toast.LENGTH_SHORT).show()
            _postSuccess.value = false

        }
    }


    fun slCategory(data: String) {
        category = data
    }
}