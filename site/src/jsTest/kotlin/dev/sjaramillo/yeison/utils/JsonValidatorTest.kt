package dev.sjaramillo.yeison.utils

import kotlin.test.Test
import kotlin.test.assertTrue
import kotlin.test.assertFalse

class JsonValidatorTest {
    
    @Test
    fun testValidJsonObject() {
        // Test a simple valid JSON object
        val result = validateJson("""{"name": "John", "age": 30}""")
        assertTrue(result.isSuccess, "Valid JSON object should be successfully validated")
    }
    
    @Test
    fun testValidJsonArray() {
        // Test a valid JSON array
        val result = validateJson("""[1, 2, 3, 4, 5]""")
        assertTrue(result.isSuccess, "Valid JSON array should be successfully validated")
    }
    
    @Test
    fun testValidJsonPrimitive() {
        // Test valid JSON primitives
        assertTrue(validateJson("123").isSuccess, "Valid JSON number should be successfully validated")
        assertTrue(validateJson("\"hello\"").isSuccess, "Valid JSON string should be successfully validated")
        assertTrue(validateJson("true").isSuccess, "Valid JSON boolean should be successfully validated")
        assertTrue(validateJson("null").isSuccess, "Valid JSON null should be successfully validated")
    }
    
    @Test
    fun testValidComplexJson() {
        // Test a more complex nested JSON
        val complexJson = """
            {
                "person": {
                    "name": "John Doe",
                    "age": 30,
                    "address": {
                        "street": "123 Main St",
                        "city": "Anytown"
                    },
                    "phoneNumbers": [
                        "555-1234",
                        "555-5678"
                    ],
                    "active": true
                }
            }
        """.trimIndent()
        
        val result = validateJson(complexJson)
        assertTrue(result.isSuccess, "Valid complex JSON should be successfully validated")
    }
    
    @Test
    fun testInvalidJsonSyntax() {
        // Test JSON with syntax errors
        val invalidJson1 = """{"name": "John", "age": 30,}"""  // Extra comma
        val result1 = validateJson(invalidJson1)
        assertFalse(result1.isSuccess, "JSON with extra comma should fail validation")
        
        val invalidJson2 = """{"name": "John" "age": 30}"""  // Missing comma
        val result2 = validateJson(invalidJson2)
        assertFalse(result2.isSuccess, "JSON with missing comma should fail validation")
        
        val invalidJson3 = """{"name": "John", "age": }"""  // Missing value
        val result3 = validateJson(invalidJson3)
        assertFalse(result3.isSuccess, "JSON with missing value should fail validation")
    }
    
    @Test
    fun testInvalidJsonStructure() {
        // Test JSON with structural errors
        val invalidJson1 = """{"name": "John", "age": 30"""  // Missing closing brace
        val result1 = validateJson(invalidJson1)
        assertFalse(result1.isSuccess, "JSON with missing closing brace should fail validation")
        
        val invalidJson2 = """[1, 2, 3"""  // Missing closing bracket
        val result2 = validateJson(invalidJson2)
        assertFalse(result2.isSuccess, "JSON with missing closing bracket should fail validation")
    }
    
    @Test
    fun testEdgeCases() {
        // Test empty string
        val emptyResult = validateJson("")
        assertFalse(emptyResult.isSuccess, "Empty string should fail validation")
        
        // Test whitespace-only string
        val whitespaceResult = validateJson("   ")
        assertFalse(whitespaceResult.isSuccess, "Whitespace-only string should fail validation")
    }
}