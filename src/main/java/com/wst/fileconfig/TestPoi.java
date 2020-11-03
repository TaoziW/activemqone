package com.wst.fileconfig;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;

import java.io.FileInputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *  .doc .docx 文档测试类
 *
 */
public class TestPoi {



    public static void main(String[] args) {
        String filename = "E:\\File\\测试word文档1.docx";
        TestPoi tp = new TestPoi();
        //.docx和doc文件的读取
        String content = tp.readWord(filename);
        System.out.println(content);
        System.out.println("测试是否包含："+content.endsWith("文字"));
        Pattern p = Pattern.compile("\\s*|\t|\r|\n");
        //content.replaceAll("\n", "");
        Matcher m = p.matcher(content);
        String dest = m.replaceAll("");
        System.out.println(dest);
        System.out.println("content====" + content.contains("圣诞节"));
    }

    /**
     * 读取word文件内容
     *
     * @param path
     * @return buffer
     */
    public String readWord(String path) {
        String buffer = "";
        try {
            if (path.endsWith(".doc")) {
                FileInputStream is = new FileInputStream(path);
                WordExtractor ex = new WordExtractor(is);
                buffer = ex.getText();
                is.close();
            } else if (path.endsWith(".docx")) {
                OPCPackage opcPackage = POIXMLDocument.openPackage(path);
                POIXMLTextExtractor extractor = new XWPFWordExtractor(opcPackage);
                buffer = extractor.getText();
                opcPackage.close();
            } else {
                System.out.println("此文件不是word文件！");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer;
    }
}
