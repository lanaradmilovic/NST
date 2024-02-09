package com.nst.domaci.NST.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.nst.domaci.NST.entity.Engagement;
import com.nst.domaci.NST.entity.Lecture;
import com.nst.domaci.NST.entity.LectureSchedule;
import com.nst.domaci.NST.entity.Subject;
import com.nst.domaci.NST.entity.form.TeachingForm;

import java.io.IOException;

public class SubjectSerializer extends JsonSerializer<Subject> {
    @Override
    public void serialize(Subject subject, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();

        jsonGenerator.writeNumberField("id", subject.getId());
        jsonGenerator.writeStringField("name", subject.getName());
        jsonGenerator.writeNumberField("espb", subject.getEspb());

        if (subject.getDepartment() != null){
            jsonGenerator.writeObjectFieldStart("department");
            jsonGenerator.writeNumberField("id", subject.getDepartment().getId());
            jsonGenerator.writeStringField("name", subject.getDepartment().getName());
            jsonGenerator.writeEndObject();
        }

        if (subject.getFund()!=null) {
            jsonGenerator.writeObjectFieldStart("fund");
            jsonGenerator.writeNumberField("lecture", subject.getFund().getLecture());
            jsonGenerator.writeNumberField("exercise", subject.getFund().getExercise());
            jsonGenerator.writeNumberField("labExercise", subject.getFund().getLab());
            jsonGenerator.writeEndObject();
        }

        jsonGenerator.writeArrayFieldStart("lecturers");
        if (subject.getEngagements()!=null && !subject.getEngagements().isEmpty()) {
            for (Engagement e : subject.getEngagements()) {
                jsonGenerator.writeStartObject();
                jsonGenerator.writeStringField("lecturer", e.getMember().getFirstName() + " " + e.getMember().getLastName());
                jsonGenerator.writeArrayFieldStart("teachingForms");
                for (TeachingForm teachingForm : e.getTeachingForm()) {
                    jsonGenerator.writeString(teachingForm.name().toLowerCase());
                }
                jsonGenerator.writeEndArray();

                jsonGenerator.writeEndObject();
            }
        }
        jsonGenerator.writeEndArray();

        LectureSchedule schedule = subject.getLectureScheduleCurrentYear();
        if (schedule!=null){
            jsonGenerator.writeObjectFieldStart("schedule");
            jsonGenerator.writeNumberField("id", schedule.getId());
            jsonGenerator.writeNumberField("year", schedule.getYear());

            jsonGenerator.writeArrayFieldStart("lectures");
            if (schedule.getLectures()!=null){
                for (Lecture l : schedule.getLectures()){
                    jsonGenerator.writeStartObject();
                    jsonGenerator.writeStringField("form", l.getForm().name());
                    if (l.getDateTime()!=null) jsonGenerator.writeStringField("dateTime", l.getDateTime().toString());
                    jsonGenerator.writeStringField("description", l.getDescription());
                    jsonGenerator.writeStringField("lecturer", l.getEngagement().getMember().getFirstName());
                    jsonGenerator.writeEndObject();
                }
            }
            jsonGenerator.writeEndArray();
            jsonGenerator.writeEndObject();
        }

        jsonGenerator.writeEndObject();
    }
}
