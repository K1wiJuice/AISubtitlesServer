package com.AISubtitles.Server.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Accessors(chain = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "user_auths")
public class UserAuths {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_auths_id")
    private Integer UserAuthsId;

    @Column
    private Integer userId;

    @Column
    private String loginType;

    @Column
    private String userPassword;
}
