package com.nst.domaci.NST.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.nst.domaci.NST.entity.AcademicTitleHistory;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class AcademicTitleHistorySerializer extends JsonSerializer<AcademicTitleHistory> {

    @Override
    public void serialize(AcademicTitleHistory academicTitleHistory, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();

        jsonGenerator.writeNumberField("id", academicTitleHistory.getId());

        jsonGenerator.writeObjectFieldStart("member");
        jsonGenerator.writeNumberField("id", academicTitleHistory.getMember().getId());
        jsonGenerator.writeStringField("firstname", academicTitleHistory.getMember().getFirstName());
        jsonGenerator.writeStringField("lastname", academicTitleHistory.getMember().getLastName());
        jsonGenerator.writeEndObject();

        jsonGenerator.writeStringField("startDate", academicTitleHistory.getStartDate().format(DateTimeFormatter.ISO_DATE));
        jsonGenerator.writeStringField("endDate", academicTitleHistory.getEndDate()!= null ? academicTitleHistory.getEndDate().format(DateTimeFormatter.ISO_DATE): "");

        jsonGenerator.writeObjectFieldStart("academicTitle");
        jsonGenerator.writeNumberField("id", academicTitleHistory.getAcademicTitle().getId());
        jsonGenerator.writeStringField("name", academicTitleHistory.getAcademicTitle().getName());
        jsonGenerator.writeEndObject();

        jsonGenerator.writeObjectFieldStart("scientificField");
        jsonGenerator.writeNumberField("id", academicTitleHistory.getScientificField().getId());
        jsonGenerator.writeStringField("name", academicTitleHistory.getScientificField().getName());
        jsonGenerator.writeEndObject();

        jsonGenerator.writeEndObject();
    }
}

