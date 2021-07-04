package com.example.omdb.presentation.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import com.example.omdb.di.Component
import com.example.omdb.di.ViewModelFactory
import com.example.omdb.helpers.GlobalState
import com.example.omdb.helpers.State
import javax.inject.Inject

abstract class BaseFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private var component: Component? = null

    abstract fun doViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): View

    abstract fun afterViewCreated()
    abstract fun getGlobalStateData(): LiveData<GlobalState>
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("Fragment", "Oncreate called")
        super.onCreate(savedInstanceState)
        component?.inject(this) ?: initInjector()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("Fragment", "Oncreateview called")
        return doViewBinding(inflater, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        afterViewCreated()
        getGlobalStateData().observe(viewLifecycleOwner) {
            when (it.state) {
                State.SUCCESS -> dismissProgress()
                State.ERROR -> handleError(it.message ?: "Something Went Wrong")
                State.LOADING -> showProgress()
            }
        }
    }

    abstract fun dismissProgress()

    abstract fun showProgress()

    private fun handleError(message: String) {
        dismissProgress()
        showDialog(message)
    }

    private fun showDialog(message: String) {

    }

    private fun initInjector() {
        component = DaggerComponent.builder().activityContext(requireContext())
            .build()
        component?.inject(this)
    }
}