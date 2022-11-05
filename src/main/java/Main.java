import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {
    public static int capacityQueue = 100;
    public static BlockingQueue<String> queueA = new ArrayBlockingQueue<>(capacityQueue);
    public static BlockingQueue<String> queueB = new ArrayBlockingQueue<>(capacityQueue);
    public static BlockingQueue<String> queueC = new ArrayBlockingQueue<>(capacityQueue);

    final public static int maxLengthOfWord = 100_000;
    final public static int maxAmounthOfWords = 10_000;
    final public static String lettersSet = "abc";


    public static void main(String[] args) {

        Thread creator = new Thread(() -> {
            for (int i = 0; i < maxAmounthOfWords; i++) {
                String str;
                try {
                    str = generateText(lettersSet, maxLengthOfWord);
                    queueA.put(str);
                    queueB.put(str);
                    queueC.put(str);
                } catch (InterruptedException e) {
                    return;
                }
            }
        });

        creator.start();

        new Thread(() -> {

            String maxAmountAStr = "";
            int globalMaxA = 0;
            StringBuilder sb = new StringBuilder();

            while (creator.isAlive()) {
                try {
                    sb.append(queueA.take());
                    int currentMaxA = 0;
                    for (int i = 0; i < sb.length(); i++) {
                        if (sb.charAt(i) == 'a') {
                            currentMaxA++;
                        }
                    }
                    if (currentMaxA > globalMaxA) {
                        globalMaxA = currentMaxA;
                        maxAmountAStr = sb.toString();
                    }
                    sb.setLength(0);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.printf("Максимум \'a\' = %d в слове, начинающимся на:\n %80.80s ... и т.д.\n", globalMaxA, maxAmountAStr);
        }).start();

        new Thread(() -> {

            String maxAmountBStr = "";
            int globalMaxB = 0;
            StringBuilder sb = new StringBuilder();

            while (creator.isAlive()) {
                try {
                    sb.append(queueB.take());
                    int currentMaxB = 0;
                    for (int i = 0; i < sb.length(); i++) {
                        if (sb.charAt(i) == 'b') {
                            currentMaxB++;
                        }
                    }
                    if (currentMaxB > globalMaxB) {
                        globalMaxB = currentMaxB;
                        maxAmountBStr = sb.toString();
                    }
                    sb.setLength(0);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.printf("Максимум \'b\' = %d в слове, начинающимся на:\n %80.80s ... и т.д.\n", globalMaxB, maxAmountBStr);
        }).start();

        new Thread(() -> {

            int globalMaxC = 0;
            String maxAmountCStr = "";
            StringBuilder sb = new StringBuilder();

            while (creator.isAlive()) {
                try {
                    sb.append(queueC.take());
                    int currentMaxC = 0;
                    for (int i = 0; i < sb.length(); i++) {
                        if (sb.charAt(i) == 'c') {
                            currentMaxC++;
                        }
                    }
                    if (currentMaxC > globalMaxC) {
                        globalMaxC = currentMaxC;
                        maxAmountCStr = sb.toString();
                    }
                    sb.setLength(0);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.printf("Максимум \'c\' = %d в слове, начинающимся на:\n %80.80s ... и т.д.\n", globalMaxC, maxAmountCStr);
        }).start();
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}