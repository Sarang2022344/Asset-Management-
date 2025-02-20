package com.asset.management.exception;

public class AssetAlreadyDisposedException extends RuntimeException{
    public AssetAlreadyDisposedException(String message) {
        super(message);
    }
}
