package com.nst.domaci.NST.entity;
import com.nst.domaci.NST.entity.form.TeachingForm;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lecture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Enumerated(EnumType.STRING)
    private TeachingForm form;
    @ManyToOne(fetch = FetchType.EAGER)
    private Engagement engagement;
    private LocalDateTime dateTime;
    private String description;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "lecture_schedule_id")
    private LectureSchedule lectureSchedule;
}
