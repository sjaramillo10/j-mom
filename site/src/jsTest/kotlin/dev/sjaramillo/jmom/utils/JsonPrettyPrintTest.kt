package dev.sjaramillo.jmom.utils

import kotlin.test.Test
import kotlin.test.assertTrue
import kotlin.test.assertFalse
import kotlin.test.assertEquals

class JsonPrettyPrintTest {

    @Test
    fun testPrettyPrintValidJsonObject() {
        // Test pretty printing a simple valid JSON object
        val input = """{"name":"John","age":30}"""
        val result = prettyPrintJson(input)

        assertTrue(result.isSuccess, "Pretty printing valid JSON object should succeed")

        val prettyJson = result.getOrNull() ?: ""
        assertTrue(prettyJson.contains("  "), "Pretty printed JSON should use 2-space indentation")
        assertTrue(prettyJson.contains("{\n"), "Pretty printed JSON should have line breaks")

        // Verify the content is preserved
        assertTrue(prettyJson.contains("\"name\""), "Pretty printed JSON should preserve field names")
        assertTrue(prettyJson.contains("\"John\""), "Pretty printed JSON should preserve string values")
        assertTrue(prettyJson.contains("30"), "Pretty printed JSON should preserve numeric values")
    }

    @Test
    fun testPrettyPrintValidJsonArray() {
        // Test pretty printing a valid JSON array
        val input = """[1,2,3,4,5]"""
        val result = prettyPrintJson(input)

        assertTrue(result.isSuccess, "Pretty printing valid JSON array should succeed")

        val prettyJson = result.getOrNull() ?: ""
        assertTrue(prettyJson.contains("  "), "Pretty printed JSON should use 2-space indentation")
        assertTrue(prettyJson.contains("[\n"), "Pretty printed JSON should have line breaks")

        // Verify all elements are preserved
        for (i in 1..5) {
            assertTrue(prettyJson.contains(i.toString()), "Pretty printed JSON should preserve array elements")
        }
    }

    @Test
    fun testPrettyPrintComplexJson() {
        // Test pretty printing a more complex nested JSON
        val input = """{"person":{"name":"John Doe","age":30,"address":{"street":"123 Main St","city":"Anytown"},"phoneNumbers":["555-1234","555-5678"],"active":true}}"""
        val result = prettyPrintJson(input)

        assertTrue(result.isSuccess, "Pretty printing complex JSON should succeed")

        val prettyJson = result.getOrNull() ?: ""
        assertTrue(prettyJson.contains("  "), "Pretty printed JSON should use 2-space indentation")
        assertTrue(prettyJson.contains("{\n"), "Pretty printed JSON should have line breaks")

        // Verify nested structure is preserved
        assertTrue(prettyJson.contains("\"person\""), "Pretty printed JSON should preserve top-level fields")
        assertTrue(prettyJson.contains("\"address\""), "Pretty printed JSON should preserve nested fields")
        assertTrue(prettyJson.contains("\"phoneNumbers\""), "Pretty printed JSON should preserve array fields")

        // Verify values are preserved
        assertTrue(prettyJson.contains("\"John Doe\""), "Pretty printed JSON should preserve string values")
        assertTrue(prettyJson.contains("30"), "Pretty printed JSON should preserve numeric values")
        assertTrue(prettyJson.contains("true"), "Pretty printed JSON should preserve boolean values")
    }

    @Test
    fun testPrettyPrintInvalidJson() {
        // Test pretty printing invalid JSON
        val invalidJson = """{"name": "John", "age": 30,}"""  // Extra comma
        val result = prettyPrintJson(invalidJson)

        assertFalse(result.isSuccess, "Pretty printing invalid JSON should fail")
    }

    @Test
    fun testPrettyPrintSingleWordEdgeCase() {
        // Test pretty printing a single word (without quotes) which should be invalid
        val singleWordResult = prettyPrintJson("hello")

        assertFalse(singleWordResult.isSuccess, "Pretty printing single word without quotes should fail")
    }

    @Test
    fun testPrettyPrintJsonPrimitives() {
        // Test pretty printing valid JSON primitives
        val numberResult = prettyPrintJson("123")
        assertTrue(numberResult.isSuccess, "Pretty printing valid JSON number should succeed")
        assertEquals("123", numberResult.getOrNull()?.trim(), "Pretty printed JSON number should be preserved")

        val stringResult = prettyPrintJson("\"hello\"")
        assertTrue(stringResult.isSuccess, "Pretty printing valid JSON string should succeed")
        assertEquals("\"hello\"", stringResult.getOrNull()?.trim(), "Pretty printed JSON string should be preserved")

        val booleanResult = prettyPrintJson("true")
        assertTrue(booleanResult.isSuccess, "Pretty printing valid JSON boolean should succeed")
        assertEquals("true", booleanResult.getOrNull()?.trim(), "Pretty printed JSON boolean should be preserved")

        val nullResult = prettyPrintJson("null")
        assertTrue(nullResult.isSuccess, "Pretty printing valid JSON null should succeed")
        assertEquals("null", nullResult.getOrNull()?.trim(), "Pretty printed JSON null should be preserved")
    }
}
