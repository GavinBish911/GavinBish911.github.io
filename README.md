# Enhancement 3: Database Optimization and Advanced CRUD Operations (Python)

This repository documents the third major update to our animal management system, focusing on optimized database interactions and extended Create, Read, Update, Delete (CRUD) functionality in Python with MongoDB. Through the `AnimalShelter` class, we aim to handle higher volumes of data more efficiently, perform multiple operations in batches, and track records accurately.

---

## Table of Contents

1. [Overview](#overview)  
2. [Key Features](#key-features)  
3. [Setup and Installation](#setup-and-installation)  
4. [Usage](#usage)  
5. [Contributing](#contributing)  

---

## Overview

Previously, the system included standard CRUD methods but lacked the ability to efficiently manage multiple records and track database usage at scale. **Enhancement 3** introduces new methods for batch insertion, bulk updates, and multi-criteria deletions, as well as record counting. These improvements are designed to support larger datasets and streamline administrative tasks such as mass updates or cleanups in the `animals` collection.

**Primary Objectives:**

- Implement *batch* CRUD operations (insert, update, delete) to speed up workflows.  
- Add a record-counting mechanism to facilitate reporting and data management.  
- Provide clear exception handling and feedback for multi-record operations.

---

## Key Features

1. **Multiple Insertions (`create_multiple`)**  
   - Inserts a list of records in a single operation.  
   - Returns success status and count of inserted documents.

2. **Bulk Updates (`update_multiple`)**  
   - Processes a list of update instructions, each containing a `query` and `update_data`.  
   - Consolidates modifications to handle large-scale changes efficiently.

3. **Batch Deletions (`delete_multiple`)**  
   - Removes records matching multiple query criteria in one consolidated process.  
   - Helps perform systematic data cleanup without repeated method calls.

4. **Record Counting (`count_records`)**  
   - Quickly retrieves the total number of documents matching a given criterion.  
   - Useful for analytics, reporting, or validating large-scale insertions and updates.

5. **Error Handling and Validation**  
   - Methods print error messages to console on exceptions, offering clearer debugging.  
   - Includes basic checks to prevent invalid or empty data from processing.

---

## Setup and Installation

1. **Clone this Repository**  
   ```bash
   git clone https://github.com/YourUsername/CRUD Operations.git
   cd CRUD Operations
   ```
2. **Install Python Dependencies**
  - Ensure you have Python 3.7+ installed. Then install the required packages (e.g., pymongo)
  - If you do not, manually install:
       ```bash
       pip install pymongo
       ```
3. Configure MongoDB
   - Update the class constructor parameters (USER, PASS, HOST, PORT, DB, COL) if you have different credentials or a different environment.
   - By default, it connects to a MongoDB instance named AAC with a animals collection.

## Usage

1. Instantiate the AnimalShelter Class
   ```bash
   from crud_operations_Enhanced import AnimalShelter
   shelter = AnimalShelter(username="YourUser", password="YourPass", host="YourHost", port=YourPort)
   ```
2. Basic CRUD Operations
   - Create
      ```bash
      record = {"name": "Buddy", "species": "Dog"}
      success = shelter.create(record)
      ```
   - Read
     ```bash
     results = shelter.read({"species": "Dog"}, limit=10)
     ```
   - Update
     ```bash
     count_updated = shelter.update({"name": "Buddy"}, {"name": "Buddy The Great"}, multiple=False)
     ```
   - Delete
     ```bash
     count_deleted = shelter.delete({"name": "Buddy The Great"}, multiple=False)
     ```

3. Enhanced Operations
   - Count Records
     ```bash
     dog_count = shelter.count_records({"species": "Dog"})
     ```
   - Create Multiple
     ```bash
     new_animals = [
        {"name": "Mittens", "species": "Cat"},
        {"name": "Rex", "species": "Dog"}
      ]
      success, inserted_count = shelter.create_multiple(new_animals)
     ```
   - Update Multiple
     ```bash
     records_to_update = [
        {"query": {"species": "Cat"}, "update_data": {"status": "Adopted"}},
        {"query": {"species": "Dog"}, "update_data": {"status": "Available"}}
      ]
      success, updated_count = shelter.update_multiple(records_to_update)
     ```
   - Delete Multiple
     ```bash
     queries_to_remove = [
        {"species": "Unicorn"},
        {"species": "Dragon"}
      ]
      success, deleted_count = shelter.delete_multiple(queries_to_remove)
     ```

4. Handling Exceptions
   - Methods that encounter MongoDB or validation errors will print diagnostic messages, guiding you toward a resolution.
  
## Contributing

We welcome contributions! Feel free to open issues or submit pull requests:

   1. Fork the repository and create a feature branch.
   2. Commit your changes with clear and descriptive messages.
   3. Submit a pull request outlining your modifications and their benefits.

Thank you for exploring Enhancement 2!
If you have any questions or suggestions, please reach out via the Issues tab or contact the maintainer directly. Your feedback is valuable in improving the system further.
