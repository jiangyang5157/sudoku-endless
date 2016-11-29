package com.gmail.jiangyang5157.sudoku.puzzle;

import java.io.Serializable;

public class PuzzleCache implements Serializable {

    private static final long serialVersionUID = 2305843009213693952L;

    private NodeCache[][] nodesCache = null;
    private Level level = null;

    public PuzzleCache() {
    }

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
