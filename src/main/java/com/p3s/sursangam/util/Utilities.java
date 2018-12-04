package com.p3s.sursangam.util;

import com.p3s.sursangam.model.Role;
import com.p3s.sursangam.model.User;

public class Utilities {
    public static String getUserType(User user) {
        String userType = "";
        for(Role role : user.getRoles()) {
            if(role.getName().name().equals("ROLE_USER")) {
                userType ="USER";
            } else if(role.getName().name().equals("ROLE_GROUP")) {
                userType ="GROUP";
            } else {
                userType ="ADMIN";
            }
        }
        return userType;
    }
}
