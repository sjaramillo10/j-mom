package dev.sjaramillo.jmom.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import dev.sjaramillo.jmom.utils.validateJson
import org.jetbrains.compose.web.css.borderRadius
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.fontFamily
import org.jetbrains.compose.web.css.fontSize
import org.jetbrains.compose.web.css.height
import org.jetbrains.compose.web.css.padding
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.rgba
import org.jetbrains.compose.web.css.width
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.dom.TextArea

@InitRoute
fun initHomePage(ctx: InitRouteContext) {
    ctx.data.add(PageLayoutData("JSON Validator"))
}

@Page
@Layout(".components.layouts.PageLayout")
@Composable
fun HomePage() {
    var jsonInput by remember { mutableStateOf("") }
    var validationResult by remember { mutableStateOf<ValidationResult?>(null) }

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
                Text("JSON Validator")
            }

            // JSON Input TextArea
            TextArea(
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
                        ValidationResult.Error("Please enter JSON data")
                    } else {
                        // Use validateJson method to validate JSON
                        validateJson(jsonInput).fold(
                            onSuccess = { ValidationResult.Success("JSON is valid") },
                            onFailure = { ValidationResult.Error(it.message ?: "Unknown error") }
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
