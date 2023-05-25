package capstone.part1.goosukki

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import capstone.part1.goosukki.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        // 게시글 업로드 버튼 클릭 시 UploadPostActivity 실행
        binding.uploadPostStartButton.setOnClickListener {
            activity?.let {
                val intent = Intent(it, PostUploadActivity::class.java)
                startActivity(intent)
            }
        }
    }
}