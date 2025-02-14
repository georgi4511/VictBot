package com.github.georgi4511.victbot.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "vict_user")
public class VictUser {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long id;

    @NonNull
    String discordId;

    @OneToMany(mappedBy = "victUser", cascade = CascadeType.ALL)
    Set<Reminder> reminders;

    @OneToMany(mappedBy = "victUser", cascade = CascadeType.ALL)
    Set<Bookmark> bookmarks;
}
