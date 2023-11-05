package com.api.codigoqr.services;

import com.api.codigoqr.Model.Errors.InvalidDateRangeException;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.property.*;
import org.springframework.stereotype.Service;

import net.fortuna.ical4j.model.component.VEvent;

import java.awt.image.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Version;

@Service
public class QrEventService {

    public String generateCalendarEvent(String eventName, String location, String startDate, String endDate) throws IOException, InvalidDateRangeException {
        // Convertir las cadenas de fecha y hora a objetos LocalDateTime
        LocalDateTime startLocalDateTime = LocalDateTime.parse(startDate);
        LocalDateTime endLocalDateTime = LocalDateTime.parse(endDate);

        // Convertir LocalDateTime a Date
        Date startDateDate = Date.from(startLocalDateTime.toInstant(ZoneOffset.UTC));
        Date endDateDate = Date.from(endLocalDateTime.toInstant(ZoneOffset.UTC));

        if (endDateDate.before(startDateDate)) {
            throw new InvalidDateRangeException("The end time or date of the event is earlier than the start time or date.");
        }

        DateTime start = new DateTime(startDateDate);
        DateTime end = new DateTime(endDateDate);

        VEvent event = new VEvent();

        event.getProperties().add(new Uid("1"));
        event.getProperties().add(new DtStart(start));
        event.getProperties().add(new DtEnd(end));
        event.getProperties().add(new Location(location));
        event.getProperties().add(new Summary(eventName));

        Calendar calendar = new Calendar();
        calendar.getProperties().add(new ProdId("-//Events Calendar//iCal4j 1.0//EN"));
        calendar.getProperties().add(Version.VERSION_2_0);
        calendar.getComponents().add(event); // Agregar el evento al calendario

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        CalendarOutputter outputter = new CalendarOutputter();
        outputter.output(calendar, out); // Usar el calendario, no el evento

        return out.toString();
    }

    public BufferedImage generateQRCode(String data) throws WriterException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, 250, 250, hints);

        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }
}
