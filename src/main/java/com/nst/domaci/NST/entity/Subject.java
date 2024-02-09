package com.nst.domaci.NST.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nst.domaci.NST.serialization.SubjectSerializer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.Year;
import java.util.List;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@JsonSerialize(using = SubjectSerializer.class)
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Subject name is mandatory")
    @Size(min = 2, max = 50, message = "Subject name is between 2 and 50 characters long")
    private String name;
    private int espb;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "department_id")
    private Department department;
    @OneToMany(mappedBy = "subject", fetch = FetchType.LAZY)
    private List<Engagement> engagements;
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "subject", cascade = CascadeType.ALL)
    private Fund fund;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "subject", cascade = CascadeType.ALL)
    private List<LectureSchedule> lectureSchedules;

    public LectureSchedule getLectureScheduleCurrentYear() {
        int currentYear = Year.now().getValue();

        Optional<LectureSchedule> currentYearLectureSchedule = lectureSchedules.stream()
                .filter(schedule -> schedule.getYear() == currentYear)
                .findFirst();

        return currentYearLectureSchedule.orElse(null);
    }


}