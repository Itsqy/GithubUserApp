package com.rifqi.githubuserapp.detail.following

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


class FollowingFragment : Fragment() {

    private val featureViewModel: FeatureViewModel by viewModels()
    var userName: String? = null
    lateinit var userAdapter: FeatureAdapter
    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showLoading()
//setup rv
        userName = arguments?.getString("name")
        userAdapter = FeatureAdapter(requireActivity().applicationContext)
        binding?.apply {
            rvFollowing.layoutManager = LinearLayoutManager(activity)
            rvFollowing.setHasFixedSize(true)
            rvFollowing.adapter = userAdapter
        }

//        setup viewmodel
        featureViewModel.setFollowing(userName.toString())
        featureViewModel.getFollowing().observe(viewLifecycleOwner) {
            userAdapter.setListUser(it)
        }


    }

    fun showLoading() {
        featureViewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding?.apply {
                    pbFollowing.visibility = View.VISIBLE
                    rvFollowing.visibility = View.INVISIBLE
                }

            } else {
                binding?.apply {
                    pbFollowing.visibility = View.INVISIBLE
                    rvFollowing.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}