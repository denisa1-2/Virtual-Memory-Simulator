package statistics;

import core.MemoryManager;
import model.Frame;

public class PerformanceMetrics {

    private final int totalPageFaults;
    private final int totalAccesses;
    private final double faultRate;
    private final double pFault;
    private final double averageMemoryAccessTime;
    private final double memoryUtilizationPercent;


    public PerformanceMetrics(MemoryManager manager, int[] pageSequence, double tRam, double tPenalty){
        this.totalPageFaults = manager.getFaults();
        this.totalAccesses = pageSequence.length;

        if(totalAccesses > 0){
            this.faultRate = (double) totalPageFaults / totalAccesses;
        }else{
            this.faultRate = 0.0;
        }
        this.pFault = this.faultRate;

        this.averageMemoryAccessTime = tRam + pFault * tPenalty;

        Frame[] frames = manager.getFrames();
        int occupied = 0;
        for(Frame f : frames){
            if(!f.isFree()){
                occupied++;
            }
        }
        if(frames.length > 0){
            this.memoryUtilizationPercent = (double) occupied / frames.length * 100.0;
        }else{
            this.memoryUtilizationPercent = 0.0;
        }
    }

    public int getTotalAccesses() {
        return totalAccesses;
    }

    public int getTotalPageFaults() {
        return totalPageFaults;
    }

    public double getFaultRate() {
        return faultRate;
    }

    public double getpFault() {
        return pFault;
    }

    public double getAverageMemoryAccessTime() {
        return averageMemoryAccessTime;
    }

    public double getMemoryUtilizationPercent() {
        return memoryUtilizationPercent;
    }
}
