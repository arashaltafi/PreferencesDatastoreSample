package com.arash.altafi.preferencesdatastoresample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.arash.altafi.preferencesdatastoresample.databinding.ActivityMainBinding
import com.arash.altafi.preferencesdatastoresample.viewmodel.ActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val activityViewModel: ActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnGet.setOnClickListener {
                activityViewModel.retrieveData()
            }
            btnDrop.setOnClickListener {
                activityViewModel.dropData()
            }
            btnSave.setOnClickListener {
                activityViewModel.phone = tvPhoneSave.text.toString()
                activityViewModel.name = tvNameSave.text.toString()
                activityViewModel.address = tvAddressSave.text.toString()
                activityViewModel.saveData()
                tvPhoneSave.text?.clear()
                tvNameSave.text?.clear()
                tvAddressSave.text?.clear()
            }
            activityViewModel.phonebook.observe(this@MainActivity) {
                tvNameGet.setText(it.name)
                tvPhoneGet.setText(it.phone)
                tvAddressGet.setText(it.address)
            }
            activityViewModel.isDropPhonebook.observe(this@MainActivity) {
                if (it) {
                    tvNameGet.text?.clear()
                    tvPhoneGet.text?.clear()
                    tvAddressGet.text?.clear()
                }
            }
        }
    }
}