package com.example.calculadora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.calculadora.ui.theme.CalculadoraTheme
import net.objecthunter.exp4j.ExpressionBuilder
import kotlin.math.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculadoraTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Calculator(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun Calculator(modifier: Modifier = Modifier) {
    var input by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var trigFunction by remember { mutableStateOf<String?>(null) }

    Column(modifier = modifier.padding(16.dp)) {
        Text(
            text = "Result: $result",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )

        Text(
            text = input,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )

        Column(modifier = Modifier.fillMaxWidth()) {
            Row {
                Button(onClick = { input += "7" }, modifier = Modifier.weight(1f)) { Text("7") }
                Button(onClick = { input += "8" }, modifier = Modifier.weight(1f)) { Text("8") }
                Button(onClick = { input += "9" }, modifier = Modifier.weight(1f)) { Text("9") }
                Button(onClick = { input += "/" }, modifier = Modifier.weight(1f)) { Text("/") }
            }
            Row {
                Button(onClick = { input += "4" }, modifier = Modifier.weight(1f)) { Text("4") }
                Button(onClick = { input += "5" }, modifier = Modifier.weight(1f)) { Text("5") }
                Button(onClick = { input += "6" }, modifier = Modifier.weight(1f)) { Text("6") }
                Button(onClick = { input += "*" }, modifier = Modifier.weight(1f)) { Text("*") }
            }
            Row {
                Button(onClick = { input += "1" }, modifier = Modifier.weight(1f)) { Text("1") }
                Button(onClick = { input += "2" }, modifier = Modifier.weight(1f)) { Text("2") }
                Button(onClick = { input += "3" }, modifier = Modifier.weight(1f)) { Text("3") }
                Button(onClick = { input += "-" }, modifier = Modifier.weight(1f)) { Text("-") }
            }
            Row {
                Button(onClick = { input += "0" }, modifier = Modifier.weight(1f)) { Text("0") }
                Button(onClick = { input += "." }, modifier = Modifier.weight(1f)) { Text(".") }
                Button(onClick = {
                    val calculatedResult = try {
                        evalExpression(input)
                    } catch (e: Exception) {
                        "Error"
                    }
                    result = calculatedResult.toString()
                    input = ""
                }, modifier = Modifier.weight(1f)) { Text("=") }
                Button(onClick = { input += "+" }, modifier = Modifier.weight(1f)) { Text("+") }
            }
            Row {
                Button(onClick = { input = "" }, modifier = Modifier.weight(1f)) { Text("C") }
                Button(onClick = { trigFunction = "sin" }, modifier = Modifier.weight(1f)) { Text("Sin") }
                Button(onClick = { trigFunction = "cos" }, modifier = Modifier.weight(1f)) { Text("Cos") }
                Button(onClick = { trigFunction = "tan" }, modifier = Modifier.weight(1f)) { Text("Tan") }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row {
                Button(onClick = {
                    val angle = input.toDoubleOrNull()
                    if (angle != null) {
                        val radians = Math.toRadians(angle)
                        result = when (trigFunction) {
                            "sin" -> "sin: ${sin(radians)}"
                            "cos" -> "cos: ${cos(radians)}"
                            "tan" -> "tan: ${tan(radians)}"
                            else -> "Select a trig function"
                        }
                        input = ""
                    }
                }, modifier = Modifier.weight(1f)) { Text("Compute Trig") }
            }
        }
    }
}

fun evalExpression(expression: String): Double {
    return try {
        val expr = ExpressionBuilder(expression).build()
        expr.evaluate()
    } catch (e: Exception) {
        Double.NaN
    }
}

@Preview(showBackground = true)
@Composable
fun CalculatorPreview() {
    CalculadoraTheme {
        Calculator()
    }
}
