package com.github.georgi4511.victbot.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Impressions {

    @Id
    @Column(name = "vict_guild_id")
    private Long id;

    private Integer badBotCount = 0;

    private Integer goodBotCount = 0;

    @NonNull
    @MapsId
    @OneToOne
    @JoinColumn(name = "vict_guild_id")
    private VictGuild victGuild;
}
