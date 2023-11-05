package com.api.codigoqr.services;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;

@Service
public class QrEmailService {

    private String generateMailToURI(String email, String subject, String message) {
        return "mailto:" + email + "?subject=" + subject + "&body=" + message;
    }

    public BufferedImage generateMailToQRCode(String email, String subject, String message) throws WriterException {
        String mailtoURI = generateMailToURI(email, subject, message);
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(mailtoURI, BarcodeFormat.QR_CODE, 250, 250);

        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }


}
