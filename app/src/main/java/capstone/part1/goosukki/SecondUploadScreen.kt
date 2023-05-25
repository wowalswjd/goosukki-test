package capstone.part1.goosukki

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.viewpager2.widget.ViewPager2
import capstone.part1.goosukki.ui.theme.BgGrey
import capstone.part1.goosukki.ui.theme.Nct
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.google.accompanist.pager.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue


@Composable
fun SecondUploadScreen(navController: NavController, viewModel: PostUploadViewModel = viewModel()) {

    Scaffold(
        topBar = { AppBar("캡션 추가", onBackClicked = { navController.popBackStack() }) },
        modifier = Modifier.fillMaxSize(),
        backgroundColor = BgGrey,
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(vertical = it.calculateBottomPadding())
        ) {

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(horizontal = 10.dp, vertical = 30.dp) // 여백 크기 조정
            ) {
                val selectedImageUris by remember { viewModel.selectedImageUris }
                if (selectedImageUris.isEmpty()) {
                    MultiplePhotoPicker(onImagesSelected = { images ->
                        viewModel.selectedImageUris.value = images
                    })
                } else {
                    // 이미지 URI 리스트가 비어 있지 않으면서 이미 선택된 이미지가 있는 경우
                    HorizontalPagerWithImages(selectedImageUris)
                }

                //textfield
            }

            Button(
                // 맨 위 Column에서 padding start 30.dp 이미 줌
                onClick = { navController.navigate("thirdScreen") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp, end = 30.dp, bottom = 30.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Nct)
            ) {
                Text(text = "다음")
            }
        }
    }
}

@Composable
fun MultiplePhotoPicker(onImagesSelected: (List<Uri>) -> Unit) {
    val multiplePhotoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia()
    ) { selectedImageUris ->
        onImagesSelected(selectedImageUris)
    }

    val request = remember { PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly) }

    // 초기화된 ActivityResultLauncher를 사용하여 launch()를 호출
    LaunchedEffect(Unit) {
        multiplePhotoPicker.launch(request)
    }
}



@OptIn(ExperimentalCoilApi::class, ExperimentalFoundationApi::class, ExperimentalPagerApi::class)
@Composable
fun HorizontalPagerWithImages(images: List<Uri>) {
    val pagerState = rememberPagerState()

    val captionList = remember { mutableStateListOf<TextFieldValue>().apply { repeat(images.size) { add(TextFieldValue()) } } }


    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 5.dp)
    ) {
        HorizontalPager(
            state = pagerState,
            count = images.size,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .wrapContentHeight()
        ) { page ->
            val imageUri = images.getOrNull(page)
            if (imageUri != null) {
                val pageOffset = (page - pagerState.currentPage).coerceIn(-1, 1)

                val scale = animateFloatAsState(if (pageOffset == 0) 1f else 0.85f).value
                val alpha = animateFloatAsState(if (pageOffset == 0) 1f else 0.85f).value

                Box(
                    Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f, true)
                        .padding(horizontal = 30.dp, vertical = 30.dp)
                        .graphicsLayer {
                            scaleX = scale
                            scaleY = scale
                            this.alpha = alpha
                        }
                ) {
                    Image(
                        painter = rememberImagePainter(imageUri),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )

                    Box(Modifier.align(Alignment.BottomCenter)) {
                        val captionState = remember { mutableStateOf(TextFieldValue()) }

                        TextField(
                            value = captionState.value,
                            modifier = Modifier
                                .fillMaxWidth()
                                .onFocusChanged { focusState ->
                                    if (!focusState.isFocused) {
                                        // 캡션을 캡션 리스트에 추가합니다.
                                        captionList[page] = captionState.value
                                    }
                                },
                            onValueChange = { captionState.value = it },
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = Color.LightGray.copy(alpha=0.5f),
                                textColor = Color.Black,
                                cursorColor = Color.White,
                                focusedIndicatorColor = Color.White,
                                unfocusedIndicatorColor = Color.White
                            ),
                            placeholder = { Text(text = "캡션을 입력해주세요", color = Color.DarkGray) }
                        )
                    }
                }
            }
        }

        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            indicatorWidth = 8.dp,
            indicatorHeight = 8.dp,
            indicatorShape = CircleShape,
            activeColor = Nct,
            inactiveColor = Color.LightGray,
        )
    }

    // 저장된 캡션 리스트를 확인하기 위한 로그 출력
    LaunchedEffect(captionList) {
        captionList.forEachIndexed { index, caption ->
            Log.d("Caption", "Image ${index + 1}: ${caption.text}")
        }
    }
}