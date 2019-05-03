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
    public static BufferedImage imgLastBits(int[] pixels, int nImageWidth, int nImageHeight) {
        BufferedImage retImg = new BufferedImage(nImageWidth, nImageHeight, BufferedImage.TYPE_INT_RGB);
        retImg.setRGB(0, 0, nImageWidth, nImageHeight, extractLastBits(pixels), 0, nImageWidth);
        return retImg;
    }

    // Принимает массив пикселей pixels, возвращает этот же массив только с теми битами,
    // куда происходила запись сообщения, остальные обнуляет
    public static int[] extractLastBits(int[] pixels) throws NullPointerException {
        int[] pixs = pixels;
        for (int i = 0; i < pixels.length; i++) {
            int nR = (pixs[i] >> 16) & 0x3;
            int nG = (pixs[i] >> 8) & 0x7;
            int nB = pixs[i] & 0x7;
            pixs[i] = nR << 16 | nG << 8 | nB;
        }
        return pixs;
    }

    // Возвращает массив пикселей из изображения 
    public static int[] imgToPix(BufferedImage im, int nImageWidth, int nImageHeight) throws InterruptedException {
        int[] pixels = new int[nImageWidth * nImageHeight];
        // Создание объекта класса PixelGrabber 
        PixelGrabber pgr = new PixelGrabber(
                im, //  исходное изображение
                0, //  (x) координаты вырезаемой прямоугольной 
                0, //  (y) области, на основе которой строится массив пикселов
                nImageWidth, //  (w) pixels
                nImageHeight, //  (h)
                pixels, //
                0, //  смещение в массиве, начиная с которого 
                //  туда будут записаны данные первого пиксела
                nImageWidth);   //  расстояние между строками пикселов в массиве
        //  Заполнение массива
        pgr.grabPixels();
        return pixels;
    }

    // Вставляет строку message в массив пикселей pixels в определённую позицию posPix. 
    // Возвращает изображение со скрытым сообщением
    public static BufferedImage ins(int[] pixels, String message, int posPix, int nImageWidth, int nImageHeight) throws NullPointerException, UnsupportedEncodingException {
        if (message.equals("")) {
            throw new NullPointerException("Введте сообщение");
        }
        if (pixels == null) {
            throw new NullPointerException("Select a file");
        }
        if (posPix > pixels.length) {
            throw new NullPointerException("№ пикселя");
        }
        //int[] newPix = new int[nImageWidth * nImageHeight];
        int[] newPix = pixels;
        // Преобразование строки в целочисленный массив для вставки в массив пикселей
        int[] intMsg = msgToInt(message);
        // Преобразование проверочного символа в целочисленный массив 
        int[] intCheck1 = msgToInt("~");
        int[] intCheck2 = msgToInt("^");
        int tempPosPix = posPix;

        if (intMsg.length > (newPix.length - tempPosPix - 8)) {
            throw new NullPointerException("Недостаточно места для записи");
        }
        // Символы до сообщения 
        newPix[tempPosPix] = insToPix(intCheck1[0], newPix[tempPosPix]);
        tempPosPix++;
        newPix[tempPosPix] = insToPix(intCheck1[0], newPix[tempPosPix]);
        tempPosPix++;
        for (int j = 0; j < intMsg.length + 1; tempPosPix++, j++) {
            // Если достигнут конец сообщения, то записываются символы, по которым 
            // можно определить конец сообщения
            if (j == intMsg.length) {
                newPix[tempPosPix] = insToPix(intCheck1[0], newPix[tempPosPix]);
                tempPosPix += 7;
                newPix[tempPosPix] = insToPix(intCheck2[0], newPix[tempPosPix]);
                break;
            }
            // В пиксель записывается элемент сообщения
            newPix[tempPosPix] = insToPix(intMsg[j], newPix[tempPosPix]);
        }
        BufferedImage retImg = new BufferedImage(nImageWidth, nImageHeight,
                BufferedImage.TYPE_INT_RGB);
        retImg.setRGB(0, 0, nImageWidth, nImageHeight, newPix, 0, nImageWidth);
//        lTrace.setText("Сообщение записано");
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

    // Извлекает из пекселей биты сообщения и возвращает их в виде строки 
    public static String ext(int[] pixels, int posPix) throws NullPointerException {
        if (pixels == null) {
            throw new NullPointerException("Select a file");
        }
        int[] tempPix = pixels;
        int[] extMsg = new int[pixels.length];
        int tempPosPix = posPix + 2;
        String s = null;
        String resultStr = null;
        int index;
        for (; tempPosPix < tempPix.length; tempPosPix++) {
            extMsg[tempPosPix] = extFromPix(tempPix[tempPosPix]);
        }
        try {
            s = new String(intToMsg(extMsg), "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        index = s.indexOf('~');
        if (s.charAt(index + 7) == '^') {
//            lTrace.setText("Есть скрытое сообщение");
            resultStr = s.substring(posPix + 2, index);
//            tpInsText.setText(resultStr);
        }
//        else {lTrace.setText("Скрытое сообщение отсутствует");}        
        return resultStr;
    }

    // Извлекает биты сообщения из битов картинки
    public static int extFromPix(int pixs) {
        int nR = (pixs >> 16) & 0x3;
        int msgR = (nR << 6);
        //int msgR = 0xff & (nR << 6);

        int nG = (pixs >> 8) & 0x7;
        int msgG = (nG << 3);
        //int msgG = 0xff & (nG << 3);

        int msgB = pixs & 0x7;
        return msgR | msgG | msgB;
    }

    // Преобразует строку в целочисленный массив
    private static int[] msgToInt(String message) throws UnsupportedEncodingException {
        byte[] msgToByte = message.getBytes("UTF-8");
        int[] imsg = new int[msgToByte.length];
        for (int i = 0; i < msgToByte.length; i++) {
            imsg[i] = (0xfff & msgToByte[i]);
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
