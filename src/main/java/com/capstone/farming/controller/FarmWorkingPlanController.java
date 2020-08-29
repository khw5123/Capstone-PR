package com.capstone.farming.controller;

import com.capstone.farming.model.WorkingPlan;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
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

    @GetMapping("/plan")
    public String getPlan(Model model, String kidofcomdtySeCode, String cntntsNo, String cntntsNoName) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
        //수정필요
        String url = "";
        try {
            // XML Parsing
            dbFactoty = DocumentBuilderFactory.newInstance();
            dBuilder = dbFactoty.newDocumentBuilder();
            //수정필요
            url = "http://api.nongsaro.go.kr/service/farmWorkingPlanNew/workScheduleGrpList?apiKey="+apiKey; // 요청 URL
            Document doc = dBuilder.parse(url);
            doc.getDocumentElement().normalize();
            doc.getDocumentElement().getNodeName();

            HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
            NodeList items = doc.getElementsByTagName("item");
            for(int i = 0; i < items.getLength(); i++) {
                Node node = items.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) { // 해당 노드의 종류 판정(Element일 때)
                    Element ele = (Element) node;
                    hashMap.put(getTagValue("codeNm", ele), Integer.parseInt(getTagValue("kidofcomdtySeCode", ele)));
                }
            }
            String json = new ObjectMapper().writeValueAsString(hashMap);
            model.addAttribute("selectedCode",kidofcomdtySeCode);
            model.addAttribute("nameNcode", json);
        } catch(Exception e) {
            log.error("An error occurred in the FarmWorkingPlan method", e);
        }

        if (kidofcomdtySeCode != null && kidofcomdtySeCode != "" && (cntntsNo == null || cntntsNo == "")){
            try {
                dbFactoty = DocumentBuilderFactory.newInstance();
                dBuilder = dbFactoty.newDocumentBuilder();
                url = "http://api.nongsaro.go.kr/service/farmWorkingPlanNew/workScheduleLst?apiKey="+apiKey+"&kidofcomdtySeCode="+Integer.parseInt(kidofcomdtySeCode); // 요청 URL
                Document doc = dBuilder.parse(url);
                doc.getDocumentElement().normalize();
                doc.getDocumentElement().getNodeName();

                ArrayList<WorkingPlan> workingPlans = new ArrayList<>();
                NodeList items = doc.getElementsByTagName("item");
                for(int i = 0; i < items.getLength(); i++) {
                    Node node = items.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) { // 해당 노드의 종류 판정(Element일 때)
                        Element ele = (Element) node;
                        workingPlans.add(new WorkingPlan(Integer.parseInt(getTagValue("cntntsNo", ele)),
                                getTagValue("fileDownUrlInfo", ele),
                                getTagValue("fileName", ele)));
                    }
                }
                model.addAttribute("selectedCode",kidofcomdtySeCode);
                model.addAttribute("workingPlans",workingPlans);
            } catch(Exception e) {
                log.error("An error occurred in the FarmWorkingPlan method", e);
            }
        }

        if (cntntsNo != null && cntntsNo != ""){
            try {
                dbFactoty = DocumentBuilderFactory.newInstance();
                dBuilder = dbFactoty.newDocumentBuilder();
                url = "http://api.nongsaro.go.kr/service/farmWorkingPlanNew/workScheduleEraInfoLst?apiKey="+apiKey+"&cntntsNo="+Integer.parseInt(cntntsNo); // 요청 URL

                Document doc = dBuilder.parse(url);
                doc.getDocumentElement().normalize();
                doc.getDocumentElement().getNodeName();

                String htmlCn = "";
                NodeList item = doc.getElementsByTagName("htmlCn");
                for(int i = 0; i < item.getLength(); i++) {
                    Node node = item.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) { // 해당 노드의 종류 판정(Element일 때)
                        Element ele = (Element) node;
                        String nodeName = ele.getNodeName();
                        htmlCn = ele.getTextContent();
                    }
                }

                model.addAttribute("selectedCode", kidofcomdtySeCode);
                if (cntntsNoName != null && cntntsNoName != ""){
                    model.addAttribute("cntntsNoName", cntntsNoName);
                }
                model.addAttribute("htmlCn", htmlCn);
            } catch(Exception e) {
                log.error("An error occurred in the FarmWorkingPlan method", e);
            }
        }

        return "WorkingPlan/plan";
    }

    private static String getTagValue(String tag, Element eElement) {
        NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
        Node nValue = (Node) nlList.item(0);
        if(nValue == null) {
            return null;
        }
        return nValue.getNodeValue();
    }

}
