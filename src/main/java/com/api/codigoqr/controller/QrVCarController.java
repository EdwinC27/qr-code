package com.api.codigoqr.controller;

import com.api.codigoqr.Model.Constants.EndPoints;
import com.api.codigoqr.services.QrURLService;
import com.api.codigoqr.services.QrVCarService;
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
public class QrVCarController {
    private static final Logger LOGGER = LoggerFactory.getLogger(QrVCarController.class);

    @Autowired
    QrVCarService qrVCarService;

    @GetMapping(EndPoints.GENERATE_QR_CONTACT)
    public ResponseEntity<JSONObject> getQrCodeContact(HttpServletResponse response, @RequestParam(value = "name") String name,
                                                       @RequestParam(value = "email") String email,
                                                       @RequestParam(value = "phone") String phone,
                                                       @RequestParam(value = "website") String website,
                                                       @RequestParam(value = "city") String city,
                                                       @RequestParam(value = "country") String country) throws WriterException, IOException {

        String vCardData = qrVCarService.generateVCard(name, email, phone, website, city, country);

        BufferedImage image = qrVCarService.generateQRCode(vCardData);

        ServletOutputStream outputStream = response.getOutputStream();
        ImageIO.write(image, "png", outputStream);
        outputStream.flush();
        outputStream.close();

        LOGGER.debug("We create a qr-code VCar Correctly");

        return new ResponseEntity<>(HttpStatus.OK);
    }

}