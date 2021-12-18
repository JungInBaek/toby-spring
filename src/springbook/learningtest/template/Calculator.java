package springbook.learningtest.template;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {

    public Integer calcSum(String filepath) throws IOException {
        LineCallback<Integer> callback = new LineCallback<Integer>() {
            public Integer doSomethingWithLine(String line, Integer value) {

                return value + Integer.valueOf(line);
            }
        };

        return lineReadTemplate(filepath, callback, 0);
    }

    public Integer calcMultiply(String filepath) throws IOException {
        LineCallback<Integer> callback = new LineCallback<Integer>() {
            public Integer doSomethingWithLine(String line, Integer value) {

                return value * Integer.valueOf(line);
            }
        };

        return lineReadTemplate(filepath, callback, 1);
    }

    public String concatenate(String filepath) throws IOException {
        LineCallback<String> callback = new LineCallback<String>() {
            public String doSomethingWithLine(String line, String value) {

                return value + line;
            };
        };

        return lineReadTemplate(filepath, callback, "");
    }

    //  템플릿
    public <T> T lineReadTemplate(String filepath, LineCallback<T> callback, T initVal) throws IOException {
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(filepath));
            T result = initVal;
            String line = null;

            while((line = br.readLine()) != null) {
                result = callback.doSomethingWithLine(line, result);
            }

            return result;
        } catch(IOException e) {
            System.out.println(e.getMessage());
            throw e;
        } finally {
            if(br != null) {
                try {
                    br.close();
                } catch(IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}
