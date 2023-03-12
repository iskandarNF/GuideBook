package tj.iskandar.guidebook.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import tj.iskandar.guidebook.db.entity.GuideEntity

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ItemBook(item: GuideEntity, onClick:(String)->Unit) {
    Card(
        elevation = 1.dp, modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .height(300.dp), shape = RoundedCornerShape(20.dp), onClick = {
                onClick(item.url)
        }
    ) {
        Row(Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
            SubcomposeAsyncImage(
                model = item.icon,
                contentDescription = "",
                modifier = Modifier
                    .size(100.dp)
                    .background(color = Color(0xFFFAFAFA))
                    .clip(RoundedCornerShape(10.dp))
            ) {
                val state = painter.state
                if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                    CircularProgressIndicator(
                        color = Color(0xFF4CAF50)
                    )
                } else {
                    SubcomposeAsyncImageContent()
                }
            }
            Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceAround, horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = item.name, fontSize = 20.sp)
                Text(text = item.startDate, fontSize = 16.sp)
                Text(text = item.endDate, fontSize = 16.sp)

            }
            }

    }
}