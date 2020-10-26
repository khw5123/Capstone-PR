package com.capstone.farming.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class PurchaseStatisticsController {

    // 연간추이
    @RequestMapping("/Statistics/PurchaseStatisticsYearly")
    public String purchaseStatisticsYearly(Model model, String cropName) {
        try {
            // 작물을 선택하지 않았을 경우(초기 접속)
            if(cropName == null) {
                model.addAttribute("cropName", "식량작물 선택");
                return "Statistics/PurchaseStatisticsYearly";
            }
            model.addAttribute("cropName", cropName);
        } catch(Exception e) {
            log.error("", e);
        }
        return "Statistics/PurchaseStatisticsYearly";
    }

    // 월간추이
    @RequestMapping("/Statistics/PurchaseStatisticsMonthly")
    public String purchaseStatisticsMonthly(Model model, String cropName) {
        try {
            // 작물을 선택하지 않았을 경우(초기 접속)
            if(cropName == null) {
                model.addAttribute("cropName", "식량작물 선택");
                return "Statistics/PurchaseStatisticsMonthly";
            }
            model.addAttribute("cropName", cropName);
        } catch(Exception e) {
            log.error("", e);
        }
        return "Statistics/PurchaseStatisticsMonthly";
    }
}
