package dev.sjaramillo.jmom.utils

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement

/**
 * Pretty prints a JSON string with 2 spaces indentation.
 *
 * @param json The JSON string to format
 * @return Result.success(String) with the formatted JSON if valid, Result.failure(Exception) otherwise
 */
@OptIn(kotlinx.serialization.ExperimentalSerializationApi::class)
internal fun prettyPrintJson(json: String): Result<String> {
    return try {
        // First, validate the JSON
        val validationResult = validateJson(json)
        if (validationResult.isFailure) {
            return Result.failure(validationResult.exceptionOrNull() ?: Exception("Invalid JSON"))
        }

        // Parse the JSON to a JsonElement
        val jsonElement = Json.parseToJsonElement(json)

        // Pretty print with 2 spaces indentation
        val prettyJson = Json {
            prettyPrint = true
            prettyPrintIndent = "  " // 2 spaces
        }.encodeToString(JsonElement.serializer(), jsonElement)

        Result.success(prettyJson)
    } catch (e: Exception) {
        Result.failure(Exception("Error formatting JSON: ${e.message}"))
    }
}