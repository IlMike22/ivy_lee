package de.mindmarket.ivyleemaster.core.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.mindmarket.ivyleemaster.R
import de.mindmarket.ivyleemaster.core.domain.model.Genre
import de.mindmarket.ivyleemaster.core.domain.model.Idea
import de.mindmarket.ivyleemaster.idea.presentation.IdeaAction
import de.mindmarket.ivyleemaster.ui.theme.IvyLeeMasterTheme

@Composable
fun IvyIdeaItem(
    idea: Idea,
    modifier: Modifier = Modifier,
    onAction: (IdeaAction) -> Unit
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
            .clickable {

            }
            .height(220.dp)
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(2.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
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
                Button(
                    onClick = {
                        onAction(IdeaAction.OnMoveToTasksClick(idea))
                    },
                ) {
                    Text(stringResource(R.string.idea_item_move_button_text))
                }
            }

            Text(
                text = idea.title,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = idea.subtitle,
                fontSize = 14.sp
            )

            Spacer(Modifier.height(32.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (idea.isRepeatable) {
                    AssistChip(
                        label = { Text(text = stringResource(R.string.card_item_repeatable_text)) },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.Share,
                                modifier = Modifier.size(16.dp),
                                contentDescription = stringResource(R.string.card_item_repeatable_text)
                            )
                        },
                        onClick = {}
                    )
                }

                if (idea.isUrgent) {
                    AssistChip(
                        label = { Text(text = stringResource(R.string.card_item_urgent_text)) },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.Info,
                                modifier = Modifier.size(16.dp),
                                contentDescription = stringResource(R.string.card_item_urgent_text)
                            )
                        },
                        onClick = {}
                    )
                }
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
                title = "Idea Title",
                subtitle = "Idea Subtitle is optional and can be empty.",
                genre = Genre.FINANCE,
                isUrgent = true,
                isRepeatable = true
            ),
            onAction = {}
        )
    }
}