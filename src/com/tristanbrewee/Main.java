package com.tristanbrewee;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static void main(String[] args) {
        //PrepWork
        List<Boolean> numbers = new ArrayList<>();
        int limit = getUserInput();
        setNumbersToTrue(numbers, limit);

        long startOfProgram = System.currentTimeMillis();

        //ProcessData
        calculatePrimes(numbers);
        List<Integer> primes = numbersToPrimes(numbers);

        long endOfCalculations = System.currentTimeMillis();

        //PrintPrimes
        printPrimes(primes);

        long endOfProgram = System.currentTimeMillis();
        printTimes(startOfProgram, endOfCalculations, endOfProgram);
    }

    //Asks for upper limit
    private static int getUserInput(){
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("Till how many?");
            String input = scanner.nextLine();
            try {
                return Integer.parseInt(input);
            }
            catch (Exception e){
                //Keep on looping
            }
        }while (true);
    }

    //Populate numbers
    private static void setNumbersToTrue(List<Boolean> numbers, int limit) {
        for (int i = 0; i < limit; i++) {
            numbers.add(true);
        }
    }

    //Calculates primes and eliminates non-primes
    private static void calculatePrimes(List<Boolean> numbers) {
        int inBetween = 0;
        for (int i = 1; i < numbers.size(); ) {//no incrementation of i, we want to loop only primes
            if (i > inBetween) {//print a counter to know the program hasn't crashed (only useful with large upper limits)
                System.out.println("counter:" + inBetween);
                inBetween += 1000;
            }
            crossOf(numbers, i + 1);//cross of the multiples of the prime/ i+1 represents the number since index starts ar 0, not 1
            int index = numbers.subList(i + 1, numbers.size()).indexOf(true);//get next prime if it exists within the upper limit
            if (index == -1)
                return;
            i = numbers.subList(i + 1, numbers.size()).indexOf(true) + i + 1;//set i to index of next prime
        }
    }

    //Unnecessary code, just here for completeness
    private static boolean isPrime(int number) {
        int divider = 2;
        while (divider < number) {
            if (number % divider == 0)
                return false;
            divider++;
        }
        return true;
    }

    //cross of all multiples of the prime
    private static void crossOf(List<Boolean> numbers, int number) {
        int i = 2;
        while (true) {
            try {
                int crossOff = number * i;
                numbers.set(crossOff - 1, false);//-1 because we need the index, not the prime
                i++;
            } catch (Exception e) {//Will always throw a IndexOutOfBound or IntegerOverflow eventually
                return;
            }
        }
    }

    //Makes list of actual prime numbers
    private static List<Integer> numbersToPrimes(List<Boolean> numbers) {
        List<Integer> primes = new ArrayList<>();
        for (int i = 1; i < numbers.size(); i++) {
            if (numbers.get(i))
                primes.add(i + 1);
        }
        return primes;
    }

    //Prints out prime numbers 10 per row
    private static void printPrimes(List<Integer> primes) {
        AtomicInteger counter = new AtomicInteger(0);
        primes.forEach(e -> {
                    System.out.printf("%15d", e);
                    counter.getAndIncrement();
                    if (counter.get() % 10 == 0)
                        System.out.print("\n");
                });
        System.out.println();
    }

    //Prints time elapsed
    private static void printTimes(long start, long calc, long prog){
        long calcElapsed = calc - start;
        long progElapsed = prog - start;
        System.out.printf("Calculations took: %d hours, %d minutes, %d seconds %n",
                TimeUnit.MILLISECONDS.toHours(calcElapsed),
                TimeUnit.MILLISECONDS.toMinutes(calcElapsed),
                TimeUnit.MILLISECONDS.toSeconds(calcElapsed));
        System.out.printf("Program took: %d hours, %d minutes, %d seconds %n",
                TimeUnit.MILLISECONDS.toHours(progElapsed),
                TimeUnit.MILLISECONDS.toMinutes(progElapsed),
                TimeUnit.MILLISECONDS.toSeconds(progElapsed));
    }
}
