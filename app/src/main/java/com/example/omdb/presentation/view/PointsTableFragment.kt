package com.example.omdb.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.starwars.starwarstournament.R
import com.starwars.starwarstournament.databinding.FragmmentMainBinding
import com.starwars.starwarstournament.helper.GlobalState
import com.starwars.starwarstournament.helper.getViewModel
import com.starwars.starwarstournament.presentation.view.PointsTableAdapter
import com.starwars.starwarstournament.presentation.viewmodel.PointsTableViewModel

class PointsTableFragment : BaseFragment() {

    private lateinit var viewModel: PointsTableViewModel
    private lateinit var viewBinding: FragmmentMainBinding
    private var isSwipe: Boolean = false

    override fun doViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): View {
        viewBinding = FragmmentMainBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun afterViewCreated() {
        initUI()
        if (!viewModel.pointsTableData.hasActiveObservers())
            registerObservers()
    }

    override fun getGlobalStateData(): LiveData<GlobalState> = viewModel.globalStateData


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    override fun dismissProgress() {
        if (viewBinding.swipeRefreshLayout.isRefreshing)
            viewBinding.swipeRefreshLayout.isRefreshing = false
    }

    override fun showProgress() {
        if (!isSwipe)
            viewBinding.swipeRefreshLayout.isRefreshing = true
    }

    private fun initViewModel() {
        viewModel = this.getViewModel(viewModelFactory)
        registerObservers()
        requestInitData()
    }

    private fun registerObservers() {
        viewModel.pointsTableData.observe(this) {
            it?.apply {
                if (viewBinding.recyclerView.adapter == null)
                    viewBinding.recyclerView.adapter =
                        PointsTableAdapter(::handlePlayerItemClick)
                (viewBinding.recyclerView.adapter as PointsTableAdapter).submitList(this)
            }
        }
    }

    private fun requestInitData() {
        if (viewModel.pointsTableData.value == null) {
            viewModel.getPointsDetailData()
        }
    }

    private fun initUI() {
        viewBinding.labelTop.text = getString(R.string.player_list)
        viewBinding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }
        viewBinding.swipeRefreshLayout.setOnRefreshListener {
            isSwipe = true
            viewModel.getPointsDetailData()
        }
    }

    private fun handlePlayerItemClick(id: Int) {
        val action =
            PointsTableFragmentDirections.actionPointsTableFragmentToMatchesDetailsFragment(playerId = id)
        findNavController().navigate(action)
    }
}