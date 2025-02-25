package com.asset.management.exception;

public class NoAssignedAssetsException extends RuntimeException{
    public NoAssignedAssetsException(String message) {
        super(message);
    }
}
