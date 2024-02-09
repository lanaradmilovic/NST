package com.nst.domaci.NST.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nst.domaci.NST.serialization.LectureScheduleSerializer;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
@Entity
@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "lecture_schedule")
@JsonSerialize(using = LectureScheduleSerializer.class)
public class LectureSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "subject_id")
    private Subject subject;
    private int year;
    @OneToMany(mappedBy = "lectureSchedule",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Lecture> lectures;
}
