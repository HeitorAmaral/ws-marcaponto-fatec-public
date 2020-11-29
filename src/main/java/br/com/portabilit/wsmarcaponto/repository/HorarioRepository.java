package br.com.portabilit.wsmarcaponto.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.portabilit.wsmarcaponto.domain.Horario;

@Repository
public interface HorarioRepository extends JpaRepository<Horario, Integer> {

	@Transactional(readOnly = true)
	@Query(value = "SELECT * FROM horario WHERE expediente_id = ?1 AND dia_da_semana = ?2 AND tipo_do_registro = ?3", nativeQuery = true)
	Optional<Horario> findByExpedienteIdAndDiaDaSemanaIdAndTipoDoRegistroId(int expedienteId, int diaDaSemanaId,
			int tipoDoRegistroId);

	@Transactional(readOnly = true)
	@Query(value = "SELECT * FROM horario WHERE expediente_id = ?1 AND dia_da_semana = ?2", nativeQuery = true)
	List<Horario> findByExpedienteIdAndDiaDaSemanaId(Integer expedienteId, Integer diaDaSemana);
}
