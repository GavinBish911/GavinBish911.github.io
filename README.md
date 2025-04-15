# Enhancement 1: Advanced Search Functionality and Patient Data Improvements

This repository documents the first enhancement made to our appointment scheduling system. The goal of Enhancement 1 was to improve the system’s search capabilities and expand the patient data model, making it more robust and user-friendly for healthcare professionals and administrative staff.

---

## Table of Contents
1. [Overview](#overview)
2. [Key Features](#key-features)
3. [Installation](#installation)
4. [Usage](#usage)
5. [Project Structure](#project-structure)
6. [Contributing](#contributing)
7. [License](#license)

---

## Overview
Originally, this application supported only minimal lookups using unique identifiers. With Enhancement 1, the system now allows comprehensive searches by various criteria (e.g., name, date, time, phone number, and email). Additionally, we expanded the patient data model to include insurance information, contact details, and other relevant fields, resulting in a more reliable and complete patient record.

**Primary Objectives:**
- Introduce a flexible, robust search functionality for appointments.
- Enhance the patient data model with new fields (e.g., insurance info, phone number).
- Streamline data entry and retrieval for improved user experience.

---

## Key Features

1. **Flexible Search Parameters**  
   - Users can now look up appointments by name, date, time, phone number, email address, and more.
   - This significantly reduces the time spent manually browsing through limited search results.

2. **Extended Patient Data Model**  
   - Added new attributes such as insurance information, contact details, and other relevant medical data.
   - Ensures a more comprehensive view of each patient’s profile within the system.

3. **Improved User Experience**  
   - Enhanced interface for scheduling and reviewing appointment details.
   - Clear, organized forms and search fields make data entry and retrieval easier for administrative or medical staff.

4. **Separation of Concerns**  
   - Back-end logic handling search and retrieval is abstracted from the front-end presentation.
   - The codebase is now more modular, making future enhancements and maintenance smoother.

---

## Installation

1. **Clone the Repository**  
      ```bash
      git clone https://github.com/YourUsername/Enhancement1-AdvancedSearchAndPatientData.git
      cd Enhancement1-AdvancedSearchAndPatientData

2. **Install Dependencies**
   
   - Python Example
      ```bash
      pip install -r requirements.text
      ```
  -  It is reconmmended to use a virtual environment
      ```bash
      python -m venv venv
      source venv/bin/activate   # On macOS/Linux
      venv\Scripts\activate.text # On Windows
      pip install -r requirements.text
      ```
   - Other Stacks
   If you are using Node.js or another environment, please refer to the project’s documentation or your own setup instructions for installation steps
   
3. Configure Database
   - Set up or update your database schema based on the provided script in the db folder.
   - Ensure you have configured environment variables or database connection settings as needed.

4. Run the Application
   -Python Example
      ```bash
      pythong app.py
      ```
   -Node.js Example
      ```bash
      node server.js
      ```
5. Access the System
   - Once running, navigate to http://localhost:<PORT> in your browser (replace <PORT> with the correct port number).
   
