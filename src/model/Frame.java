package model;

public class Frame {
    private int frameNumber;
    private Page page;

    public Frame(int frameNumber) {
        this.frameNumber = frameNumber;
    }

    public boolean isFree(){
        return page == null;
    }

    public void loadPage(Page page) {
        this.page = page;
    }

    public Page getPage(){
        return page;
    }

    public int getFrameNumber() {
        return frameNumber;
    }
}
