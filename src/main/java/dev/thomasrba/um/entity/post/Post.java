package dev.thomasrba.um.entity.post;


import dev.thomasrba.um.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "_post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String content;
    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "USER_ID", referencedColumnName = "id")
    private User user;
    @Enumerated(EnumType.STRING)
    private Category category;
    private Date createdAt;
    private Date updatedAt;

}
