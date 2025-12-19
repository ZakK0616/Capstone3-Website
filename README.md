# Capstone3-Website
🛒 E-Commerce Backend Capstone
===============================================================================================================================================================================================================================================================================

This project is a full-stack e-commerce backend application built using Java, Spring Boot, and MySQL. It provides secure REST APIs that allow users to register, log in, view products, manage shopping carts, and update their profiles. The goal of this project was to design a realistic online shopping platform with authentication, database integration, and core commerce functionality.

🚀 Features
===============================================================================================================================================================================================================================================================================
🔐 Authentication & Authorization
===============================================================================================================================================================================================================================================================================

User registration and login

Secure endpoints using Spring Security

Principal used to identify authenticated users

🧾 Product & Category Management
===============================================================================================================================================================================================================================================================================
View all products

Filter products by:

Category

Price range

Sub-category

CRUD operations for categories

🛒 Shopping Cart System
===============================================================================================================================================================================================================================================================================

Add products to cart

Update item quantities

Remove items

Load cart by logged-in user

👤 User Profile
===============================================================================================================================================================================================================================================================================

View and update profile information

Profile created automatically when a user registers

🏗️ Tech Stack
===============================================================================================================================================================================================================================================================================

Java

Spring Boot

Spring Security

MySQL

JDBC / DAO Pattern

Postman (for API testing)

Screenshots 📸
===============================================================================================================================================================================================================================================================================
🐞 BUG #1 — The product search SQL logic
===============================================================================================================================================================================================================================================================================

<img width="1920" height="1080" alt="Desktop Screenshot 2025 12 15 - 20 44 46 85" src="https://github.com/user-attachments/assets/731a06f0-3341-412a-a95f-f97eb057676b" />

The Problem
===============================================================================================================================================================================================================================================================================
The code provided had:
(price <= ? OR ? = -1)

(price >= ? OR ? = -1)

But I notice minPrice and maxPrice are reversed.
Your logic says:

price <= minPrice

price >= maxPrice

which is the opposite of what you want.

Correct Logic
===============================================================================================================================================================================================================================================================================

It should be:

price >= minPrice

price <= maxPrice

🐞 BUG #2 — Product update endpoint
==============================================================================================================================================================================================================================================================================
<img width="1920" height="1080" alt="Desktop Screenshot 2025 12 15 - 20 44 46 85" src="https://github.com/user-attachments/assets/df05147d-612e-4bc4-9bf7-6d01f4fc8c9a" />

The Problem
==============================================================================================================================================================================================================================================================================

The controller calls productDao.update(id, product),
but in the earlier code I saw that the DAO method signature is wrong or missing.

Typical mistakes in this bug:

The DAO update method is not implemented right

DAO update signature doesn’t match controller

No check if product exists before updating

Missing validation → updating with null fields


My Interesting code 📸
==============================================================================================================================================================================================================================================================================


<img width="1920" height="1080" alt="Desktop Screenshot 2025 12 19 - 09 49 14 76" src="https://github.com/user-attachments/assets/4d1f4020-dd5e-4a64-b7da-91cd6ec9c7a1" />


Website Screenshots 📸
==============================================================================================================================================================================================================================================================================


<img width="1920" height="1080" alt="Desktop Screenshot 2025 12 19 - 09 43 16 56" src="https://github.com/user-attachments/assets/2c2a860c-be0a-44c3-bb94-d7b683830e6d" />
<img width="1920" height="1080" alt="Desktop Screenshot 2025 12 19 - 09 45 29 10" src="https://github.com/user-attachments/assets/7f93b08b-e933-44b1-b325-6c6cbd4b6d0a" />
<img width="1920" height="1080" alt="Desktop Screenshot 2025 12 19 - 09 43 34 15" src="https://github.com/user-attachments/assets/e7fabc35-43c9-4112-ac3a-9b6b831518fd" />
<img width="1920" height="1080" alt="Desktop Screenshot 2025 12 19 - 09 43 40 56" src="https://github.com/user-attachments/assets/d907832f-784c-431d-b621-251c764ab5ad" />


