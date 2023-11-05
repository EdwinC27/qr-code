package com.api.codigoqr.services;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;

import java.awt.image.*;

@Service
public class QrWiFiService {

    private String generateWifiConfigURI(String ssid, String password, String securityType) {
        String security = "nopass";

        if (securityType.equals("WPA")) {
            security = "WPA";
        } else if (securityType.equals("WEP")) {
            security = "WEP";
        }

        return "WIFI:S:" + ssid + ";T:" + security + ";P:" + password + ";;";
    }

    public BufferedImage generateQRCode(String ssid, String password, String securityType) throws WriterException {
        String wifiURI = generateWifiConfigURI(ssid, password, securityType);

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(wifiURI, BarcodeFormat.QR_CODE, 250, 250);

        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

}
