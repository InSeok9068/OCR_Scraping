package main.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class Brand {
    private final String url;
    @Setter
    private String categoryName;
}
