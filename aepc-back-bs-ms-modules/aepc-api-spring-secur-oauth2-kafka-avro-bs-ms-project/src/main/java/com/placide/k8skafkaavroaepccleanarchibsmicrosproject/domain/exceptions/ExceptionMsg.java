package com.placide.k8skafkaavroaepccleanarchibsmicrosproject.domain.exceptions;

public class ExceptionMsg {
    private ExceptionMsg() {
    }
    public static final String PROJECT_ALREADY_EXISTS_EXCEPTION = "Project Already Exists Exception";
    public static final String PROJECT_FIELD_EMPTY_EXCEPTION = "Project, One or more Fields Empty Exception";
    public static final String PROJECT_NOT_FOUND_EXCEPTION = "Project Not Found Exception";
    public static final String PROJECT_UNKNOWN_PRIORITY_EXCEPTION = "Project Priority Unknown Exception";
    public static final String PROJECT_UNKNOWN_STATE_EXCEPTION = "Project State Unknown Exception";
    public static final String REMOTE_COMPANY_API_EXCEPTION = "Remote Company API Unreachable Exception";
    public static final String REMOTE_COMPANY_API_NAME_NOT_FOUND_EXCEPTION = "Remote Company API Name Not Found Exception";
    public static final String REMOTE_COMPANY_API_AGENCY_NOT_FOUND_EXCEPTION = "Remote Company API Agency Not Found Exception";
    public static final String REMOTE_EMPLOYEE_API_EXCEPTION = "Remote Employee API Unreachable Exception";
    public static final String PROJECT_ASSIGNED_TO_EMPLOYEE_EXCEPTION = "Project Assigned To Remote Employee Exception";
    public static final String PROJECT_ASSIGNED_TO_COMPANY_EXCEPTION = "Project Assigned To Remote Company Exception";
    public static final String REMOTE_EMPLOYEE_STATE_UNAUTHORIZED_EXCEPTION = "Remote Employee State Unauthorized Exception";
}
