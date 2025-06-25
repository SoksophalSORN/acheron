# Auto-Generated Methods by Spring Data JPA

When you extend a Spring Data JPA interface like `JpaRepository<T, ID>`, you get a rich set of **auto-generated methods** — no need to define them yourself.

---
## Core CRUD Methods from `CrudRepository`
>

| Method | Description | Usage Example |
|:-------|:------------|:--------------|
| `S save(S entity)` | Saves a given entity (create or update) | `usrRepo.save(user);` |
| `Optional<T> findById(ID id)` | Retrieves an entity by its ID | `usrRepo.findById(1L);` |
| `boolean existsById(ID id)` | Checks if an entity exists by ID | `usrRepo.existsById(1L);` |
| `Iterable<T> findAll()` | Returns all entities | `usrRepo.findAll();` |
| `Iterable<T> findAllById(Iterable<ID> ids)` | Finds all entities by their IDs | `usrRepo.findAllById(List.of(1L, 2L));` |
| `long count()` | Returns the total number of entities | `usrRepo.count();` |
| `void deleteById(ID id)` | Deletes an entity by ID | `usrRepo.deleteById(1L);` |
| `void delete(T entity)` | Deletes a given entity | `usrRepo.delete(user);` |
| `void deleteAll(Iterable<? extends T> entities)` | Deletes all given entities | `usrRepo.deleteAll(users);` |
| `void deleteAll()` | Deletes all entities in the repository | `usrRepo.deleteAll();` |

---
## Paging and Sorting from `PagingAndSortingRepository`
>

| Method | Description | Usage Example |
|:-------|:------------|:--------------|
| `Iterable<T> findAll(Sort sort)` | Returns all entities sorted by the given `Sort` object | `usrRepo.findAll(Sort.by("username"));` |
| `Page<T> findAll(Pageable pageable)` | Returns a `Page` of entities based on `Pageable` (for pagination) | `usrRepo.findAll(PageRequest.of(0, 10));` |

---
## Extra Methods from `JpaRepository`
>

| Method | Description | Usage Example |
|:-------|:------------|:--------------|
| `List<T> findAll()` | Returns all entities as a list | `usrRepo.findAll();` |
| `List<T> findAllById(Iterable<ID> ids)` | Returns a list instead of iterable | `usrRepo.findAllById(List.of(1L, 2L));` |
| `void flush()` | Flushes changes to the database immediately | `usrRepo.flush();` |
| `<S extends T> S saveAndFlush(S entity)` | Saves and flushes immediately | `usrRepo.saveAndFlush(user);` |
| `void deleteInBatch(Iterable<T> entities)` | Deletes a batch of entities | `usrRepo.deleteInBatch(users);` *(deprecated in newer Spring versions)* |
| `void deleteAllInBatch()` | Deletes all entities in a batch operation | `usrRepo.deleteAllInBatch();` |
| `T getOne(ID id)` | Returns a reference proxy (lazy-loaded) — deprecated | `usrRepo.getOne(1L);` *(deprecated)* |
| `T getById(ID id)` | Returns a reference proxy (lazy-loaded) | `usrRepo.getById(1L);` |
| `<S extends T> List<S> saveAllAndFlush(Iterable<S> entities)` | Saves and flushes a batch | `usrRepo.saveAllAndFlush(users);` |
| `void deleteAllByIdInBatch(Iterable<ID> ids)` | Deletes entities by ID in a batch | `usrRepo.deleteAllByIdInBatch(List.of(1L, 2L));` |

---
## Custom Query Methods You Can Add
Beyond the built-ins, Spring Data JPA can **generate queries by method name**:
>

| Method Name | Description | Usage Example |
|:------------|:------------|:--------------|
| `findByEmail(String email)` | Finds by field `email` | `usrRepo.findByEmail("john@example.com");` |
| `existsByUsername(String username)` | Checks existence by username | `usrRepo.existsByUsername("john123");` |
| `countByStatus(String status)` | Counts rows matching status | `usrRepo.countByStatus("ACTIVE");` |
| `deleteByIsActiveFalse()` | Deletes records where `isActive` is false | `usrRepo.deleteByIsActiveFalse();` |

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

