package algorithms;

import model.Frame;
import model.PageTable;

public class OPT implements PageReplacementAlgorithm{

    private int predict(int[] pagesSequence, Frame[] frames, int pn, int index){
        int res = -1;
        int farthest = index;

        for(int i = 0; i < frames.length; i++){
            Frame frame = frames[i];
            if(frame.isFree()){
                return i;
            }
            int framePage = frame.getPage().getId();
            int j;
            for(j = index; j < pn; j++){
                if(framePage == pagesSequence[j]){
                    if(j > farthest){
                        farthest = j;
                        res = i;
                    }
                    break;
                }
            }
            if(j == pn){
                return i;
            }
        }
        if(res == -1){
            return frames.length - 1;
        }
        return res;
    }

    @Override
    public int selectVictim(int[] pageSequence, Frame[] frames, PageTable pageTable, int currentIndex) {
        int pn = pageSequence.length;
        int start = Math.min(currentIndex+1, pn);
        return predict(pageSequence, frames, pn, start);
    }
}
