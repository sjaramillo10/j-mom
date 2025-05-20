package dev.sjaramillo.jmom.utils

import kotlinx.serialization.json.Json
import kotlinx.serialization.SerializationException

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
