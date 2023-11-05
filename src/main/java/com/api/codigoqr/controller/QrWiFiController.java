package com.api.codigoqr.controller;

import com.api.codigoqr.Model.Constants.EndPoints;
import com.api.codigoqr.Model.Errors.CustomErrorResponse;
import com.api.codigoqr.services.QrWiFiService;
import com.google.zxing.WriterException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
public class QrWiFiController {
    private static final Logger LOGGER = LoggerFactory.getLogger(QrWiFiController.class);

    @Autowired
    QrWiFiService qrWifiService;

    @GetMapping(EndPoints.GENERATE_QR_WIFI)
    @Operation(summary = "Generate a QR code for a Wi-Fi network")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "QR code for the Wi-Fi network generated successfully", content = @Content(mediaType = "image/png")),
            @ApiResponse(responseCode = "400", description = "Incorrect or missing parameters", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Error generating the QR code for the Wi-Fi network", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class))),
    })
    public ResponseEntity<JSONObject> getQrCodeWifi(HttpServletResponse response,
                                                    @RequestParam(value = "ssid", required = true) String ssid,
                                                    @RequestParam(value = "password", required = true) String password,
                                                    @RequestParam(value = "securityType", required = true) String securityType) throws WriterException, IOException {

        BufferedImage image = qrWifiService.generateQRCode(ssid, password, securityType);

        ServletOutputStream outputStream = response.getOutputStream();
        ImageIO.write(image, "png", outputStream);
        outputStream.flush();
        outputStream.close();

        LOGGER.debug("We create a QR code for a Wi-Fi network correctly");

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
