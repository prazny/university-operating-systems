package com;


import java.util.*;

public class Generator {
    /*ArrayList<Process> processes = new ArrayList<>();
    private int[] times = new ArrayList<>();
     startTimes = new ArrayList<>();*/
     private int[][] processesData;
     private int size = 0;


    public Generator(int processesCount, int timemMin, int timeMax, int[] distributionTime, int maxStartTime, int[] distributionStartTime) {
        processesData = new int[processesCount][2];
        generate(processesCount, timemMin, timeMax, distributionTime, 0);
        generate(processesCount, 1, maxStartTime, distributionStartTime, 1);
    }



    public void generate(int count, int min, int max, int[] distribution, int tabNo) {
        Random generator = new Random();

        int sum = 0;
        for (int k : distribution) {
            sum += k;
        }
        if(sum != 100) return;



        //przydzielanie liczb do danego przedziału według rozkładu
        int[] distributionCount = getDistributionCount(count, distribution);
        int[][] compartments = getCompartments(distribution.length, min, max);



        int counter = 0;
        for(int i=0; i<distributionCount.length;i++) {
            for(int j=0; j<distributionCount[i]; j++) {
                int s = generator.nextInt(compartments[i][1]-compartments[i][0]+1) + compartments[i][0];
                processesData[counter][tabNo] = s;
                counter++;
                size += s;
            }
        }

        List<Integer> swap = new ArrayList<>();
        for (int i = 0; i < counter; i++) {
            swap.add(processesData[i][tabNo]);
        }
        int c=0;
        Collections.shuffle(swap);
        for(Integer s : swap) {
            processesData[c][tabNo] = s;
            c++;
        }

        /*
        for(int i=0; i<distribution.length;i++) {
           // int processesCount = count * distribution[i]/10;
            //System.out.println(count * distribution[i] / 10);
            int r = (max-min)/distribution.length;

            for(int j=0; j<distributionCount[i]; j++) {
                int s = generator.nextInt(r-1)+(i*r)+min;
                processesData[counter][tabNo] = s;
                size += s;
                counter++;
            }
        }*/


        for(int i=0; i<count;i++) {
            //System.out.println(processesData[i][tabNo]);
        }
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

            //System.out.println(distributionCount[i][0] + " - " + distributionCount[i][1]);

        }
        return distributionCount;

    }

    public ArrayList<Process> getArrayList() {
        ArrayList<Process> processes = new ArrayList<>();

        for(int i=0; i<processesData.length; i++) {
            Process p = new Process(i, processesData[i][1], processesData[i][0]);
            processes.add(p);
        }

        return processes;
    }

    public Queue<Process> getQueue() {
        Queue<Process> processes = new LinkedList<>();
        for(int i=0; i<processesData.length; i++) {
            Process p = new Process(i, processesData[i][1], processesData[i][0]);
            processes.add(p);
        }
        return processes;
    }

    public void generatorStatistics() {
        int sumOfTime = 0;
        int maxTime = 0;
        int minTime = processesData[0][0];
        int shortTimeCount = 0;
        int meidumTimeCount = 0;
        int longTimeCount = 0;
        for(int i=0; i<processesData.length;i++) {
            sumOfTime += processesData[i][0];

            if(processesData[i][0] > maxTime) maxTime=processesData[i][0];
            if(processesData[i][0] < minTime) minTime=processesData[i][0];
        }
        System.out.println("Statystyki generatora");
        System.out.println("Ilość procesów: " + processesData.length);
        System.out.println("Najkrótsza faza: " + minTime);
        System.out.println("Najdłuższa faza: " + maxTime);
        System.out.println("Suma czasów trwania procesów: " + sumOfTime);
        System.out.format("%n%n%n");
    }



    public int getSize() {
        return size;
    }

}
