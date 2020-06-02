package com.capstone.farming.controller;

import com.capstone.farming.model.Crop;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class CropController {
    private final String apiKey = "20200519ARN2H1BVVDHK2Y5BBHDPZG"; // API Key
    private final String maxNumOfRows = "20"; // 가져올 작물의 최대 개수
    private final String cropTag = "item"; // 작물 루트 태그
    public static final HashMap<String, String> cropTags = new HashMap<String, String>(); // 작물 태그 내 속성에 따른 태그들
    static {
        cropTags.put("품종명", "cntntsSj");
        cropTags.put("육성년도", "unbrngYear");
        cropTags.put("육성기관", "unbrngInsttInfo");
        cropTags.put("주요특성", "mainChartrInfo");
        cropTags.put("이미지파일", "imgFileLinkOriginal");
        cropTags.put("정보파일", "atchFileLink");
    }

    @RequestMapping("/CropInfo")
    public String cropPage(Model model, String cropName) {
        try {
            // 작물을 선택하지 않았을 경우(초기 접속)
            if(cropName == null) {
                model.addAttribute("cropName", "식량작물 선택");
                return "CropInfo";
            }
            // XML Parsing
            DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
            String url = "http://api.nongsaro.go.kr/service/varietyInfo/varietyList?apiKey=" + apiKey +
                                "&numOfRows=" + maxNumOfRows + "&svcCodeNm=" + cropName; // 요청 URL
            Document doc = dBuilder.parse(url);
            doc.getDocumentElement().normalize();
            doc.getDocumentElement().getNodeName();
            NodeList nList = doc.getElementsByTagName(cropTag);
            List<Crop> crop = new ArrayList<Crop>(); // 작물 정보들이 저장될 리스트
            for(int i=0; i < nList.getLength(); i++) {
                if(i > Integer.parseInt(maxNumOfRows)) {
                    break;
                }
                Node nNode = nList.item(i);
                if(nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    // 작물 정보 저장
                    crop.add(new Crop(i+1,
                                      getTagValue(cropTags.get("품종명"), eElement),
                                      getTagValue(cropTags.get("육성년도"), eElement),
                                      getTagValue(cropTags.get("육성기관"), eElement),
                                      getTagValue(cropTags.get("주요특성"), eElement),
                                      getTagValue(cropTags.get("이미지파일"), eElement),
                                      getTagValue(cropTags.get("정보파일"), eElement)
                    ));
                }
            }
            model.addAttribute("cropName", cropName);
            model.addAttribute("cropList", crop);
        } catch(Exception e) {
            System.out.println(e);
        }
        return "CropInfo";
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
