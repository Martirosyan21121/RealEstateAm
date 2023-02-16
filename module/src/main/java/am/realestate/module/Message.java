package am.realestate.module;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String text;
    private String subject;
    @JoinColumn(name = "local_time")
    private LocalTime localTime;

    @JoinColumn(name = "local_date")
    private LocalDate localDate;
    @JoinColumn(name = "for_to")
    private String forTo;
    @JoinColumn(name = "user_id")
    @ManyToOne
    private User userId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return id == message.id && Objects.equals(text, message.text) && Objects.equals(subject, message.subject) && Objects.equals(localTime, message.localTime) && Objects.equals(forTo, message.forTo) && Objects.equals(userId, message.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, subject, localTime, forTo, userId);
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", subject='" + subject + '\'' +
                ", localTime=" + localTime +
                ", forTo='" + forTo + '\'' +
                ", userId=" + userId +
                '}';
    }
}