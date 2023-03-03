package com.rifqi.githubuserapp.detail.followers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.rifqi.githubuserapp.R
import com.rifqi.githubuserapp.databinding.FragmentFollowersBinding
import com.rifqi.githubuserapp.databinding.FragmentFollowingBinding
import com.rifqi.githubuserapp.detail.FeatureAdapter
import com.rifqi.githubuserapp.detail.FeatureViewModel
import com.rifqi.githubuserapp.mainmenu.UsersAdapter


class FollowersFragment : Fragment() {

    private val featureViewModel: FeatureViewModel by viewModels()
    var userName: String? = null
    lateinit var userAdapter: FeatureAdapter

    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showLoading()
        userName = arguments?.getString("name")
        userAdapter = FeatureAdapter(requireActivity().applicationContext)
        binding.rvFollowers.layoutManager = LinearLayoutManager(activity)
        binding.rvFollowers.setHasFixedSize(true)
        binding.rvFollowers.adapter = userAdapter


        featureViewModel.setFollowers(userName.toString())
        featureViewModel.getFollowers().observe(viewLifecycleOwner) {
            userAdapter.setListUser(it)
        }


    }

    fun showLoading() {
        featureViewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.pbFollowers.visibility = View.VISIBLE
                binding.rvFollowers.visibility = View.INVISIBLE

            } else {
                binding.pbFollowers.visibility = View.INVISIBLE
                binding.rvFollowers.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}