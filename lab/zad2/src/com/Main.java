package com;

import com.Algorithms.*;
import com.RealTimeAlgorithms.*;

public class Main {
    public static int _cycles = 2; // ilośc cykli
    public static int _maxDiscCylinders = 200; //ilość cylindrów
    public static int _startCylinder = 20; // numer cylindra nad którym jest głowica w momecie startu
    public static int _scanFistDirection = -1; // -1 w lewo, 1 w prawo
    public static int _requestCount = 500; // ilość zadań w jednym cyklu

    public static double _realTimeRequestChance = 0.2; // szansa na żadanie typu realtime 0-1 w takich scenariuszach
    public static int[] _distributionBlockPositions = new int[]{100,0,0,0,0, 0 ,0 , 0, 0, 0, 0, 0}; //rozkład żądań
    public static int _maxTimeArrival = 1000; // maksymalny moment wejścia żądania
    // założenia: _maxDiscBlocks, _requestCount : liczba 10^n, gdzie n to liczba naturalna
    // distributionBlockPositions : 5 liczb podzielnych przez 10 (razem z 0), suma=100

    public static int _deadlineMin = 40; // minimalny deadline dla żądań realtime
    public static int _deadlineMax = 40; // maksymalny deadline dla żądań realtime

    public static void main(String[] args) {


        Generator gen = new Generator(_maxDiscCylinders, _requestCount,
                _realTimeRequestChance, _distributionBlockPositions, _maxTimeArrival, _deadlineMin, _deadlineMax);

        //ArrayList<Request> testArray = gen.getArrayList(new int[]{98,183,37,122,14,124,65,67});

        Algorithms[] algs = new Algorithms[]{
                new FCFS(),
                new SSTF(),
                new SCAN(),
                new CSCAN(),
        };

        RealTimeAlgorithms[] RTalgs = new RealTimeAlgorithms[] {
            null,
            new EDF(),
            new FDSCAN(),
        };
        Results results = new Results(algs, RTalgs, gen);

    }
}
