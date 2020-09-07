package components;
import interfaces.BlockCreator;
import sprites.Block;
import java.util.Map;
/**
 * The type Blocks from symbols factory.
 */
public class BlocksFromSymbolsFactory {

    private Map<String, Integer> spacerWidths;
    private Map<String, BlockCreator> blockCreators;

    /**
     * Instantiates a new Blocks from symbols factory.
     *
     * @param spacerWidths  the spacer widths
     * @param blockCreators the block creators
     */
    public BlocksFromSymbolsFactory(Map<String, Integer> spacerWidths, Map<String, BlockCreator> blockCreators) {
        this.spacerWidths = spacerWidths;
        this.blockCreators = blockCreators;
    }


    // Returns true if 's' is a valid space symbol.
    public boolean isSpaceSymbol(String s) {
        return spacerWidths.containsKey(s);
    }

    // Returns true if 's' is a valid block symbol.
    public boolean isBlockSymbol(String s) {
        return blockCreators.containsKey(s);
    }


    /**
     *  Return a block according to the definitions associated
     * with symbol s. The block will be located at position (xpos, ypos).
     */
    public Block getBlock(String s, int xpos, int ypos) {
        BlockCreator bc = blockCreators.get(s);
        return bc.create(xpos, ypos);
    }

    // Returns the width in pixels associated with the given spacer-symbol.
    public int getSpaceWidth(String s) {
        return spacerWidths.get(s);
    }
}
