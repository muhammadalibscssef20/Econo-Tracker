package com.example.econo_tracker

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.econo_tracker.ui.theme.EconoTrackerTheme
import kotlinx.coroutines.delay
import android.window.SplashScreen as SplashScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StartApp()
        }
    }
}


data class Transaction(
    val title: String,
    val amount: Int,
    val transactionType: String
)



@Composable
fun Testing() {
    Text(text = "Hello World")
}


@Composable
fun StartApp() {
    val sherif = SharedPrefs(context = LocalContext.current)
    sherif.save("expense", 0)
    sherif.save("income", 0)

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "splash_screen") {
        composable("splash_screen") {
            SplashScreen(navController = navController)
        }
        composable("main_screen") {
            MainScreen(navController = navController)
        }
        composable("transaction_screen") {
            TransactionScreen(navController = navController)
        }
        composable("overview_screen") {
            OverViewScreen(navController = navController)
        }

    }
}

@Composable
fun SplashScreen(navController: NavController) {
    LaunchedEffect(Unit) {
        delay(3000)
        navController.navigate("main_screen")
    }
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Economic Tracker",
                style = MaterialTheme.typography.displaySmall.copy(fontSize = 32.sp, letterSpacing = 5.sp),
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(20.dp))
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Splash Screen Logo"
            )
        }
    }
}

@Composable
fun MainScreen(navController: NavController) {


    var currentBalance by remember { mutableStateOf(0) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp), // Apply padding around the entire Column
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Current Balance: $currentBalance",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = { navController.navigate("transaction_screen") },
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium // Rounded corners
            ) {
                Text(text = "Add Income/Expense")
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { navController.navigate("overview_screen") },
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium // Consistent rounded corners with other buttons
            ) {
                Text(text = "Overview")
            }
        }
    }
}
@Composable
fun TransactionScreen(navController: NavController) {
    val sherif = SharedPrefs(context = LocalContext.current)
    // Initial state setup
    var title by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var showDropdown by remember { mutableStateOf(false) }
    var selectedTransactionType by remember { mutableStateOf("") }
    val transactionTypes = listOf("Income", "Expense")
    var textFieldSize by remember { mutableStateOf(Size.Zero) }

    // Icon for the dropdown menu
    val icon = if (showDropdown) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Title TextField
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Amount TextField
        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Amount") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Transaction Type Dropdown
        OutlinedTextField(
            value = selectedTransactionType,
            onValueChange = { selectedTransactionType = it },
            modifier = Modifier
                .fillMaxWidth()
                ,
            label = { Text("Transaction Type") },
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Button to add transaction
        Button(
            onClick = {

                if(selectedTransactionType.lowercase() == "expense"){
                    sherif.save("expense", sherif.load("expense", 0) + amount.toInt())
                } else {
                    sherif.save("income", sherif.load("income", 0) + amount.toInt())
                }

                // Logic to handle adding a transaction goes here
                navController.navigate("main_screen")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Add Transaction")
        }
    }
}

@Composable
fun OverViewScreen(navController: NavController) {
    val context = LocalContext.current
    val sharedPrefs = SharedPrefs(context = context)
    val expense = sharedPrefs.load("expense", 0)
    val income = sharedPrefs.load("income", 0)
    val total = income - expense

    Column(modifier = Modifier.padding(16.dp)) {
        // Display income
        OverviewCard(label = "Income", amount = income)

        Spacer(modifier = Modifier.height(8.dp))

        // Display expense
        OverviewCard(label = "Expense", amount = expense)

        Spacer(modifier = Modifier.height(8.dp))

        // Display total
        OverviewCard(label = "Total", amount = total)
    }
}

@Composable
fun OverviewCard(label: String, amount: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "$$amount",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}



private class SharedPrefs(context: Context) {
    private val prefs = context.getSharedPreferences("EconoTracker", Context.MODE_PRIVATE)

    fun save(key: String, value: Int) {
        prefs.edit().putInt(key, value).apply()
    }

    fun load(key: String, value: Int): Int{
        return prefs.getInt(key, value) ?: 0
    }
}




