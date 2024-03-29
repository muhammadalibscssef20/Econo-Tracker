/*
package com.example.econo_tracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.econo_tracker.ui.theme.EconoTrackerTheme

class MainActivity2 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EconoTrackerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                }
            }
        }
    }
}


@Composable
fun TransactionScreen(navController: NavController) {

    var title by remember {
        mutableStateOf("")
    }

    var amount by remember {
        mutableIntStateOf(0)
    }

    var transactionType by remember {
        mutableStateOf("Income")
    }

    var showDropdown by remember {
        mutableStateOf(false)
    }

    val transactionTypes = listOf("Income", "Expense")

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        TextField(value = title, placeholder = {
            Text(text = "Title")
        }, onValueChange = {
            title = it
        })

        Spacer(modifier = Modifier.size(20.dp))

        TextField(value = amount.toString(), placeholder = {
            Text(text = "Amount")
        }, onValueChange = {
            amount = it.toInt()
        })

        Spacer(modifier = Modifier.size(20.dp))

        Button(onClick = { showDropdown = true }) {
            Text(text = transactionType)
        }

        DropdownMenu(
            expanded = showDropdown,
            onDismissRequest = { showDropdown = false }
        ) {
            transactionTypes.forEach { type ->
                DropdownMenuItem(onClick = {
                    transactionType = type
                    showDropdown = false
                }) {
                    Text(text = type)
                }
            }
        }

        Spacer(modifier = Modifier.size(20.dp))

        Button(onClick = {
            navController.navigate("overview_screen")
        }) {
            Text(text = "Add Transaction")
        }

    }
}

@Composable
fun TransactionTypeDropdown() {
    var transactionType by remember {
        mutableStateOf("Income")
    }

    var showDropdown by remember {
        mutableStateOf(false)
    }

    val transactionTypes = listOf("Income", "Expense")

    Button(onClick = { showDropdown = true }) {
        Text(text = transactionType)
    }

    DropdownMenu(
        expanded = showDropdown,
        onDismissRequest = { showDropdown = false }
    ) {
        transactionTypes.forEach { type ->
            DropdownMenuItem(onClick = {
                transactionType = type
                showDropdown = false
            }) {
                Text(text = type)
            }
        }
    }
}


*/
