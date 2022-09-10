package com.example.demorestapi.common;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.validation.Errors;

import java.io.IOException;

@JsonComponent
public class ErrorsSerializer extends JsonSerializer<Errors> {

    @Override
    public void serialize(Errors errors, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartArray();
        errors.getFieldErrors().forEach(e -> {
            try {
                gen.writeStartObject();
                gen.writeStringField("field", e.getField());
                gen.writeStringField("objectName", e.getObjectName());
                gen.writeStringField("code", e.getCode());
                gen.writeStringField("defaultMessage", e.getDefaultMessage());
                if (e.getRejectedValue() != null) {
                    gen.writeStringField("rejectedValue", e.getRejectedValue().toString());
                }
                gen.writeEndObject();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        errors.getGlobalErrors().forEach(e -> {
            try {
                gen.writeStartObject();
                gen.writeStringField("defaultMessage", e.getDefaultMessage());
                gen.writeStringField("code", e.getCode());
                gen.writeStringField("objectName", e.getObjectName());
                gen.writeEndObject();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        gen.writeEndArray();
    }
}
