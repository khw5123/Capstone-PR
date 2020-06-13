package com.capstone.farming.controller;

import com.capstone.farming.model.RealTimePriceResponse;
import com.capstone.farming.service.RealTimePriceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;

@Slf4j
@Controller
@RequestMapping("/Price")
public class RealTimePriceController {

    private RealTimePriceService realTimePriceService;

    @Autowired
    public RealTimePriceController(RealTimePriceService realTimePriceService) {
        this.realTimePriceService = realTimePriceService;
    }

    @RequestMapping(value = "RealTimePrice")
    public String realTimePrice(Model model) {

        return "Price/RealTimePrice";
    }

    @ResponseBody
    @RequestMapping(value = "RealTimePrice/Data")
    public HashMap<String, Object> realTimePrice(Model model,
                                                 @RequestParam(value = "numOfRows", required = false, defaultValue = "10") int numOfRows,
                                                 @RequestParam(value = "pageNo", required = false, defaultValue = "1") int pageNo,
                                                 @RequestParam(value = "delngDe", required = false) String delngDe,
                                                 @RequestParam(value = "prdlstCd", required = false) String prdlstCd,
                                                 @RequestParam(value = "spciesCd", required = false) String spciesCd,
                                                 @RequestParam(value = "whsalCd", required = false) String whsalCd)
                                                                        throws IOException, IllegalArgumentException {

        HashMap<String, Object> map = new HashMap<String, Object>();
        String message = null;

        try {

            RealTimePriceResponse realTimePriceResponse = realTimePriceService.getRealTimePriceList(numOfRows, pageNo,
                                                                                delngDe, prdlstCd,
                                                                                spciesCd, whsalCd);

            int totalPage = (realTimePriceResponse.getTotalCount() - 1) / numOfRows + 1;

            if(realTimePriceResponse.getTotalCount() == 0) message = "해당 거래 내역이 없습니다.";

            map.put("realTimePriceList", realTimePriceResponse.getRealTimePriceList());
            map.put("pageNo", pageNo);
            map.put("totalPage", totalPage);

        } catch (IllegalArgumentException e) {
            message = "경락일자, 부류, 품목은 필수입니다.";
        }

        map.put("message", message);

        return map;
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "입력 변수가 잘못되었습니다.")
    @ExceptionHandler(IllegalArgumentException.class)
    public void handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("==================================================================================");
        log.error("handle IllegalArgumentException\n", e);
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "I/O 에러 발생")
    @ExceptionHandler(IOException.class)
    public void handleIOException(IOException e) {
        log.error("==================================================================================");
        log.error("handle IOException\n", e);
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "오류 발생")
    @ExceptionHandler(Exception.class)
    public void handleException(Exception e) {
        log.error("==================================================================================");
        log.error("handle Exception\n", e);
    }
}
