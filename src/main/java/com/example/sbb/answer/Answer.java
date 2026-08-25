package com.example.sbb.answer;

import com.example.sbb.question.Question;
import com.example.sbb.user.SiteUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Answer {
    @Id   // answer entity --> pk id 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 신규 record가 insert 될때 자동 id값을 1씩 증가
    private Integer id;

    @Column(columnDefinition = "Text") // field 타입을 text로 지정
    private String content;

    private LocalDateTime createDate;

    @ManyToOne
    private Question question;

    @ManyToOne
    private SiteUser author;

    private LocalDateTime modifyDate;
}
