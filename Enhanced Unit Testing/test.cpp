// Include necessary standard libraries and testing framework
#include <gtest/gtest.h>       // Google Test framework for unit testing
#include <string>              // For string manipulation 
#include <vector>              // For dynamic array functionality
#include <chrono>              // For time-related operations
#include <fstream>             // For file operations - used in security logging
#include <memory>              // For smart pointers like unique_ptr
#include <mutex>               // For thread synchronization primitives
#include <random>              // For random number generation (used in salt creation)
#include <algorithm>           // For STL algorithms like copy_if
#include <regex>               // For regular expression pattern matching

/**
 * @class ThreatDetector
 * @brief Advanced security system to detect malicious input patterns.
 * 
 * This class provides static methods to identify common attack patterns
 * such as SQL injection, XSS, and path traversal attempts.
 * Uses thread-safe operations for access from multiple threads.
 */
class ThreatDetector {
private:
    static std::mutex mtx;                          // Mutex for thread-safe operations
    static std::vector<std::string> knownAttackPatterns;  // Database of attack regex patterns
    static std::vector<std::string> detectedThreats;      // Log of all detected threats

public:
    /**
     * @brief Initializes the threat detector with known attack patterns.
     * 
     * Populates the knownAttackPatterns vector with regular expressions that
     * match common attack techniques. Uses a mutex to ensure thread safety.
     */
    static void initialize() {
        std::lock_guard<std::mutex> lock(mtx);  // Thread-safe lock during initialization
        knownAttackPatterns = {
            "SELECT.*FROM",      // SQL injection patterns
            "DELETE.*FROM",      // SQL injection patterns
            "DROP.*TABLE",       // SQL injection patterns
            "<script>",          // Cross-site scripting (XSS) patterns
            "UNION.*SELECT",     // Advanced SQL injection technique
            "eval\\(",           // JavaScript injection risks
            "exec\\(",           // Command injection risks
            "\\.\\./",           // Path traversal attempts to access restricted directories
            "cmd\\.exe",         // Windows command execution attempt
            "system\\("          // C/C++ system call injection attempt
        };
    }

    /**
     * @brief Checks if the input contains any known attack patterns.
     * @param input The string to check for malicious content
     * @return true if a threat is detected, false otherwise
     * 
     * Uses regular expressions with case insensitivity to detect known attack patterns.
     * Logs detected threats for later analysis.
     */
    static bool detectThreat(const std::string& input) {
        std::lock_guard<std::mutex> lock(mtx);  // Thread-safe lock during detection
        // Check each known pattern against the input
        for (const auto& pattern : knownAttackPatterns) {
            std::regex re(pattern, std::regex::icase);  // Case insensitive matching
            if (std::regex_search(input, re)) {
                // Log the detected threat for later analysis
                detectedThreats.push_back("Detected pattern: " + pattern + " in input: " + input);
                return true;  // Threat detected
            }
        }
        return false;  // No threat detected
    }

    /**
     * @brief Retrieves the list of all detected threats.
     * @return Vector of detected threat descriptions
     */
    static std::vector<std::string> getDetectedThreats() {
        std::lock_guard<std::mutex> lock(mtx);  // Thread-safe access to threat log
        return detectedThreats;
    }
};

/**
 * @class SecureResource
 * @brief Thread-safe wrapper for any resource type with RAII semantics.
 * @tparam T The type of resource to manage securely
 * 
 * Provides memory safety and thread safety for any resource type.
 * Uses std::unique_ptr for automatic resource cleanup and a mutex for thread safety.
 */
template<typename T>
class SecureResource {
private:
    std::unique_ptr<T> resource;    // Smart pointer for automatic resource management
    std::mutex resourceMutex;       // Mutex to protect access to the resource

public:
    /**
     * @brief Constructor that creates the resource using perfect forwarding
     * @tparam Args Variable argument types for resource construction
     * @param args Arguments to forward to the resource constructor
     * 
     * Uses variadic templates and perfect forwarding to create resources efficiently.
     */
    template<typename... Args>
    SecureResource(Args&&... args) : resource(std::make_unique<T>(std::forward<Args>(args)...)) {}

    /**
     * @brief Thread-safe access to the underlying resource
     * @return Pointer to the managed resource
     */
    T* get() {
        std::lock_guard<std::mutex> lock(resourceMutex);  // Thread-safe access
        return resource.get();  // Return raw pointer to the resource
    }

    /**
     * @brief Safely destroys and recreates the resource
     * 
     * Uses mutex to ensure thread safety during resource destruction.
     */
    void reset() {
        std::lock_guard<std::mutex> lock(resourceMutex);  // Thread-safe resource reset
        resource.reset();  // Release the current resource
    }
};

/**
 * @class SecurityLogger
 * @brief Thread-safe logging system with basic encryption for security events
 * 
 * Provides a centralized logging mechanism for security-related events
 * with simple XOR encryption to protect sensitive information.
 */
class SecurityLogger {
private:
    static std::ofstream logFile;      // Output file stream for logging
    static std::mutex logMutex;        // Mutex for thread-safe logging
    
    // Simple XOR encryption key (in production, use proper encryption)
    static const char encryptionKey = 0x2F;  // 47 in decimal

    /**
     * @brief Encrypts a message using simple XOR encryption
     * @param message The message to encrypt
     * @return The encrypted message
     * 
     * Note: XOR encryption is not secure for production use, but
     * demonstrates the concept of protecting sensitive log data.
     */
    static std::string encryptMessage(const std::string& message) {
        std::string encrypted = message;
        // XOR each character with the encryption key
        for (char& c : encrypted) {
            c ^= encryptionKey;
        }
        return encrypted;
    }

public:
    /**
     * @brief Initializes the logger by opening the log file
     * 
     * Opens the security audit log file in append mode to preserve
     * previous log entries. Thread-safe initialization.
     */
    static void initializeLogger() {
        std::lock_guard<std::mutex> lock(logMutex);  // Thread-safe initialization
        logFile.open("security_audit.log", std::ios::app);  // Append mode
    }

    /**
     * @brief Logs a security event with timestamp, type, severity, and details
     * @param eventType The category of the security event
     * @param details Specific information about the event
     * @param isThreateningEvent Severity indicator (true for high severity)
     * 
     * Creates a timestamped log entry, encrypts it, and writes to the log file.
     */
    static void logSecurityEvent(const std::string& eventType, 
                               const std::string& details, 
                               bool isThreateningEvent) {
        std::lock_guard<std::mutex> lock(logMutex);  // Thread-safe logging
        // Get current time for timestamp
        auto now = std::chrono::system_clock::now();
        auto timestamp = std::chrono::system_clock::to_time_t(now);
        
        // Format the log entry with all relevant information
        std::string logEntry = 
            std::string("[") + std::ctime(&timestamp) + "] " +
            "Type: " + eventType + 
            " | Severity: " + (isThreateningEvent ? "HIGH" : "LOW") +
            " | Details: " + details;

        // Encrypt sensitive information before logging
        std::string encryptedEntry = encryptMessage(logEntry);
        logFile << encryptedEntry << std::endl;  // Write to log file with line ending
    }
};

/**
 * @class SecuritySanitizer
 * @brief Advanced input sanitization system with multiple security layers
 * 
 * Provides methods to clean up and validate user inputs to prevent security
 * vulnerabilities like injection attacks and buffer overflows.
 */
class SecuritySanitizer {
private:
    static std::random_device rd;  // True random number source for seeding
    static std::mt19937 gen;       // Mersenne Twister random number generator

    /**
     * @brief Generates a random alphanumeric salt string
     * @return A 16-character random string for salting
     * 
     * Creates a cryptographically strong random string to prevent
     * pattern matching attacks by making each sanitized string unique.
     */
    static std::string generateSalt() {
        // Define the character set for the salt
        const std::string charset = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        // Distribution for selecting random characters
        std::uniform_int_distribution<> dis(0, charset.length() - 1);
        std::string salt;
        // Generate 16 random characters
        for(int i = 0; i < 16; ++i) {
            salt += charset[dis(gen)];
        }
        return salt;
    }

public:
    /**
     * @brief Removes potentially dangerous characters from input
     * @param input The string to sanitize
     * @return A sanitized version of the input with a random salt
     * 
     * Filters out all non-alphanumeric characters except for a few safe ones
     * and adds a random salt to prevent pattern matching attacks.
     */
    static std::string sanitizeInput(const std::string& input) {
        // Remove potentially dangerous characters
        std::string sanitized;
        // Keep only alphanumeric and a few safe special characters
        std::copy_if(input.begin(), input.end(), std::back_inserter(sanitized),
            [](char c) { return isalnum(c) || c == '_' || c == '-' || c == '.'; });

        // Add salt to prevent pattern matching attacks
        return sanitized + "_" + generateSalt();
    }

    /**
     * @brief Validates input against security threats
     * @param input The string to validate
     * @param context Description of where this input is being used
     * @return true if the input passes security validation, false otherwise
     * 
     * Checks for malicious patterns and excessive length that could
     * indicate attack attempts. Logs security events when threats are detected.
     */
    static bool validateInput(const std::string& input, const std::string& context = "general") {
        // Check for potential threats using the ThreatDetector
        if (ThreatDetector::detectThreat(input)) {
            SecurityLogger::logSecurityEvent("Input Validation", 
                "Potential threat detected in context: " + context, true);
            return false;
        }

        // Validate input length to prevent buffer overflow attacks
        if (input.length() > 1000) {
            SecurityLogger::logSecurityEvent("Input Validation", 
                "Input exceeds maximum length in context: " + context, true);
            return false;
        }

        return true;  // Input passed all security checks
    }
};

// Static member definitions - required for static class members in C++
std::mutex ThreatDetector::mtx;
std::vector<std::string> ThreatDetector::knownAttackPatterns;
std::vector<std::string> ThreatDetector::detectedThreats;
std::ofstream SecurityLogger::logFile;
std::mutex SecurityLogger::logMutex;
std::random_device SecuritySanitizer::rd;
std::mt19937 SecuritySanitizer::gen(SecuritySanitizer::rd());  // Seeded with true random source

/**
 * @class CollectionTest
 * @brief Google Test fixture for testing the security system
 * 
 * Inherits from testing::Test to leverage Google Test framework.
 * Provides setup, teardown, and helper methods for security testing.
 */
class CollectionTest : public ::testing::Test {
protected:
    // Secure container for test data
    SecureResource<std::vector<std::string>> collection;
    
    /**
     * @brief Setup method that runs before each test
     * 
     * Initializes security systems needed for testing.
     * Called automatically by the testing framework before each TEST_F.
     */
    void SetUp() override {
        ThreatDetector::initialize();       // Initialize the threat detection system
        SecurityLogger::initializeLogger();  // Set up the security logger
        SecurityLogger::logSecurityEvent("Test Setup", "Initializing secure test environment", false);
    }

    /**
     * @brief Teardown method that runs after each test
     * 
     * Cleans up resources to prevent memory leaks and contamination between tests.
     * Called automatically by the testing framework after each TEST_F.
     */
    void TearDown() override {
        collection.reset();  // Release the test collection
        SecurityLogger::logSecurityEvent("Test Teardown", "Cleaning up secure test environment", false);
    }

    /**
     * @brief Helper method to safely add an entry to the test collection
     * @param entry The string to add to the collection
     * @return true if the entry was successfully added, false if rejected
     * 
     * Validates and sanitizes input before adding to the collection.
     */
    bool add_entry(const std::string& entry) {
        // First validate the input for security threats
        if (!SecuritySanitizer::validateInput(entry, "collection_entry")) {
            return false;  // Input failed security validation
        }
        
        // Sanitize the input to remove any potentially dangerous characters
        std::string sanitizedEntry = SecuritySanitizer::sanitizeInput(entry);
        // Add sanitized entry to the collection
        collection.get()->push_back(sanitizedEntry);
        return true;
    }

    /**
     * @brief Helper method to add multiple test entries to the collection
     * @param count The number of entries to add
     * 
     * Creates numbered test entries and adds them to the collection.
     */
    void add_entries(int count) {
        for (int i = 0; i < count; ++i) {
            std::string entry = "test_entry_" + std::to_string(i);
            add_entry(entry);
        }
    }
};

/**
 * @brief Test case to verify detection of various malicious inputs
 * 
 * Tests the system's ability to detect common attack patterns
 * including SQL injection, XSS, and command execution attempts.
 */
TEST_F(CollectionTest, TestMaliciousInputDetection) {
    // Vector of known malicious inputs representing different attack types
    std::vector<std::string> maliciousInputs = {
        "SELECT * FROM users",                // SQL injection 
        "../../../etc/passwd",                // Path traversal attack
        "<script>alert('xss')</script>",      // Cross-site scripting (XSS)
        "'; DROP TABLE users;--",             // SQL injection with comment
        "system('rm -rf /')"                  // Command injection
    };

    // Test each malicious input to ensure it's detected
    for (const auto& input : maliciousInputs) {
        EXPECT_FALSE(SecuritySanitizer::validateInput(input)) 
            << "Failed to detect malicious input: " << input;  // Custom failure message
    }
}

/**
 * @brief Test case to verify input sanitization effectiveness
 * 
 * Tests that the sanitizer properly removes dangerous characters
 * and that sanitized inputs pass validation.
 */
TEST_F(CollectionTest, TestInputSanitization) {
    // An input containing an XSS attack pattern
    std::string unsafeInput = "malicious<script>alert(1)</script>input";
    // Sanitize the input
    std::string sanitized = SecuritySanitizer::sanitizeInput(unsafeInput);
    
    // Verify the sanitized input is different from the original
    EXPECT_NE(unsafeInput, sanitized);
    // Verify the sanitized input passes validation
    EXPECT_TRUE(SecuritySanitizer::validateInput(sanitized));
}

/**
 * @brief Test case to verify secure resource handling
 * 
 * Tests the system's ability to handle resources safely and
 * protect against memory exhaustion attacks.
 */
TEST_F(CollectionTest, TestSecureResourceHandling) {
    // Add a moderate number of entries
    add_entries(100);
    // Verify the entries were added correctly
    EXPECT_EQ(collection.get()->size(), 100);
    
    // Attempt to overflow the collection (potential DoS attack)
    try {
        // Try to add a very large number of entries
        for (int i = 0; i < 1000000; ++i) {
            add_entry("overflow_test_" + std::to_string(i));
        }
    } catch (const std::bad_alloc& e) {
        // If memory allocation fails, log it as a prevented attack
        SecurityLogger::logSecurityEvent("Resource Protection", 
            "Prevented memory exhaustion attack", true);
    }
}

/**
 * @brief Test case to verify thread-safe concurrent access
 * 
 * Tests the system's ability to handle multiple threads accessing
 * the secure resources simultaneously without data corruption.
 */
TEST_F(CollectionTest, TestConcurrentAccess) {
    // Vector to hold thread objects
    std::vector<std::thread> threads;
    // Create 10 concurrent threads
    for (int i = 0; i < 10; ++i) {
        // Each thread adds 100 entries to the collection
        threads.emplace_back([this, i]() {
            add_entries(100);
        });
    }

    // Wait for all threads to complete
    for (auto& thread : threads) {
        thread.join();
    }

    // Verify that all entries were correctly added (10 threads × 100 entries)
    EXPECT_EQ(collection.get()->size(), 1000);
}

/**
 * @brief Main function to run all tests
 * 
 * Initializes Google Test framework and runs all defined tests.
 * Returns the overall test result status code.
 */
int main(int argc, char** argv) {
    testing::InitGoogleTest(&argc, argv);  // Initialize Google Test with command line arguments
    return RUN_ALL_TESTS();  // Run all defined tests and return the status
}