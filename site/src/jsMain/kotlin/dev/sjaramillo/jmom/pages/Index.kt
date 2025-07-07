package dev.sjaramillo.jmom.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import dev.sjaramillo.jmom.components.layouts.PageLayoutData
import dev.sjaramillo.jmom.utils.prettyPrintJson
import dev.sjaramillo.jmom.utils.validateJson
import org.jetbrains.compose.web.css.borderRadius
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.fontFamily
import org.jetbrains.compose.web.css.fontSize
import org.jetbrains.compose.web.css.height
import org.jetbrains.compose.web.css.minWidth
import org.jetbrains.compose.web.css.padding
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.rgba
import org.jetbrains.compose.web.css.width
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.dom.TextArea
import org.jetbrains.compose.web.dom.Select
import org.jetbrains.compose.web.dom.Option

@InitRoute
fun initHomePage(ctx: InitRouteContext) {
    ctx.data.add(PageLayoutData("J-son Validator"))
}

@Page
@Layout(".components.layouts.PageLayout")
@Composable
fun HomePage() {
    var jsonInput by remember { mutableStateOf("") }
    var validationResult by remember { mutableStateOf<ValidationResult?>(null) }
    var selectedIndentation by remember { mutableStateOf(2) } // Default to 2 spaces

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.cssRem),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(90.percent)
                .maxWidth(800.px)
                .gap(1.cssRem),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Title
            H1(
                attrs = Modifier
                    .fontWeight(FontWeight.Bold)
                    .fontSize(2.cssRem)
                    .margin(bottom = 1.cssRem)
                    .color(
                        when (ColorMode.current) {
                            ColorMode.LIGHT -> Colors.Black
                            ColorMode.DARK -> Colors.White
                        }
                    )
                    .toAttrs()
            ) {
                Text("J-son Validator")
            }

            // Indentation Selector
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .margin(bottom = 0.5.cssRem),
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SpanText(
                        text = "Indentation:",
                        modifier = Modifier
                            .margin(right = 0.5.cssRem)
                            .fontWeight(FontWeight.Bold)
                    )

                    // Spinner (dropdown) for indentation selection
                    Select(
                        attrs = {
                            style {
                                padding(0.5.cssRem)
                                fontSize(1.cssRem)
                                borderRadius(0.5.cssRem)
                                minWidth(150.px)
                            }
                            onInput { event ->
                                // Parse the selected value to Int and update the state
                                val spaces = event.value?.toIntOrNull() ?: 2
                                selectedIndentation = spaces
                            }
                        }
                    ) {
                        // Add options for 2, 4, 6, and 8 spaces
                        listOf(2, 4, 6, 8).forEach { spaces ->
                            Option(
                                value = spaces.toString(),
                                attrs = {
                                    if (selectedIndentation == spaces) {
                                        attr("selected", "true")
                                    }
                                }
                            ) {
                                Text("$spaces spaces")
                            }
                        }
                    }
                }
            }

            // JSON Input TextArea
            TextArea(
                value = jsonInput,
                attrs = {
                    style {
                        width(100.percent)
                        height(300.px)
                        padding(1.cssRem)
                        fontSize(1.cssRem)
                        fontFamily("monospace")
                        borderRadius(0.5.cssRem)
                        property("resize", "vertical")
                    }
                    attr("placeholder", "Paste your JSON here...")
                    onInput { jsonInput = it.value }
                }
            )

            // Validate Button
            Button(
                onClick = {
                    validationResult = if (jsonInput.isBlank()) {
                        ValidationResult.Error("Where is my J-son? 🥹")
                    } else {
                        // Use validateJson method to validate JSON
                        validateJson(jsonInput).fold(
                            onSuccess = { 
                                // If JSON is valid, pretty print it with selected indentation
                                prettyPrintJson(jsonInput, selectedIndentation).fold(
                                    onSuccess = { prettyJson ->
                                        // Update the text area with pretty-printed JSON
                                        jsonInput = prettyJson
                                        ValidationResult.Success("Good J-son! 🥰")
                                    },
                                    onFailure = { ValidationResult.Error("Error formatting JSON: " + (it.message ?: "Unknown error")) }
                                )
                            },
                            onFailure = { ValidationResult.Error("Bad J-son! 😤\n" + (it.message ?: "Unknown error")) }
                        )
                    }
                },
                modifier = Modifier
                    .width(150.px)
                    .height(40.px)
                    .margin(topBottom = 1.cssRem)
            ) {
                SpanText("Validate")
            }

            // Validation Result
            validationResult?.let { result ->
                Div(
                    attrs = Modifier
                        .fillMaxWidth()
                        .padding(1.cssRem)
                        .backgroundColor(
                            when (result) {
                                is ValidationResult.Success -> rgba(0, 128, 0, 0.1)
                                is ValidationResult.Error -> rgba(255, 0, 0, 0.1)
                            }
                        )
                        .borderRadius(0.5.cssRem)
                        .toAttrs()
                ) {
                    SpanText(
                        text = result.message,
                        modifier = Modifier.color(
                            when (result) {
                                is ValidationResult.Success -> Colors.Green
                                is ValidationResult.Error -> Colors.Red
                            }
                        )
                    )
                }
            }
        }
    }
}

sealed class ValidationResult(val message: String) {
    class Success(message: String) : ValidationResult(message)
    class Error(message: String) : ValidationResult(message)
}
