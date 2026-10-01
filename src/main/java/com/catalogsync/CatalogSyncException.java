package com.catalogsync;

/** Error codes and messages belong to the common source contract. */
public class CatalogSyncException extends Exception {
    public enum Code { NOT_FOUND, INVALID_DATA, SOURCE_FAILURE, UNKNOWN_SOURCE }

    private final Code code;

    public CatalogSyncException(Code code, String message) {
        super(message);
        this.code = code;
    }

    public Code getCode() { return code; }
}
