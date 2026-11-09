package ru.yandex.practicum.contacts

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test
import ru.yandex.practicum.contacts.data.models.CountryCode
import ru.yandex.practicum.contacts.data.models.MessagingApp
import ru.yandex.practicum.contacts.presentation.country.CountryCodeBottomSheet
import ru.yandex.practicum.contacts.presentation.messengers.MessengersBottomSheet

class BottomSheetRegressionTest {
    @get:Rule val compose = createComposeRule()
    @Test fun countriesKeepMultipleSelectionAfterReopening() {
        compose.setContent {
            var selected by remember { mutableStateOf(emptySet<CountryCode>()) }
            var show by remember { mutableStateOf(true) }
            MaterialTheme {
                if (show) CountryCodeBottomSheet(selected, { selected = it }, { show = false })
                else Button({ show = true }) { Text("Reopen") }
            }
        }
        compose.onNodeWithText("+1").performClick()
        compose.onNodeWithText("+44").performClick()
        compose.onAllNodes(isToggleable() and isOn()).assertCountEquals(2)
        compose.onNodeWithContentDescription("Закрыть").performClick()
        compose.onNodeWithText("Reopen").performClick()
        compose.onAllNodes(isToggleable() and isOn()).assertCountEquals(2)
        compose.onNodeWithText("+1").performClick()
        compose.onAllNodes(isToggleable() and isOn()).assertCountEquals(1)
    }
    @Test fun messengersKeepMultipleSelectionAfterReopening() {
        compose.setContent {
            var selected by remember { mutableStateOf(emptySet<MessagingApp>()) }
            var show by remember { mutableStateOf(true) }
            MaterialTheme {
                if (show) MessengersBottomSheet(selected, { selected = it }, { show = false })
                else Button({ show = true }) { Text("Reopen") }
            }
        }
        compose.onNodeWithText("TELEGRAM").performClick()
        compose.onNodeWithText("WHATS_APP").performClick()
        compose.onAllNodes(isToggleable() and isOn()).assertCountEquals(2)
        compose.onNodeWithContentDescription("Закрыть").performClick()
        compose.onNodeWithText("Reopen").performClick()
        compose.onAllNodes(isToggleable() and isOn()).assertCountEquals(2)
        compose.onNodeWithText("TELEGRAM").performClick()
        compose.onAllNodes(isToggleable() and isOn()).assertCountEquals(1)
    }
}
