package com.example.estake.mainModule.uis

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.estake.databinding.FragmentInvestBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InvestFragment : Fragment() {
    private lateinit var binding: FragmentInvestBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentInvestBinding.inflate(inflater, container, false)
        return binding.root
    }
}