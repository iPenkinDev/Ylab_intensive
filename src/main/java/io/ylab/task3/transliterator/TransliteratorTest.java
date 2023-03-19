package io.ylab.task3.transliterator;

public class TransliteratorTest {
    public static void main(String[] args) {
        Transliterator transliterator = new TransliteratorImpl();
        String res = transliterator
                .transliterate("HELLO! ПРИВЕТ! Go, boy!");
        String res2 = transliterator.transliterate("привет, МИР!");
        System.out.println(res);
        System.out.println(res2);
    }
}
