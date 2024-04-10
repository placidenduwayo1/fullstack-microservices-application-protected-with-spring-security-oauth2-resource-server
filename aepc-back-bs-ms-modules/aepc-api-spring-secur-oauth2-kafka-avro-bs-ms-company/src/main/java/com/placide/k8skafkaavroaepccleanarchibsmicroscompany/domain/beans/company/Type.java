package com.placide.k8skafkaavroaepccleanarchibsmicroscompany.domain.beans.company;

public enum Type {
    CLIENT("CLIENT"),
    PROSPECT("PROSPECT"),
    ESN("ESN");

    private final String cType;

    Type(String cType) {
        this.cType = cType;
    }

    public String getcType() {
        return cType;
    }
}
