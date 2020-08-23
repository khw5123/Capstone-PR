package com.capstone.farming.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class EducationController {

    // 영농 교육(농사로)
    @RequestMapping("/Education/Education")
    public String education(Model model) {
        return "Education/Education";
    }

    // 영농 교육(귀농귀촌종합센터)
    @RequestMapping("/Education/Education2")
    public String education2(Model model) {
        return "Education/Education2";
    }
}
