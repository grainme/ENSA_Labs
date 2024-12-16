package org.codefromscratch.App;

public class App {
    public static long fact(int n) {
        if(n < 0) {
            throw new IllegalArgumentException("n doit etre positif.");
        }
        if(n == 0) {
            return 1;
        }
        return n * fact(n-1);
    }

    public static void main(String... args){
    }
}
