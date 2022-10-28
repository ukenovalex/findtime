package ru.ukenov.findtime.android.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Place
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.ukenov.findtime.android.theme.AppTheme

sealed class Screen(val title: String) {
    object TimeZoneScreen : Screen("Timezone")
    object FindTimeScreen : Screen("Find Time")
}

data class BottomNavigationItem(
    val route: String,
    val icon: ImageVector,
    val iconContentDescription: String
)

val bottomNavigationItems = listOf(
    BottomNavigationItem(
        Screen.TimeZoneScreen.title,
        Icons.Filled.Language,
        "TimeZones"
    ),
    BottomNavigationItem(
        Screen.FindTimeScreen.title,
        Icons.Filled.Place,
        "Find Time"
    )
)

@Preview
@Composable
fun MainView(actionBarFun: topBarFun = { emptyComposable() }) {
    val showAddDialog = remember { mutableStateOf(false) }
    val currentTimezoneStrings = remember {
        SnapshotStateList<String>()
    }
    val selectedIndex = remember { mutableStateOf(0) }

    AppTheme {
        Scaffold(
            topBar = {
                actionBarFun(selectedIndex.value)
            },
            floatingActionButton = {
                if (selectedIndex.value == 0) {
                    FloatingActionButton(
                        modifier = Modifier.padding(16.dp),
                        onClick = { showAddDialog.value = true }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null
                        )
                    }
                }
            },
            bottomBar = {
                BottomNavigation {
                    bottomNavigationItems.forEachIndexed { i, bottomNavigationItem ->
                        BottomNavigationItem(
                            icon = {
                                Icon(
                                    bottomNavigationItem.icon,
                                    contentDescription = bottomNavigationItem.iconContentDescription
                                )
                            },
                            selected = selectedIndex.value == i,
                            onClick = { selectedIndex.value = i }
                        )
                    }
                }
            }
        ){ padding ->
            padding.toString()
            if (showAddDialog.value) {
                AddTimeZoneDialog(
                    onAdd = { newTimezones ->
                        showAddDialog.value = false
                        for (zone in newTimezones) {
                            if (!currentTimezoneStrings.contains(zone)) {
                                currentTimezoneStrings.add(zone)
                            }
                        }
                    },
                    onDismiss = {
                        showAddDialog.value = false
                    },
                )
            }
            when (selectedIndex.value) {
                0 -> TimeZoneScreen(currentTimezoneStrings = currentTimezoneStrings)
                else -> FindMeetingScreen(currentTimezoneStrings)
            }
        }

    }
}