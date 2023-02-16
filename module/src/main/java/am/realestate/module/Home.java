package am.realestate.module;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "home")
public class Home {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String description;
    @JoinColumn(name = "home_type")
    @Enumerated(EnumType.STRING)
    private HomeType homeType;
    private int bedroom;
    private int livingroom;
    private int kitchen;
    private int parking;
    @JoinColumn(name = "home_code")
    private int homeCode;
    private int price;
    private String address;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;


}
