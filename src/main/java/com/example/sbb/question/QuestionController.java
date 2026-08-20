package com.example.sbb.question;

import com.example.sbb.answer.AnswerForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//Url 프릭픽스
@RequestMapping("/question")
@RequiredArgsConstructor
@Controller
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/list")
    public String list(Model model,
                       @RequestParam(value = "page", defaultValue="0") int page) {
//        List<Question> questionList = this.questionService.getList();
        Page<Question> paging = this.questionService.getList(page);
//        model.addAttribute("questionList", questionList);
        model.addAttribute("paging", paging);
        return "question_list";  // question_list.html  리턴 됨
    }

    @GetMapping("/detail/{id}")
    // @PathVariable
    public String detail(Model model,
                         @PathVariable("id") Integer id,
                         AnswerForm answerForm) {
        Question question = this.questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "question_detail";
    }

    @GetMapping("/create")
    public String questionCreate(QuestionForm questionForm) {
        return "question_form";
    }

    @PostMapping("/create")
    public String questionCreate(@Valid QuestionForm questionForm,
                                 BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "question_form";
        }

        // ToDo: 질문을 저장한다 --> 수정시 아래줄 처럼 작성
        this.questionService.create(questionForm.getSubject(), questionForm.getContent());
        return  "redirect:/question/list"; // 질문 저장후 질문목록으로 이동
    }
}
