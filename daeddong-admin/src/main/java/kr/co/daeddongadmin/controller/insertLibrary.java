package kr.co.daeddongadmin.controller;

import java.util.Random;

public class insertLibrary {
    private static final String[] FIRST_WORDS = {
            "푸른", "고요한", "영원한", "밝은", "햇살", "황금", "맑은", "마법의", "비밀의"
    };

    private static final String[] SECOND_WORDS = {
            "골짜기", "안식처", "항구", "언덕", "숲", "목초지", "숲", "안식처", "만"
    };

    public static void main(String[] args) {
        String libraryName = generateLibraryName();
        System.out.println("도서관 이름: " + libraryName);
    }

    public static String generateLibraryName() {
        Random random = new Random();
        String firstWord = FIRST_WORDS[random.nextInt(FIRST_WORDS.length)];
        String secondWord = SECOND_WORDS[random.nextInt(SECOND_WORDS.length)];

        return firstWord + " " + secondWord + " 도서관";
    }
}
