package com.example.sbb;

import com.example.sbb.question.Question;
import com.example.sbb.question.QuestionRepository;
import com.example.sbb.question.QuestionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class SbbApplicationTests {

    // DI(의존성 주입) --> questionRepository 라는 객체를 스프링 만들어서 제공해줌
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuestionService questionService;


    @Test
    void testJpa() {

        Question q1 = new Question();
        q1.setSubject("sbb가 무엇인가요?");
        q1.setContent("sbb에 대해서 알고 싶습니다.");
        q1.setCreateDate(LocalDateTime.now()); // static 메서드 호출 --> class명.메서드명
        this.questionRepository.save(q1);

        Question q2 = new Question();
        q2.setSubject("스프링부트 모델 질문입니다.");
        q2.setContent("id는 자동으로 생성되나요?");
        q2.setCreateDate(LocalDateTime.now()); // static 메서드 호출 --> class명.메서드명
        this.questionRepository.save(q2);
    }

    @Test
    void testJpa_02() {
        List<Question> all = this.questionRepository.findAll();  // question table의 record 전체를 List 타입, all 저장
        assertEquals(4, all.size());  // assertEquals(기대값, 실제값 즉 db의 값)

        Question q = all.get(0);
        assertEquals("sbb가 무엇인가요?", q.getSubject());

    }

    @Test
    void testJpa_03() {
        for (int i = 1 ; i <= 300 ; i++) {
            String subject = String.format("테스트 데이터입니다: [%03d", i);
            String content = "내용 없음";
            this.questionService.create(subject, content);
        }
    }
}