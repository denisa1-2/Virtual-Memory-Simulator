package algorithms;

import model.Frame;
import model.PageTable;

public interface PageReplacementAlgorithm {

    int selectVictim(int[] pageSequence, Frame[] frames, PageTable pageTable, int currentIndex);
}
