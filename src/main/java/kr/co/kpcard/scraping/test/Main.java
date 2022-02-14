package kr.co.kpcard.scraping.test;

public class Main {
    public static void main(String[] args) {
        String text = "javascript:goGoodsDetail('81', 'G00000230568');";
        System.out.println(text.substring(text.indexOf('\'') + 1, text.indexOf(',') - 1));
        System.out.println(text.substring(text.indexOf(',') + 3, text.lastIndexOf('\'')));
    }
}
