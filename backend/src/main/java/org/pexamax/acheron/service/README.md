# Spring Boot Request Mapping Annotations

In Spring Boot (Spring Web), you use various annotations to map HTTP requests to controller methods. These annotations are located in the `org.springframework.web.bind.annotation` package.

## Main Mapping Annotations

### 1. `@RequestMapping`
- **General purpose** annotation for mapping HTTP requests.
- Can be applied at **class** or **method** level.
- Supports specifying:
  - HTTP methods (GET, POST, etc.)
  - Paths (URLs)
  - Consumes/produces media types
  - Headers, parameters, and more.

### 2. Shortcut Annotations for Specific HTTP Methods
These are specialized annotations introduced for convenience and clarity:

| Annotation        | HTTP Method | Description                   |
|-------------------|-------------|-------------------------------|
| `@GetMapping`     | GET         | Maps HTTP GET requests         |
| `@PostMapping`    | POST        | Maps HTTP POST requests        |
| `@PutMapping`     | PUT         | Maps HTTP PUT requests         |
| `@DeleteMapping`  | DELETE      | Maps HTTP DELETE requests      |
| `@PatchMapping`   | PATCH       | Maps HTTP PATCH requests       |

---

## Usage Example

```java
@RestController
@RequestMapping("/items")
public class ItemController {

    @GetMapping          // Handles GET requests to /items
    public List<Item> getAll() { ... }

    @PostMapping         // Handles POST requests to /items
    public Item create(@RequestBody Item item) { ... }

    @PutMapping("/{id}") // Handles PUT requests to /items/{id}
    public Item update(@PathVariable Long id, @RequestBody Item item) { ... }

    @DeleteMapping("/{id}") // Handles DELETE requests to /items/{id}
    public void delete(@PathVariable Long id) { ... }
}
```
