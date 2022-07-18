package com;

import java.util.*;
import java.util.stream.IntStream;

public class Generator {
    private int[] requestData;
    private int[] deadline;
    private int[] arrival;
    private int size = 0;

    private final int _requestCount;
    private final int _maxDiscPositions;
    private final double _realTimeRequestCount;
    private final int[] _distribution;
    private final int _maxTimeArrival;
    private final int _deadlineMin;
    private final int _deadlineMax;

    public Generator(int maxDiscPositions, int requestCount, double realTimeRequestChance, int[] distribution, int maxTimeArrival, int deadlineMin, int deadlineMax) {
        this._maxDiscPositions = maxDiscPositions;
        this._requestCount = requestCount;
        this._realTimeRequestCount = realTimeRequestChance * requestCount;
        this._distribution = distribution;
        this._maxTimeArrival = maxTimeArrival;
        this._deadlineMin = deadlineMin;
        this._deadlineMax = deadlineMax;
    }

    public void run() {
        requestData = new int[_requestCount];
        deadline = new int[_requestCount];
        arrival = new int[_requestCount];
        generate(_requestCount, 1, _maxDiscPositions, _distribution);
    }


    public void generate(int count, int min, int max, int[] distribution) {
        Random generator = new Random();

        int sum = 0;
        for (int k : distribution) {
            sum += k;
        }
        if(sum != 100) return;

        //przydzielanie liczb do danego przedziału według rozkładu
        int[] distributionCount = getDistributionCount(count, distribution);
        //System.out.println(Arrays.toString(distributionCount));

        int[][] compartments = getCompartments(distribution.length, min, max);



        int counter = 0;
        for(int i=0; i<distributionCount.length;i++) {
            for(int j=0; j<distributionCount[i]; j++) {
                requestData[counter] = generator.nextInt(compartments[i][1]-compartments[i][0]+1) + compartments[i][0];

                counter++;
            }
        }

        for(int i=0; i<_realTimeRequestCount; i++) {
            deadline[i] = generator.nextInt(_deadlineMax-_deadlineMin+1) +_deadlineMin;
        }

        for(int i=0; i<_realTimeRequestCount; i++) {
            arrival[i] = generator.nextInt(_maxTimeArrival);
        }

        List<Integer> swap = new ArrayList<>();
        List<Integer> swapDeadline = new ArrayList<>();
        for (int i = 0; i < counter; i++) {
            swap.add(requestData[i]);
            swapDeadline.add(deadline[i]);
        }

        int c=0;
        Collections.shuffle(swap);
        Collections.shuffle(swap);
        Collections.shuffle(swapDeadline);
        Collections.shuffle(swapDeadline);
        for(Integer s : swap) {
            requestData[c] = s;
            c++;
        }
        c=0;
        for(Integer s : swapDeadline) {
            deadline[c] = s;
            c++;
        }

        for(int i=0; i<count;i++) {
           // System.out.println(requestData[i]);
        }

    }

    private boolean isInArray(int val) {
        for (int r : requestData) {
            if (r == val) return true;

        }
        return false;
    }

    public int[] getDistributionCount(int count, int[] distribution){
        int[] distributionCount = new int[distribution.length];
        int allocated = 0;

        for(int i=0; i<distribution.length; i++) {
            distributionCount[i] =  distribution[i]* count/100;
            allocated += distribution[i]* count/100;
        }

        if(allocated < count) {
            for(int i=allocated; i<count;i++){
                distributionCount[i%distribution.length]++;
            }
        }
        // System.out.println(Arrays.toString(distributionCount));
        return distributionCount;
    }

    public int[][] getCompartments(int compCount, int min, int max) {
        int count = max-min+1;
        int[][] distributionCount = new int[compCount][2];

        int minLocal;
        int maxLocal;
        int countInComp = count/compCount;

        for(int i=0; i<compCount; i++) {
            minLocal = min+(countInComp*i);
            maxLocal = minLocal + countInComp;
            distributionCount[i][0] = minLocal;
            distributionCount[i][1] = maxLocal-1;

        }
        return distributionCount;

    }

    public ArrayList<Request> getArrayList() {
        ArrayList<Request> requests = new ArrayList<>();

        for(int i=0; i<requestData.length; i++) {
            Request r = new Request(i, requestData[i], deadline[i], arrival[i]);
            requests.add(r);
        }

        return requests;
    }

    public ArrayList<Request> getArrayList(int[] positions) {
        ArrayList<Request> requests = new ArrayList<>();
        if(positions.length != requestData.length) return null;

        for(int i=0; i<requestData.length; i++) {
            Request r = new Request(i, positions[i], deadline[i], 0);
            requests.add(r);
        }

        return requests;
    }


}
