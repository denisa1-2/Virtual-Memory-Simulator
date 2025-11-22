package core;

import algorithms.FIFO;
import algorithms.PageReplacementAlgorithm;
import model.Frame;
import model.Page;
import model.PageTable;
import model.PageTableEntry;

public class MemoryManager {

    private Frame[] frames;
    private PageTable pageTable;
    private PageReplacementAlgorithm algorithm;

    private int hits = 0;
    private int faults = 0;
    private long time = 0;

    public MemoryManager(int numberOfFrames, int numberOfPages, PageReplacementAlgorithm algorithm) {
        this.algorithm = algorithm;

        frames = new Frame[numberOfFrames];
        for(int i = 0; i < numberOfFrames; i++) {
            frames[i] = new Frame(i);
        }
        pageTable = new PageTable(numberOfPages);
    }

    public void runSimulation(int[] pageSequence) {
       for (int i=0;i<pageSequence.length;i++) {
           runSingle(pageSequence,i);
       }
    }

    private boolean runSingle(int[] sequence, int index) {
        int pagenumber = sequence[index];
        time++;

        PageTableEntry entry = pageTable.getEntry(pagenumber);

        //hit
        if(entry.isValid()){
            hits++;
            entry.setReferenced(true);
            entry.setLastAccessTime(time);
            return true;
        }

        //miss
        faults++;

        int freeFrame = findFreeFrame();
        if(freeFrame != -1){
            loadPageIntoFrame(pagenumber, freeFrame);
        }else{
            int victimFrame = algorithm.selectVictim(sequence, frames, pageTable,index);
            replacePage(pagenumber, victimFrame);
        }
        return false;
    }

    public static class Step{
        public final int page;
        public final boolean hit;
        public final int frameIndex;
        public final Frame[] snapshot;

        public Step(int page, boolean hit, int frameIndex, Frame[] snapshot) {
            this.page = page;
            this.hit = hit;
            this.frameIndex = frameIndex;
            this.snapshot = snapshot;
        }
    }

    public void reset(){
        time = 0;
        hits = 0;
        faults = 0;

        for(Frame f : frames){
            f.loadPage(null);
        }

        for(int i=0;i<pageTable.size();i++){
            PageTableEntry e = pageTable.getEntry(i);
            e.setValid(false);
            e.setReferenced(false);
            e.setModified(false);
            e.setFrameNumber(-1);
            e.setLastAccessTime(-1);
        }
    }

    public Step step(int pageNumber, int[] sequence, int index){
        boolean hit = runSingle(sequence, index);
        int frameIndex = findFrame(pageNumber);
        Frame[] snapshot = copyFrames();

        return new Step(pageNumber, hit, frameIndex, snapshot);
    }


    private int findFreeFrame() {
        for(int i = 0; i < frames.length; i++) {
            if(frames[i].isFree()){
                return i;
            }
        }
        return -1;
    }

    private void loadPageIntoFrame(int pageNumber, int frameIndex) {
        Frame frame = frames[frameIndex];
        frame.loadPage(new Page(pageNumber));

        PageTableEntry entry = pageTable.getEntry(pageNumber);
        entry.setValid(true);
        entry.setFrameNumber(frameIndex);
        entry.setReferenced(true);
        entry.setLastAccessTime(time);

        if(algorithm instanceof FIFO fifo){
            fifo.registerPageLoad(pageNumber);
        }
    }

    private void replacePage(int newPage, int victimIndex) {
        Frame victimFrame = frames[victimIndex];
        Page oldPage = victimFrame.getPage();

        PageTableEntry oldEntry = pageTable.getEntry(oldPage.getId());
        oldEntry.setValid(false);

        victimFrame.loadPage(new Page(newPage));
        PageTableEntry newEntry = pageTable.getEntry(newPage);
        newEntry.setValid(true);
        newEntry.setFrameNumber(victimIndex);
        newEntry.setReferenced(true);
        newEntry.setLastAccessTime(time);

        if(algorithm instanceof FIFO fifo){
            fifo.registerPageLoad(newPage);
        }
    }

    private int findFrame(int page) {
        for(int i = 0; i < frames.length; i++) {
            if(!frames[i].isFree() && frames[i].getPage().getId() == page){
                return i;
            }
        }
        return -1;
    }

    private Frame[] copyFrames() {
        Frame[] arr = new Frame[frames.length];
        for(int i = 0; i < frames.length; i++) {
            arr[i] =  new Frame(frames[i].getFrameNumber());
            if(!frames[i].isFree()){
                arr[i].loadPage(new Page(frames[i].getPage().getId()));
            }
        }
        return arr;
    }

    public int getHits() {
        return hits;
    }
    public int getFaults() {
        return faults;
    }
    public Frame[] getFrames() {
        return frames;
    }
    public PageTable getPageTable() {
        return pageTable;
    }

}

