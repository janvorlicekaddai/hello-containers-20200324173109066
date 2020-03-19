package cz.addai.components.session;

import java.util.List;

public class PotentialRecipients {

    private final List<String> names;

    public PotentialRecipients(List<String> names) {
        this.names = names;
    }

    public List<String> getNames() {
        return names;
    }
}
