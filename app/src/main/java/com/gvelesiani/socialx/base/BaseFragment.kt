package com.gvelesiani.socialx.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

typealias Inflater<T> = (inflater: LayoutInflater, view: ViewGroup?, attach: Boolean) -> T

abstract class BaseFragment<B : ViewBinding>(protected val inflater: Inflater<B>) : Fragment() {

    private var _binding: B? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = this.inflater.invoke(inflater, container, false)
        return binding.root
    }


    abstract fun setupView(savedInstanceState: Bundle?)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(savedInstanceState)
        setupObservers()
    }

    abstract fun setupObservers()

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
