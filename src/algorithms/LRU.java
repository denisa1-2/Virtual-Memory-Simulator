package algorithms;

import model.Frame;
import model.PageTable;
import model.PageTableEntry;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class LRU implements PageReplacementAlgorithm {

    @Override
    public int selectVictim(int[] pages, Frame[] frames, PageTable pageTable, int currentIndex){
        long oldestTime = Long.MAX_VALUE;
        int victimIndex = -1;

        for(int i = 0; i < frames.length; i++){
            int pageId = frames[i].getPage().getId();
            PageTableEntry entry = pageTable.getEntry(pageId);

            if(entry.getLastAccessTime() < oldestTime){
                oldestTime = entry.getLastAccessTime();
                victimIndex = i;
            }
        }
        return victimIndex;
    }

}
