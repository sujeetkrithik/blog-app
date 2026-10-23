The Blog Application is a backend web application developed using Java, Spring Boot and MySql database that provides a platform for users to create, read, update, delete and comment blog posts.

It includes secure authentication and authorization using Spring Security to protect application resources and manage user access.

It provides User registration and account creation, Secure user login and logout, Password encryption using BCrypt, Role-based access control for different user types and protection against unauthorized access to restricted endpoints.

It inludes Create and publish new blog posts, Retrieve all available blog posts, View individual blog posts by their unique ID, Update existing blog posts, Delete blog posts, Store blog titles, content, and other relevant details, Validate blog data before processing requests and Manage blog content through RESTful APIs.

It includes Handle application errors and invalid requests, Validate user input, Return meaningful error responses, Handle cases such as missing resources and invalid IDs and Improve API reliability through consistent error handling.

The Blog app follows a layered architecture to improve maintainability
Controller Layer: Handles incoming HTTP requests and sends responses.
Service Layer: Implements business logic and application rules.
Repository Layer: Communicates with the database.
Entity Layer: Defines the application's data models.
DTO Layer: Transfers data between application layers and API clients.
Security Layer: Configures authentication, authorization, and access control.
Exception Layer: Handles application exceptions and error responses.
