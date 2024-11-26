package com.melissa.todoapp.addtasks.ui

import android.app.Dialog
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.melissa.todoapp.addtasks.ui.models.TaskModel

@Composable
fun TasksScreen(tasksViewModel: TasksViewModel) {
    val showDialogState: Boolean by tasksViewModel.showDialod.observeAsState(false)

    Box(modifier = Modifier.fillMaxSize()) {
        AddTasksDialog(
            showDialogState,
            onDismiss = { tasksViewModel.onDialogClose() },
            onTaskAdded = { tasksViewModel.onTasksCreated(it) })
        FAB(
            Modifier.align(alignment = Alignment.BottomEnd),
            onShowDialogClick = { tasksViewModel.onShowDialogClick() })
        TasksList(tasksViewModel)
    }
}

@Composable
fun TasksList(tasksViewModel: TasksViewModel) {
    val myTasks: List<TaskModel> = tasksViewModel.tasks
    LazyColumn {
        items(myTasks, key = { it.id }) { task ->
            ItemTask(taskModel = task, tasksViewModel)
        }
    }
}

@Composable
fun ItemTask(taskModel: TaskModel, tasksViewModel: TasksViewModel) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp).pointerInput(Unit){
                detectTapGestures(onLongPress = {
                    tasksViewModel.onItemRemove(taskModel)
                })
            },
        elevation = CardDefaults.elevatedCardElevation(8.dp)
    ) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(taskModel.task, modifier = Modifier
                .weight(1f)
                .padding(horizontal = 4.dp))
            Checkbox(checked = taskModel.selected, onCheckedChange = {
                tasksViewModel.onCheckBoxSelected(taskModel)
            })
        }
    }
}

@Composable
fun FAB(modifier: Modifier, onShowDialogClick: () -> Unit?) {
    FloatingActionButton(
        onClick = {
            onShowDialogClick()
        },
        shape = CircleShape,
        modifier = modifier
    ) {
        Icon(Icons.Filled.Add, contentDescription = "")
    }
}

@Composable
fun AddTasksDialog(show: Boolean, onDismiss: () -> Unit?, onTaskAdded: (String) -> Unit?) {
    var myTask by remember { mutableStateOf("") }
    if (show) {
        Dialog(onDismissRequest = { onDismiss() }) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                Text(
                    text = "Añade tu tarea",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.size(16.dp))
                TextField(
                    value = myTask,
                    maxLines = 1,
                    singleLine = true,
                    onValueChange = { myTask = it })
                Spacer(modifier = Modifier.size(16.dp))
                Button(onClick = {
                    onTaskAdded(myTask)
                    myTask = ""
                }, modifier = Modifier.fillMaxWidth()) {
                    Text("Add Task")
                }
            }
        }
    }
}