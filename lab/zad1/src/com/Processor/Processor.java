package com.Processor;

import com.Process;

import java.util.List;

public interface Processor {
    private void symulate() { }
    String getMethodName();
    int getCycles();
    int getUsingCycles();
    int getDoneProcessesCount();
    int getSwitchingCount();

    List<Process> getDoneProcesses();
}
