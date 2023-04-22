package com.mobile.contact_manager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Number implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column
    String number;
    @Column
    String type;

    @ManyToOne
    @JsonIgnore
    @ToString.Exclude
    @JoinColumn(name = "contact_id", referencedColumnName = "id")
    Contact contact;
}
