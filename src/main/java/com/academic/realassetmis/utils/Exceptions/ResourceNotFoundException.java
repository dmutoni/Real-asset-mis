package com.academic.realassetmis.utils.Exceptions;

import java.util.UUID;

public class ResourceNotFoundException extends  RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
    public ResourceNotFoundException(String resourceName, String fieldName, UUID fieldValue) {
        super(String.format("%s with %s ['%s'] " +
                "is not found", resourceName, fieldName, fieldValue));
    }
}
