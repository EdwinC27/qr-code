package com.api.codigoqr.controller;

import com.api.codigoqr.Model.Constants.EndPoints;
import com.api.codigoqr.Model.Errors.ICalDataNullException;
import com.api.codigoqr.Model.Errors.InvalidDateRangeException;
import com.api.codigoqr.services.QrEventService;
import com.google.zxing.WriterException;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.*;
import java.io.IOException;

@RestController
@RequestMapping(EndPoints.BASE_URL)
public class QrEventController {
    private static final Logger LOGGER = LoggerFactory.getLogger(QrEventController.class);

    @Autowired
    QrEventService qrEventService;

    @GetMapping(EndPoints.GENERATE_QR_EVENT)
    public ResponseEntity<JSONObject> generateEventQRCode(HttpServletResponse response,
                                                          @RequestParam(value = "eventName") String eventName,
                                                          @RequestParam(value = "location") String location,
                                                          @RequestParam(value = "startDate") String startDate,
                                                          @RequestParam(value = "endDate") String endDate) throws WriterException, IOException, InvalidDateRangeException, ICalDataNullException {

        String iCalData = qrEventService.generateCalendarEvent(eventName, location, startDate, endDate);


        if (iCalData == null) {
            throw new ICalDataNullException("iCalData is null");
        }

        BufferedImage image = qrEventService.generateQRCode(iCalData);

        ServletOutputStream outputStream = response.getOutputStream();
        ImageIO.write(image, "png", outputStream);
        outputStream.flush();
        outputStream.close();

        LOGGER.debug("We create a qr-code Event Correctly");

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
