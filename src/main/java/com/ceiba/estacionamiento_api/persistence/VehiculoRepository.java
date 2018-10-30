package com.ceiba.estacionamiento_api.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ceiba.estacionamiento_api.persistence.entities.VehiculoEntity;

@Repository
public interface VehiculoRepository extends JpaRepository<VehiculoEntity, Long> {
	
	@Query(value = "SELECT * FROM vehiculo WHERE vehiculo.placa = :placa ", nativeQuery = true)
	public VehiculoEntity consultarPorPlaca(@Param("placa") String placa);
	
}