package net.bluebunnex.shiveringhills.src;

public interface IPlayer {

    String getStatusText();

    /**
     *
     * @param vanillaHealthRestored value associated with health restored in vanilla game (but here used to calculate
     *                              hunger restored)
     * @return true if item was used, false otherwise
     */
    boolean feed(int vanillaHealthRestored);
}
