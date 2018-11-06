package com.ceiba.estacionamiento_api.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.ceiba.estacionamiento_api.persistence.entities.ParqueoEntity;

@Repository
public interface ParqueoRepository extends JpaRepository<ParqueoEntity, Long> {
	
	@Query(value = "SELECT * FROM parqueo INNER JOIN vehiculo ON vehiculo.id = parqueo.vehiculo_id WHERE parqueo.fecha_salida IS NULL AND LOWER(vehiculo.placa) LIKE LOWER(:placa) ", nativeQuery = true)
	public ParqueoEntity consultarParqueoActivoPorPlaca(@Param("placa") String placa);
	
	@Query(value = "SELECT * FROM parqueo WHERE fecha_salida IS NULL ORDER BY fecha_ingreso DESC", nativeQuery = true)
	public List<ParqueoEntity> obtenerParqueosActivos();
	
	@Query(value = "SELECT COUNT(*) as total FROM parqueo INNER JOIN vehiculo ON vehiculo.id = parqueo.vehiculo_id WHERE parqueo.fecha_salida IS NULL AND vehiculo.tipo_vehiculo_id = :tipoVehiculo ", nativeQuery = true)
	public Integer obtenerParqueosActivosPorTipoVehiculo(@Param("tipoVehiculo") int tipoVehiculo);

}