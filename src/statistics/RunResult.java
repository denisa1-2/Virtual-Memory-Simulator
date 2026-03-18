package statistics;

public class RunResult {

    private final String algorithm;
    private final int frames;
    private final int pages;
    private final int totalAccesses;
    private final int pageFaults;
    private final double faultRate;
    private final double averageMemoryAccessTime;
    private final double memoryUtilizationPercent;

    public RunResult(String algorithm, int frames, int pages, int totalAccesses, int pageFaults, double faultRate, double averageMemoryAccessTime, double memoryUtilizationPercent) {
        this.algorithm = algorithm;
        this.frames = frames;
        this.pages = pages;
        this.totalAccesses = totalAccesses;
        this.pageFaults = pageFaults;
        this.faultRate = faultRate;
        this.averageMemoryAccessTime = averageMemoryAccessTime;
        this.memoryUtilizationPercent = memoryUtilizationPercent;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public int getFrames() {
        return frames;
    }

    public int getPages() {
        return pages;
    }

    public int getTotalAccesses() {
        return totalAccesses;
    }

    public int getPageFaults() {
        return pageFaults;
    }

    public double getFaultRate() {
        return faultRate;
    }

    public double getAverageMemoryAccessTime() {
        return averageMemoryAccessTime;
    }

    public double getMemoryUtilizationPercent() {
        return memoryUtilizationPercent;
    }
}
