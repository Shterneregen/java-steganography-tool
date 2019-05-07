package random.stego;

import random.gui.MainFrame;

import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Steganography {

    // Принимает массив битов изображения, оставляет в массиве только те биты, в которые происходит запись
    // На их основе возвращает изображение.
    public static BufferedImage doLastBitImage(int[] pixels, int imageWidth, int imageHeight) {
        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        image.setRGB(0, 0, imageWidth, imageHeight, extractLastBits(pixels), 0, imageWidth);
        return image;
    }

    // Принимает массив пикселей pixels, возвращает этот же массив только с теми битами,
    // куда происходила запись сообщения, остальные обнуляет
    public static int[] extractLastBits(int[] pixels) throws NullPointerException {
        for (int i = 0; i < pixels.length; i++) {
            int nR = (pixels[i] >> 16) & 0x3;
            int nG = (pixels[i] >> 8) & 0x7;
            int nB = pixels[i] & 0x7;
            pixels[i] = nR << 16 | nG << 8 | nB;
        }
        return pixels;
    }

    // Возвращает массив пикселей из изображения 
    public static int[] imgToPix(BufferedImage originalImage, int imageWidth, int imageHeight) throws InterruptedException {
        int[] pixels = new int[imageWidth * imageHeight];
        PixelGrabber pgr = new PixelGrabber(
                originalImage,
                0, //  (x) координаты вырезаемой прямоугольной 
                0, //  (y) области, на основе которой строится массив пикселов
                imageWidth, // Width
                imageHeight, // Height
                pixels,
                0, //  смещение в массиве, начиная с которого. туда будут записаны данные первого пиксела
                imageWidth); //  расстояние между строками пикселов в массиве
        pgr.grabPixels();
        return pixels;
    }

    // Вставляет строку message в массив пикселей pixels в определённую позицию posPix. 
    // Возвращает изображение со скрытым сообщением
    public static BufferedImage insertHiddenMessage(int[] pixels,
                                                    String message,
                                                    int posPix,
                                                    int imageWidth,
                                                    int imageHeight
    ) throws NullPointerException, UnsupportedEncodingException {
        if (message.equals("")) {
            throw new NullPointerException("Insert message");
        }
        if (pixels == null) {
            throw new NullPointerException("Select a file");
        }
        if (posPix > pixels.length) {
            throw new NullPointerException("№ пикселя");
        }
        // Преобразование строки в целочисленный массив для вставки в массив пикселей
        int[] intMsg = msgToInt(message);
        // Преобразование проверочного символа в целочисленный массив 
        int[] intCheck1 = msgToInt("~");
        int[] intCheck2 = msgToInt("^");
        int tempPosPix = posPix;

        if (intMsg.length > (pixels.length - tempPosPix - 8)) {
            throw new NullPointerException("Not enough space to write");
        }
        // Check characters
        pixels[tempPosPix] = insToPix(intCheck1[0], pixels[tempPosPix]);
        tempPosPix++;
        pixels[tempPosPix] = insToPix(intCheck1[0], pixels[tempPosPix]);
        tempPosPix++;
        for (int j = 0; j < intMsg.length + 1; tempPosPix++, j++) {
            // Если достигнут конец сообщения, то записываются символы, по которым 
            // можно определить конец сообщения
            if (j == intMsg.length) {
                pixels[tempPosPix] = insToPix(intCheck1[0], pixels[tempPosPix]);
                tempPosPix += 7;
                pixels[tempPosPix] = insToPix(intCheck2[0], pixels[tempPosPix]);
                break;
            }
            // В пиксель записывается элемент сообщения
            pixels[tempPosPix] = insToPix(intMsg[j], pixels[tempPosPix]);
        }
        BufferedImage retImg = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        retImg.setRGB(0, 0, imageWidth, imageHeight, pixels, 0, imageWidth);
        return retImg;
    }

    // intMsg элемент массива сообщения записывается в pixel биты элемента массива пикселей изображения
    // Возвращает пиксель со скрытым сообщением
    private static int insToPix(int intMsg, int pixel) {
        int msg1, msg2, msg3, nR, nG, nB, msgR, msgG, msgB;
        msg1 = (intMsg >> 6) & 0x3; // 0x3 = 0000 0011
        msg2 = (intMsg >> 3) & 0x7; // 0x7 = 0000 0111
        msg3 = intMsg & 0x7;

        nR = (pixel >> 16) & 0xfc;  // 0xfc = 1111 1100
        msgR = nR | msg1;

        nG = (pixel >> 8) & 0xf8;   // 0xf8 = 1111 1000
        msgG = nG | msg2;

        nB = pixel & 0xf8;          // 0xf8 = 1111 1000
        msgB = nB | msg3;

        return (msgR << 16 | msgG << 8 | msgB);
    }

    /**
     * Extract message from image
     *
     * @param pixels           image pixels
     * @param msgPixelPosition start pixel number of message
     * @return message from image
     * @throws NullPointerException
     */
    public static String extractHiddenMessage(int[] pixels, int msgPixelPosition) throws NullPointerException {
        if (pixels == null) {
            throw new NullPointerException("Select a file");
        }
        int[] extMsg = new int[pixels.length];
        int tempPosPix = msgPixelPosition + 2;
        String s = null;
        String resultStr = null;
        int index;
        for (; tempPosPix < pixels.length; tempPosPix++) {
            extMsg[tempPosPix] = extractIntMsgFromPixel(pixels[tempPosPix]);
        }
        try {
            s = new String(intToMsg(extMsg), "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        index = s.indexOf('~');
        if (s.charAt(index + 7) == '^') {
            resultStr = s.substring(msgPixelPosition + 2, index);
        }
        return resultStr;
    }

    /**
     * Extracts message bits from image bits
     *
     * @param rgbPixel pixel with message
     * @return message byte
     */
    public static int extractIntMsgFromPixel(int rgbPixel) {
        int nR = (rgbPixel >> 16) & 0x3;
        int msgR = (nR << 6);

        int nG = (rgbPixel >> 8) & 0x7;
        int msgG = (nG << 3);

        int msgB = rgbPixel & 0x7;
        return msgR | msgG | msgB;
    }

    // Преобразует строку в целочисленный массив
    private static int[] msgToInt(String message) throws UnsupportedEncodingException {
        byte[] msgByteArray = message.getBytes("UTF-8");
        int[] imsg = new int[msgByteArray.length];
        for (int i = 0; i < msgByteArray.length; i++) {
            imsg[i] = (0xfff & msgByteArray[i]);
        }
        return imsg;
    }

    // Преобразует целочисленный массив в байтовый
    private static byte[] intToMsg(int[] imsg) {
        byte[] intToByte = new byte[imsg.length];
        for (int i = 0; i < imsg.length; i++) {
            intToByte[i] = (byte) imsg[i];
        }
        return intToByte;
    }
}
