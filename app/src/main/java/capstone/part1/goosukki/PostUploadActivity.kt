package capstone.part1.goosukki

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.Nullable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import capstone.part1.goosukki.ui.theme.GoosukkiTheme
import capstone.part1.goosukki.FirstUploadScreen
import capstone.part1.goosukki.SecondUploadScreen
import capstone.part1.goosukki.ThirdUploadScreen
import capstone.part1.goosukki.ui.theme.BgGrey
import capstone.part1.goosukki.ui.theme.Nct

class PostUploadActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GoosukkiTheme {
                // A surface container using the 'background' color from the theme
                PostUploadScreen()
            }
        }
    }
}


@Composable
fun PostUploadScreen() {

    val navController = rememberNavController()
    val viewModel: PostUploadViewModel = viewModel() // 첫 번째 화면에서 생성


    NavHost(navController = navController, startDestination = "firstScreen") {
        composable("firstScreen") {
            FirstUploadScreen(navController = navController, viewModel = viewModel)
        }
        composable("secondScreen") {
            SecondUploadScreen(navController = navController, viewModel = viewModel)
        }
        composable("thirdScreen") {
            ThirdUploadScreen(navController = navController, viewModel = viewModel)
        }
    }

}


// AppBar 함수 재사용 용도
@Composable
fun AppBar(ScreenName: String, @Nullable onBackClicked: (() -> Unit)? = null) {
    Column {
        TopAppBar(
            title = { Text(text = "$ScreenName", color = Color.White, textAlign = TextAlign.Center) },
            backgroundColor = Color.Black,
            navigationIcon = {
                if (onBackClicked != null) {
                    IconButton(onClick = { onBackClicked() }) {
                        Icon(
                            tint = Color.White,
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "뒤로 가기 버튼"
                        )
                    }
                }
            },
            actions = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        tint = Color.White,
                        imageVector = Icons.Filled.Close,
                        contentDescription = "게시물 업로드 중단 버튼")
                }
            },
        )

    }
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    GoosukkiTheme {
        PostUploadScreen()
    }
}