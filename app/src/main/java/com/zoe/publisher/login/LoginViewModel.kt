package com.zoe.publisher.login

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.zoe.publisher.R
import com.zoe.publisher.UserManager
import com.zoe.publisher.Users

class LoginViewModel(application: Application) : AndroidViewModel(application) {


    @SuppressLint("StaticFieldLeak")
    val context = getApplication<Application>().applicationContext


    private val _loginSuccess = MutableLiveData<Boolean>()
    val loginSuccess: LiveData<Boolean>
        get() = _loginSuccess

    private val db = Firebase.firestore
    private val ref = db.collection("users")

    fun login(userId: String, userPassword: String) {
        if (userId.isEmpty() || userPassword.isEmpty()) {
            Toast.makeText(context, "資料未填寫", Toast.LENGTH_SHORT).show()
        } else {
            ref.whereEqualTo("userId", userId)
                .get()
                .addOnSuccessListener {
                    Log.d("checkingUSER", "${it.documents}")
                    if (it.documents.isNullOrEmpty()) {
                        Toast.makeText(context, "查無此帳戶，請檢查", Toast.LENGTH_SHORT).show()
                    } else {
                        for (element in it) {
                            val user = Users(
                                email = element.data["email"].toString(),
                                userId = element.data["userId"].toString(),
                                nickName = element.data["nickName"].toString(),
                                password = element.data["password"].toString(),
                                token = element.data["token"].toString(),
                            )
                            verifyAccount(user, userPassword)
                        }
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w("Errors: ", exception)
                }
        }
    }

    private fun verifyAccount(user: Users, inputPassword: String) {
        if (inputPassword != user.password) {
            Toast.makeText(context, "密碼錯誤，請檢查", Toast.LENGTH_SHORT).show()
            _loginSuccess.value = false
        } else {

            Toast.makeText(context, "登入成功", Toast.LENGTH_SHORT).show()
            UserManager.userToken = user.token
            UserManager.userName = user.nickName

            _loginSuccess.value = true
        }
    }

    fun register(
        inputEmail: String,
        inputUserId: String,
        inputNickname: String,
        inputPassword: String,
    ) {
        val newToken = ref.document().id  //產生獨立token

        val newUser = Users(
            email = inputEmail,
            userId = inputUserId,
            nickName = inputNickname,
            password = inputPassword,
            token = newToken
        )

        if (inputEmail.isNotEmpty() && inputNickname.isNotEmpty() &&
            inputUserId.isNotEmpty() && inputPassword.isNotEmpty()
        ) {
            ref.document(newToken)
                .set(newUser)
                .addOnSuccessListener {
                    Toast.makeText(context, "註冊成功", Toast.LENGTH_SHORT).show()

                    //註冊後自動導入登入
                    login(userId = inputUserId, userPassword = inputPassword)
                }
                .addOnFailureListener {
                    Toast.makeText(context, "註冊失敗", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(context, "資料有空值，請檢查", Toast.LENGTH_SHORT).show()
        }


    }
}