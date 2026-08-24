package com.example.sbb.question;

import com.example.sbb.answer.AnswerForm;
import com.example.sbb.user.SiteUser;
import com.example.sbb.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
//Url 프릭픽스
@RequestMapping("/question")
@RequiredArgsConstructor
@Controller
public class QuestionController {

    private final QuestionService questionService;
    private final UserService userService;

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

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String questionCreate(QuestionForm questionForm) {
        return "question_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String questionCreate(@Valid QuestionForm questionForm,
                                 BindingResult bindingResult ,
                                 Principal principal) {

        if (bindingResult.hasErrors()) {
            return "question_form";
        }

        SiteUser siteUser = this.userService.getUser(principal.getName());
        this.questionService.create(questionForm.getSubject(), questionForm.getContent(), siteUser);
        return  "redirect:/question/list"; // 질문 저장후 질문목록으로 이동
    }
}
