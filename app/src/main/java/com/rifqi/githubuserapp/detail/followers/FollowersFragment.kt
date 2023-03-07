package com.rifqi.githubuserapp.detail.followers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.rifqi.githubuserapp.databinding.FragmentFollowersBinding
import com.rifqi.githubuserapp.detail.FeatureAdapter
import com.rifqi.githubuserapp.detail.FeatureViewModel


class FollowersFragment : Fragment() {

    private val featureViewModel: FeatureViewModel by viewModels()
    var userName: String? = null
    lateinit var featureAdapter: FeatureAdapter

    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showLoading()
        userName = arguments?.getString("name")
        featureAdapter = FeatureAdapter(requireActivity().applicationContext)
        binding?.apply {
            rvFollowers.layoutManager = LinearLayoutManager(activity)
            rvFollowers.setHasFixedSize(true)
            rvFollowers.adapter = featureAdapter
        }


        featureViewModel.setFollowers(userName.toString())
        featureViewModel.getFollowers().observe(viewLifecycleOwner) {
            featureAdapter.setListUser(it)
        }


    }

    fun showLoading() {
        featureViewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding?.apply {
                    pbFollowers.visibility = View.VISIBLE
                    rvFollowers.visibility = View.INVISIBLE
                }

            } else {
                binding?.apply {
                    pbFollowers.visibility = View.INVISIBLE
                    rvFollowers.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}