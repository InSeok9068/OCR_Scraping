//package kr.co.kpcard.scraping.common.config;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.io.FileUtils;
//import org.apache.commons.lang3.exception.ExceptionUtils;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.stereotype.Component;
//
//import java.io.File;
//import java.io.IOException;
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class StartupEvent implements ApplicationRunner {
//
//    @Value("${save-path.excel}")
//    private String excelSavePath;
//
//    @Value("${save-path.image}")
//    private String imageSavePath;
//
//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//        try {
//            File excelDirecotry = new File(excelSavePath);
//            File imageDirecotry = new File(imageSavePath);
//
//            FileUtils.cleanDirectory(excelDirecotry);
//            FileUtils.cleanDirectory(imageDirecotry);
//        } catch (IOException ioException) {
//            log.error("디렉토리 초기화 실패 : {}", ExceptionUtils.getStackTrace(ioException));
//        }
//    }
//}
