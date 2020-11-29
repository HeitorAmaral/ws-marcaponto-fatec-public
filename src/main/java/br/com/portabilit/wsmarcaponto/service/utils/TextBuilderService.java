package br.com.portabilit.wsmarcaponto.service.utils;

import org.springframework.stereotype.Service;

/**
 * Classe responsável por centralizar e construir todas as exceções da
 * aplicação.
 * 
 * @author heitor.amaral
 *
 */
@Service
public class TextBuilderService {

	public String getGenericMethodExceptionForNoneParameter(String entityName, String methodName,
			String errorDescription) {
		String exceptionText = "Erro genérico no método " + methodName + "() da entidade " + entityName.toUpperCase()
				+ ". Erro: " + errorDescription;
		return exceptionText;
	}

	public String getIntegrityViolationMethodExceptionForMultiParameters(String entityName, String methodName,
			String[] parameters, String errorDescription) {
		String exceptionText = "Erro de violação de integridade de dados no método " + methodName + "(";
		for (int i = 0; i < parameters.length; i++) {
			if ((i + 1) < parameters.length) {
				exceptionText = exceptionText.concat(parameters[i] + ", ");
			} else {
				exceptionText = exceptionText.concat(parameters[i]);
			}
		}
		return exceptionText.concat(") da entidade " + entityName.toUpperCase() + ". Erro: " + errorDescription);
	}

	public String getGenericMethodExceptionForMultiParameters(String entityName, String methodName, String[] parameters,
			String errorDescription) {
		String exceptionText = "Erro genérico no método " + methodName + "(";
		for (int i = 0; i < parameters.length; i++) {
			if ((i + 1) < parameters.length) {
				exceptionText = exceptionText.concat(parameters[i] + ", ");
			} else {
				exceptionText = exceptionText.concat(parameters[i]);
			}
		}
		return exceptionText.concat(") da entidade " + entityName.toUpperCase() + ". Erro: " + errorDescription);
	}

	public String getValidationExceptionForInvalidStatus(String entityName, String operationName, String statusName) {
		String exceptionText = "Para ser realizada a operação do tipo " + operationName.toUpperCase() + " na entidade "
				+ entityName.toUpperCase() + " é necessário que o valor da propriedade STATUS seja "
				+ statusName.toUpperCase();
		return exceptionText;
	}

	public String getValidationExceptionForInvalidValues(String entityName, String firstPropertyName,
			String secondPropertyName, String type) {
		String exceptionText = "O valor da propriedade " + firstPropertyName.toUpperCase()
				+ " informada para a entidade " + entityName.toUpperCase() + " deve ser " + type.toUpperCase()
				+ " do que o valor da propriedade " + secondPropertyName.toUpperCase();
		return exceptionText;
	}

	public String getValidationExceptionForNotFoundRegister(String entityName, String propertyName,
			String propertyValue) {
		String exceptionText = "Não foi encontrado nenhuma entidade do tipo " + entityName.toUpperCase()
				+ " com a propriedade " + propertyName.toUpperCase() + " = " + propertyValue.toUpperCase();
		return exceptionText;
	}

	public String getValidationExceptionForInativeRegister(String entityName, String propertyName,
			String propertyValue) {
		String exceptionText = "A entidade " + entityName.toUpperCase() + " que possue a propriedade "
				+ propertyName.toUpperCase() + " com o valor " + propertyName.toUpperCase()
				+ " está INATIVADA no sistema.";
		return exceptionText;
	}

	public String getValidationExceptionForMultiRegister(String entityName, String propertyName, String propertyValue) {
		String exceptionText = "Já existe no sistema uma entidade " + entityName.toUpperCase()
				+ " utilizando a propriedade " + propertyName.toUpperCase() + " com o valor de "
				+ propertyValue.toUpperCase();
		return exceptionText;
	}

	public String getValidationExceptionForMultiRegisterAndTwoParameters(String entityName, String propertyOneName,
			String propertyOneValue, String propertyTwoName, String propertyTwoValue) {
		String exceptionText = "Já existe no sistema uma entidade " + entityName.toUpperCase()
				+ " utilizando a propriedade " + propertyOneName.toUpperCase() + " com o valor de "
				+ propertyOneValue.toUpperCase() + " e a propriedade " + propertyTwoName.toUpperCase()
				+ " com o valor de " + propertyTwoValue.toUpperCase();
		return exceptionText;
	}

	public String getValidationExceptionForMultiRegisterAndMultiParameters(String entityName,
			String[] parametersName, String[] parametersValue) {
		String exceptionText = "Já existe no sistema uma entidade " + entityName.toUpperCase()
				+ " utilizando os mesmos valores de propriedade, sendo: ";
		for (int i = 0; i < parametersName.length; i++) {
			if ((i + 1) < parametersName.length) {
				exceptionText = exceptionText
						.concat(parametersName[i].toUpperCase() + " com o valor de " + parametersValue[i] + ", ");
			} else {
				exceptionText = exceptionText
						.concat(parametersName[i].toUpperCase() + " com o valor de " + parametersValue[i]);
			}
		}
		return exceptionText;
	}

	public String getIntegrityExceptionOnInactivate(String entityName, String childEntityName) {
		String exceptionText = "Não é possível INATIVAR a entidade " + entityName.toUpperCase()
				+ ", pois existem registros filhos de " + childEntityName.toUpperCase()
				+ " que estão ATIVADOS no sistema! Para realizar a operação, REMOVA ou INATIVE os registros relacionados de "
				+ childEntityName.toUpperCase() + ".";
		return exceptionText;
	}

	public String getIntegrityExceptionOnDelete(String entityName, String childEntityName) {
		String exceptionText = "Não é possível APAGAR a entidade " + entityName.toUpperCase()
				+ ", pois existem registros filhos de " + childEntityName.toUpperCase() + " no sistema!";

		if (childEntityName.trim().toUpperCase().equals("PONTO")) {
			return exceptionText;
		} else {
			return exceptionText.concat(" Para realizar a operação, REMOVA os registros relacionados de "
					+ childEntityName.toUpperCase() + ".");

		}
	}
}
