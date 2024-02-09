package com.nst.domaci.NST.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.nst.domaci.NST.entity.Engagement;
import com.nst.domaci.NST.entity.form.TeachingForm;

import java.io.IOException;

public class EngagementSerializer extends JsonSerializer<Engagement> {
    @Override
    public void serialize(Engagement engagement, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();

        jsonGenerator.writeNumberField("id", engagement.getId());
        jsonGenerator.writeNumberField("year", engagement.getYear());

        jsonGenerator.writeObjectFieldStart("member");
        jsonGenerator.writeNumberField("id", engagement.getMember().getId());
        jsonGenerator.writeStringField("firstname", engagement.getMember().getFirstName());
        jsonGenerator.writeStringField("lastname", engagement.getMember().getLastName());
        jsonGenerator.writeEndObject();

        jsonGenerator.writeObjectFieldStart("subject");
        jsonGenerator.writeNumberField("id", engagement.getSubject().getId());
        jsonGenerator.writeStringField("name", engagement.getSubject().getName());
        jsonGenerator.writeEndObject();

        jsonGenerator.writeArrayFieldStart("teachingForm");
        for (TeachingForm form : engagement.getTeachingForm()) {
            jsonGenerator.writeString(form.name());
        }
        jsonGenerator.writeEndArray();

        jsonGenerator.writeEndObject();
    }


}
