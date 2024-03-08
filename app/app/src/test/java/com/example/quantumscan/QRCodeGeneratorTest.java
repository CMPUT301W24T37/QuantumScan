package com.example.quantumscan;

import static org.junit.Assert.assertNotNull;

import android.graphics.Bitmap;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class QRCodeGeneratorTest {
    @Test
    public void testGenerateQRCodeBitmap() {
        // Generated QRCode
        Bitmap bitmapQRcode = QRCodeGenerator.generateQRCodeBitmap("eventID",250,250);
        // Test if generated successful
        assertNotNull("QR code bitmap should not be null", bitmapQRcode);

    }

    @Test
    public void testbitmapToBase64() {
        // Generated QRCode
        Bitmap bitmapQRcode = QRCodeGenerator.generateQRCodeBitmap("eventID",250,250);
        String base64String = QRCodeGenerator.bitmapToBase64(bitmapQRcode);
        // Convert to base64
        assertNotNull("QR code bitmap should not be null", base64String);
    }

    @Test
    public void testbase64ToBitmap() {
        // Generated QRCode
        Bitmap bitmapQRcode = QRCodeGenerator.generateQRCodeBitmap("eventID",250,250);
        String base64String = QRCodeGenerator.bitmapToBase64(bitmapQRcode);
        Bitmap decodedBitmap = QRCodeGenerator.base64ToBitmap(base64String);
        assertNotNull("QR code bitmap should not be null", decodedBitmap);

    }
}
