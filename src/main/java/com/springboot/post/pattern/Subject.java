package com.springboot.post.pattern;


import com.springboot.post.entity.User;


public interface Subject {
    void addObserver(User observer);
    void removeObserver(User observer);
    void notifyObservers(String message);
}
