package kr.co.kpcard.scraping.common.excel;

import kr.co.kpcard.scraping.common.dto.ScrapProductInfoDto;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;


@Component
public class ExcelService {

    @Value("${save-path.excel}")
    private String excelSavePath;

    public void create(List<ScrapProductInfoDto> scrapProductInfoDtoList, String fileName) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("OCR 상품 정보");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("순번");
        headerRow.createCell(1).setCellValue("발급사");
        headerRow.createCell(2).setCellValue("상품명");
        headerRow.createCell(3).setCellValue("브랜드");
        headerRow.createCell(4).setCellValue("연관상품명");
        headerRow.createCell(5).setCellValue("연관브랜드");
        headerRow.createCell(6).setCellValue("카테고리코드");
        headerRow.createCell(7).setCellValue("카테고리명");
        headerRow.createCell(8).setCellValue("쿠폰타입코드");
        headerRow.createCell(9).setCellValue("쿠폰타입명");
        headerRow.createCell(10).setCellValue("가격");
        headerRow.createCell(11).setCellValue("설명");
        headerRow.createCell(12).setCellValue("이미지 파일명");

        for (int i = 1; i <= scrapProductInfoDtoList.size(); i++) {
            Row row = sheet.createRow(i);
            ScrapProductInfoDto scrapProductInfoDto = scrapProductInfoDtoList.get(i - 1);
            int cellIndex = 0;
            row.createCell(cellIndex++).setCellValue(scrapProductInfoDto.getSeqNo());
            row.createCell(cellIndex++).setCellValue(scrapProductInfoDto.getIssuer());
            row.createCell(cellIndex++).setCellValue(scrapProductInfoDto.getTitle());
            row.createCell(cellIndex++).setCellValue(scrapProductInfoDto.getBrand());
            row.createCell(cellIndex++).setCellValue(scrapProductInfoDto.getSearchTitle());
            row.createCell(cellIndex++).setCellValue(scrapProductInfoDto.getSearchBrand());
            row.createCell(cellIndex++).setCellValue(scrapProductInfoDto.getCategoryCode());
            row.createCell(cellIndex++).setCellValue(scrapProductInfoDto.getCategoryDesc());
            row.createCell(cellIndex++).setCellValue(scrapProductInfoDto.getCouponTypeCode());
            row.createCell(cellIndex++).setCellValue(scrapProductInfoDto.getCouponTypeDesc());
            row.createCell(cellIndex++).setCellValue(scrapProductInfoDto.getPrice());
            row.createCell(cellIndex++).setCellValue(scrapProductInfoDto.getContent());
            row.createCell(cellIndex).setCellValue(scrapProductInfoDto.getImageUrl());
        }

        FileOutputStream fos = new FileOutputStream(excelSavePath + fileName + ".xlsx");
        workbook.write(fos);
        workbook.close();
    }
}
