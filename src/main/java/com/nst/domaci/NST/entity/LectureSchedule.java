package com.nst.domaci.NST.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nst.domaci.NST.serialization.LectureScheduleSerializer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;
@Entity
@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "lecture_schedule")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@JsonSerialize(using = LectureScheduleSerializer.class)
public class LectureSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "subject_id")
    @NotNull
    private Subject subject;
    @NotNull
    @JoinColumn(name = "schedule_year")
    private int scheduleYear;
    @OneToMany(mappedBy = "lectureSchedule",cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<Lecture> lectures;
}
