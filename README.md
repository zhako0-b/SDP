# Catalog Sync

A Java 17 demonstration combining Bridge and Adapter in one product-catalog workflow.

- **Bridge:** two synchronization operations share the `SupplierCatalogSource` contract.
- **Exactly one Adapter:** supplier C wraps the unchanged legacy parser. Suppliers A and B are direct local implementations using normalized products.
- **Complexity module:** source selection at runtime from the input SKU, with provider registration through `ServiceLoader`.

## Build and test

Install JDK 17 or later and Maven 3.9 or later, then run from this folder:

```sh
mvn clean verify
```

This runs JUnit 5 tests and produces `target/catalog-sync.jar`. First-time builds need network access to Maven Central. The executable itself has no external runtime dependencies or network calls.

## Run

```sh
java -jar target/catalog-sync.jar full A-1001 B-2002 C-10234
java -jar target/catalog-sync.jar incremental A-1001 B-2002 C-10234 A-1001
```

The repeated A-1001 in the second command prints `unchanged`: history is shared within one execution. The tests also exercise a changed price. Each new process starts with empty state.

```sh
java -jar target/catalog-sync.jar full C-missing Z-1 invalid
```

These inputs demonstrate missing data, an unknown supplier, and invalid SKU syntax. The program continues through the requested inputs, then exits with code 1 if any input failed. Invalid command syntax exits with code 2; a successful run exits with code 0.

## Required design files

- [Design rationale PDF](docs/design-rationale.pdf) — the required document, limited to two pages.
- [UML class diagram](docs/uml-class-diagram.png) — the final class diagram as an image.

## Add a supplier without editing existing Java classes

1. Add a class implementing `SupplierCatalogSource` with a unique prefix, `fetchProduct(SupplierSku)`, and a public no-argument constructor.
2. Append its fully qualified class name to `src/main/resources/META-INF/services/com.catalogsync.SupplierCatalogSource`.
3. Build again and pass a SKU with the new prefix. The existing client and factory discover it automatically.

The delivered application registers exactly three implementations. A fourth source exists only as a test stub to demonstrate extension.

## Add a synchronization operation

Extend `CatalogSync`, implement `afterSync(Product)`, and instantiate it with any source. Existing synchronization and supplier classes do not change. The test-only `StockOnlySync` demonstrates this. Exposing a new mode in this small CLI would require separately wiring that mode into `Client`.

## Scope and submission

This is an in-memory educational model, not a live REST/GraphQL integration or a persistent inventory system. `LegacyEdiCatalogParser.java` is unchanged from the original archive; its historical comments are inherited text.

The assignment also requires an individual oral defense and a Git repository link. Publish this folder to your own repository after reviewing it; do not include `target/`. Confirm that this domain was not used in your previous assignments or class examples. The application and documentation cannot establish domain originality on your behalf.
