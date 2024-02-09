package com.nst.domaci.NST.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.nst.domaci.NST.entity.*;

import java.io.IOException;

public class DepartmentSerializer extends JsonSerializer<Department> {
    @Override
    public void serialize(Department department, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();

        jsonGenerator.writeNumberField("id", department.getId());
        jsonGenerator.writeStringField("name", department.getName());
        jsonGenerator.writeStringField("shortName", department.getShortName());

        if (department.getCurrentLeader() != null) {

            Member currentLeader = department.getCurrentLeader();
            jsonGenerator.writeObjectFieldStart("currentLeader");
            jsonGenerator.writeNumberField("id", currentLeader.getId());
            jsonGenerator.writeStringField("firstname", currentLeader.getFirstName());
            jsonGenerator.writeStringField("lastname", currentLeader.getLastName());
            jsonGenerator.writeEndObject();
        }
        if (department.getCurrentSecretary() != null) {
            Member secretary = department.getCurrentSecretary();
            jsonGenerator.writeObjectFieldStart("currentSecretary");
            jsonGenerator.writeNumberField("id", secretary.getId());
            jsonGenerator.writeStringField("firstname", secretary.getFirstName());
            jsonGenerator.writeStringField("lastname", secretary.getLastName());
            jsonGenerator.writeEndObject();
        }
        if (department.getSubjects() != null && !department.getSubjects().isEmpty()) {
            jsonGenerator.writeArrayFieldStart("subjects");

            for (Subject subject : department.getSubjects()) {
                jsonGenerator.writeStartObject();

                jsonGenerator.writeNumberField("id", subject.getId());
                jsonGenerator.writeStringField("name", subject.getName());
                jsonGenerator.writeNumberField("espb", subject.getEspb());

                jsonGenerator.writeEndObject();
            }

            jsonGenerator.writeEndArray();
        }

        if (department.getLeaderHistories() != null && !department.getLeaderHistories().isEmpty()) {
            jsonGenerator.writeArrayFieldStart("leaderHistory");
            for (LeaderHistory leaderHistory : department.getLeaderHistories()) {
                jsonGenerator.writeStartObject();
                jsonGenerator.writeNumberField("id", leaderHistory.getId());
                jsonGenerator.writeStringField("firstname", leaderHistory.getMember().getFirstName());
                jsonGenerator.writeStringField("lastname", leaderHistory.getMember().getLastName());
                jsonGenerator.writeStringField("startDate", leaderHistory.getStartDate().toString());
                jsonGenerator.writeStringField("endDate", leaderHistory.getEndDate() != null ? leaderHistory.getEndDate().toString() : "");

                jsonGenerator.writeEndObject();
            }
            jsonGenerator.writeEndArray();
        }
        if (department.getSecretaryHistories() != null && !department.getSecretaryHistories().isEmpty()) {
            jsonGenerator.writeArrayFieldStart("secretaryHistory");

            for (SecretaryHistory secretaryHistory : department.getSecretaryHistories()) {
                jsonGenerator.writeStartObject();

                jsonGenerator.writeNumberField("id", secretaryHistory.getId());
                jsonGenerator.writeStringField("firstname", secretaryHistory.getMember().getFirstName());
                jsonGenerator.writeStringField("lastname", secretaryHistory.getMember().getLastName());
                jsonGenerator.writeStringField("startDate", secretaryHistory.getStartDate().toString());
                if (secretaryHistory.getEndDate() != null) {
                    jsonGenerator.writeStringField("endDate", secretaryHistory.getEndDate().toString());
                }

                jsonGenerator.writeEndObject();
            }

            jsonGenerator.writeEndArray();
        }

        if (department.getMembers() != null && !department.getMembers().isEmpty()) {
            jsonGenerator.writeArrayFieldStart("members");

            for (Member member : department.getMembers()) {
                jsonGenerator.writeStartObject();

                jsonGenerator.writeNumberField("id", member.getId());
                jsonGenerator.writeStringField("firstname", member.getFirstName());
                jsonGenerator.writeStringField("lastname", member.getLastName());

                jsonGenerator.writeEndObject();

            }
            jsonGenerator.writeEndArray();
        }


        jsonGenerator.writeEndObject();

    }
}
