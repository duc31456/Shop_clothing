package com.example.ck.Utils;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;

public class PDFUtils {
    public static void addNewItem(Document document, String text, int align, Font font) throws DocumentException {
        Chunk chunk = new Chunk(text,font);
        Paragraph paragraph = new Paragraph(chunk);
        paragraph.setAlignment(align);
        document.add(paragraph);
    }

    public static void addLineSpace(Document document) throws DocumentException
    {
        document.add(new Paragraph(""));
    }

    public static void addNewItemWithLeftorRight(Document document, String leftText, String rightText,
                                                 Font leftFont, Font rightFont) throws DocumentException{
        Chunk leftTextChunk = new Chunk(leftText, leftFont);
        Chunk rightTextChunk = new Chunk(rightText, rightFont);
        Paragraph paragraph = new Paragraph(leftTextChunk);
        paragraph.add(new Chunk(new VerticalPositionMark()));
        paragraph.add(rightTextChunk);
        document.add(paragraph);
    }


    public static void addLineSeperator(Document document) throws DocumentException{
        LineSeparator lineSeparator = new LineSeparator();
        lineSeparator.setLineColor(new BaseColor(0,0,0,68));
        addLineSpace(document);
        document.add(new Chunk(lineSeparator));
        addLineSpace(document);
    }
}
