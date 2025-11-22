package algorithms;

import model.Frame;
import model.PageTable;

import java.util.LinkedList;
import java.util.Queue;

public class FIFO implements PageReplacementAlgorithm {

    private final Queue<Integer> queue = new LinkedList<>();

    @Override
    public int selectVictim(int[] pages, Frame[] frames, PageTable table, int currentIndex){
        Integer victimPage = queue.poll();

        if(victimPage == null){
            return 0;
        }
        for(int i = 0; i<frames.length; i++) {
            if (!frames[i].isFree() && frames[i].getPage().getId() == victimPage) {
                return i;
            }
        }
        return 0;
    }


    public void registerPageLoad(int pageNumber){
        queue.add(pageNumber);
    }
}
