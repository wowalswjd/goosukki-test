package capstone.part1.goosukki

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import capstone.part1.goosukki.databinding.FragmentCameraBinding

class CameraFragment : Fragment(R.layout.fragment_camera) {

    private lateinit var binding: FragmentCameraBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCameraBinding.bind(view)
    }
}