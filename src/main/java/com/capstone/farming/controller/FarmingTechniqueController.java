package com.capstone.farming.controller;

import com.capstone.farming.task.APIExplorer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/FarmingTechnique")
public class FarmingTechniqueController {

    @Value("${custom.api.farming-technique-u-r-l}")
    private String farmingTechniqueURL;

    @Value("${custom.api.farming-technique-a-p-i-key}")
    private String farmingTechniqueAPIKey;

    @RequestMapping("")
    public String farmingTechnique(Model model) {

        return "FarmingTechnique/FarmingTechnique";
    }

    @ResponseBody
    @RequestMapping("MainCategoryList")
    public String mainCategoryList(Model model) throws IOException {

        APIExplorer apiExplorer = new APIExplorer();
        String xml = apiExplorer.request(farmingTechniqueURL + "/mainCategoryList?apiKey=" + farmingTechniqueAPIKey);

//        XmlMapper xmlMapper = new XmlMapper();
////        JsonNode node = xmlMapper.readTree(response);
//        List entries = xmlMapper.readValue(response, List.class);
//
//        ObjectMapper jsonMapper = new ObjectMapper();
//        String json = jsonMapper.writeValueAsString(entries);

        JSONObject jObject = XML.toJSONObject(xml);
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
//        Object json = mapper.readValue(jObject.toString(), Object.class);
        JsonNode node = mapper.readTree(jObject.toString());
        String output = mapper.writeValueAsString(node.at("/response/body/items/item"));

        return output;
    }

    @ResponseBody
    @RequestMapping("MiddleCategoryList")
    public String middleCategoryList(Model model,
                                     @RequestParam(value = "mainCategoryCode", required = false) String mainCategoryCode)
                                                            throws IOException {

        APIExplorer apiExplorer = new APIExplorer();
        String xml = apiExplorer.request(farmingTechniqueURL + "/middleCategoryList?apiKey=" + farmingTechniqueAPIKey
                                                                + "&mainCategoryCode=" + mainCategoryCode);

        JSONObject jObject = XML.toJSONObject(xml);
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        JsonNode node = mapper.readTree(jObject.toString());
        String output = mapper.writeValueAsString(node.at("/response/body/items/item"));

        return output;
    }

    @ResponseBody
    @RequestMapping("SubCategoryList")
    public String subCategoryList(Model model,
                                     @RequestParam(value = "middleCategoryCode", required = false) String middleCategoryCode)
            throws IOException {

        APIExplorer apiExplorer = new APIExplorer();
        String xml = apiExplorer.request(farmingTechniqueURL + "/subCategoryList?apiKey=" + farmingTechniqueAPIKey
                + "&middleCategoryCode=" + middleCategoryCode);

        JSONObject jObject = XML.toJSONObject(xml);
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        JsonNode node = mapper.readTree(jObject.toString());
        String output = mapper.writeValueAsString(node.at("/response/body/items/item"));

        return output;
    }

    @ResponseBody
    @RequestMapping("MainTechList")
    public String mainTechList(Model model,
                                  @RequestParam(value = "subCategoryCode", required = false) String subCategoryCode)
            throws IOException {

        APIExplorer apiExplorer = new APIExplorer();
        String xml = apiExplorer.request(farmingTechniqueURL + "/mainTechList?apiKey=" + farmingTechniqueAPIKey
                + "&subCategoryCode=" + subCategoryCode);

        JSONObject jObject = XML.toJSONObject(xml);
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        JsonNode node = mapper.readTree(jObject.toString());
        String output = mapper.writeValueAsString(node.at("/response/body/items/item"));

        return output;
    }

    @ResponseBody
    @RequestMapping("SubTechList")
    public String subTechList(Model model,
                                  @RequestParam(value = "subCategoryCode", required = false) String subCategoryCode,
                                  @RequestParam(value = "mainTechCode", required = false) String mainTechCode)
            throws IOException {

        APIExplorer apiExplorer = new APIExplorer();
        String xml = apiExplorer.request(farmingTechniqueURL + "/subTechList?apiKey=" + farmingTechniqueAPIKey
                + "&subCategoryCode=" + subCategoryCode
                + "&mainTechCode=" + mainTechCode);

        JSONObject jObject = XML.toJSONObject(xml);
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        JsonNode node = mapper.readTree(jObject.toString());
        String output = mapper.writeValueAsString(node.at("/response/body/items/item"));

        return output;
    }

    @ResponseBody
    @RequestMapping("TechInfoList")
    public String techInfoList(Model model,
                                  @RequestParam(value = "subCategoryCode", required = false) String subCategoryCode,
                                  @RequestParam(value = "subTechCode", required = false) String subTechCode)
            throws IOException {

        APIExplorer apiExplorer = new APIExplorer();
        String xml = apiExplorer.request(farmingTechniqueURL + "/techInfoList?apiKey=" + farmingTechniqueAPIKey
                + "&subCategoryCode=" + subCategoryCode
                + "&subTechCode=" + subTechCode);

        JSONObject jObject = XML.toJSONObject(xml);
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        JsonNode node = mapper.readTree(jObject.toString());
        String output = mapper.writeValueAsString(node.at("/response/body/items/item"));

        return output;
    }

    @ResponseBody
    @RequestMapping("VideoList")
    public String videoList(Model model,
                                  @RequestParam(value = "subCategoryCode", required = false) String subCategoryCode)
            throws IOException {

        APIExplorer apiExplorer = new APIExplorer();
        String xml = apiExplorer.request(farmingTechniqueURL + "/videoList?apiKey=" + farmingTechniqueAPIKey
                + "&subCategoryCode=" + subCategoryCode);

        JSONObject jObject = XML.toJSONObject(xml);
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        JsonNode node = mapper.readTree(jObject.toString());
        String output = mapper.writeValueAsString(node.at("/response/body/items/item"));

        return output;
    }

    @ResponseBody
    @RequestMapping("VarietyList")
    public String varietyList(Model model,
                                  @RequestParam(value = "subCategoryCode", required = false) String subCategoryCode)
            throws IOException {

        APIExplorer apiExplorer = new APIExplorer();
        String xml = apiExplorer.request(farmingTechniqueURL + "/varietyList?apiKey=" + farmingTechniqueAPIKey
                + "&subCategoryCode=" + subCategoryCode);

        JSONObject jObject = XML.toJSONObject(xml);
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        JsonNode node = mapper.readTree(jObject.toString());
        String output = mapper.writeValueAsString(node.at("/response/body/items/item"));

        return output;
    }


}
