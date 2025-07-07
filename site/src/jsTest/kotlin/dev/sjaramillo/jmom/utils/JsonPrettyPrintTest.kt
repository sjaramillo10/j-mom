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

    @Test
    fun testConfigurableIndentation() {
        // Test JSON object with different indentation values
        val jsonObject = """{"name":"John","age":30,"address":{"street":"123 Main St","city":"Anytown"}}"""

        // Test with 2 spaces (default)
        val result2Spaces = prettyPrintJson(jsonObject, 2)
        assertTrue(result2Spaces.isSuccess, "Pretty printing with 2 spaces should succeed")
        val prettyJson2Spaces = result2Spaces.getOrNull() ?: ""

        // Test with 4 spaces
        val result4Spaces = prettyPrintJson(jsonObject, 4)
        assertTrue(result4Spaces.isSuccess, "Pretty printing with 4 spaces should succeed")
        val prettyJson4Spaces = result4Spaces.getOrNull() ?: ""

        // Test with 6 spaces
        val result6Spaces = prettyPrintJson(jsonObject, 6)
        assertTrue(result6Spaces.isSuccess, "Pretty printing with 6 spaces should succeed")
        val prettyJson6Spaces = result6Spaces.getOrNull() ?: ""

        // Test with 8 spaces
        val result8Spaces = prettyPrintJson(jsonObject, 8)
        assertTrue(result8Spaces.isSuccess, "Pretty printing with 8 spaces should succeed")
        val prettyJson8Spaces = result8Spaces.getOrNull() ?: ""

        // Verify that the indentation is different for each configuration
        assertTrue(prettyJson2Spaces != prettyJson4Spaces, "2-space indentation should be different from 4-space indentation")
        assertTrue(prettyJson4Spaces != prettyJson6Spaces, "4-space indentation should be different from 6-space indentation")
        assertTrue(prettyJson6Spaces != prettyJson8Spaces, "6-space indentation should be different from 8-space indentation")

        // Verify that the content is preserved in all configurations
        listOf(prettyJson2Spaces, prettyJson4Spaces, prettyJson6Spaces, prettyJson8Spaces).forEach { json ->
            assertTrue(json.contains("\"name\""), "JSON should preserve field names")
            assertTrue(json.contains("\"John\""), "JSON should preserve string values")
            assertTrue(json.contains("\"address\""), "JSON should preserve nested fields")
            assertTrue(json.contains("\"street\""), "JSON should preserve nested field names")
            assertTrue(json.contains("\"123 Main St\""), "JSON should preserve nested field values")
        }
    }
}
