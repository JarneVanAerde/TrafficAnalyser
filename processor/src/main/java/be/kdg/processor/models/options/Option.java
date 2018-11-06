package be.kdg.processor.models.options;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Simple POJO used to store an option.
 * Key-value pairs are chosen to dynamically create new options
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Option {
    @Id
    private String key;
    private double value;
}
