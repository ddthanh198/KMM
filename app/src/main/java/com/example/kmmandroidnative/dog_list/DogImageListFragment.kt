package com.example.kmmandroidnative.dog_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.kmmandroidnative.R
import com.example.kmmandroidnative.databinding.FragmentDogImageListBinding

class DogImageListFragment : Fragment() {

    companion object {
        const val KEY_BREED_NAME = "KEY_BREED_NAME"

        fun newInstance(breedName : String) : DogImageListFragment {
            return DogImageListFragment().apply {
                val bundle = Bundle().apply {
                    putString(KEY_BREED_NAME, breedName)
                }

                arguments = bundle
            }
        }
    }

    private lateinit var dogImageListAdapter : DogImageListAdapter
    private lateinit var binding : FragmentDogImageListBinding
    private lateinit var viewModel : DogImageListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDogImageListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeData()
        initData()
    }

    private fun initView() {
        viewModel = ViewModelProvider(this)[DogImageListViewModel::class.java]
        dogImageListAdapter = DogImageListAdapter(requireContext())
        val gridLayoutManager = GridLayoutManager(requireContext(), 2)
        binding.listDogImage.apply {
            layoutManager = gridLayoutManager
            adapter = dogImageListAdapter
        }
    }

    private fun observeData() {
        viewModel.listDogImageUrl.observe(viewLifecycleOwner) {
            dogImageListAdapter.updateList(it)
        }
    }

    private fun initData() {
        val breedName = arguments?.getString(KEY_BREED_NAME)
        breedName?.let {
            viewModel.getDogImageUrlList(it)
        }
    }
}