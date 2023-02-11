package am.realestate.module;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String surname;
    private String email;
    @JoinColumn(name = "phone_number")
    private int phoneNumber;
    @JoinColumn(name = "local_date")
    private LocalDate localDate;
    private int token;
    @JoinColumn(name = "is_active")
    private boolean isActive;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && phoneNumber == user.phoneNumber && token == user.token && isActive == user.isActive && Objects.equals(name, user.name) && Objects.equals(surname, user.surname) && Objects.equals(email, user.email) && Objects.equals(localDate, user.localDate) && Objects.equals(password, user.password) && userType == user.userType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, email, phoneNumber, localDate, token, isActive, password, userType);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", localDate=" + localDate +
                ", token=" + token +
                ", isActive=" + isActive +
                ", password='" + password + '\'' +
                ", userType=" + userType +
                '}';
    }
}
