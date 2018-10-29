package com.ceiba.estacionamiento_api.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ceiba.estacionamiento_api.persistence.entities.TipoVehiculoEntity;

@Repository
public interface TipoVehiculoRepository extends JpaRepository<TipoVehiculoEntity, Long> {

}
