ZSS Bookstore Interview Solution

Overview

This project implements a REST API for managing Books and Categories, including:
Creating categories (with title)
Creating books (title, description, price, and assigned category)
Retrieving all books or books filtered by category
Purchasing a book by integrating with the provided mock transaction API
Built with Spring Boot, Java 17, and PostgreSQL.

Assumptions

Categories and books must have unique identifiers automatically generated.
Price is stored as a decimal value with two decimal places.
Book purchase triggers a call to the external transaction API to simulate payment.
For simplicity, no authentication is implemented beyond the token used for the transaction API.
The transaction API token is configured via application.yml.

How to Run

Prerequisites
Java 17 or higher installed
PostgreSQL installed and running
Maven installed
** Since MapStruct is used, mvn clean install is required for the response classes to be created

What Was Not Completed
The purchase API integration assumes happy path scenarios
Retry logic can be implemented, and failure handling can be improved.
