package knights.zerotwo.pagination;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

@ParametersAreNonnullByDefault
public class OptionTree {

    @Nonnull
    private List<OptionTree> nextLocations = new ArrayList<>();

    private String description;
    private String shortDesc;

    private OptionTree(String description) {
        this.description = description;
        this.shortDesc = description;
    }

    private OptionTree(String description, String shortDesc) {
        this.description = description;
        this.shortDesc = shortDesc;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Nonnull
    public List<OptionTree> getNextLocations() {
        return nextLocations;
    }

    public void setNextLocations(List<OptionTree> nextLocations) {
        this.nextLocations = nextLocations;
    }
}
