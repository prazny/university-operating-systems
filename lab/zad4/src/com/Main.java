package com;

import java.util.Random;

public class Main {

    public static int processCount = 5;
    public static int frameCount = 100;


    public static int pageRefCountPerProcess = 10000;
    public static double pageRefCountPerProcessFactor = 0.1; // przybliżenie w % długości ciągu odwołań
    //długość ciągu odwołań = pageRefCountPerProcess * [1 (+/-) random(0, pageRefCountPerProcessFactor)]

    public static int pagesPerProcess = 50;
    public static double pagesPerProcessFactor = 0.5; //przybliżenie w % ilości dostępnych stron dla procesu
    //ilość stron do których proces może się odwołać = pagesPerProcess * [1 (+/-) random(0, pagesPerProcessFactor)]

    public static double localChance = 0.2; // szansa <0, 1> na wygenerowanie ciągu zgodnego z z.l.o.
    public static double localLengthFactor = 0.15; // wspolczynnik długości podciagow
    //długość podciągu = pageRefCountPerProcess * localLengthFactor
    public static double localPageReferenceFactor = 0.1; // jaką czesc z calego ciagu bierzemy pod uwage w ciagu zgodnym z  z.l.o.
    //ilość stron do których podciąg zgodny z z.l.o. może się odwołać = localPageReferenceFactor * pagesPerProcess

    public static double ppfMin = 0.25; //ppf, gdy zabieramy ramke
    public static double ppfMax = 0.75; //ppf, gdy dodajemy ramke
    public static double ppfPauseProccess = 0.9; //ppf, gdy wstrzymujemy proces
    public static int tFrequency = 20; //okno czasowe badania ppf
    public static int minFrameLimit = 3; //minimalna ilośc ramek jaką proces musi utrzymać

    public static int tZoneModel = 20; //okno czasowe badania wss
    public static int cZoneModel = 10; //co jaki czas badamy wss

    public static double szamotanieFactor = 0.55;



    public static void main(String[] args) {
        Generator generator = new Generator();
        //generator.showTest();

        Simulator simulator = new Simulator(generator.getProcesses());
    }

    public static double randomDouble(double min, double max) {
        Random gen = new Random();
        return min + (max - min) * gen.nextDouble();
    }

    public static int randomInt(int min, int max) {
        Random gen = new Random();
        return gen.nextInt(max + 1 - min) + min;
    }
}
