package com.nst.domaci.NST.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.nst.domaci.NST.entity.Lecture;

import java.io.IOException;

public class LectureSerializer extends JsonSerializer<Lecture> {
    @Override
    public void serialize(Lecture lecture, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();

        jsonGenerator.writeNumberField("id", lecture.getId());

        // Serialize engagement as an object with member id and subject id
        jsonGenerator.writeObjectFieldStart("engagement");
        jsonGenerator.writeNumberField("memberId", lecture.getEngagement().getMember().getId());
        jsonGenerator.writeNumberField("subjectId", lecture.getEngagement().getSubject().getId());
        jsonGenerator.writeEndObject();

        jsonGenerator.writeStringField("dateTime", lecture.getDateTime().toString());
        jsonGenerator.writeStringField("description", lecture.getDescription());
        jsonGenerator.writeNumberField("lectureScheduleId", lecture.getLectureSchedule().getId());

        jsonGenerator.writeEndObject();
    }
}
