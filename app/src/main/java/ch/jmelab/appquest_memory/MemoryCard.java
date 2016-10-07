package ch.jmelab.appquest_memory;

/**
 * Simple class for a memory card.
 */
public class MemoryCard {
    private String mPath;
    private String mCode;

    public MemoryCard(String path, String code) {
        this.mPath = path;
        this.mCode = code;
    }

    public MemoryCard() {}

    public String getPath() {
        return mPath;
    }

    public void setPath(String path) {
        this.mPath = path;
    }

    public String getCode() {
        return mCode;
    }

    public void setCode(String code) {
        this.mCode = code;
    }
}
