package com.example.test.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User owner;

    private int score;

    // for contributors, based on requirements:
    //  - we can have many-to-many relationship between project and user
    //  - we can have a contribution entity with additional information
//    @ManyToMany
//    @JoinTable(
//            name = "project_user",
//            joinColumns = @JoinColumn(name = "project_id"),
//            inverseJoinColumns = @JoinColumn(name = "user_id"))
//    private Set<User> contributors;
}
