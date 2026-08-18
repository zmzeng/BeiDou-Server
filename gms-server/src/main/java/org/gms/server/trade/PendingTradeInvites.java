package org.gms.server.trade;

import org.gms.client.Character;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Pending trade invites targeting artificial characters (no real client to accept the packet).
 * Host capability used by {@link org.gms.server.Trade}; plugins may poll the same queue.
 */
public final class PendingTradeInvites {

    private static final PendingTradeInvites INSTANCE = new PendingTradeInvites();

    private final Map<Character, Character> queues = new ConcurrentHashMap<>();

    private PendingTradeInvites() {
    }

    public static PendingTradeInvites getInstance() {
        return INSTANCE;
    }

    public void addTradeRequest(Character artificial, Character partner) {
        queues.putIfAbsent(artificial, partner);
    }

    public Character getTradeRequest(Character artificial) {
        return queues.get(artificial);
    }

    public boolean hasPendingTrades(Character artificial) {
        return queues.containsKey(artificial);
    }

    public void removeTradeRequest(Character artificial) {
        queues.remove(artificial);
    }
}
