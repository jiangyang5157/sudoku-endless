package com.gmail.jiangyang5157.sudoku.puzzle;

public class PuzzleCache {

    private NodeCache[][] nodesCache = null;

    private Level level = null;

    public PuzzleCache(NodeCache[][] nodesCache, Level level) {
        setNodesCache(nodesCache);
        setLevel(level);
    }

    public NodeCache[][] getNodesCache() {
        return nodesCache;
    }

    public void setNodesCache(NodeCache[][] nodesCache) {
        this.nodesCache = nodesCache;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }
}
