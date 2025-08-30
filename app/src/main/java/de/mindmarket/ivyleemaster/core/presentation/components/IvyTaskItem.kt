package de.mindmarket.ivyleemaster.core.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import de.mindmarket.ivyleemaster.R
import de.mindmarket.ivyleemaster.idea.presentation.IdeaAction
import de.mindmarket.ivyleemaster.task.domain.model.Status
import de.mindmarket.ivyleemaster.task.domain.model.Task
import de.mindmarket.ivyleemaster.task.presentation.TaskAction
import de.mindmarket.ivyleemaster.ui.theme.IvyLeeMasterTheme

@Composable
fun IvyTaskItem(
    task: Task,
    onAction: (TaskAction) -> Unit,
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
            .clickable {

            }
            .height(220.dp)
            .padding(8.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .weight(1f),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                    ,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = task.title,
                        style = MaterialTheme.typography.headlineMedium
                    )

                    Spacer(Modifier.height(4.dp))

                    Text(
                        text = task.description,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                Spacer(Modifier.height(4.dp))
                Text(text = task.status.name)
            }

            Button(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(8.dp),
                onClick = {
                    onAction(TaskAction.OnTaskCompleteClick(task.id))
                },
            ) {
                Text(stringResource(R.string.task_on_task_complete_button_text))
            }
        }
    }
}

@Preview
@Composable
private fun IvyTaskItemPreview() {
    IvyLeeMasterTheme {
        IvyTaskItem(
            task = Task(
                id = "0",
                title = "Task title",
                description = "Task description",
                status = Status.OPEN
            ),
            onAction = {}
        )
    }
}