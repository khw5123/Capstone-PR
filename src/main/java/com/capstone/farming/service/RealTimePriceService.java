package com.capstone.farming.service;

import com.capstone.farming.dao.RealTimePriceDAO;
import com.capstone.farming.model.RealTimePriceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
//@Scope(value = "prototype", proxyMode= ScopedProxyMode.TARGET_CLASS)
public class RealTimePriceService {

    private RealTimePriceDAO realTimePriceDAO;

    @Autowired
    public RealTimePriceService(RealTimePriceDAO realTimePriceDAO) {
        this.realTimePriceDAO = realTimePriceDAO;
    }

    public RealTimePriceResponse getRealTimePriceList(int numOfRows, int pageNo,
                                                      String delngDe, String prdlstCd,
                                                      String spciesCd, String whsalCd)
                                                                throws IOException, IllegalArgumentException {

        return realTimePriceDAO.getRealTimePriceList(numOfRows, pageNo,
                                                            delngDe, prdlstCd,
                                                            spciesCd, whsalCd);
    }
}
