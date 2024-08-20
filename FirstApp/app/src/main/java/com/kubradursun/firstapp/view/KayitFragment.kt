package com.kubradursun.firstapp.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kubradursun.firstapp.databinding.FragmentKayitBinding


class KayitFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private var _binding: FragmentKayitBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentKayitBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Kayıt ol butonuna tıklanma işlemi
        binding.button.setOnClickListener { kayitol(it) }
        // Giriş yap butonuna tıklanma işlemi
        binding.button2.setOnClickListener { girisyap(it) }
      val guncelKullanici =  auth.currentUser
        if(guncelKullanici != null){
            val action=KayitFragmentDirections.actionKayitFragmentToFeedFragment()
            Navigation.findNavController(view).navigate(action)
        }
    }

    private fun kayitol(view: View) {
        val email = binding.editTextTextEmailAddress.text.toString()
        val password = binding.editTextTextPassword.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Kullanıcı oluşturuldu, FeedFragment'e geçiş yap
                        val action = KayitFragmentDirections.actionKayitFragmentToFeedFragment()
                        Navigation.findNavController(view).navigate(action)
                    } else {
                        // Hata oluştu, detaylı hata mesajını göster
                        val errorMessage = task.exception?.localizedMessage ?: "Bilinmeyen bir hata oluştu."
                        Log.e("FirebaseAuth", "Kayıt Hatası: ${task.exception}")
                        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show()
                    }
                }
                .addOnFailureListener { exception ->
                    // Hata mesajını göster
                    Log.e("FirebaseAuth", "Hata: ${exception.localizedMessage}")
                    Toast.makeText(requireContext(), exception.localizedMessage, Toast.LENGTH_LONG).show()
                }
        } else {
            // Email veya şifre boşsa hata mesajı göster
            Toast.makeText(requireContext(), "Lütfen e-posta ve şifre girin.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun girisyap(view: View) {
        val email = binding.editTextTextEmailAddress.text.toString()
        val password = binding.editTextTextPassword.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Giriş başarılı, FeedFragment'e geçiş yap
                        val action = KayitFragmentDirections.actionKayitFragmentToFeedFragment()
                        Navigation.findNavController(view).navigate(action)
                    } else {
                        // Giriş hatası, detaylı hata mesajını göster
                        val errorMessage = task.exception?.localizedMessage ?: "Bilinmeyen bir hata oluştu."
                        Log.e("FirebaseAuth", "Giriş Hatası: ${task.exception}")
                        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show()
                    }
                }
                .addOnFailureListener { exception ->
                    // Hata mesajını göster
                    Log.e("FirebaseAuth", "Hata: ${exception.localizedMessage}")
                    Toast.makeText(requireContext(), exception.localizedMessage, Toast.LENGTH_LONG).show()
                }
        } else {
            // Email veya şifre boşsa hata mesajı göster
            Toast.makeText(requireContext(), "Lütfen e-posta ve şifre girin.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
