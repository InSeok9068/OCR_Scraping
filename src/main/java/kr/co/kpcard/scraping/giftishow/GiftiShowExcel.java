package kr.co.kpcard.scraping.giftishow;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class GiftiShowExcel {
    public static void create(List<Map<String, String>> productInfoList, String fileName) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("OCR 상품 정보");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("브랜드");
        headerRow.createCell(1).setCellValue("상품명");
        headerRow.createCell(2).setCellValue("가격");
        headerRow.createCell(3).setCellValue("이미지 파일명");

        for (int i = 1; i <= productInfoList.size(); i++) {
            Row row = sheet.createRow(i);
            Map<String, String> productInfo = productInfoList.get(i - 1);
            int cellIndex = 0;
            row.createCell(cellIndex++).setCellValue(productInfo.get("brand"));
            row.createCell(cellIndex++).setCellValue(productInfo.get("title"));
            row.createCell(cellIndex++).setCellValue(productInfo.get("price"));
            row.createCell(cellIndex).setCellValue(productInfo.get("imageFileName"));
        }

        FileOutputStream fos = new FileOutputStream("C:\\excel\\" + fileName + ".xlsx");
        workbook.write(fos);
        workbook.close();
    }
}
