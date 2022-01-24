package com.sport.system.play.champion.championservice.security.service;


import com.sport.system.play.champion.championservice.presentation.presenter.Pager;
import com.sport.system.play.champion.championservice.security.presenter.TokenPresenter;
import com.sport.system.play.champion.championservice.security.presenter.UserLoginPresenter;
import com.sport.system.play.champion.championservice.security.presenter.UserPresenter;

public interface UserService {
    UserPresenter save(UserPresenter userPresenter);
    Pager getUsers(Integer page, Integer size);
    void deleteUser(String idUser);
    UserPresenter update(UserPresenter userPresenter) throws Throwable;
    TokenPresenter login(UserLoginPresenter presenter);
    TokenPresenter validate(String token);
}
