# Enhancement 2: Comprehensive Unit Testing and Algorithm Refinement (C++)

This repository documents the second major update to our appointment scheduling and security system, focusing on robust **C++** unit testing (using **Google Test**) and refining core algorithms. The goal is to ensure that new features can be introduced with minimal risk of breaking existing functionality, while simultaneously optimizing security operations (like threat detection and sanitization).

---

## Table of Contents

1. [Overview](#overview)  
2. [Key Features](#key-features)  
3. [Project Setup](#project-setup)  
4. [Usage](#usage)  
5. [Contributing](#contributing)  

---

## Overview

Prior to this enhancement, the project had limited visibility into potential regressions whenever new code was added. It also contained security routines (e.g., `ThreatDetector`, `SecuritySanitizer`) that needed more rigorous testing. By implementing **Google Test**-based unit tests and improving the internal algorithms, Enhancement 2 ensures stable performance, concurrency safety, and better handling of malicious inputs.

**Primary Objectives:**

- Achieve thorough **C++** unit testing coverage across all core classes (e.g., `ThreatDetector`, `SecuritySanitizer`, `SecurityLogger`, `SecureResource`).
- Refine security algorithms to handle malicious inputs, concurrency, and potential resource exhaustion more effectively.
- Provide a well-documented testing workflow for developers and maintainers.

---

## Key Features

1. **Google Test Integration**  
   - Comprehensive test suite verifying threat detection, sanitization, and resource handling.  
   - Structured test fixtures (`TEST_F` classes) for concurrency and stress scenarios.

2. **Algorithmic Refinements**  
   - Enhanced detection patterns for SQL injection, XSS, and command execution attempts.  
   - Optimized concurrency and resource locking via C++ standard library mutexes.

3. **Secure Logging**  
   - Centralized logging of security-relevant events (e.g., detected threats) with basic encryption.  
   - Thread-safe operations that can handle simultaneous logging from multiple classes.

4. **Improved Maintainability**  
   - Clear separation of concerns among classes handling threat detection, logging, and sanitization.  
   - Simplified error messages and resource release logic to reduce code complexity.

---

## Project Setup

1. **Clone the Repository**  
   ```bash
   git clone https://github.com/YourUsername/Enhancement2-CppUnitTesting.git
   cd Enhancement2-CppUnitTesting
   ```
   
2. **Install Google Test**
   - Option A (System Install)
      ```bash
      sudo apt-get update
      sudo apt-get install libgtest-dev
      ```
    - Then build the Google Test library from source (if required):
      ```bash
      cd /usr/src/gtest
      sudo cmake .
      sudo make
      sudo cp *.a /usr/lib
      ```
   - Option B (FetchContent/CMake):
   If using CMake, you can fetch and integrate Google Test directly in your build process.
   
3. Build the Project
   - CMake Example:
      ```bash
      mkdir build && cd build
      cmake ..
      make
      ```

4. Run the Tests
   - Once compiled, an executable (e.g., testSuite) will be created in the build directory.
   - Execute the tests:
      ```bash
      ./testSuite
      ```

## Usage

1. Core Security Classes
   - ThreatDetector: Identifies attack patterns such as SQL injection and XSS.
   - SecuritySanitizer: Removes dangerous characters and salts user input.
   - SecurityLogger: Logs security events with simple XOR encryption.
   - SecureResource<T>: Manages thread-safe access to resources using smart pointers.

2. Test Execution
   - The Google Test suite checks scenarios like malicious input detection, sanitization effectiveness, concurrency handling, and resource exhaustion prevention.
   - View the console output to see which tests passed or failed, along with any custom failure messages.

3. View or Edit Patient Information
   - Access extended patient profiles, which now include fields such as insurance details and multiple contact points.
   - Update or correct patient information directly in the system, ensuring accuracy and completeness.

4. Extending the Tests
   - Add new test cases in the test.cpp file or create additional test files.
   - Follow the existing fixture approach (CollectionTest) for consistent concurrency and environment setup.
  

## Contributing

We welcome contributions! Feel free to open issues or submit pull requests:

   1. Fork the repository and create a feature branch.
   2. Commit your changes with clear and descriptive messages.
   3. Submit a pull request outlining your modifications and their benefits.

Thank you for exploring Enhancement 2!
If you have any questions or suggestions, please reach out via the Issues tab or contact the maintainer directly. Your feedback is valuable in improving the system further.
