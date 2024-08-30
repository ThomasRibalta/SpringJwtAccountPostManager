Voici une version révisée du README, en mettant davantage l'accent sur ta décision d'implémenter des fonctionnalités supplémentaires au-delà du système de base de registre et de connexion :

---

# Account and Post Manager

This project is my first experience using JWT and Spring Boot, built upon a tutorial by AmigosCode. The primary goal is to create an account and post management system while gaining a deeper understanding of JWT authentication.

## Project Overview

- **Tutorial Reference**: [AmigosCode JWT Tutorial](https://youtu.be/KxqlJblhzfI?si=8ypzT0TmwivBOgqF)
- **Security Implementation**: The security aspect of this project is inspired by the tutorial, particularly the use of filters for JWT authentication.

### Key Components

- **JWT Authentication Filter**: 
  ```java
  public class JwtAuthenticationFilter extends OncePerRequestFilter {
      @Override
      protected void doFilterInternal(
              @NonNull HttpServletRequest request,
              @NonNull HttpServletResponse response,
              @NonNull FilterChain filterChain)
              throws ServletException, IOException {
      }
  }
  ```

- **Token Generation**: The token includes the user's email in the payload:
  ```java
  public String generateToken(Map<String, Object> claims, UserDetails userDetails) {
      return Jwts.builder()
              .setClaims(claims)
              .setSubject(userDetails.getUsername())
              .setIssuedAt(new Date(System.currentTimeMillis()))
              .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
              .signWith(getSignInKey(), SignatureAlgorithm.HS256)
              .compact();
  }
  ```

- **User Identification**: The `getUsername()` method returns the email, which I kept for consistency with the tutorial. However, I might consider changing it to `getEmail()` in future projects. Instead of retrieving users by email, I may switch to using user IDs.

## Features

In addition to the basic registration and login functionalities provided in the tutorial, I chose to implement a more comprehensive system. This includes:

- **User Management**: Beyond registration and login, the system supports additional user methods for better account management.
- **Post Management**: A simple CRUD system for managing posts, allowing users to create, read, update, and delete posts.
- **Password Update**: Users can change their passwords using a PATCH request, enhancing account security.

## Testing

I used Postman to test the API endpoints thoroughly, ensuring that all functionalities work as intended.

## Conclusion

This project has been a valuable learning experience, deepening my appreciation for Spring Boot. The more I explore JWT authentication, the more eager I am to replicate and understand its workings.

---
