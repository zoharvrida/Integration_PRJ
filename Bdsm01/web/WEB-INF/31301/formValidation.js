function validateForm_frmMainAwal() {
	
	form = document.getElementById("frmMain");
	clearErrorMessages(form);
	clearErrorLabels(form);
	
	var errors = false;
	var continueValidation = true;
	
	// field name: svd.namCustFull
	// validator name: requiredstring
	errors = requiredStringValidation(form, 'svd.namCustFull', '<s:text name="31301.svd.namCustFull" />', continueValidation) || errors;
	
	// field name: svd.fatherName
	// validator name: requiredstring
	errors = requiredStringValidation(form, 'svd.fatherName', '<s:text name="31301.svd.fatherName" />', continueValidation) || errors;
	
	// field name: svd.birthPlace
	// validator name: requiredstring
	errors = requiredStringValidation(form, 'svd.birthPlace', '<s:text name="31301.svd.svd.birthPlace" />', continueValidation) || errors;
	
	// field name: strData.datBirthCust
	// validator name: requiredstring
	errors = requiredStringValidation(form, 'strData.datBirthCust', '<s:text name="31301.svd.datBirthCust" />', continueValidation) || errors;
	
	// field name: svd.txtCustSex
	// validator name: requiredstring
	errors = requiredStringValidation(form, 'svd.txtCustSex', '<s:text name="31301.svd.txtCustSex" />', continueValidation) || errors;
	
	// field name: objData.hajiId
	// validator name: requiredstring
	errors = requiredStringValidation(form, 'objData.hajiId', '<s:text name="31301.svd.hajiId" />', continueValidation) || errors;
	
	// field name: svd.codNatId
	// validator name: requiredstring
	errors = requiredStringValidation(form, 'svd.codNatId', '<s:text name="31301.svd.codNatId" />', continueValidation) || errors;
	
	// field name: svd.txtCustAdrAdd1
	// validator name: requiredstring
	errors = requiredStringValidation(form, 'svd.txtCustAdrAdd1', '<s:text name="31301.svd.txtCustAdrAdd1" />', continueValidation) || errors;
	
	// field name: svd.txtCustAdrZip
	// validator name: requiredstring
	errors = requiredStringValidation(form, 'svd.txtCustAdrZip', '<s:text name="31301.svd.txtCustAdrZip" />', continueValidation) || errors;
	
	// field name: svd.desaName
	// validator name: requiredstring
	errors = requiredStringValidation(form, 'svd.desaName', '<s:text name="31301.svd.desaName" />', continueValidation) || errors;
	
	// field name: svd.kecamatanName
	// validator name: requiredstring
	errors = requiredStringValidation(form, 'svd.kecamatanName', '<s:text name="31301.svd.kecamatanName" />', continueValidation) || errors;
	
	// field name: svd.namPermadrState
	// validator name: requiredstring
	errors = requiredStringValidation(form, 'svd.namPermadrState', '<s:text name="31301.svd.namPermadrState" />', continueValidation) || errors;
	
	// field name: svd.namPermadrCity
	// validator name: requiredstring
	errors = requiredStringValidation(form, 'svd.namPermadrCity', '<s:text name="31301.svd.namPermadrCity" />', continueValidation) || errors;
	
	// field name: svd.setoranAwal
	// validator name: requiredstring
	errors = requiredStringValidation(form, 'svd.setoranAwal', '<s:text name="31301.svd.setoranAwal" />', continueValidation) || errors;
	
	// field name: svd.terbilang
	// validator name: requiredstring
	errors = requiredStringValidation(form, 'svd.terbilang', '<s:text name="31301.svd.terbilang" />', continueValidation) || errors;
	
	// field name: svd.txtProfessCat
	// validator name: requiredstring
	errors = requiredStringValidation(form, 'svd.txtProfessCat', '<s:text name="31301.svd.txtProfessCat" />', continueValidation) || errors;
	
	// field name: svd.marstatdesc
	// validator name: requiredstring
	errors = requiredStringValidation(form, 'svd.marstatdesc', '<s:text name="31301.svd.marstatdesc" />', continueValidation) || errors;
	
	// field name: svd.codCustEducn
	// validator name: requiredstring
	errors = requiredStringValidation(form, 'svd.codCustEducn', '<s:text name="31301.svd.codCustEducn" />', continueValidation) || errors;
	
	return !errors;
}
