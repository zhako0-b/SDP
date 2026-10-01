package com.catalogsync;

/** Implementor: sources return valid products or a shared checked failure. */
public interface SupplierCatalogSource {
    String supplierPrefix();

    /** sku must be non-null. No supplier-specific failure may escape this call. */
    Product fetchProduct(SupplierSku sku) throws CatalogSyncException;
}
