package capstone.part1.goosukki

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import capstone.part1.goosukki.databinding.FragmentUserBinding


class UserFragment : Fragment(R.layout.fragment_user) {

    private lateinit var binding: FragmentUserBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUserBinding.bind(view)
    }
}