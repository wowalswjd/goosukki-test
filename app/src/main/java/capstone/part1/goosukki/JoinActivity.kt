package capstone.part1.goosukki

import android.content.Intent
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import capstone.part1.goosukki.databinding.ActivityJoinBinding
import capstone.part1.goosukki.databinding.ActivityMainBinding
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

// 회원가입 임시 파일

class JoinActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJoinBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJoinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 아이디 중복확인 버튼 클릭 시 요청
        binding.duplicateIdCheckBtn.setOnClickListener {
            // 아이디 edittext에 있는 문자열 받아오기
            var idInput = binding.idEditText.text.toString()
            // 요청 보내기(파라미터에 id 넣기)
            DuplicateIdApiRequest(idInput)
        }

        // passwordWatcher 초기화
        binding.passwordEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                updatePasswordCheckView()
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        // checkPasswordWatcher 초기화
        binding.checkPasswordEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                updatePasswordCheckView()
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        // MainActivity로 이동 (임시 버튼 - 곧 삭제 예정)
        binding.goToMainBtn.setOnClickListener {
            val intent = Intent(this, PostUploadActivity::class.java)
            startActivity(intent)
        }


    }

    // 아이디 중복확인 결과 받아오는 함수
    private fun DuplicateIdApiRequest(idInput : String) {
        // 1. retrofit 객체 생성
        // - baseURL(로컬 서버의 경우)은 현재 서버 코드에 접속하고 있는 IPv4 주소로 설정
        // 로깅 인터셉터 추가
        val retrofit = Retrofit.Builder()
            .baseUrl("http://43.200.243.113:8081")
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(
                        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                    ).build()
            )
            .build()

        // 2. service 객체 생성
        val apiService : DuplicateIdApiService = retrofit.create(DuplicateIdApiService::class.java)

        // 3. Call 객체 생성 - id 입력값 보내기
        val call = apiService.getDuplicateId(idInput)

        // 4. 네트워크 통신
        call.enqueue(object: Callback<Duplicate> {
            // 서버로부터 응답을 받았을 때 호출되는 콜백 메서드
            override fun onResponse(call: Call<Duplicate>, response: Response<Duplicate>) {
                if (response.isSuccessful) {
                    // 응답 내용(body)을 Duplicate 객체로 가져옴
                    val duplicate = response.body()
                    if (duplicate != null && duplicate.success) {
                        // 아이디 중복체크 성공
                        Toast.makeText(this@JoinActivity, "성공!!!", Toast.LENGTH_LONG).show()
                    } else {
                        // 아이디 중복체크 실패
                        Toast.makeText(this@JoinActivity, "아이디를 다시 설정해주세요", Toast.LENGTH_LONG).show()
                    }
                } else {
                    // 서버 응답 실패
                    val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                    val responseCode = response.code()
                    Log.d("joinActivity", "$responseCode $errorMessage")
                    Toast.makeText(this@JoinActivity, "서버 응답 실패 ($responseCode): $errorMessage", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<Duplicate>, t: Throwable) {
                // 네트워크 실패
                Log.e("JoinActivity", "Network request failed", t)
                Toast.makeText(this@JoinActivity, "네트워크 실패", Toast.LENGTH_LONG).show()
            }
        })
    }

    // 비밀번호와 비밀번호 확인이 같은지 확인하는 함수 (결과는 비밀번호 확인 edittext 오른쪽에서 확인 가능)
    private fun updatePasswordCheckView() {
        val passwordInput = binding.passwordEditText.text.toString()
        val checkPasswordInput = binding.checkPasswordEditText.text.toString()

        // 비밀번호 부분이 비어있지 않고, 비밀번호 부분과 비밀번호 확인 부분이 같을 때
        val isPasswordMatched = passwordInput.isNotEmpty() && passwordInput == checkPasswordInput

        // 위 조건을 만족할 때 제출 버튼 활성화 (추후 닉네임 & 아이디 중복확인 완료 시 활성화되도록 추가 예정)
        binding.submitInfoBtn.isEnabled = isPasswordMatched

        // 위 조건을 만족할 때 아이콘 체크 표시로 변경, 만족하지 않을 시 x 표시로 변경
        binding.isPasswordCheckedImageView.setImageResource(
            if (isPasswordMatched) R.drawable.baseline_check_24 else R.drawable.baseline_close_24
        )

        // 위 조건을 만족할 때 아이콘 색상 초록색으로 변경, 만족하지 않을 시 빨간색으로 변경
        val color = ContextCompat.getColor(this@JoinActivity, if (isPasswordMatched) R.color.nct else R.color.red)
        binding.isPasswordCheckedImageView.imageTintList = ColorStateList.valueOf(color)
    }


}



