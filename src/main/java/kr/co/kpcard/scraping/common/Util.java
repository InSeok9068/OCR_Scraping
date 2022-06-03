package kr.co.kpcard.scraping.common;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;

public class Util {
    public static String getUniqueFileName() {
        return DateFormatUtils.format(new Date(), "yyyyMMddHHmmss") + RandomStringUtils.randomNumeric(5);
    }
}
