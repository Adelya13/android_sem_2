package kpfu.itis.valisheva.android_app.presentation.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import kpfu.itis.valisheva.android_app.App
import kpfu.itis.valisheva.android_app.R
import kpfu.itis.valisheva.android_app.databinding.ActivityMainBinding
import kpfu.itis.valisheva.android_app.presentation.extentions.findController
import kpfu.itis.valisheva.android_app.presentation.viewmodels.FirstModelView

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var controller: NavController



    override fun onCreate(savedInstanceState: Bundle?) {
//        (this.application as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also{
            setContentView(it.root)
        }
        controller = findController(R.id.container)

    }
}
