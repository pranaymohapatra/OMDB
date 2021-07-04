package com.example.omdb.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.omdb.presentation.view.BaseFragment
import com.example.omdb.presentation.view.MatchDetailsAdapter
import com.starwars.starwarstournament.R
import com.starwars.starwarstournament.databinding.FragmmentMainBinding
import com.starwars.starwarstournament.helper.GlobalState
import com.starwars.starwarstournament.helper.getViewModel
import com.starwars.starwarstournament.presentation.viewmodel.MatchesDetailViewModel

class MatchesDetailsFragment : BaseFragment() {
    private lateinit var viewModel: MatchesDetailViewModel
    private lateinit var viewBinding: FragmmentMainBinding
    private val args: MatchesDetailsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    override fun dismissProgress() {
        viewBinding.swipeRefreshLayout.isRefreshing = false
    }

    override fun showProgress() {
        viewBinding.swipeRefreshLayout.isRefreshing = true
    }

    override fun doViewBinding(inflater: LayoutInflater, container: ViewGroup?): View {
        viewBinding = FragmmentMainBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun afterViewCreated() {
        initUI()
    }

    override fun getGlobalStateData(): LiveData<GlobalState> = viewModel.globalStateData


    private fun initViewModel() {
        viewModel = this.getViewModel(viewModelFactory)
        viewModel.matchesDetailData.observe(this) {
            it?.apply {
                if (viewBinding.recyclerView.adapter == null)
                    viewBinding.recyclerView.adapter = MatchDetailsAdapter()
                (viewBinding.recyclerView.adapter as MatchDetailsAdapter).submitList(this)
            }
        }
        requestInitData()
    }

    private fun initUI() {
        viewBinding.labelTop.text = getString(R.string.matches_list)
        viewBinding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }
        viewBinding.swipeRefreshLayout.isEnabled = false
    }

    private fun requestInitData() {
        if (viewModel.matchesDetailData.value == null) {
            viewModel.getMatchesDetailsData(args.playerId)
        }
    }
}
