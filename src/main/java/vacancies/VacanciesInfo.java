package vacancies;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class VacanciesInfo {
    private List<Object> objects;
    private int total;
    private boolean more;
    private int subscriptionId;
    private boolean subscriptionActive;

    @JsonCreator
    public VacanciesInfo(@JsonProperty("objects") List<Object> objects, @JsonProperty("total") int total,
                         @JsonProperty("more") boolean more, @JsonProperty("subscription_id") int subscriptionId,
                         @JsonProperty("subscription_active") boolean subscriptionActive) {
        this.objects = objects;
        this.total = total;
        this.more = more;
        this.subscriptionId = subscriptionId;
        this.subscriptionActive = subscriptionActive;
    }
}
