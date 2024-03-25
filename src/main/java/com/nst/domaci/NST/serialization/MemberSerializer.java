package com.nst.domaci.NST.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.nst.domaci.NST.entity.AcademicTitleHistory;
import com.nst.domaci.NST.entity.Engagement;
import com.nst.domaci.NST.entity.Member;
import com.nst.domaci.NST.entity.form.TeachingForm;

import java.io.IOException;

// Import statements...

public class MemberSerializer extends JsonSerializer<Member> {
    @Override
    public void serialize(Member member, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();

        jsonGenerator.writeNumberField("id", member.getId());
        jsonGenerator.writeStringField("firstname", member.getFirstName());
        jsonGenerator.writeStringField("lastname", member.getLastName());

        if (member.getAcademicTitle() != null) {
            jsonGenerator.writeStringField("academicTitle", member.getAcademicTitle().getName());
        }
        if (member.getScientificField() != null) {
            jsonGenerator.writeStringField("scientificField", member.getScientificField().getName());
        }
        if (member.getEducationTitle() != null) {
            jsonGenerator.writeStringField("educationTitle", member.getEducationTitle().getName());
        }

        jsonGenerator.writeObjectFieldStart("department");
        jsonGenerator.writeNumberField("id", member.getDepartment().getId());
        jsonGenerator.writeStringField("name", member.getDepartment().getName());
        jsonGenerator.writeStringField("shortname", member.getDepartment().getShortName());
        jsonGenerator.writeEndObject();

        if (member.getEngagements() != null && !member.getEngagements().isEmpty()) {
            jsonGenerator.writeArrayFieldStart("engagements");
            for (Engagement engagement : member.getEngagements()) {
                jsonGenerator.writeStartObject();
                jsonGenerator.writeNumberField("id", engagement.getId());
                jsonGenerator.writeNumberField("year", engagement.getEngagementYear());

                jsonGenerator.writeObjectFieldStart("subject");
                jsonGenerator.writeStringField("name", engagement.getSubject().getName());
                jsonGenerator.writeNumberField("espb", engagement.getSubject().getEspb());
                jsonGenerator.writeArrayFieldStart("lectureForms");
                if (engagement.getTeachingForm() != null){
                    for (TeachingForm form : engagement.getTeachingForm()){
                        jsonGenerator.writeString(form.name());
                    }
                }
                jsonGenerator.writeEndArray();
                jsonGenerator.writeEndObject();

                jsonGenerator.writeEndObject();
            }
            jsonGenerator.writeEndArray();
        }

        if (member.getAcademicTitleHistories() != null && !member.getAcademicTitleHistories().isEmpty()) {
            jsonGenerator.writeArrayFieldStart("academicTitles");
            for (AcademicTitleHistory title : member.getAcademicTitleHistories()) {
                jsonGenerator.writeStartObject();
                jsonGenerator.writeNumberField("id", title.getId());
                jsonGenerator.writeStringField("academicTitle", title.getAcademicTitle().getName());
                jsonGenerator.writeStringField("scientificField", title.getScientificField().getName());
                jsonGenerator.writeStringField("startDate", title.getStartDate().toString());
                jsonGenerator.writeStringField("endDate", title.getEndDate() != null ? title.getEndDate().toString() : "");
                jsonGenerator.writeEndObject();
            }
            jsonGenerator.writeEndArray();
        }

        jsonGenerator.writeEndObject();
    }
}


