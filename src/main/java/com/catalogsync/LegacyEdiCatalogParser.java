package com.catalogsync;

import java.util.Map;

/**
 * Legacy class from the 2008 EDI integration. DO NOT MODIFY: the accounting
 * module still calls it directly. This is the Adaptee.
 *
 * It is genuinely incompatible with SupplierCatalogSource:
 *  - different method name (parseLine, not fetchProduct)
 *  - different parameter (a raw CSV row string, not a sku)
 *  - different failure mechanism (empty array sentinel, not an exception)
 */
public class LegacyEdiCatalogParser {

    private final Map<String, String> rawCsvLinesBySku;

    public LegacyEdiCatalogParser(Map<String, String> rawCsvLinesBySku) {
        this.rawCsvLinesBySku = rawCsvLinesBySku;
    }

    public String[] parseLine(String rawCsvRow) {
        if (rawCsvRow == null || rawCsvRow.isBlank()) {
            return new String[0];
        }
        String[] fields = rawCsvRow.split(";");
        if (fields.length != 4) {
            return new String[0];
        }
        return fields;
    }

    public String getRawLineForSku(String sku) {
        return rawCsvLinesBySku.get(sku);
    }
}
