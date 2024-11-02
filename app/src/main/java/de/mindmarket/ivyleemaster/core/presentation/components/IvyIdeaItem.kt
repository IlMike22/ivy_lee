package de.mindmarket.ivyleemaster.core.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.mindmarket.ivyleemaster.core.domain.model.Genre
import de.mindmarket.ivyleemaster.core.domain.model.Idea
import de.mindmarket.ivyleemaster.ui.theme.IvyLeeMasterTheme

@Composable
fun IvyIdeaItem(
    idea: Idea,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 4.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    text = idea.title.text.toString(),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                if (idea.genre != null) {
                    Image(
                        modifier = Modifier
                            .size(32.dp),
                        colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.primary),
                        imageVector = idea.genre.icon,
                        contentDescription = null
                    )
                }
            }
            Text(
                modifier = Modifier.padding(4.dp),
                text = idea.subtitle.text.toString(),
                fontSize = 14.sp
            )

            Spacer(Modifier.height(32.dp))

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = "Repeatable: ${idea.isRepeatable}",
                    fontSize = 14.sp,
                    fontStyle = FontStyle.Italic
                )
                Text(
                    text = "Urgent: ${idea.isUrgent}",
                    fontSize = 14.sp,
                    fontStyle = FontStyle.Italic
                )
            }

        }
    }
}

@Preview
@Composable
private fun IvyIdeaItemPreview() {
    IvyLeeMasterTheme {
        IvyIdeaItem(
            idea = Idea(
                id = "", userId = "",
                title = TextFieldState("Idea Title"),
                subtitle = TextFieldState("Idea Subtitle is optional and can be empty."),
                genre = Genre.FINANCE,
                isUrgent = false,
                isRepeatable = true
            )
        )
    }
}