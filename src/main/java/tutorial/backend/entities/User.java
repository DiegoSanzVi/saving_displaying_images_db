package tutorial.backend.entities;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import java.util.List;

/**
 * User entity class for handling user information and roles.
 */
@Entity(name = "UserInfo") // "User" is a reserved word in some SQL implementations
public class User extends IdentifiedStorageObject {

    private String name;
    private String surname;
    private String email;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] profilePicture;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }

    @Override
    public String toString() {
        return name + " " + surname;
    }
}
