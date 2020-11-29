package br.com.portabilit.wsmarcaponto.domain.dto;

import java.io.Serializable;
import java.util.Date;

import br.com.portabilit.wsmarcaponto.domain.Ponto;
import br.com.portabilit.wsmarcaponto.domain.enums.StatusDoPonto;
import br.com.portabilit.wsmarcaponto.service.utils.ConverterService;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class PontoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(position = 1)
	private Integer id;

	@ApiModelProperty(position = 2)
	private Integer colaboradorId;

	@ApiModelProperty(position = 3)
	private Date data;

	@ApiModelProperty(position = 4)
	private String horario;

	@ApiModelProperty(position = 5)
	private Integer tempoVariavel;

	@ApiModelProperty(position = 6)
	private String localizacao;

	@ApiModelProperty(position = 7)
	private String tipoDoRegistro;

	@ApiModelProperty(position = 8)
	private StatusDoPonto status;

	@ApiModelProperty(position = 9)
	private Boolean manual;

	@ApiModelProperty(position = 10)
	private String obs;

	public PontoDTO(Ponto ponto) {
		ConverterService converterService = new ConverterService();
		this.id = ponto.getId();
		this.colaboradorId = ponto.getColaboradorId();
		this.data = ponto.getData();
		this.horario = converterService.minuteToHour(ponto.getHorario());
		this.tempoVariavel = ponto.getTempoVariavel();
		this.localizacao = ponto.getLocalizacao();
		this.tipoDoRegistro = ponto.getTipoDoRegistro().getNome();
		this.status = ponto.getStatus();
		this.manual = ponto.getManual();
		this.obs = ponto.getObs();
	}
}
