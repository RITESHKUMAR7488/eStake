package com.example.estake.mainModule.uis

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.estake.common.models.PropertyModel
import com.example.estake.databinding.FragmentHomeBinding
import com.example.estake.mainModule.adapters.PropertyAdapter
import com.example.estake.mainModule.adapters.StoryAdapter
import com.example.estake.mainModule.adapters.StoryModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupStories()
        setupProperties()
    }

    private fun setupStories() {
        // ⚡ Dummy Data: Matching the "Trending" top bar in your video
        val storyList = listOf(
            StoryModel("Reliance", "https://upload.wikimedia.org/wikipedia/en/thumb/4/4c/Reliance_Digital_logo.svg/1200px-Reliance_Digital_logo.svg.png"),
            StoryModel("Tanishq", "https://upload.wikimedia.org/wikipedia/commons/thumb/2/28/Tanishq_Logo.svg/2560px-Tanishq_Logo.svg.png"),
            StoryModel("Westside", "https://upload.wikimedia.org/wikipedia/commons/2/25/Westside_Logo.png"),
            StoryModel("Zudio", "https://upload.wikimedia.org/wikipedia/commons/thumb/4/47/Zudio_logo.jpg/800px-Zudio_logo.jpg"),
            StoryModel("HDFC", "https://upload.wikimedia.org/wikipedia/commons/thumb/2/2a/HDFC_Bank_Logo.svg/2560px-HDFC_Bank_Logo.svg.png"),
            StoryModel("Bandhan", "https://upload.wikimedia.org/wikipedia/en/thumb/c/c6/Bandhan_Bank_logo.svg/1200px-Bandhan_Bank_logo.svg.png"),
            StoryModel("Giva", "https://giva.co/cdn/shop/files/GIVA_Logo_Black.png?v=1614766625")
        )

        binding.rvStories.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = StoryAdapter(storyList)
        }
    }

    private fun setupProperties() {
        // ⚡ Dummy Data: Matching the Property Cards in your video
        val propertyList = listOf(
            PropertyModel(
                title = "Property at Park Street",
                rentAmount = "₹ 6.5 Lakhs",
                roi = 6.8,
                irr = "11-13%",
                fundedPercentage = 60,
                tenantName = "Reliance",
                // Using a generic image or the reliance logo for the card icon
                tenantLogoUrl = "https://upload.wikimedia.org/wikipedia/en/thumb/4/4c/Reliance_Digital_logo.svg/1200px-Reliance_Digital_logo.svg.png"
            ),
            PropertyModel(
                title = "Property at New Market",
                rentAmount = "₹ 4.2 Lakhs",
                roi = 6.5,
                irr = "10-12%",
                fundedPercentage = 30,
                tenantName = "Tanishq",
                tenantLogoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/2/28/Tanishq_Logo.svg/2560px-Tanishq_Logo.svg.png"
            ),
            PropertyModel(
                title = "Property at Ballygunge",
                rentAmount = "₹ 7.8 Lakhs",
                roi = 7.0,
                irr = "12-13%",
                fundedPercentage = 90,
                tenantName = "HDFC",
                tenantLogoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/2/2a/HDFC_Bank_Logo.svg/2560px-HDFC_Bank_Logo.svg.png"
            )
        )

        binding.rvProperties.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = PropertyAdapter(propertyList)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}