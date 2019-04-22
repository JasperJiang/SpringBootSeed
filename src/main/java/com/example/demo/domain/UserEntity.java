package com.example.demo.domain;

import com.example.demo.vo.UserVo;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "USER")
@Getter
@Setter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private Long id;

    @Column(name = "userName", nullable = false)
    private String userName;

    @Column(name = "password", nullable = false)
    private String password;


    public UserVo toUserVo(){
        return UserVo.builder()
                .id(this.getId())
                .userName(this.getUserName())
                .build();
    }
}
