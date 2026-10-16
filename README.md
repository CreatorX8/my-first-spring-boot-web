# My First Spring Boot Web Application

## Overview

A full-stack web application built with Spring Boot and React for managing games. The backend provides REST API endpoints, while the frontend provides the user interface.

## Technologies Used

* Java
* Spring Boot
* Spring Security
* Spring Addons OIDC
* Google JWT authentication
* Spring Data JPA
* H2 Database
* React
* Vite
* Maven

## Features

* Google sign-in
* JWT-based authentication
* Role-based authorization
* Game management through REST API endpoints
* Database persistence using H2
* Automated unit, integration, and authorization tests

## Authentication and Authorization

The backend validates Google-issued JWTs for protected API requests.

### Roles

* **USER:** Can access permitted game-management endpoints.
* **ADMIN:** Can access permitted endpoints, including deleting games.

### Expected Security Responses

* `401 Unauthorized`: The request has no valid authentication.
* `403 Forbidden`: The user is authenticated but lacks permission for the requested action.

The administrator role is assigned according to the configured administrator email and the backend's authorization rules.

## Running the Backend

From the project root, run:

```bash
./mvnw spring-boot:run
```

The backend should run at:

`http://localhost:8080`

## Running the Frontend

The React frontend is maintained in a separate project from this Spring Boot backend.

1. Open the React project separately in IntelliJ IDEA or VS Code.

2. Open a terminal in the React project's directory.

3. Install the dependencies:

   ```bash
   npm install
   ```

4. Start the development server:

   ```bash
   npm run dev
   ```

5. Open the local URL displayed by Vite, usually `http://localhost:5173`.

Ensure the Spring Boot backend is running at `http://localhost:8080` before using the frontend.

## Repository Structure

* **Backend:** This repository contains the Spring Boot REST API, Google JWT authentication, authorization, and database configuration.
* **Frontend:** The React and Vite application is maintained separately.

## Running Tests

From the backend project root, run:

```bash
./mvnw test
```

The test suite includes unit, integration, and authorization tests.

## Configuration and Security Notes

* Configure the Google OAuth client ID required by the application.
* Keep private credentials, access tokens, and secrets out of Git.
* Configure allowed frontend origins appropriately for deployment.
* Review database settings before using the application in production.

## Repository

https://github.com/CreatorX8/my-first-spring-boot-web