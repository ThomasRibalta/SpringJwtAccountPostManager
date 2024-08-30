package dev.thomasrba.um.entity;

import dev.thomasrba.um.entity.post.Category;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class PostRequest {
    private Integer id;
    private String title;
    private String content;
    private Category category;
}
