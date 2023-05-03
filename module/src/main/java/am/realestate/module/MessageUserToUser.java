package am.realestate.module;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "messageusertouser")
public class MessageUserToUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String text;
    @JoinColumn(name = "for_To")
    private String forTo;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;
}
