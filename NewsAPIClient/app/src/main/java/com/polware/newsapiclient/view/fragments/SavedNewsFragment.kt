package com.polware.newsapiclient.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.polware.newsapiclient.R
import com.polware.newsapiclient.data.utils.SwipeToDeleteCallback
import com.polware.newsapiclient.databinding.FragmentSavedNewsBinding
import com.polware.newsapiclient.view.MainActivity
import com.polware.newsapiclient.view.NewsAdapter
import com.polware.newsapiclient.viewmodel.NewsViewModel

class SavedNewsFragment : Fragment() {
    private var _bindingSN: FragmentSavedNewsBinding? = null
    private val bindingSaved get() = _bindingSN!!
    private lateinit var viewModelFragment: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _bindingSN = FragmentSavedNewsBinding.inflate(inflater, container, false)
        return bindingSaved.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModelFragment = (activity as MainActivity).viewModel
        newsAdapter = (activity as MainActivity).newsAdapter
        initRecyclerView()

        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("selected_article", it)
            }
            findNavController().navigate(R.id.action_savedNewsFragment_to_detailsNewsFragment, bundle)
        }

        viewModelFragment.getSavedNews().observe(viewLifecycleOwner) {
            newsAdapter.differ.submitList(it)
        }

        // Deletes article from Database by Swiping to the sides
        ItemTouchHelper(object: SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val article = newsAdapter.differ.currentList[position]
                viewModelFragment.deleteArticle(article)
                Snackbar.make(view,"Article Deleted!", Snackbar.LENGTH_LONG)
                    .apply {
                        setAction("Undo"){
                            viewModelFragment.saveArticle(article)
                        }
                        show()
                    }
            }
        }).attachToRecyclerView(bindingSaved.rvSavedNews)

    }

    private fun initRecyclerView() {
        bindingSaved.rvSavedNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

}