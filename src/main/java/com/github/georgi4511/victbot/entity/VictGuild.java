package com.github.georgi4511.victbot.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "vict_guild")
public class VictGuild {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long id;

    @Column(unique = true)
    @NonNull
    String discordId;

    @OneToMany(mappedBy = "victGuild", cascade = CascadeType.ALL)
    Set<Reminder> reminders;

    @OneToMany(mappedBy = "victGuild", cascade = CascadeType.ALL)
    Set<Bookmark> bookmarks;

    @OneToOne(mappedBy = "victGuild", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    Impressions impressions;
}
