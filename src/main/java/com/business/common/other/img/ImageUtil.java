package com.business.common.other.img;

import com.business.common.message.ExceptionMessage;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author yuton
 * @ClassName: ImgUtil
 * @Description: 图片缩放截取Util
 * @date May 12, 2011 11:27:55 AM
 */
@Slf4j
public class ImageUtil {

    /**
     * 实现图像的等比缩放
     *
     * @param source
     * @param targetW
     * @param targetH
     * @return
     */
    public static BufferedImage resize(BufferedImage source, int targetW,
                                       int targetH) {
        // targetW，targetH分别表示目标长和宽
        int type = source.getType();
        BufferedImage target = null;
        double sx = (double) targetW / source.getWidth();
        double sy = (double) targetH / source.getHeight();
        // 这里想实现在targetW，targetH范围内实现等比缩放。如果不需要等比缩放
        // 则将下面的if else语句注释即可
        if (sx < sy) {
            sx = sy;
            targetW = (int) (sx * source.getWidth());
        } else {
            sy = sx;
            targetH = (int) (sy * source.getHeight());
        }
        if (type == BufferedImage.TYPE_CUSTOM) { // handmade
            ColorModel cm = source.getColorModel();
            WritableRaster raster = cm.createCompatibleWritableRaster(targetW,
                    targetH);
            boolean alphaPremultiplied = cm.isAlphaPremultiplied();
            target = new BufferedImage(cm, raster, alphaPremultiplied, null);
        } else
            target = new BufferedImage(targetW, targetH, type);
        Graphics2D g = target.createGraphics();
        // smoother than exlax:
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g.drawRenderedImage(source, AffineTransform.getScaleInstance(sx, sy));
        g.dispose();
        return target;
    }

    /**
     * 实现图像的等比缩放和缩放后的截取
     *
     * @param inFilePath  要截取文件的路径
     * @param outFilePath 截取后输出的路径
     * @param width       要截取宽度
     * @param hight       要截取的高度
     * @param proportion
     * @throws Exception
     */

    public static void saveImageAsJpg(String inFilePath, String outFilePath,
                                      int width, int hight, boolean proportion) throws Exception {
        File file = new File(inFilePath);
        InputStream in = new FileInputStream(file);
        File saveFile = new File(outFilePath);

        BufferedImage srcImage = ImageIO.read(in);
        if (width > 0 || hight > 0) {
            // 原图的大小
            int sw = srcImage.getWidth();
            int sh = srcImage.getHeight();
            // 如果原图像的大小小于要缩放的图像大小，直接将要缩放的图像复制过去
            if (sw > width && sh > hight) {
                srcImage = resize(srcImage, width, hight);
            } else {
                String fileName = saveFile.getName();
                String formatName = fileName.substring(fileName
                        .lastIndexOf('.') + 1);
                ImageIO.write(srcImage, formatName, saveFile);
                return;
            }
        }
        // 缩放后的图像的宽和高
        int w = srcImage.getWidth();
        int h = srcImage.getHeight();
        // 如果缩放后的图像和要求的图像宽度一样，就对缩放的图像的高度进行截取
        if (w == width) {
            // 计算X轴坐标
            int x = 0;
            int y = h / 2 - hight / 2;
            saveSubImage(srcImage, new Rectangle(x, y, width, hight), saveFile);
        }
        // 否则如果是缩放后的图像的高度和要求的图像高度一样，就对缩放后的图像的宽度进行截取
        else if (h == hight) {
            // 计算X轴坐标
            int x = w / 2 - width / 2;
            int y = 0;
            saveSubImage(srcImage, new Rectangle(x, y, width, hight), saveFile);
        }
        in.close();
    }

    /**
     * @param @param  is
     * @param @param  destPath
     * @param @param  newWidth
     * @param @param  newHeight
     * @param @return
     * @return int
     * @Title: saveCompress
     * @Description: 压缩图片，统一压缩成JPG格式，返回压缩后的大小（字节）
     */
    public static int saveCompress(InputStream is, String destPath,
                                   int newWidth, int newHeight) {
        try {
            BufferedImage srcImage = ImageIO.read(is);
            BufferedImage destImage = resize(srcImage, newWidth, newHeight);
            File destFile = new File(destPath);
            String fileName = destFile.getName();
            String formatName = fileName
                    .substring(fileName.lastIndexOf('.') + 1);
            ImageIO.write(destImage, formatName, destFile);
            return (int) destFile.length();
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * @param @param  is
     * @param @param  destPath
     * @param @param  newWidth
     * @param @return
     * @return int
     * @Title: saveCompressBaseWidth
     * @Description: 基于宽度等比压缩
     */
    public static int saveCompressBaseWidth(InputStream is, String destPath,
                                            int newWidth) {
        return saveCompressBaseHeight(is, destPath, newWidth);
    }

    /**
     * @param @param  bytes
     * @param @param  destPath
     * @param @param  newWidth
     * @param @return
     * @return int
     * @Title: saveCompressBaseWidth
     * @Description: 基于宽度等比压缩
     */
    public static int saveCompressBaseWidth(byte[] bytes, String destPath,
                                            int newWidth) {
        try {
            BufferedImage srcImage = ImageIO.read(new ByteArrayInputStream(
                    bytes));
            return getCompressBaseWidthOrHight(srcImage, destPath, newWidth);
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * @param @param  is
     * @param @param  destPath
     * @param @param  newH
     * @param @return
     * @return int
     * @Title: compressBaseHeight
     * @Description: 基于高度等比压缩
     */
    public static int saveCompressBaseHeight(InputStream is, String destPath,
                                             int newH) {
        try {
            BufferedImage srcImage = ImageIO.read(is);
            return getCompressBaseWidthOrHight(srcImage, destPath, newH);
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * @param @param  is
     * @param @param  destPath
     * @param @param  newW
     * @param @param  newH
     * @param @return
     * @return int
     * @Title: saveCompressAndCutBaseHeight
     * @Description: 压缩并截取图片，以高度为基准压缩，截图为居中正方形
     */
    public static int saveCompressAndCutBaseHeight(InputStream is,
                                                   String destPath, int newH) {
        try {
            BufferedImage srcImage = ImageIO.read(is);
           return getCompressAndCutBaseHeight(srcImage,destPath,newH);
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * @param @param  is
     * @param @param  destPath
     * @param @param  newW
     * @param @param  newH
     * @param @return
     * @return int
     * @Title: saveCompressAndCutBaseHeight
     * @Description: 压缩并截取图片，以高度为基准压缩，截图为居中正方形
     */
    public static int saveCompressAndCutBaseHeight(byte[] data,
                                                   String destPath, int newH) {
        try {
            BufferedImage srcImage = ImageIO
                    .read(new ByteArrayInputStream(data));
            return getCompressAndCutBaseHeight(srcImage,destPath,newH);
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * @param @param  is
     * @param @param  destPath
     * @param @return
     * @return int
     * @Title: savePicture
     * @Description: 按比例压缩图片
     */
    public static int savePictureTrumbs(InputStream is, String destPath,
                                        int newH) {
        return saveCompressAndCutBaseHeight(is, destPath, newH);
    }

    /**
     * @param @param  is
     * @param @param  destPath
     * @param @return
     * @return int
     * @Title: savePicture
     * @Description: 按比例压缩图片
     */
    public static int savePictureTrumbs(byte[] data, String destPath, int newH) {
        return saveCompressAndCutBaseHeight(data, destPath, newH);
    }

    /**
     * @param @param  is
     * @param @param  x
     * @param @param  y
     * @param @param  width
     * @param @param  height
     * @param @return
     * @return BufferedImage
     * @Title: cutImage
     * @Description: 裁剪图片
     */
    public static BufferedImage cutImage(InputStream is, int x, int y,
                                         int width, int height) {
        try {
            BufferedImage srcImage = ImageIO.read(is);
            return srcImage.getSubimage(x, y, width, height);
        } catch (Exception e) {
            log.error(ExceptionMessage.IO_EXCEPTION.getExceptionMsg());
            return null;
        }
    }

    /**
     * @param @param bi
     * @param @param nw
     * @param @param nh
     * @param @param tarFile
     * @return boolean
     * @Title: compressAndSave
     * @Description: 压缩处理
     */
    public static boolean compressAndSave(BufferedImage bi, int nw, int nh,
                                          File tarFile) {
        try {
            BufferedImage destImage = resize(bi, nw, nh);
            // 输出文件
            String fileName = tarFile.getName();
            String formatName = fileName
                    .substring(fileName.lastIndexOf('.') + 1);
            ImageIO.write(destImage, formatName, tarFile);
            return true;
        } catch (Exception e) {
            log.error(ExceptionMessage.IO_EXCEPTION.getExceptionMsg());
            return false;
        }
    }

    /**
     * 实现缩放后的截图
     *
     * @param image          缩放后的图像
     * @param subImageBounds 要截取的子图的范围
     * @param subImageFile   要保存的文件
     * @throws IOException
     */
    private static void saveSubImage(BufferedImage image,
                                     Rectangle subImageBounds, File subImageFile) throws IOException {
        if (subImageBounds.x < 0 || subImageBounds.y < 0
                || subImageBounds.width - subImageBounds.x > image.getWidth()
                || subImageBounds.height - subImageBounds.y > image.getHeight()) {
            System.out.println("Bad   subimage   bounds");
            return;
        }
        BufferedImage subImage = image.getSubimage(subImageBounds.x,
                subImageBounds.y, subImageBounds.width, subImageBounds.height);
        String fileName = subImageFile.getName();
        String formatName = fileName.substring(fileName.lastIndexOf('.') + 1);
        ImageIO.write(subImage, formatName, subImageFile);
    }

    public static void main(String[] args) {
        try {
            int size = saveCompress(new FileInputStream(
                    new File("d:\\test.jpg")), "d:\\test1.jpg", 200, 150);
            System.out.println(size);
            // size = saveCompress(new FileInputStream(new
            // File("d:\\test.gif")), "d:\\test2.gif", 30, 30);
            // System.out.println(size);
            // size = saveCompress(new FileInputStream(new
            // File("d:\\test.png")), "d:\\test3.png", 30, 30);
            // System.out.println(size);
        } catch (Exception e) {

        }
    }

    private static int getCompressBaseWidthOrHight(BufferedImage srcImage, String destPath, int newWidth) throws IOException {
        int srcW = srcImage.getWidth();
        int srcH = srcImage.getHeight();
        File destFile = new File(destPath);
        String fileName = destFile.getName();
        String formatName = fileName
                .substring(fileName.lastIndexOf('.') + 1);
        if (newWidth < srcW) {
            int newHeight = (srcH * newWidth) / srcW;
            BufferedImage destImage = resize(srcImage, newWidth, newHeight);
            ImageIO.write(destImage, formatName, destFile);
        } else {
            ImageIO.write(srcImage, formatName, destFile);
        }
        return (int) destFile.length();
    }

    private static int getCompressAndCutBaseHeight(BufferedImage srcImage, String destPath, int newH) throws IOException {
        int srcW = srcImage.getWidth();
        int srcH = srcImage.getHeight();
        File destFile = new File(destPath);
        String fileName = destFile.getName();
        String formatName = fileName
                .substring(fileName.lastIndexOf('.') + 1);
        if (newH < srcH) {

            int x = 0;
            int y = 0;
            int newLength = 0;
            if (srcW < srcH) {
                // 竖图
                y = srcH / 2 - (srcW / 2);
                newLength = srcW;
            } else {
                x = srcW / 2 - (srcH / 2);
                newLength = srcH;
            }
            // 截取图片
            srcImage = srcImage.getSubimage(x, y, newLength, newLength);
            BufferedImage destImage = resize(srcImage, newH, newH);
            ImageIO.write(destImage, formatName, destFile);
        } else {
            // 截取图片
            srcImage = srcImage.getSubimage(srcW / 2 - srcH / 2, 0, srcH,
                    srcH);
            ImageIO.write(srcImage, formatName, destFile);
        }
        return (int) destFile.length();
    }
}
