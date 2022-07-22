package kr.co.kpcard.scraping.common.util;

import kr.co.kpcard.scraping.common.domain.ScrapProductInfo;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelUtil {
    public static void create(List<ScrapProductInfo> scrapProductInfoList, String fileName) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("OCR 상품 정보");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("발급사");
        headerRow.createCell(1).setCellValue("상품명");
        headerRow.createCell(2).setCellValue("브랜드");
        headerRow.createCell(3).setCellValue("연관브랜드");
        headerRow.createCell(4).setCellValue("카테고리");
        headerRow.createCell(5).setCellValue("쿠폰타입");
        headerRow.createCell(6).setCellValue("가격");
        headerRow.createCell(7).setCellValue("설명");
        headerRow.createCell(8).setCellValue("이미지 파일명");

        for (int i = 1; i <= scrapProductInfoList.size(); i++) {
            Row row = sheet.createRow(i);
            ScrapProductInfo scrapProductInfo = scrapProductInfoList.get(i - 1);
            int cellIndex = 0;
            row.createCell(cellIndex++).setCellValue(scrapProductInfo.getIssuer());
            row.createCell(cellIndex++).setCellValue(scrapProductInfo.getTitle());
            row.createCell(cellIndex++).setCellValue(scrapProductInfo.getBrand());
            row.createCell(cellIndex++).setCellValue(scrapProductInfo.getSubBrand());
            row.createCell(cellIndex++).setCellValue(scrapProductInfo.getCategory());
            row.createCell(cellIndex++).setCellValue(scrapProductInfo.getCouponType());
            row.createCell(cellIndex++).setCellValue(scrapProductInfo.getPrice());
            row.createCell(cellIndex++).setCellValue(scrapProductInfo.getContent());
            row.createCell(cellIndex).setCellValue(scrapProductInfo.getImage());
        }

        FileOutputStream fos = new FileOutputStream("C:\\excel\\" + fileName + ".xlsx");
        workbook.write(fos);
        workbook.close();
    }
}
