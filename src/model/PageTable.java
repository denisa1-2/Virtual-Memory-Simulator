package model;

public class PageTable {
    private PageTableEntry[] entries;

    public PageTable(int numberOfPages) {
        entries = new PageTableEntry[numberOfPages];
        for (int i = 0; i < numberOfPages; i++) {
            entries[i] = new PageTableEntry();
        }
    }

    public PageTableEntry getEntry(int pageNumber) {
        return entries[pageNumber];
    }

    public int size(){
        return entries.length;
    }
}
