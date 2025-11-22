package model;

public class PageTableEntry {
    private boolean valid;
    private int frameNumber;
    private boolean referenced;
    private boolean modified;
    private long lastAccessTime;

    public PageTableEntry(){
        this.valid = false;
        this.frameNumber = -1;
        this.referenced = false;
        this.modified = false;
        this.lastAccessTime = -1;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public int getFrameNumber() {
        return frameNumber;
    }

    public void setFrameNumber(int frameNumber) {
        this.frameNumber = frameNumber;
    }

    public boolean isReferenced() {
        return referenced;
    }

    public void setReferenced(boolean referenced) {
        this.referenced = referenced;
    }

    public boolean isModified() {
        return modified;
    }

    public void setModified(boolean modified) {
        this.modified = modified;
    }

    public long getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(long lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }
}
