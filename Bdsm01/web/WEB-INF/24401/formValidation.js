function validateForm_frmInquiry() {
	form = document.getElementById("frmInquiry");
	clearErrorMessages(form);
	clearErrorLabels(form);

	var errors = false;
	var continueValidation = true;

	// field name: txtMap.idTypeInq
	// validator name: requiredstring
	errors = requiredStringValidation(form, 'strData.idTypeInqDesc', '<s:text name="24301.id.id.type" />', continueValidation) || errors;

	// field name: idNumberInq
	// validator name: requiredstring
	errors = requiredStringValidation(form, 'strData.idNumberInq', '<s:text name="24301.id.id.number" />', continueValidation) || errors;
	
	// field name: txtMap.trxRefNoInq
	// validator name: requiredstring
	errors = requiredStringValidation(form, 'strData.trxRefNoInq', '<s:text name="24401.trx.trx.ref.no" />', continueValidation) || errors;

	return !errors;
}

