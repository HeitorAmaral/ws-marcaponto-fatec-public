package br.com.portabilit.wsmarcaponto.service.utils;

import java.text.ParseException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.portabilit.wsmarcaponto.domain.Colaborador;
import br.com.portabilit.wsmarcaponto.domain.Expediente;
import br.com.portabilit.wsmarcaponto.domain.Funcao;
import br.com.portabilit.wsmarcaponto.domain.Horario;
import br.com.portabilit.wsmarcaponto.domain.Ponto;
import br.com.portabilit.wsmarcaponto.domain.Setor;
import br.com.portabilit.wsmarcaponto.domain.enums.DiaDaSemana;
import br.com.portabilit.wsmarcaponto.domain.enums.StatusDoPonto;
import br.com.portabilit.wsmarcaponto.domain.enums.TipoDoRegistro;
import br.com.portabilit.wsmarcaponto.domain.security.Usuario;
import br.com.portabilit.wsmarcaponto.domain.security.enums.Perfil;
import br.com.portabilit.wsmarcaponto.repository.ColaboradorRepository;
import br.com.portabilit.wsmarcaponto.repository.ExpedienteRepository;
import br.com.portabilit.wsmarcaponto.repository.FuncaoRepository;
import br.com.portabilit.wsmarcaponto.repository.HorarioRepository;
import br.com.portabilit.wsmarcaponto.repository.PontoRepository;
import br.com.portabilit.wsmarcaponto.repository.SetorRepository;
import br.com.portabilit.wsmarcaponto.repository.security.UsuarioRepository;

@Service
public class DatabaseService {

	@Autowired
	private SetorRepository setorRepository;

	@Autowired
	private FuncaoRepository funcaoRepository;

	@Autowired
	private ColaboradorRepository colaboradorRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private ExpedienteRepository expedienteRepository;

	@Autowired
	private HorarioRepository horarioRepository;

	@Autowired
	private PontoRepository pontoRepository;

	@Autowired
	private ConverterService converterService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public void instantiateTestDatabase() throws ParseException {

		Setor setor00 = new Setor("Master");
		Setor setor01 = new Setor("Tecnologia da Informação");
		Setor setor02 = new Setor("Financeiro");
		Setor setor03 = new Setor("Recursos Humanos");

		setor00 = setorRepository.save(setor00);
		setor01 = setorRepository.save(setor01);
		setor02 = setorRepository.save(setor02);
		setor03 = setorRepository.save(setor03);

		Funcao funcao00 = new Funcao("Master", null, true, setor00.getId());
		funcao00 = funcaoRepository.save(funcao00);
		Funcao funcao01 = new Funcao("Gerente de Tecnologia da Informação", funcao00.getId(), true, setor01.getId());
		funcao01 = funcaoRepository.save(funcao01);
		Funcao funcao02 = new Funcao("Auxiliar de Tecnologia da Informação", funcao01.getId(), false, setor01.getId());
		funcao02 = funcaoRepository.save(funcao02);
		Funcao funcao03 = new Funcao("Gerente Financeiro", setor00.getId(), true, setor02.getId());
		funcao03 = funcaoRepository.save(funcao03);
		Funcao funcao04 = new Funcao("Analista Financeiro", funcao03.getId(), false, setor02.getId());
		funcao04 = funcaoRepository.save(funcao04);

		Expediente expediente00 = new Expediente("Master", "Expediente para alocar o colaborador ADM - Master");
		Expediente expediente01 = new Expediente("ADM01", "Horário Administrativo 01");
		Expediente expediente02 = new Expediente("ADM02", "Horário Administrativo 02");

		expediente00 = expedienteRepository.save(expediente00);
		expediente01 = expedienteRepository.save(expediente01);
		expediente02 = expedienteRepository.save(expediente02);

		Horario horario01 = new Horario(expediente01.getId(), DiaDaSemana.SEG.getId(), TipoDoRegistro.IJ.getId(),
				"08:00", "00:10", "00:10");
		Horario horario02 = new Horario(expediente01.getId(), DiaDaSemana.SEG.getId(), TipoDoRegistro.II.getId(),
				"12:00", "00:10", "00:10");
		Horario horario03 = new Horario(expediente01.getId(), DiaDaSemana.SEG.getId(), TipoDoRegistro.FI.getId(),
				"13:00", "00:10", "00:10");
		Horario horario04 = new Horario(expediente01.getId(), DiaDaSemana.SEG.getId(), TipoDoRegistro.FJ.getId(),
				"18:00", "00:10", "00:10");
		Horario horario05 = new Horario(expediente01.getId(), DiaDaSemana.TER.getId(), TipoDoRegistro.IJ.getId(),
				"08:00", "00:10", "00:10");
		Horario horario06 = new Horario(expediente01.getId(), DiaDaSemana.TER.getId(), TipoDoRegistro.II.getId(),
				"12:00", "00:10", "00:10");
		Horario horario07 = new Horario(expediente01.getId(), DiaDaSemana.TER.getId(), TipoDoRegistro.FI.getId(),
				"13:00", "00:10", "00:10");
		Horario horario08 = new Horario(expediente01.getId(), DiaDaSemana.TER.getId(), TipoDoRegistro.FJ.getId(),
				"18:00", "00:10", "00:10");
		Horario horario09 = new Horario(expediente01.getId(), DiaDaSemana.QUA.getId(), TipoDoRegistro.IJ.getId(),
				"08:00", "00:10", "00:10");
		Horario horario10 = new Horario(expediente01.getId(), DiaDaSemana.QUA.getId(), TipoDoRegistro.II.getId(),
				"12:00", "00:10", "00:10");
		Horario horario11 = new Horario(expediente01.getId(), DiaDaSemana.QUA.getId(), TipoDoRegistro.FI.getId(),
				"13:00", "00:10", "00:10");
		Horario horario12 = new Horario(expediente01.getId(), DiaDaSemana.QUA.getId(), TipoDoRegistro.FJ.getId(),
				"18:00", "00:10", "00:10");
		Horario horario13 = new Horario(expediente01.getId(), DiaDaSemana.QUI.getId(), TipoDoRegistro.IJ.getId(),
				"08:00", "00:10", "00:10");
		Horario horario14 = new Horario(expediente01.getId(), DiaDaSemana.QUI.getId(), TipoDoRegistro.II.getId(),
				"12:00", "00:10", "00:10");
		Horario horario15 = new Horario(expediente01.getId(), DiaDaSemana.QUI.getId(), TipoDoRegistro.FI.getId(),
				"13:00", "00:10", "00:10");
		Horario horario16 = new Horario(expediente01.getId(), DiaDaSemana.QUI.getId(), TipoDoRegistro.FJ.getId(),
				"18:00", "00:10", "00:10");
		Horario horario17 = new Horario(expediente01.getId(), DiaDaSemana.SEX.getId(), TipoDoRegistro.IJ.getId(),
				"08:00", "00:10", "00:10");
		Horario horario18 = new Horario(expediente01.getId(), DiaDaSemana.SEX.getId(), TipoDoRegistro.II.getId(),
				"12:00", "00:10", "00:10");
		Horario horario19 = new Horario(expediente01.getId(), DiaDaSemana.SEX.getId(), TipoDoRegistro.FI.getId(),
				"13:00", "00:10", "00:10");
		Horario horario20 = new Horario(expediente01.getId(), DiaDaSemana.SEX.getId(), TipoDoRegistro.FJ.getId(),
				"17:00", "00:10", "00:10");

		horario01 = horarioRepository.save(horario01);
		horario02 = horarioRepository.save(horario02);
		horario03 = horarioRepository.save(horario03);
		horario04 = horarioRepository.save(horario04);
		horario05 = horarioRepository.save(horario05);
		horario06 = horarioRepository.save(horario06);
		horario07 = horarioRepository.save(horario07);
		horario08 = horarioRepository.save(horario08);
		horario09 = horarioRepository.save(horario09);
		horario10 = horarioRepository.save(horario10);
		horario11 = horarioRepository.save(horario11);
		horario12 = horarioRepository.save(horario12);
		horario13 = horarioRepository.save(horario13);
		horario14 = horarioRepository.save(horario14);
		horario15 = horarioRepository.save(horario15);
		horario16 = horarioRepository.save(horario16);
		horario17 = horarioRepository.save(horario17);
		horario18 = horarioRepository.save(horario18);
		horario19 = horarioRepository.save(horario19);
		horario20 = horarioRepository.save(horario20);

		Colaborador colaborador00 = new Colaborador("Master", "administrador@portabilit.com.br", funcao00.getId(),
				expediente00.getId(), true);
		Colaborador colaborador01 = new Colaborador("Cesar Augusto Seleguin Facioli", "cesar.facioli@fatec.sp.gov.br",
				funcao01.getId(), expediente01.getId(), true);
		Colaborador colaborador02 = new Colaborador("Heitor Augusto Melecardi do Amaral",
				"heitor.amaral@fatec.sp.gov.br", funcao02.getId(), expediente01.getId(), true);

		colaborador00 = colaboradorRepository.save(colaborador00);
		colaborador01 = colaboradorRepository.save(colaborador01);
		colaborador02 = colaboradorRepository.save(colaborador02);

		Usuario usuario00 = new Usuario(colaborador00.getEmail(), bCryptPasswordEncoder.encode("xxx"),
				colaborador00.getId(), colaborador00.getAtivo());
		usuario00.addPerfil(Perfil.RESPONSAVEL);
		usuario00.addPerfil(Perfil.ADMIN);
		Usuario usuario01 = new Usuario(colaborador01.getEmail(), bCryptPasswordEncoder.encode("MarcaPonto@123"),
				colaborador01.getId(), colaborador01.getAtivo());
		usuario01.addPerfil(Perfil.RESPONSAVEL);
		Usuario usuario02 = new Usuario(colaborador02.getEmail(), bCryptPasswordEncoder.encode("MarcaPonto@123"),
				colaborador02.getId(), colaborador02.getAtivo());

		usuario00 = usuarioRepository.save(usuario00);
		usuario01 = usuarioRepository.save(usuario01);
		usuario02 = usuarioRepository.save(usuario02);

		Ponto ponto01 = new Ponto(colaborador01.getId(), converterService.stringToDate("11/06/2020"),
				converterService.hourToMinute("08:00"), 0, "", TipoDoRegistro.IJ, StatusDoPonto.AA, true, "");
		Ponto ponto02 = new Ponto(colaborador01.getId(), converterService.stringToDate("11/06/2020"),
				converterService.hourToMinute("12:20"), 0, "", TipoDoRegistro.II, StatusDoPonto.AA, true, "");
		Ponto ponto03 = new Ponto(colaborador01.getId(), converterService.stringToDate("11/06/2020"),
				converterService.hourToMinute("13:25"), 0, "", TipoDoRegistro.FI, StatusDoPonto.AA, true, "");
		Ponto ponto04 = new Ponto(colaborador01.getId(), converterService.stringToDate("11/06/2020"),
				converterService.hourToMinute("18:00"), 0, "", TipoDoRegistro.FJ, StatusDoPonto.AA, true, "");
		Ponto ponto05 = new Ponto(colaborador02.getId(), converterService.stringToDate("21/06/2020"),
				converterService.hourToMinute("06:30"), 150, "", TipoDoRegistro.IJ, StatusDoPonto.A, false, "");

		pontoRepository.saveAll(Arrays.asList(ponto01, ponto02, ponto03, ponto04, ponto05));
	}
}
