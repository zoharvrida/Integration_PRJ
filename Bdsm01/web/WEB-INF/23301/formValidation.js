function validateForm_frmMain() {
	form = document.getElementById("frmMain");
	clearErrorMessages(form);
	clearErrorLabels(form);

	var errors = false;
	var continueValidation = true;
	
	
	// field name: accountNo
	errors = requiredStringValidation(form, 'accountNo', '<s:text name="23301.account.no.mandatory" />', continueValidation) || errors;
	
	// field name: billingNo
	errors = requiredStringValidation(form, 'billingNo', '<s:text name="23301.biller.subscriber.id.mandatory" />', continueValidation) || errors;
	
	// field name: billerName
	errors = requiredStringValidation(form, 'billerName', '<s:text name="23301.biller.name.mandatory" />', continueValidation) || errors;
	
	// field name: billerBankName
	errors = requiredStringValidation(form, 'billerBankName', '<s:text name="23301.biller.bank.name.mandatory" />', continueValidation) || errors;
	
	// field name: billerAccountNo
	errors = requiredStringValidation(form, 'billerAccountNo', '<s:text name="23301.biller.account.no.mandatory" />', continueValidation) || errors;
	
	// field name: billingPurpose
	errors = requiredStringValidation(form, 'billingPurpose', '<s:text name="23301.billing.purpose.mandatory" />', continueValidation) || errors;
	
	// field name: nominal
	errors = requiredStringValidation(form, 'nominal', '<s:text name="23301.billing.nominal" />', continueValidation) || errors;
	
	
	if (continueValidation) {
		var maxNominal = parseFloat('<s:property value="%{objData.maxNominal}" />');
		if (form.elements['nominal']) {
	        field = form.elements['nominal'];
	        var nominal = parseFloat(field.value);
	        if (nominal > maxNominal) {
	            addError(field, '<s:text name="23301.billing.nominal.max"><s:param><s:text name="format.money"><s:param name="value" value="%{objData.maxNominal}" /></s:text></s:param></s:text>');
	            errors = true;
	        }
	    }
	}
	
	return !errors;
}
