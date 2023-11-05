package com.api.codigoqr.controller;

import com.api.codigoqr.Model.Constants.EndPoints;
import com.api.codigoqr.services.QrTextService;
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
public class QrTextController {
    private static final Logger LOGGER = LoggerFactory.getLogger(QrTextController.class);

    @Autowired
    QrTextService qrTextService;

    @GetMapping(EndPoints.GENERATE_QR_TEXT)
    public ResponseEntity<JSONObject> getQrCodeText(HttpServletResponse response, @RequestParam(value = "document") String text) throws WriterException, IOException {

        BufferedImage image = qrTextService.generateQRCodeText(text);

        ServletOutputStream outputStream = response.getOutputStream();
        ImageIO.write(image, "png", outputStream);
        outputStream.flush();
        outputStream.close();

        LOGGER.debug("We create a qr-code Text Correctly");

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
