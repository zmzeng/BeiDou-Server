package org.gms.server.life;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MonsterPartyExperienceTest {

    @Test
    void resolvesCurrentMapObjectsByAuthoritativePartyMemberId() {
        Candidate liveHuman = new Candidate(1, true);
        Candidate liveCompanion = new Candidate(5, true);
        Candidate staleCompanion = new Candidate(6, false);
        Candidate otherPlayer = new Candidate(9, true);

        List<Candidate> resolved = Monster.resolvePartyExperienceMembers(
                List.of(1, 5, 6),
                List.of(liveHuman, liveCompanion, staleCompanion, otherPlayer),
                Candidate::characterId,
                Candidate::presentInWorld);

        assertEquals(List.of(liveHuman, liveCompanion), resolved);
    }

    @Test
    void returnsNoMembersWithoutPartyMembershipIds() {
        Candidate online = new Candidate(1, true);

        assertEquals(List.of(), Monster.resolvePartyExperienceMembers(
                List.of(), List.of(online), Candidate::characterId, Candidate::presentInWorld));
    }

    private record Candidate(int characterId, boolean presentInWorld) {
    }
}
