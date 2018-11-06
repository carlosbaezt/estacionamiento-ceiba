package com.ceiba.estacionamiento_api.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ceiba.estacionamiento_api.persistence.entities.VehiculoEntity;

@Repository
public interface VehiculoRepository extends JpaRepository<VehiculoEntity, Long> {
	
	public VehiculoEntity findByPlacaIgnoreCase(String placa);
	
}