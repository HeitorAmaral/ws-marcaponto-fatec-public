package br.com.portabilit.wsmarcaponto.controller.exception;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StandardError implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(position = 1)
	private Long timestamp;

	@ApiModelProperty(position = 2)
	private Integer status;

	@ApiModelProperty(position = 3)
	private String error;

	@ApiModelProperty(position = 4)
	private String message;

	@ApiModelProperty(position = 5)
	private String path;

}