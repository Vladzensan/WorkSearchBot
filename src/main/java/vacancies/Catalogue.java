package vacancies;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Catalogue {
    private String titleRus;
    private String urlRus;
    private String title;
    private String titleTrimmed;
    private int key;
    private Object[] positions;

    @JsonCreator
    public Catalogue(@JsonProperty("title_rus") String titleRus, @JsonProperty("url_rus") String urlRus,
                     @JsonProperty("title") String title, @JsonProperty("title_trimmed") String titleTrimmed,
                     @JsonProperty("key") int key, @JsonProperty("positions") Object[] positions) {
        this.titleRus = titleRus;
        this.urlRus = urlRus;
        this.title = title;
        this.titleTrimmed = titleTrimmed;
        this.key = key;
        this.positions = positions;
    }
}
