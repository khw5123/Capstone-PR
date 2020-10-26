package com.capstone.farming.task;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;


@RunWith(SpringRunner.class)
@SpringBootTest()
public class XlsprocessingTests {

    @Autowired
    XlsProcessing xlsProcessing;

    /*
    @Test
    public void testXls() throws InvalidFormatException, IOException {
        xlsProcessing.insertMongo();
    }
    */
}
