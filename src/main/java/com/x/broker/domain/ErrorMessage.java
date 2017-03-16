package com.x.broker.domain;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * An error entity for RESTful responses.
 *
 * @author Akis Papadopoulos
 */
@JacksonXmlRootElement(localName = "error")
public class ErrorMessage {

    @JacksonXmlProperty(isAttribute = true)
    private int code;

    @JacksonXmlProperty(isAttribute = false)
    private String message;

    public ErrorMessage(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ErrorMessage{" + "code=" + code
                + ", message=" + message
                + "}";
    }
}
