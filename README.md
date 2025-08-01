# Spring Boot Post Notification System

This is a backend project developed using **Spring Boot** that imitates a simplified version of Twitter. Users can follow or unfollow each other, create posts, and receive notifications when users they follow post new content.

## Features
- Follow / Unfollow users
- Create and view posts
- View followers and following lists
- Receive notifications from followed users
- View personal feed based on followed users

## Technologies Used
- Java 17
- Spring Boot
- RESTful API
- SQL

---

## API Endpoints Summary

### User APIs

- **Get all users**
  - `GET /api/users`
  - Returns JSON with `userId` and `username`

- **Get current user**
  - `GET /api/{userId}`
  - Returns JSON with `userId` and `username`

- **Get following list**
  - `GET /api/{userId}/following`
  - Returns JSON with `userId` and `username`

- **Get follower list**
  - `GET /api/{userId}/follower`
  - Returns JSON with `userId` and `username`

- **Follow another user**
  - `POST /api/{userId}/follow/{targetUserId}`

- **Unfollow a user**
  - `DELETE /api/{userId}/unfollow/{targetUserId}`

---

### Post APIs

- **Create a post**
  - `POST /api/{userId}/posts`
  - Body (JSON):
    ```json
    {
      "content": "Your post message here"
    }
    ```

- **Get current user's posts**
  - `GET /api/{userId}/posts`
  - Returns JSON with `postId`, `post`, `createdAt`, and `username`

- **Get feed (posts from following users)**
  - `GET /api/{userId}/feed`
  - Returns JSON with `postId`, `post`, `createdAt`, and `username`

---

### Notification APIs

- **Get notifications for a user (when followed users post)**
  - `GET /api/{userId}/notifications`
  - Returns JSON with `id`, `message`, and `createdAt`

---

## Configuration & Setup
# Step 1: Configure the Database
Navigate to the configuration file:

-springboot-post-rest-api/src/main/resources/application.properties
- If you are using MySQL, you only need to modify the following properties with your database details:

  spring.datasource.url=jdbc:mysql://localhost:3306/your_database_name
  spring.datasource.username=your_mysql_username
  spring.datasource.password=your_mysql_password
  spring.jpa.hibernate.ddl-auto=update
  Make sure your MySQL server is running and the database exists before launching the application.

- If Using a Different Database
  You will need to add the appropriate dependency to the pom.xml file (located at the root: springboot-post-rest-api/pom.xml).
  Update the application.properties file with the correct JDBC URL and driver class for your chosen database.

# Step 2: Run the Application
- To start the backend server:

  Locate the main class:

  /src/main/java/com/springboot/post/SpringbootPostRestApiApplication.java
  Run it using IDE, and the application will start at:
  http://localhost:8080
