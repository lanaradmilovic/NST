package com.nst.domaci.NST.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.nst.domaci.NST.entity.Lecture;
import com.nst.domaci.NST.entity.LectureSchedule;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class LectureScheduleSerializer extends JsonSerializer<LectureSchedule> {
    @Override
    public void serialize(LectureSchedule lectureSchedule, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();

        jsonGenerator.writeNumberField("id", lectureSchedule.getId());
        jsonGenerator.writeNumberField("year", lectureSchedule.getScheduleYear());

        jsonGenerator.writeObjectFieldStart("subject");
        jsonGenerator.writeNumberField("id", lectureSchedule.getSubject().getId());
        jsonGenerator.writeStringField("name", lectureSchedule.getSubject().getName());
        jsonGenerator.writeEndObject();

        jsonGenerator.writeArrayFieldStart("lectures");
        for (Lecture lecture : lectureSchedule.getLectures()) {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeNumberField("id", lecture.getId());
            jsonGenerator.writeStringField("form", lecture.getForm().name());
            jsonGenerator.writeStringField("dateTime", lecture.getDateTime().format(DateTimeFormatter.ISO_DATE_TIME));
            jsonGenerator.writeStringField("description", lecture.getDescription());
            jsonGenerator.writeEndObject();
        }
        jsonGenerator.writeEndArray();

        jsonGenerator.writeEndObject();
    }
}
