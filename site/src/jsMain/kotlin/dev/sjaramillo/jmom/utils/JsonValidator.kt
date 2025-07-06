package dev.sjaramillo.jmom.utils

import kotlinx.serialization.json.Json
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.JsonElement

/**
 * Validates if a given string is a valid JSON.
 * 
 * @param json The string to validate
 * @return Result.success(Unit) if the string is a valid JSON, Result.failure(Exception) otherwise
 */
fun validateJson(json: String): Result<Unit> {
    // Check for single word edge case (not a valid JSON primitive)
    val trimmedJson = json.trim()
    if (trimmedJson.isNotEmpty() && 
        !trimmedJson.startsWith("{") && 
        !trimmedJson.startsWith("[") && 
        !trimmedJson.startsWith("\"") && 
        trimmedJson != "true" && 
        trimmedJson != "false" && 
        trimmedJson != "null" && 
        !trimmedJson.matches(Regex("-?\\d+(\\.\\d+)?([eE][+-]?\\d+)?"))
    ) {
        return Result.failure(Exception("Invalid JSON: Single words without quotes are not valid JSON"))
    }

    return try {
        Json.parseToJsonElement(json)
        Result.success(Unit)
    } catch (e: SerializationException) {
        Result.failure(Exception("Invalid JSON: ${e.message}"))
    } catch (e: Exception) {
        Result.failure(Exception("Error validating JSON: ${e.message}"))
    }
}

/**
 * Pretty prints a JSON string with 2 spaces indentation.
 * 
 * @param json The JSON string to format
 * @return Result.success(String) with the formatted JSON if valid, Result.failure(Exception) otherwise
 */
@OptIn(kotlinx.serialization.ExperimentalSerializationApi::class)
fun prettyPrintJson(json: String): Result<String> {
    return try {
        // First validate the JSON
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
