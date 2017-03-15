package com.x.broker.domain;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * A user persistent entity bean.
 *
 * @author Akis Papadopoulos
 */
@Entity
@Table(name = "users",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = "username")})
@JacksonXmlRootElement(localName = "user")
public class User extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    @JacksonXmlProperty(isAttribute = true)
    private Long id;

    @Column(name = "username", unique = true, nullable = false, length = 25)
    @JacksonXmlProperty(isAttribute = true)
    private String username;

    @Column(name = "enabled", unique = false, nullable = false)
    @JacksonXmlProperty(isAttribute = true)
    private boolean enabled;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createdDate", unique = false, nullable = false)
    @JacksonXmlProperty(isAttribute = true)
    private Date createdDate;

    public User() {
        enabled = true;
        createdDate = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public int hashCode() {
        int hash = 5;

        hash = 23 * hash + Objects.hashCode(this.id);
        hash = 23 * hash + Objects.hashCode(this.username);

        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object == null) {
            return false;
        }

        if (this.getClass() != object.getClass()) {
            return false;
        }

        final User other = (User) object;

        if (!Objects.equals(this.username, other.username)) {
            return false;
        }

        if (!Objects.equals(this.id, other.id)) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id
                + ", username=" + username
                + ", enabled=" + enabled
                + ", createdDate=" + createdDate
                + "}";
    }
}
