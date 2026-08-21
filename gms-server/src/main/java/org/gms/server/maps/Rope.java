package org.gms.server.maps;

/**
 * Climbable rope or ladder from WZ {@code ladderRope}.
 * {@code isLadder} is true when the WZ {@code l} field is 1.
 */
public record Rope(int x, int y1, int y2, boolean isLadder) {
    /** Top of the rope (smaller y = higher on screen). */
    public int topY() { return Math.min(y1, y2); }
    /** Bottom of the rope (larger y = lower on screen). */
    public int bottomY() { return Math.max(y1, y2); }
}
