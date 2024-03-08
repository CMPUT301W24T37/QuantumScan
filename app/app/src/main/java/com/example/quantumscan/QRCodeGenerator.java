package com.example.quantumscan;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.ByteArrayOutputStream;

// get Bitmap, this Bitmap can be used in imageView.setImageBitmap(bitmap); to display
// Bitmap qrCodeBitmap = QRCodeHelper.generateQRCodeBitmap("sample text", 250, 250);
// imageView.setImageBitmap(bitmap)

// REFERENCE CODE LINK: https://stackoverflow.com/questions/8800919/how-to-generate-a-qr-code-for-an-android-application
public class QRCodeGenerator {
    public static Bitmap generateQRCodeBitmap(String text, int width, int height){
        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix bitMatrix = null;
        try {
            bitMatrix = writer.encode(text, BarcodeFormat.QR_CODE, width, height);
        } catch (WriterException e) {
            throw new RuntimeException(e);
        }
        Bitmap bitmap = Bitmap.createBitmap(250, 250, Bitmap.Config.RGB_565);
        for (int x = 0; x < bitMatrix.getWidth(); x++) {
            for (int y = 0; y < bitMatrix.getHeight(); y++) {
                bitmap.setPixel(x, y, bitMatrix.get(x, y) ? android.graphics.Color.BLACK : android.graphics.Color.WHITE);
            }
        }
        return bitmap;
    }

    // transfer bitmap to base64 format, this format can be stored in firestore
    // String base64Image = QRCodeHelper.bitmapToBase64(qrCodeBitmap);
    // REFERENCE CODE LINK: https://stackoverflow.com/questions/9224056/android-bitmap-to-base64-string
    public static String bitmapToBase64(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String base64Image = Base64.encodeToString(byteArray, Base64.DEFAULT);
        return base64Image;
    }

    // after take base64 string from firestore, this function transfer base64 to bitmap format, then can be displayed.
    // Bitmap decodedBitmap = QRCodeHelper.base64ToBitmap(base64Image);
    // imageView.setImageBitmap(bitmap)
    // REFERENCE CODE LINK: https://stackoverflow.com/questions/4837110/how-to-convert-a-base64-string-into-a-bitmap-image-to-show-it-in-a-imageview
    public static Bitmap base64ToBitmap(String base64Str){
        byte[] decodedBytes = Base64.decode(base64Str, Base64.DEFAULT);
        Bitmap bitmap2 = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
        return bitmap2;
    }

}
