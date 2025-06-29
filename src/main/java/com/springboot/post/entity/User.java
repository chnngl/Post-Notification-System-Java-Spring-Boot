package com.springboot.post.entity;

import com.springboot.post.pattern.Observer;
import com.springboot.post.pattern.Subject;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;
import java.util.stream.Collectors;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor


@Entity
@Table(name = "users")
public class User implements Observer, Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String username;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Post> posts = new ArrayList<Post>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_following", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "following_id") )
    private Set<User> following = new HashSet<>();
    @ManyToMany(mappedBy = "following",fetch = FetchType.EAGER)
    private Set<User> observers = new HashSet<>();

    @Override
    public void update(String message) {
        System.out.println(this.username + " received notification: " + message);
    }

    @Override
    public void addObserver(User observer) {
        observers.add(observer);
        printObservers();
    }

    @Override
    public void removeObserver(User observer) {
        observers.remove(observer);
        printObservers();
    }

    @Override
    public void notifyObservers(String message) {
        List<User> observers = this.getObservers().stream().collect(Collectors.toList());
        for (User observer : observers) {
            observer.update(message);
        }
    }

    public void printObservers() {
        List<User> observers = this.getObservers().stream().collect(Collectors.toList());
        System.out.println("Observers of " + this.getUsername() + ":");
        for (User observer : observers) {
            System.out.println(observer.getUsername());
        }
    }

    //add below methods to ensure that instances of User class can be
    // properly compared and used in collections like Set
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass())
            return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
