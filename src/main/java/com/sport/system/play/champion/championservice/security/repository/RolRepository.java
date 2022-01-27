package com.sport.system.play.champion.championservice.security.repository;

import com.sport.system.play.champion.championservice.enums.EnumRol;
import com.sport.system.play.champion.championservice.security.entity.Rol;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RolRepository extends CrudRepository<Rol, UUID> {
    Rol findByName(EnumRol role);
}
