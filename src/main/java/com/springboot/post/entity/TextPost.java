package com.springboot.post.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("TEXT")
public class TextPost extends Post {

}
