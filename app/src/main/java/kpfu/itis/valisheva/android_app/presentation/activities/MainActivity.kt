package kpfu.itis.valisheva.android_app.presentation.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import kpfu.itis.valisheva.android_app.R
import kpfu.itis.valisheva.android_app.databinding.ActivityMainBinding
import kpfu.itis.valisheva.android_app.presentation.extentions.findController

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var controller: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also{
            setContentView(it.root)
        }
        controller = findController(R.id.container)

    }
}
