package com.bignerdranch.android.cvn.ui.main.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bignerdranch.android.cvn.LoadingResult
import com.bignerdranch.android.cvn.data.News
import com.bignerdranch.android.cvn.databinding.NewsFragmentBinding
import com.bignerdranch.android.cvn.ui.main.adapternews.NewsAdapter
import com.bignerdranch.android.cvn.ui.main.adapternews.NewsAdapterListener
import com.bignerdranch.android.cvn.ui.main.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFragment : Fragment(), NewsAdapterListener {
    private var _binding: NewsFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NewsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = NewsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
        viewModel.refreshNews()
    }

    private fun setObservers() {
        viewModel.newsLiveData.observe(viewLifecycleOwner) { loadingResult ->
            when (loadingResult) {
                is LoadingResult.Success -> showNews(loadingResult.data)
                is LoadingResult.Error -> showError(loadingResult.e)
            }
        }

        viewModel.isLoadingLiveData.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading == true)
                showProgressBar()
            else
                hideProgressBar()
        }
    }

    fun showNews(news: List<News>) {
        val adapter = NewsAdapter(news, this)
        binding.rvNews.adapter = adapter
    }

    fun showError(e: Exception) {
        Log.d("myLog", e.message.toString())
        Toast.makeText(requireContext(), "Ошибка", Toast.LENGTH_SHORT).show()
    }

    override fun onNewsItemClick(newsUrl: String) {
        var url = newsUrl
        if (!url.startsWith("http://") && !url.startsWith("https://"))
            url = "https://" + newsUrl
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }

    fun hideProgressBar() {
        binding.progressBarLoadingNews.visibility = View.GONE
    }

    fun showProgressBar() {
        binding.progressBarLoadingNews.visibility = View.VISIBLE
    }
}