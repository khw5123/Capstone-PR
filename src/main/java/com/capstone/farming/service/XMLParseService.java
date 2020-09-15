package com.capstone.farming.service;

import com.capstone.farming.model.WorkingPlan;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
public class XMLParseService {

    private final String apiKey = "20200519ARN2H1BVVDHK2Y5BBHDPZG"; // API Key
    private DocumentBuilderFactory dbFactoty;
    private DocumentBuilder dBuilder;
    private String url;

    public String getAreaCode() {
        String json = "";
        try {
            // XML Parsing
            dbFactoty = DocumentBuilderFactory.newInstance();
            dBuilder = dbFactoty.newDocumentBuilder();

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

            json = new ObjectMapper().writeValueAsString(hashMap);
        } catch(Exception e) {
            log.error("An error occurred in the FarmWorkingPlan method", e);
        }
        return json;
    }

    private static String getTagValue(String tag, Element eElement) {
        NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
        Node nValue = (Node) nlList.item(0);
        if(nValue == null) {
            return null;
        }
        return nValue.getNodeValue();
    }

    public List getPlanCrops (String kidofcomdtySeCode) {
        ArrayList<WorkingPlan> workingPlans = new ArrayList<>();

        try {
            dbFactoty = DocumentBuilderFactory.newInstance();
            dBuilder = dbFactoty.newDocumentBuilder();
            url = "http://api.nongsaro.go.kr/service/farmWorkingPlanNew/workScheduleLst?apiKey="+apiKey+"&kidofcomdtySeCode="+Integer.parseInt(kidofcomdtySeCode); // 요청 URL
            Document doc = dBuilder.parse(url);
            doc.getDocumentElement().normalize();
            doc.getDocumentElement().getNodeName();

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

        } catch(Exception e) {
            log.error("An error occurred in the FarmWorkingPlan method", e);
        }
        return workingPlans;
    }

    public String getPlanHTML(String cntntsNo){
        String htmlCn = "";
        try {
            dbFactoty = DocumentBuilderFactory.newInstance();
            dBuilder = dbFactoty.newDocumentBuilder();
            url = "http://api.nongsaro.go.kr/service/farmWorkingPlanNew/workScheduleEraInfoLst?apiKey="+apiKey+"&cntntsNo="+Integer.parseInt(cntntsNo); // 요청 URL

            Document doc = dBuilder.parse(url);
            doc.getDocumentElement().normalize();
            doc.getDocumentElement().getNodeName();

            NodeList item = doc.getElementsByTagName("htmlCn");
            for(int i = 0; i < item.getLength(); i++) {
                Node node = item.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) { // 해당 노드의 종류 판정(Element일 때)
                    Element ele = (Element) node;
                    String nodeName = ele.getNodeName();
                    htmlCn = ele.getTextContent();
                }
            }

        } catch(Exception e) {
            log.error("An error occurred in the FarmWorkingPlan method", e);
        }
        return htmlCn;
    }

}
