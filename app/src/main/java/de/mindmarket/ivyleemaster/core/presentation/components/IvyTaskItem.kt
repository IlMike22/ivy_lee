package de.mindmarket.ivyleemaster.core.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.mindmarket.ivyleemaster.task.domain.model.Status
import de.mindmarket.ivyleemaster.task.domain.model.Task
import de.mindmarket.ivyleemaster.ui.theme.IvyLeeMasterTheme

@Composable
fun IvyTaskItem(
    task: Task,
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

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    text = task.description,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Spacer(Modifier.height(4.dp))
            Text(text = task.status.name)
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
            )
        )
    }
}