package com.api.codigoqr.controller;

import com.api.codigoqr.Model.Constants.EndPoints;
import com.api.codigoqr.Model.Errors.CustomErrorResponse;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

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
    QrVCarService qrVCardService;

    @GetMapping(EndPoints.GENERATE_QR_CONTACT)
    @Operation(summary = "Generate a QR code for a contact (vCard)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "QR code for the contact (vCard) generated successfully", content = @Content(mediaType = "image/png")),
            @ApiResponse(responseCode = "400", description = "Incorrect or missing parameters", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Error generating the QR code for the contact (vCard)", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class))),
    })
    public ResponseEntity<JSONObject> getQrCodeContact(HttpServletResponse response,
                                                       @RequestParam(value = "name", required = true) String name,
                                                       @RequestParam(value = "email", required = true) String email,
                                                       @RequestParam(value = "phone", required = true) String phone,
                                                       @RequestParam(value = "website", required = true) String website,
                                                       @RequestParam(value = "city", required = true) String city,
                                                       @RequestParam(value = "country", required = true) String country,
                                                       @RequestParam(value = "organization", required = true) String organization,
                                                       @RequestParam(value = "jobTitle", required = true) String jobTitle,
                                                       @RequestParam(value = "street", required = true) String street) throws WriterException, IOException {

        String vCardData = qrVCardService.generateVCard(name, email, phone, website, city, country, organization, jobTitle, street);

        BufferedImage image = qrVCardService.generateQRCode(vCardData);

        ServletOutputStream outputStream = response.getOutputStream();
        ImageIO.write(image, "png", outputStream);
        outputStream.flush();
        outputStream.close();

        LOGGER.debug("We create a qr-code VCard Correctly");

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
