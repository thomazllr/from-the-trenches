package com.thomazllr.anime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AnimePutRequest {
    private long id;
    private String name;
}
