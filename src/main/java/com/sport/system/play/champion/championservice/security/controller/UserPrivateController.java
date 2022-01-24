package com.sport.system.play.champion.championservice.security.controller;

import com.sport.system.play.champion.championservice.presentation.presenter.Pager;
import com.sport.system.play.champion.championservice.security.presenter.UserPresenter;
import com.sport.system.play.champion.championservice.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserPrivateController {

    @Autowired
    UserService userService;

    @GetMapping("/private/user/getUsers")
    public Pager getUsers(@RequestParam Integer page,
                                    @RequestParam Integer size) {
        return userService.getUsers(page, size);
    }

    @DeleteMapping("/private/user/deleteById")
    public void deleteById(@RequestParam String idUser){
        userService.deleteUser(idUser);
    }

    @PutMapping("/private/user/update")
    public UserPresenter updateUser(@RequestBody UserPresenter presenter) throws Throwable {
        return userService.update(presenter);
    }
}
