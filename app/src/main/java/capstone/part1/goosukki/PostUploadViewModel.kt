package capstone.part1.goosukki

import android.net.Uri
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel

class PostUploadViewModel : ViewModel() {

    // 첫 번쨰 화면 - 제목, 공개 범위
    val titleText = mutableStateOf(TextFieldValue())
    var isSecret = mutableStateOf(false)

    // 두 번째 화면
    val selectedImageUris = mutableStateOf<List<Uri>>(emptyList())
    val captionList = mutableStateListOf<MutableState<String>>()

    // 세 번째 화면 - 사용자 위치 경위도와 장소명
    val latitude = 37.5664759
    val longitude = 126.9483797
    val locationName = mutableStateOf(TextFieldValue())

}