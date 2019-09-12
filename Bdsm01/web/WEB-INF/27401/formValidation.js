function validateForm_frmComponent() {
	var skipValidation = false;
	var errors = false;
	var continueValidation = true;
	var isAtLeastOneChecked = false;
	
	
	if (skipValidation) return true;
	
	var form = document.getElementById("frmComponent");
	clearErrorMessages(form);
	clearErrorLabels(form);
	
	$("#frmComponent").find("input[name^='chkComp']").each(function(index) {
		if ($(this).attr("checked") == "checked") {
			if (!isAtLeastOneChecked)
				isAtLeastOneChecked = true;
		}
	});
	
	if (!isAtLeastOneChecked) {
		errors = true;
		messageBox(1, '<s:text name="27401.error.checkbox.atleastone" />');
	}
	else {
		var minErrorMessage = '<s:text name="27401.error.minimum.value" />';
		var maxErrorMessage = '<s:text name="27401.error.maximum.value" />';
		var compNo;
		
		
		$("#frmComponent").find("#fsCommitment").find("input[name^='strData.comp']").each(function(index) {
			/* blank value validation */
			errors = requiredStringValidation(form, $(this).attr("name"), '<s:text name="error.data.mandatory" />', continueValidation) || errors;
			
			/* minimum and maximum value validation */
			compNo = $(this).attr("name").substr("strData.comp".length);
			errors = valueValidation(
						form, 
						$(this).attr("name"), 
						Number(getNumberValue($("#minValue" + compNo).html())), 
						minErrorMessage, 
						Number(getNumberValue($("#maxValue" + compNo).html())), 
						maxErrorMessage
					)
					|| errors;
		});
		
		$("#frmComponent").find("#fsLogic").find("input[name^='chkComp']").each(function(index) {
			if ($(this).attr("checked") == "checked") {
				var logicCode = $(this).attr("name").substr("chkComp".length);
				var $parent = $(this).closest("tr");
				var childCount = $("#childCount" + logicCode).val();
				
				for (var i=0; i<childCount; i++) {
					$parent = $parent.next();
					$parent.find("input[name^='strData.comp'][type!='hidden']").each(function(index) {
						/* blank value validation */
						errors = requiredStringValidation(form, $(this).attr("name"), '<s:text name="error.data.mandatory" />', continueValidation) || errors;
						
						/* minimum and maximum value validation */
						compNo = $(this).attr("name").substr("strData.comp".length);
						errors = valueValidation(
									form, 
									$(this).attr("name"), 
									Number(getNumberValue($("#minValue" + compNo).html())), 
									minErrorMessage, 
									Number(getNumberValue($("#maxValue" + compNo).html())), 
									maxErrorMessage
								)
								|| errors;
					});
				}
			}
		});
		
	}
	
	return !errors;
}

function valueValidation(form, elementName, minValue, minErrorMessage, maxValue, maxErrorMessage) {
	var field = form.elements[elementName];
	var value = Number(getNumberValue(field.value));
	var err = false;
	
	if (value < minValue) {
		addError(field, minErrorMessage);
		err = true;
	}
	else if (value > maxValue) {
		addError(field, maxErrorMessage);
		err = true;
	}
	
	return err;
}

function getNumberValue(moneyFormattedValue) {
	$("#frmComponent_tmpMoney").val(moneyFormattedValue);
	
	return ($("#frmComponent_tmpMoney").autoNumeric("get"));
}
