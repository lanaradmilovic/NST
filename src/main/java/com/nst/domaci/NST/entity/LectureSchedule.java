package com.nst.domaci.NST.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "lecture_schedule")
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
