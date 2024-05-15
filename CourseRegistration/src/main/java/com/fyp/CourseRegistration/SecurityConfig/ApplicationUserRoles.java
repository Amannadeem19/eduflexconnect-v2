package com.fyp.CourseRegistration.SecurityConfig;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum ApplicationUserRoles {
    ADMIN(Sets.newHashSet(ApplicationUserPermissions.STUDENT_READ,
            ApplicationUserPermissions.STUDENT_WRITE,
            ApplicationUserPermissions.ANNOUNCEMENT_READ,
            ApplicationUserPermissions.TEACHER_WRITE,
            ApplicationUserPermissions.TEACHER_READ
            )),
    MANAGER_ACADEMICS(Sets.newHashSet( ApplicationUserPermissions.ANNOUNCEMENT_WRITE,
            ApplicationUserPermissions.ANNOUNCEMENT_READ)),
    SOCIETY_WALE(Sets.newHashSet( ApplicationUserPermissions.ANNOUNCEMENT_WRITE,
            ApplicationUserPermissions.ANNOUNCEMENT_READ)),

    TEACHER(Sets.newHashSet(ApplicationUserPermissions.TEACHER_WRITE,
            ApplicationUserPermissions.TEACHER_READ,
            ApplicationUserPermissions.ANNOUNCEMENT_WRITE,
            ApplicationUserPermissions.ANNOUNCEMENT_READ,
            ApplicationUserPermissions.STUDENT_READ
    )),
    STUDENT(Sets.newHashSet(
            ApplicationUserPermissions.ANNOUNCEMENT_WRITE,
            ApplicationUserPermissions.ANNOUNCEMENT_READ,
            ApplicationUserPermissions.STUDENT_WRITE,
            ApplicationUserPermissions.STUDENT_READ
    ));
    private final Set<ApplicationUserPermissions> permissions;
    ApplicationUserRoles(Set<ApplicationUserPermissions> permissions){
        this.permissions = permissions;
    }

    public Set<ApplicationUserPermissions> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities(){
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("Role_"+ this.name()));
        return permissions;
    }
}
