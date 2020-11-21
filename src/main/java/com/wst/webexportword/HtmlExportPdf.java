package com.wst.webexportword;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;
import org.springframework.context.i18n.LocaleContextHolder;
import org.w3c.tidy.Configuration;
import org.w3c.tidy.Tidy;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class HtmlExportPdf {

    public static void main(String[] args) throws Exception {
        new HtmlExportPdf().exportPdfFile("https://mbd.baidu.com/newspage/data/landingsuper?context=%7B%22nid%22%3A%22news_10001915278197834322%22%7D&n_type=0&p_from=1");
    }

    // 导出pdf add by huangt 2012.6.1
    public  File exportPdfFile(String urlStr) throws Exception {
        // String outputFile = this.fileRoot + "/" +
        // ServiceConstants.DIR_PUBINFO_EXPORT + "/" + getFileName() + ".pdf";
        String outputFile = "d:/test3.pdf";
        OutputStream os;
        try {
            os = new FileOutputStream(outputFile);

            ITextRenderer renderer = new ITextRenderer();

            String str = getHtmlFile(urlStr);
            System.out.println(str);
            renderer.setDocumentFromString(str);
            ITextFontResolver fontResolver = renderer.getFontResolver();

            fontResolver.addFont("C:/WINDOWS/Fonts/SimSun.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);// 宋体字
            fontResolver.addFont("C:/WINDOWS/Fonts/Arial.ttf",BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);// 宋体字
            renderer.layout();

            renderer.createPDF(os);

            System.out.println("转换成功！");
            os.flush();
            os.close();
            return new File(outputFile);
        } catch (FileNotFoundException e) {
            // logger.error("不存在文件！" + e.getMessage());
            throw new Exception(e);
        } catch (DocumentException e) {
            // logger.error("生成pdf时出错了！" + e.getMessage());
            throw new Exception(e);
        } catch (IOException e) {
            // logger.error("pdf出错了！" + e.getMessage());
            throw new Exception(e);
        }

    }

    // 读取页面内容 add by huangt 2012.6.1
    public String getHtmlFile(String urlStr) throws Exception {
        URL url;
        try {
            if (urlStr.indexOf("?") != -1) {
                urlStr = urlStr + "&locale="
                        + LocaleContextHolder.getLocale().toString();
            } else {
                urlStr = urlStr + "?locale="
                        + LocaleContextHolder.getLocale().toString();
            }
            url = new URL(urlStr);

            URLConnection uc = url.openConnection();
            InputStream is = uc.getInputStream();

            Tidy tidy = new Tidy();

            OutputStream os2 = new ByteArrayOutputStream();
            tidy.setXHTML(true); // 设定输出为xhtml(还可以输出为xml)
            tidy.setCharEncoding(Configuration.UTF8); // 设定编码以正常转换中文
            tidy.setTidyMark(false); // 不设置它会在输出的文件中给加条meta信息
            tidy.setXmlPi(true); // 让它加上<?xml version="1.0"?>
            tidy.setIndentContent(true); // 缩进，可以省略，只是让格式看起来漂亮一些
            tidy.parse(is, os2);

            is.close();

            // 解决乱码 --将转换后的输出流重新读取改变编码
            String temp;
            StringBuffer sb = new StringBuffer();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    new ByteArrayInputStream(
                            ((ByteArrayOutputStream) os2).toByteArray()),
                    "utf-8"));
            while ((temp = in.readLine()) != null) {
                sb.append(temp);
            }

            return sb.toString();
        } catch (IOException e) {
            // logger.error("读取客户端网页文本信息时出错了" + e.getMessage());
            throw new Exception(e);
        }
    }

}




