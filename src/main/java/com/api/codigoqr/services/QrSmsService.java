package com.api.codigoqr.services;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;

import java.awt.image.*;

@Service
public class QrSmsService {

    private String generateMailToURI(String phoneNumber, String message) {
        return "sms:" + phoneNumber + "?body=" + message;
    }

    public BufferedImage generateQRCodeFromURI(String phoneNumber, String message) throws WriterException {
        String uri = generateMailToURI(phoneNumber, message);

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(uri, BarcodeFormat.QR_CODE, 250, 250);

        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

}
