package main;

import main.domain.ProductInfo;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class Excel {
    public static void create(List<ProductInfo> productInfoList, String fileName) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("OCR 상품 정보");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("브랜드");
        headerRow.createCell(1).setCellValue("카테고리");
        headerRow.createCell(2).setCellValue("쿠폰타입");
        headerRow.createCell(3).setCellValue("상품명");
        headerRow.createCell(4).setCellValue("가격");
        headerRow.createCell(5).setCellValue("설명");
        headerRow.createCell(6).setCellValue("이미지 파일명");

        for (int i = 1; i <= productInfoList.size(); i++) {
            Row row = sheet.createRow(i);
            ProductInfo productInfo = productInfoList.get(i - 1);
            int cellIndex = 0;
            row.createCell(cellIndex++).setCellValue(productInfo.getBrand());
            row.createCell(cellIndex++).setCellValue(productInfo.getCategoryName());
            row.createCell(cellIndex++).setCellValue(productInfo.getCouponType());
            row.createCell(cellIndex++).setCellValue(productInfo.getTitle());
            row.createCell(cellIndex++).setCellValue(productInfo.getPrice());
            row.createCell(cellIndex++).setCellValue(productInfo.getContent());
            row.createCell(cellIndex).setCellValue(productInfo.getImageFileName());
        }

        FileOutputStream fos = new FileOutputStream("C:\\excel\\" + fileName + ".xlsx");
        workbook.write(fos);
        workbook.close();
    }
}
