package dev.sjaramillo.jmom.utils

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement

/**
 * Pretty prints a JSON string with configurable indentation.
 *
 * @param json The JSON string to format
 * @param spaces The number of spaces to use for indentation (default is 2)
 * @return Result.success(String) with the formatted JSON if valid, Result.failure(Exception) otherwise
 */
@OptIn(kotlinx.serialization.ExperimentalSerializationApi::class)
internal fun prettyPrintJson(json: String, spaces: Int = 2): Result<String> {
    return try {
        // First, validate the JSON
        val validationResult = validateJson(json)
        if (validationResult.isFailure) {
            return Result.failure(validationResult.exceptionOrNull() ?: Exception("Invalid JSON"))
        }

        // Parse the JSON to a JsonElement
        val jsonElement = Json.parseToJsonElement(json)

        // Create indentation string based on the number of spaces
        val indent = " ".repeat(spaces)

        // Pretty print with configurable indentation
        val prettyJson = Json {
            prettyPrint = true
            prettyPrintIndent = indent
        }.encodeToString(JsonElement.serializer(), jsonElement)

        Result.success(prettyJson)
    } catch (e: Exception) {
        Result.failure(Exception("Error formatting JSON: ${e.message}"))
    }
}
