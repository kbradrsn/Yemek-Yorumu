package com.kubradursun.firstapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.FirebaseApp
import com.kubradursun.firstapp.databinding.ActivitylayoutBinding
import com.kubradursun.firstapp.ui.theme.FirstAppTheme

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivitylayoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitylayoutBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

    }
}

