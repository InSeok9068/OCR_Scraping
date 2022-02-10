package main.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Category {
    private final String name;
    private final String url;
}
