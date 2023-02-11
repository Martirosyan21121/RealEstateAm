package am.realestate.reposerviceconfig.secur;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

public class CurrentUser extends User {
   private final am.realestate.module.User user;

    public CurrentUser(am.realestate.module.User user1) {
        super(user1.getEmail(), user1.getPassword(), AuthorityUtils.createAuthorityList(user1.getUserType().name()));
        this.user = user1;
    }
    public am.realestate.module.User getUser() {
        return this.user;
    }
}
