package com.nst.domaci.NST.entity;
import com.nst.domaci.NST.entity.form.TeachingForm;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lecture {
    @Id
    @GeneratedValue
    private long id;
    private TeachingForm form;
    @ManyToOne(fetch = FetchType.EAGER)
    private Engagement engagement;
    private LocalDateTime dateTime;
    private String description;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "lecture_schedule_id")
    private LectureSchedule lectureSchedule;
}