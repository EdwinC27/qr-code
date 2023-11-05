package com.api.codigoqr.services;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;

import java.awt.image.*;

@Service
public class QrLocationService {

    private String generateMailToURI(String latitude, String longitude) {
        return "geo:" + latitude + "," + longitude;
    }

    public BufferedImage generateQRCode(String latitude, String longitude) throws WriterException {
        String geoURI = generateMailToURI(latitude, longitude);

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(geoURI, BarcodeFormat.QR_CODE, 250, 250);

        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }



}
