package com.rifqi.githubuserapp.settingpref

import android.os.Bundle
import android.widget.CompoundButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.rifqi.githubuserapp.databinding.ActivityDarkModeBinding
import com.rifqi.githubuserapp.mainmenu.MainViewModel
import com.rifqi.githubuserapp.utils.ViewModelFactory

class DarkModeActivity : AppCompatActivity() {
    lateinit var binding: ActivityDarkModeBinding
    private var isChecked: Boolean = false
    private val mainViewModel: MainViewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDarkModeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)



        binding?.apply {
            mainViewModel.getThemeSetting()
                .observe(this@DarkModeActivity) { isDarkActive: Boolean ->

                    if (isDarkActive) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                        switchTheme.isChecked = true
                    } else {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                        switchTheme.isChecked = false
                    }

                }

            switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
                mainViewModel.saveThemeSetting(isChecked)

            }
        }
    }
}