package com.placide.k8skafkaavroaepccleanarchibsmicroscompany.domain.exceptions;


public class ExceptionMsg {
    private ExceptionMsg() {
    }

    public static final String COMPANY_NOT_FOUND_EXCEPTION = "Company Not Found Exception";
    public static final String COMPANY_FIELDS_EMPTY_EXCEPTION = "Company One or more Fields Empty Exception";
    public static final String COMPANY_ALREADY_EXISTS_EXCEPTION = "Company Already Exists Exception";
    public static final String COMPANY_TYPE_UNKNOWN_EXCEPTION = "Company type unknown Exception";
    public static final String REMOTE_ADDRESS_API_EXCEPTION = "Remote Address Api Unreachable Exception";
    public static final String COMPANY_ASSIGNED_PROJECT_EXCEPTION = "Cannot Delete Company Already Assigned Remote Project(s) Exception";
    public static final String REMOTE_ADDRESS_COMPANY_EXISTS_ON_ADDRESS_EXCEPTION = "Company already exists on that address Exception";
}
