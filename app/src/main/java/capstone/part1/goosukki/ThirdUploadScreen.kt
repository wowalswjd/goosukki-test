package capstone.part1.goosukki

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import capstone.part1.goosukki.ui.theme.BgGrey
import capstone.part1.goosukki.ui.theme.Nct
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun ThirdUploadScreen(navController: NavController, viewModel: PostUploadViewModel = viewModel()) {

    Scaffold(
        topBar = { AppBar("위치 등록", onBackClicked = { navController.popBackStack() }) },
        modifier = Modifier.fillMaxSize(),
        backgroundColor = BgGrey,
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(start = 30.dp, end = 30.dp)
            .padding(vertical = it.calculateBottomPadding())
        ) {
            // val locationNameState = remember { mutableStateOf(TextFieldValue()) }

            // 여기 사용자 현재 위치 알아오는 코드
            val current = LatLng(37.5664759, 126.9483797)
            val cameraPositionState = rememberCameraPositionState {
                position = CameraPosition.fromLatLngZoom(current, 17f)
            }
            GoogleMap(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .padding(top = 30.dp),
                cameraPositionState = cameraPositionState
            ) {
                Marker(
                    state = MarkerState(position = current),
                    title = "Singapore",
                    snippet = "Marker in Singapore"
                )
            }

            val LocationNameTextStyle = TextStyle(
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            // 공개
            Text(
                text = "장소명",
                style = LocationNameTextStyle,
                modifier = Modifier
                    .padding(top = 30.dp)
            )

            // 제목 입력 창
            TextField(
                value = viewModel.locationName.value,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                onValueChange = { viewModel.locationName.value = it},
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    textColor = Color.White,
                    cursorColor = Color.White,
                    placeholderColor = Color.LightGray,
                    focusedIndicatorColor = Color.White, // 밑줄 색상을 흰색으로 설정
                    unfocusedIndicatorColor = Color.White // 밑줄 색상을 흰색으로 설정
                ),
                placeholder = { Text(text = "장소명을 입력해주세요", color = Color.LightGray)}

            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            )

            Button(
                // 맨 위 Column에서 padding start 30.dp 이미 줌
                onClick = {  },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 30.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Nct)
            ) {
                Text(text = "완료")
            }
        }
    }
}