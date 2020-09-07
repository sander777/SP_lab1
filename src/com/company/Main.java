package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.*;

public class Main {
    private static int maxSize = 30;

    public static void main(String[] args) {
        Scanner consoleReader = new Scanner(System.in);
        System.out.print("Input file path: ");
        String path = consoleReader.nextLine();
        System.out.println("Words that match to condition: ");
        for (String i :
                filterList(readFile(path))
        ) {
            System.out.println(i);
        }
    }

    public static ArrayList<String> readFile(String path) {
        String pattern = "[a-zA-Zа-яА-ЯІіЇї']*";

        File file = new File(path);
        ArrayList<String> result = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(file)));
            int c = 0;
            StringBuilder word = new StringBuilder();
            while (-1 != (c = reader.read())) {
                if (String.valueOf((char) c).matches(pattern)) {
                    word.append((char) c);
                } else {
                    result.add(word.substring(0, Math.min(maxSize, word.length())));
                    word = new StringBuilder();
                }
            }
            result.add(word.toString());
        } catch (java.util.NoSuchElementException ignored) {
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return result;
    }

    public static ArrayList<String> filterList(ArrayList<String> list) {
        list = new ArrayList<>(
                new HashSet<>(list));
        ArrayList<String> result = new ArrayList<>();
        WordComparator comparator = new WordComparator("[^AaEeIiOoUuАаЭэЕеЫыИиОоУуЮюЯя]");
        list.sort(comparator);
        return list;
    }

    static class WordComparator implements Comparator<String> {
        public String pattern;


        public WordComparator(String _pattern) {
            this.pattern = _pattern;
        }

        @Override
        public int compare(String o1, String o2) {
            return Double.compare(countChars(o1), countChars(o2));
        }

        double countChars(String str) {
            double i = 10000;
            double ss = 0;
            double result = 0;
            for (char c : str.toCharArray()) {
                String cStr = String.valueOf(c);
                ss += (double)Character.toLowerCase(c) / i;
                i *= 10;
                if (String.valueOf(c).matches(pattern)) {
                    result += 1;
                }
            }
            result += (double) str.length() / maxSize + ss;
            return result;
        }
    }
}
