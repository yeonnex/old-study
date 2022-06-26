package com.example.restapi.common;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.validation.Errors;

import java.io.IOException;

@JsonComponent // 스프링부트가 제공. 이 시리얼라이저를 등록해줌.
public class ErrorsSerializer extends JsonSerializer<Errors> {
    @Override
    public void serialize(Errors errors,
                          JsonGenerator jGen,
                          SerializerProvider serializerProvider) throws IOException {
        jGen.writeStartArray();

        // 필드 에러 field error
        errors.getFieldErrors().forEach(e -> {
            try {
                jGen.writeStartObject();

                jGen.writeStringField("objectName", e.getObjectName());
                jGen.writeStringField("field", e.getField());
                jGen.writeStringField("code", e.getCode());
                jGen.writeStringField("defaultMessage", e.getDefaultMessage());

                if (e.getRejectedValue() != null) {
                    jGen.writeStringField("rejectedValue", e.getRejectedValue().toString());
                } else {
                    jGen.writeStringField("rejectedValue", null);
                }

                jGen.writeEndObject();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        });

        // 글로벌 에러 global error
        errors.getGlobalErrors().forEach(e -> {
            try {
                jGen.writeStartObject();
                jGen.writeStringField("objectName", e.getObjectName());
                jGen.writeStringField("code", e.getCode());
                jGen.writeStringField("defaultMessage", e.getDefaultMessage());
                jGen.writeEndObject();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        jGen.writeEndArray();
    }
}
