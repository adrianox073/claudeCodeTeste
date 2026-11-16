# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project overview

Small Spring Boot 3.4 (Java 17, Maven) web app. It evaluates texts with `Prueba.describir` and stores each text plus its result in an in-memory H2 database via `JdbcTemplate`. The app stays running (Tomcat on port 8080), so the H2 database lives as long as the process and can be inspected through the H2 web console. The local Maven repository on this machine is `D:\code\repo` (not `~/.m2`); never hardcode jar paths.

This directory is its own Git repository (branch `main`, remote `origin` = https://github.com/adrianox073/untitled.git). It sits physically inside a clone of `eugenp/tutorials` (`D:\Cursos\tutorials`) but is unrelated to it; run git commands from this directory.

## Structure

Code is organized by type under base package `com.example.untitled` (Spring Boot must not use the default package; the application class stays at the root so component scanning covers the subpackages). Tests mirror the same packages.

- `UntitledApplication` (root) — `@SpringBootApplication` entry point.
- `util.Prueba` — `public static String describir(String)`: `"vacio"` for null/empty input, otherwise the input.
- `model.Mensaje` — immutable row value (id, texto, resultado, fecha); serialized to JSON via its getters.
- `repository.MensajeRepository` — `@Repository` over table `mensaje` using `JdbcTemplate`: `guardar(texto, resultado)`, `listar()`.
- `controller.MensajeController` — REST: `GET /mensajes` lists rows, `POST /mensajes?texto=...` evaluates and stores one.
- `config.DatosDeEjemplo` — `CommandLineRunner` `@Component` that seeds `null`, `""`, `"hola"` on startup. Keep it a separate component (not a `@Bean` in the application class) so slice tests like `@JdbcTest` don't try to build it.
- `src/main/resources/application.properties` — `spring.datasource.*` (`jdbc:h2:mem:untitled;DB_CLOSE_DELAY=-1`, user `sa`, empty password), `spring.sql.init.mode=always`, H2 console at `/h2-console`.
- `src/main/resources/schema.sql` — creates table `mensaje`.
- Tests: `PruebaTest` (plain unit), `MensajeRepositoryTest` (`@JdbcTest`, embedded DB, rollback per test), `UntitledApplicationTest` (`@SpringBootTest` + MockMvc against the API, includes seeded rows).

## Common commands

```
mvn test               # run all tests
mvn spring-boot:run    # start the app on http://localhost:8080
```

H2 console: http://localhost:8080/h2-console with JDBC URL `jdbc:h2:mem:untitled`, user `sa`, empty password.

There is no linter configured.
