package com.backend.api.domain.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constant {

    public final String DEFAULT_FORMAT_DATE_TIME = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    public final String INVALID_HEADER = "INVALID_PARAMETER_HEADER";
    public final String CONSTRAINT_VIOLATION = "CONSTRAINT_VIOLATION";
    public final String INVALID_FORMAT = "INVALID_FORMAT";
    public final String METHOD_NO_SUPPORTED = "REQUEST METHOD NOT SUPPORTED";
    public final String INVALID_PARAMETER = "INVALID_PARAMETER";
    public final String INTERNAL_COMPONENT = "Internal component details";
    public final String INVALID_MONTH = "Invalid month value";
    public final String ARTICLE_DELETED_SUCCESS  = "The article have been deleted successfully!";
    public final String ARTICLE_SYNCHRONIZATION_STARTING  = "The article synchronization task is starting!";
    public final String USER_NOT_FOUND = "User does not exist.";
    public final String ARTICLE_NOT_FOUND = "Article does not exist.";
    public final String DEFAULT_ERROR  = "Sorry, our service is not responding, please try again.";
}
