package com.zoe.publisher

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.MenuItem
import android.view.View
import androidx.navigation.NavController
import com.zoe.publisher.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    lateinit var navController : NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        UserManager.init(this)

        setSupportActionBar(binding.toolbar)

        navController = findNavController(R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        btnSetUp()
        btnTitleSetup()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }


    fun btnSetUp(){

        binding.loginBtn.setOnClickListener {
            navController.navigate(R.id.action_global_loginFragment)
        }

        binding.postArticle.setOnClickListener {
            navController.navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        binding.logoutBtn.setOnClickListener {
            UserManager.userToken = null
            navController.navigate(R.id.action_global_loginFragment)
        }
    }

    fun btnTitleSetup(){
        if(UserManager.userToken != null) {
            binding.logoutBtn.visibility = View.VISIBLE
            binding.welcomeText.visibility = View.VISIBLE
            binding.welcomeText.text = "Hi, ${UserManager.userName}"
            binding.loginBtn.visibility = View.GONE
        }else{
            binding.logoutBtn.visibility = View.GONE
            binding.welcomeText.visibility = View.GONE
            binding.loginBtn.visibility = View.VISIBLE
        }
    }
}