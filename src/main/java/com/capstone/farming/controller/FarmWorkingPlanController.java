package com.capstone.farming.controller;

import com.capstone.farming.model.WorkingPlan;
import com.capstone.farming.service.XMLParseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

@Slf4j
@Controller
public class FarmWorkingPlanController {
    private final String apiKey = "20200519ARN2H1BVVDHK2Y5BBHDPZG"; // API Key

    @Autowired
    private XMLParseService xmlParseService;

    @GetMapping("/plan")
    public String getPlan(Model model, String kidofcomdtySeCode, String cntntsNo, String cntntsNoName){
            model.addAttribute("selectedCode",kidofcomdtySeCode);
            model.addAttribute("nameNcode", xmlParseService.getAreaCode());

        if (kidofcomdtySeCode != null && kidofcomdtySeCode != "" && (cntntsNo == null || cntntsNo == "")){

            model.addAttribute("selectedCode",kidofcomdtySeCode);
            model.addAttribute("workingPlans", xmlParseService.getPlanCrops(kidofcomdtySeCode));

        }

        if (cntntsNo != null && cntntsNo != ""){
                model.addAttribute("selectedCode", kidofcomdtySeCode);
                if (cntntsNoName != null && cntntsNoName != ""){
                    model.addAttribute("cntntsNoName", cntntsNoName);
                }
                model.addAttribute("htmlCn", xmlParseService.getPlanHTML(cntntsNo));
        }

        return "WorkingPlan/plan";
    }

}
