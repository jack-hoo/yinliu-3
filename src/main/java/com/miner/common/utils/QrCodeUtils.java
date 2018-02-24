package com.miner.common.utils;

/**
 * Created by hushangjie on 2017/10/9.
 */
public class QrCodeUtils {
    /**
     * 将文字生成二维码
     * @param url
     * @param request
     * @param response
     */
    /*public static void Qrcode(String url, HttpServletRequest request, HttpServletResponse response) {
        try {
            Qrcode handler = new Qrcode();
            handler.setQrcodeErrorCorrect('M');
            handler.setQrcodeEncodeMode('B');
            handler.setQrcodeVersion(7);
            byte[] contentBytes = url.getBytes("UTF-8");
            BufferedImage bufImg = new BufferedImage(145, 145, BufferedImage.TYPE_INT_RGB);
            Graphics2D gs = bufImg.createGraphics();
            gs.setBackground(Color.WHITE);
            gs.clearRect(0, 0, 145, 145);
            //设定图像颜色：BLACK
            gs.setColor(Color.BLACK);
            //设置偏移量  不设置肯能导致解析出错
            int pixoff = 2;
            //输出内容：二维码
            if(contentBytes.length > 0 && contentBytes.length < 124) {
                boolean[][] codeOut = handler.calQrcode(contentBytes);
                for(int i = 0; i < codeOut.length; i++) {
                    for(int j = 0; j < codeOut.length; j++) {
                        if(codeOut[j][i]) {
                            gs.fillRect(j * 3 + pixoff, i * 3 + pixoff,3, 3);
                        }
                    }
                }
            } else {
                System.err.println("QRCode content bytes length = " + contentBytes.length + " not in [ 0,120 ]. ");
            }
            gs.dispose();
            bufImg.flush();
            //生成二维码QRCode图片
            ImageIO.write(bufImg, "jpg", response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}
