package com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.domain.exceptions;

public class ExceptionsMsg {
    private ExceptionsMsg(){}
    public static final String EMPLOYEE_ALREADY_EXISTS_EXCEPTION="Employee Already Exists Exception";
    public static final String EMPLOYEE_NOT_FOUND_EXCEPTION="Employee Not Found Exception";
    public static final String EMPLOYEE_FIELDS_EMPTY_EXCEPTION="Employee One or More Fields Empty Exception";
    public static final String  EMPLOYEE_UNKNOWN_STATE_EXCEPTION="Employee State Unknown Exception";
    public static final String EMPLOYEE_UNKNOWN_TYPE_EXCEPTION="Employee Type Unknown Exception";;
    public static final String  REMOTE_ADDRESS_API_EXCEPTION="Remote Address Api Unreachable Exception";
    public static final String EMPLOYEE_ASSIGNED_REMOTE_PROJECT_API_EXCEPTION= "Cannot Remove Employee Assigned Project Exception";
}
