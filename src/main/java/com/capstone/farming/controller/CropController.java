package com.capstone.farming.controller;

import com.capstone.farming.model.Crop;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Controller
public class CropController {
    private final String apiKey = "20200519ARN2H1BVVDHK2Y5BBHDPZG"; // API Key
    private final String maxNumOfRows = "10"; // 가져올 작물의 최대 개수
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
    private String currentCrop = null; // 현재 작물 종류
    private NodeList nList = null; // 현재 작물의 정보가 저장될 리스트

    /*
    // 품종 데이터 엑셀 파일에 추출 시
    private int totalRow = 0;
    XSSFWorkbook workbook = new XSSFWorkbook();
    XSSFSheet sheet = workbook.createSheet();
    */

    // 식량 작물
    @RequestMapping("/Crop/FoodCropsInfo")
    public String foodCropsInfo(Model model, String cropName, @RequestParam(value="page", required=false) String page) {
        try {
            // 작물을 선택하지 않았을 경우(초기 접속일 경우)
            if(cropName == null && page == null) {
                model.addAttribute("cropName", "식량작물 선택");
                return "Crop/FoodCropsInfo";
            }
            // 작물을 선택한 경우
            if(cropName != null) {
                currentCrop = cropName;
                page = "1";
                nList = null;
                // 해당 작물 REST API 요청
                DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
                String url = "http://api.nongsaro.go.kr/service/varietyInfo/varietyList?apiKey=" + apiKey + "&numOfRows=1000&svcCodeNm=" + cropName; // 요청 URL
                Document doc = dBuilder.parse(url);
                doc.getDocumentElement().normalize();
                doc.getDocumentElement().getNodeName();
                nList = doc.getElementsByTagName(cropTag);
            } else { // 선택한 작물 내 페이지 변경인 경우
                cropName = currentCrop;
            }
            List<Crop> crop = new ArrayList<Crop>(); // 작물 정보들이 저장될 리스트
            for(int i=(Integer.parseInt(page)-1)*10; i < (Integer.parseInt(page)-1)*10 + 10; i++) {
                if(i >= nList.getLength()) {
                    break;
                }
                Node nNode = nList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    // 작물 정보 저장
                    crop.add(new Crop(i + 1,
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

            /*
            // 품종 데이터 엑셀 파일에 추출 시
            File path = new File("C:/Users/Mine/Desktop/crop_info.xlsx");
            FileOutputStream fileout = new FileOutputStream(path, true);
            for(int i=0; i < crop.size(); i++) {
                Row row = sheet.createRow(totalRow);
                Cell cell = row.createCell(0);
                cell.setCellValue(crop.get(i).getCntntsSj());
                cell = row.createCell(1);
                cell.setCellValue(crop.get(i).getUnbrngYear());
                cell = row.createCell(2);
                cell.setCellValue(crop.get(i).getUnbrngInsttInfo());
                cell = row.createCell(3);
                cell.setCellValue(crop.get(i).getMainChartrInfo());
                totalRow += 1;
            }
            System.out.println(totalRow);
            workbook.write(fileout);
            fileout.close();
            */

            // 페이지네이션
            int endPage = -1;
            if(nList.getLength() %  Integer.parseInt(maxNumOfRows) == 0) {
                endPage = nList.getLength() / Integer.parseInt(maxNumOfRows);
            } else {
                endPage = (nList.getLength() / Integer.parseInt(maxNumOfRows)) + 1;
            }
            model.addAttribute("currentPage", Integer.parseInt(page));
            model.addAttribute("prev", Integer.parseInt(page) - Integer.parseInt(page)%10 + 1 == 1 ? false : true);
            model.addAttribute("startPage", Integer.parseInt(page) - Integer.parseInt(page)%10 + 1);
            if(Integer.parseInt(page) + 10 - Integer.parseInt(page) % 10 <= endPage) {
                model.addAttribute("next", Integer.parseInt(page) + 10 - Integer.parseInt(page) % 10 >= nList.getLength() ? false : true);
                model.addAttribute("endPage", Integer.parseInt(page) + 10 - Integer.parseInt(page) % 10);
            } else {
                model.addAttribute("next", false);
                model.addAttribute("endPage", endPage);
            }
        } catch(Exception e) {
            log.error("An error occurred in the foodCropsInfo method", e);
        }
        return "Crop/FoodCropsInfo";
    }

    private static String getTagValue(String tag, Element eElement) {
        NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
        Node nValue = (Node) nlList.item(0);
        if(nValue == null) {
            return null;
        }
        return nValue.getNodeValue();
    }

    // 특용 작물
    @RequestMapping("/Crop/SpecialCropsInfo")
    public String specialCropsInfo(Model model, String cropName) {
        try {

        } catch(Exception e) {
            log.error("An error occurred in the specialCropsInfo method", e);
        }
        return "Crop/SpecialCropsInfo";
    }

    // 채소
    @RequestMapping("/Crop/VegetableInfo")
    public String vegetableInfo(Model model, String cropName) {
        try {

        } catch(Exception e) {
            log.error("An error occurred in the vegetableInfo method", e);
        }
        return "Crop/VegetableInfo";
    }

    // 과수
    @RequestMapping("/Crop/FruitInfo")
    public String fruitInfo(Model model, String cropName) {
        try {

        } catch(Exception e) {
            log.error("An error occurred in the fruitInfo method", e);
        }
        return "Crop/FruitInfo";
    }

    // 화훼
    @RequestMapping("/Crop/FloweringPlantInfo")
    public String floweringPlantInfo(Model model, String cropName) {
        try {

        } catch(Exception e) {
            log.error("An error occurred in the floweringPlantInfo method", e);
        }
        return "Crop/FloweringPlantInfo";
    }
}
