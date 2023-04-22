package com.mobile.contact_manager.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import java.io.Serializable;
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table
public class Name implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column
    String firstName;
    @Column
    String lastName;

    @JsonIgnore
    public String getFullName() {
        if(this.getFirstName() != null && this.getLastName() == null) {
            return this.getFirstName();
        } else if(this.getLastName() != null && this.getFirstName() == null) {
            return this.getLastName();
        } else if(this.getFirstName() != null && this.getLastName() != null){
            return this.getFirstName() + " " + this.getLastName();
        }
        return "";
    }
}
