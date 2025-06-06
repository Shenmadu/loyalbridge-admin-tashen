package org.example.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "partners")
public class Partner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String apiUrl;

    @Enumerated(EnumType.STRING)
    private AuthMethod authMethod;

    private double conversionRate;

    private boolean isEnabled;

    public enum AuthMethod {
        API_KEY, OAUTH, NONE
    }
}
