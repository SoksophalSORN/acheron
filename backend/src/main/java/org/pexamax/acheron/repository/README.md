# Auto-Generated Methods by Spring Data JPA

When you extend a Spring Data JPA interface like `JpaRepository<T, ID>`, you get a rich set of **auto-generated methods** — no need to define them yourself.

---
## Core CRUD Methods from `CrudRepository`
>

| Method | Description |
|:-------|:------------|
| `S save(S entity)` | Saves a given entity (create or update) |
| `Optional<T> findById(ID id)` | Retrieves an entity by its ID |
| `boolean existsById(ID id)` | Checks if an entity exists by ID |
| `Iterable<T> findAll()` | Returns all entities |
| `Iterable<T> findAllById(Iterable<ID> ids)` | Finds all entities by their IDs |
| `long count()` | Returns the total number of entities |
| `void deleteById(ID id)` | Deletes an entity by ID |
| `void delete(T entity)` | Deletes a given entity |
| `void deleteAll(Iterable<? extends T> entities)` | Deletes all given entities |
| `void deleteAll()` | Deletes all entities in the repository |

---
## Paging and Sorting from `PagingAndSortingRepository`
>

| Method | Description |
|:-------|:------------|
| `Iterable<T> findAll(Sort sort)` | Returns all entities sorted by the given `Sort` object |
| `Page<T> findAll(Pageable pageable)` | Returns a `Page` of entities based on `Pageable` (for pagination) |

---
## Extra Methods from `JpaRepository`
>

| Method | Description |
|:-------|:------------|
| `List<T> findAll()` | Overrides the Iterable from `CrudRepository` to return `List<T>` |
| `List<T> findAllById(Iterable<ID> ids)` | Returns a list instead of iterable |
| `void flush()` | Flushes changes to the database immediately |
| `<S extends T> S saveAndFlush(S entity)` | Saves and flushes immediately |
| `void deleteInBatch(Iterable<T> entities)` | Deletes in a batch (deprecated in newer versions) |
| `void deleteAllInBatch()` | Deletes all in a batch operation |
| `T getOne(ID id)` | Returns a reference proxy (lazy-loaded) — deprecated |
| `T getById(ID id)` | Same idea as `getOne`, but not deprecated |
| `<S extends T> List<S> saveAllAndFlush(Iterable<S> entities)` | Saves and flushes a batch |
| `void deleteAllByIdInBatch(Iterable<ID> ids)` | Deletes entities by ID in a batch |

---
## Custom Query Methods You Can Add
Beyond the built-ins, Spring Data JPA can **generate queries by method name**:
>

| Method Name | Description |
|:------------|:------------|
| `findByEmail(String email)` | Finds by field `email` |
| `existsByUsername(String username)` | Checks existence by username |
| `countByStatus(String status)` | Counts rows matching status |
| `deleteByIsActiveFalse()` | Deletes records where `isActive` is false |

> These are **not built-in** but are auto-generated when you **follow naming conventions**.

---
## Summary
>

By just writing:

```java
public interface UserRepository extends JpaRepository<User, Long> {}
```
You already get:

- Full CRUD
- Pagination and sorting
- Batch operations
- Flush control
- Optional and List returns
And the ability to add method-name-based custom queries

