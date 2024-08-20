package com.kubradursun.firstapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kubradursun.firstapp.R
import com.kubradursun.firstapp.adapter.PostAdapter
import com.kubradursun.firstapp.databinding.FragmentFeedBinding
import com.kubradursun.firstapp.model.Post


class FeedFragment : Fragment(), PopupMenu.OnMenuItemClickListener {
    private var _binding: FragmentFeedBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth : FirebaseAuth
    private lateinit var db : FirebaseFirestore
    val postList: ArrayList<Post> = arrayListOf()
    private var adapter : PostAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        db = Firebase.firestore
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFeedBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.floatingActionButton.setOnClickListener{floatinButtontiklandi(it)}
        firestoreverilerial()
        adapter = PostAdapter(postList)
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerview.adapter = adapter
    }



        //firesoredeki verileri uygulamamaıza alıyoruz
    private fun firestoreverilerial(){
db.collection("Posts").orderBy( "date",Query.Direction.DESCENDING).addSnapshotListener { value, error ->
    if(error!= null){
      Toast.makeText(requireContext(),error.localizedMessage,Toast.LENGTH_LONG).show()
    }else{
      if(value != null){
         if (!value.isEmpty){
        //boş değilse
             postList.clear()
             val documents = value.documents
             for(document in documents  ){
               val comment=  document.get("comment") as String
                 val email = document.get("email") as String
                 val downloadurl = document.get("downloadurl") as String
                val post = Post(email,comment,downloadurl)
                 postList.add(post)
             }
             adapter?.notifyDataSetChanged()
         }
      }
    }
}
    }



    fun floatinButtontiklandi(view: View){
        val popup = PopupMenu(requireContext(),binding.floatingActionButton)
        val inflater = popup.menuInflater
        inflater.inflate(R.menu.my_popup_menu,popup.menu)
        popup.setOnMenuItemClickListener (this)
        popup.show()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.yuklemeItem -> {
                val action = FeedFragmentDirections.actionFeedFragmentToYuklemeFragment()
                Navigation.findNavController(requireView()).navigate(action)
            }
            R.id.cikisItem -> {
                auth.signOut()
                val action = FeedFragmentDirections.actionFeedFragmentToKayitFragment()
                Navigation.findNavController(requireView()).navigate(action)
            }
        }
        return true
    }
}