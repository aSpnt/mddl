package com.aspnt.mddl.dto.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

public class ExternalApiErrorResponse {

    private String code;
    private String message;

    public ExternalApiErrorResponse code(String code) {
        this.code = code;
        return this;
    }

    @JsonProperty("code")
    public @NotNull String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ExternalApiErrorResponse message(String message) {
        this.message = message;
        return this;
    }

    @JsonProperty("message")
    public @NotNull String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            ExternalApiErrorResponse upRErrorResponse = (ExternalApiErrorResponse) o;
            return Objects.equals(this.code, upRErrorResponse.code) && Objects.equals(this.message, upRErrorResponse.message);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(this.code, this.message);
    }

    public String toString() {
        return "class UPRErrorResponse {\n" +
                "    code: " + this.toIndentedString(this.code) + "\n" +
                "    message: " + this.toIndentedString(this.message) + "\n" +
                "}";
    }

    private String toIndentedString(Object o) {
        return o == null ? "null" : o.toString().replace("\n", "\n    ");
    }
}
