package capstone.part1.goosukki

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import capstone.part1.goosukki.ui.theme.BgGrey
import capstone.part1.goosukki.ui.theme.GoosukkiTheme
import capstone.part1.goosukki.ui.theme.Nct

@Composable
fun FirstUploadScreen(navController: NavController, viewModel: PostUploadViewModel = viewModel()) {


    val titleTextStyle = TextStyle(
        color = Color.White,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
    )

    Scaffold(
        topBar = { AppBar("새 게시물", onBackClicked = null) },
        modifier = Modifier.fillMaxSize(),
        backgroundColor = BgGrey,
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(start = 30.dp, end = 30.dp)
            .padding(vertical = it.calculateBottomPadding())
        ) {

            // 제목
            Text(
                text = "제목",
                style = titleTextStyle,
                modifier = Modifier
                    .padding(top = 30.dp)
            )

            // 제목 입력 창
            TextField(
                value = viewModel.titleText.value,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                onValueChange = { viewModel.titleText.value = it },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    textColor = Color.White,
                    cursorColor = Color.White,
                    placeholderColor = Color.LightGray,
                    focusedIndicatorColor = Color.White, // 밑줄 색상을 흰색으로 설정
                    unfocusedIndicatorColor = Color.White // 밑줄 색상을 흰색으로 설정
                ),
                placeholder = { Text(text = "제목을 입력해주세요", color = Color.LightGray)}

            )

            // 공개
            Text(
                text = "공개",
                style = titleTextStyle,
                modifier = Modifier
                    .padding(top = 30.dp)
            )

            // 전체 공개/ 비공개 선택
            MultiToggleButton(onItemSelected = {

            })

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            )

            Button(
                // 맨 위 Column에서 padding start 30.dp 이미 줌
                onClick = { navController.navigate("secondScreen") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 30.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Nct)
            ) {
                Text(text = "다음")
            }
        }
    }
}

// 전체공개/ 비공개 선택
@Composable
fun MultiToggleButton(
    onItemSelected: (Boolean) -> Unit,
    viewModel: PostUploadViewModel = viewModel()
) {
    // 전체 공개일 경우 secret : false, 비공개일 경우 secret: true
    // var selectedOption by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxWidth().padding(top = 30.dp),
        contentAlignment = Alignment.Center
    ) {
        Row (
            horizontalArrangement = Arrangement.Center
        )  {
            Text(
                text = "전체 공개",
                modifier = Modifier
                    .weight(0.5f)
                    .background(
                        color = Color.White,
                        shape = CutCornerShape(topStart = 4.dp, bottomStart = 4.dp),
                    )
                    .border(
                        width = 5.dp,
                        color = if (!viewModel.isSecret.value) Nct else Color.Transparent,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .clickable {
                        viewModel.isSecret.value = false
                        onItemSelected(false)
                    }
                    .padding(10.dp),
                color = Color.Black,
                textAlign = TextAlign.Center,
            )
            Text(
                text = "비공개",
                modifier = Modifier
                    .weight(0.5f)
                    .background(
                        color = Color.White,
                        shape = CutCornerShape(topEnd = 4.dp, bottomEnd = 4.dp)
                    )
                    .border(
                        width = 5.dp,
                        color = if (viewModel.isSecret.value) Nct else Color.Transparent,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .clickable {
                        viewModel.isSecret.value = true
                        onItemSelected(true)
                    }
                    .padding(10.dp),

                color = Color.Black,
                textAlign = TextAlign.Center,
            )
        }
    }


}