package com.api.codigoqr.services;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.springframework.stereotype.Service;

import java.awt.image.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QrVCarService {

    public String generateVCard(String name, String email, String phone, String website, String city, String country, String organization, String jobTitle, String street) {
        List<String> lines = new ArrayList<>();
        lines.add("BEGIN:VCARD");
        lines.add("VERSION:3.0");
        lines.add("FN:" + name);
        lines.add("ORG:" + organization);
        lines.add("TITLE:" + jobTitle);
        lines.add("EMAIL:" + email);
        lines.add("TEL:" + phone);
        lines.add("URL:" + website);
        lines.add("ADR;TYPE=HOME:;;" + street + ";" + city + ";" + country);
        lines.add("END:VCARD");

        // Combine the lines into a single chain.
        StringBuilder vCardBuilder = new StringBuilder();
        for (String line : lines) {
            vCardBuilder.append(line);
            vCardBuilder.append("\n");
        }

        return vCardBuilder.toString();
    }

    public BufferedImage generateQRCode(String data) throws WriterException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, 250, 250, hints);

        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }
}
