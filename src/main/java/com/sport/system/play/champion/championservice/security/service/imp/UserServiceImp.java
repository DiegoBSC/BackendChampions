package com.sport.system.play.champion.championservice.security.service.imp;

import com.sport.system.play.champion.championservice.presentation.presenter.Pager;
import com.sport.system.play.champion.championservice.security.entity.Permissions;
import com.sport.system.play.champion.championservice.security.entity.Rol;
import com.sport.system.play.champion.championservice.security.entity.User;
import com.sport.system.play.champion.championservice.security.presenter.*;
import com.sport.system.play.champion.championservice.security.provider.JwtProvider;
import com.sport.system.play.champion.championservice.security.repository.UserRepository;
import com.sport.system.play.champion.championservice.security.service.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImp implements UserService {

    Logger logger = LoggerFactory.getLogger(UserServiceImp.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    public UserPresenter save(UserPresenter userPresenter) {
        User user = userRepository.save(buildEntityFromPresenter(userPresenter));
        return buildPresenterFromEntity(user);
    }

    @Override
    public Pager getUsers(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Pager paginator = new Pager();
        Page<User> pageResult;

        pageResult = userRepository.findAll(pageable);

        paginator.setTotalPages(pageResult.getTotalPages());
        paginator.setTotalElements(pageResult.getTotalElements());
        paginator.setData(pageResult.get().map(this::buildPresenterFromEntity).collect(Collectors.toSet()));
        return paginator;
    }

    @Override
    public void deleteUser(String idUser) {
        userRepository.deleteById(UUID.fromString(idUser));
    }

    @Override
    public UserPresenter update(UserPresenter userPresenter) throws Exception {

        User entityDb = userRepository.findById(userPresenter.getId()).orElse(null);

        if (entityDb == null)
            throw new Exception("Registro no encontrado");

        User user = buildEntityFromPresenter(userPresenter);
        user.setCreatedDate(entityDb.getCreatedDate());
        user.setPassword(entityDb.getPassword());
        user.setId(entityDb.getId());
        userRepository.save(user);
        return buildPresenterFromEntity(user);
    }

    @Override
    public TokenPresenter login(UserLoginPresenter presenter) {

        Optional<User> user = userRepository.findByUsername(presenter.getUsername());

        if(!user.isPresent())
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Not credential valid");

        if(passwordEncoder.matches(presenter.getPassword(), user.get().getPassword())){
            UserPresenter userPresenter = buildPresenterFromEntity(user.get());
            userPresenter.setPassword("");
            return new TokenPresenter(jwtProvider.createToken(user.get()), userPresenter);
        }else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Not credential valid");
        }
    }

    @Override
    public TokenPresenter validate(String token) {
        if (!jwtProvider.validate(token))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Roles missing");
        String username = jwtProvider.getUserName(token);
        if(!userRepository.findByUsername(username).isPresent())
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Roles missing");
        return TokenPresenter.builder().token(token).build();
    }

    private User buildEntityFromPresenter(UserPresenter presenter) {
        User user = new User();
        modelMapper.map(presenter, user);
        user.setPassword(passwordEncoder.encode(presenter.getPassword()));
        user.setRoles(presenter.getRolesPresenter()
                .stream().map(this::buildEntityRolFromPresenter).collect(Collectors.toSet()));
        user.setCreatedDate(new Date());
        return user;
    }

    private Rol buildEntityRolFromPresenter(RolPresenter presenter) {
        Rol rol = new Rol();
        modelMapper.map(presenter, rol);
        rol.setPermissions(presenter.getPermissionsPresenters()
                .stream().map(this::buildEntityPermissionsFromPresenter).collect(Collectors.toSet()));
        return rol;
    }

    private Permissions buildEntityPermissionsFromPresenter(PermissionsPresenter presenter) {
        Permissions permissions = new Permissions();
        modelMapper.map(presenter, permissions);
        return permissions;
    }

    private UserPresenter buildPresenterFromEntity(User entity) {
        UserPresenter presenter = new UserPresenter();
        modelMapper.map(entity, presenter);
        presenter.setRolesPresenter(entity.getRoles()
                .stream().map(this::buildPresenterRolFromEntity).collect(Collectors.toSet()));
        return presenter;
    }

    private RolPresenter buildPresenterRolFromEntity(Rol entity) {
        RolPresenter presenter = new RolPresenter();
        modelMapper.map(entity, presenter);
        presenter.setPermissionsPresenters(entity.getPermissions()
                .stream().map(this::buildPresenterPermissionsFromEntity).collect(Collectors.toSet()));
        return presenter;
    }

    private PermissionsPresenter buildPresenterPermissionsFromEntity(Permissions entity) {
        PermissionsPresenter presenter = new PermissionsPresenter();
        modelMapper.map(entity, presenter);
        return presenter;
    }

}
