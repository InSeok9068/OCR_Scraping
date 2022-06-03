package kr.co.kpcard.scraping.kakao;

import kr.co.kpcard.scraping.kakao.domain.KakaoProductInfo;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class KakaoExcel {
    public static void create(List<KakaoProductInfo> kakaoProductInfoList, String fileName) throws IOException {
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

        for (int i = 1; i <= kakaoProductInfoList.size(); i++) {
            Row row = sheet.createRow(i);
            KakaoProductInfo kakaoProductInfo = kakaoProductInfoList.get(i - 1);
            int cellIndex = 0;
            row.createCell(cellIndex++).setCellValue("카카오");
            row.createCell(cellIndex++).setCellValue(kakaoProductInfo.getTitle());
            row.createCell(cellIndex++).setCellValue(kakaoProductInfo.getBrand());
            row.createCell(cellIndex++).setCellValue(kakaoProductInfo.getBrand());
            row.createCell(cellIndex++).setCellValue(kakaoProductInfo.getCategoryName());
            row.createCell(cellIndex++).setCellValue(kakaoProductInfo.getCouponType());
            row.createCell(cellIndex++).setCellValue(kakaoProductInfo.getPrice());
            row.createCell(cellIndex++).setCellValue(kakaoProductInfo.getContent());
            row.createCell(cellIndex).setCellValue(kakaoProductInfo.getImageFileName());
        }

        FileOutputStream fos = new FileOutputStream("C:\\excel\\" + fileName + ".xlsx");
        workbook.write(fos);
        workbook.close();
    }
}
