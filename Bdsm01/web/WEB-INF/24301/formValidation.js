function validateForm_frmInquiry() {
	form = document.getElementById("frmInquiry");
	clearErrorMessages(form);
	clearErrorLabels(form);

	var errors = false;
	var continueValidation = true;

	// field name: txtData.customerTypeInq
	// validator name: requiredstring
	errors = requiredStringValidation(form, 'strData.customerTypeInqDesc', '<s:text name="24301.id.customer.type" />', continueValidation) || errors;
	
	// field name: txtData.idTypeInq
	// validator name: requiredstring
	errors = requiredStringValidation(form, 'strData.idTypeInqDesc', '<s:text name="24301.id.id.type" />', continueValidation) || errors;

	// field name: idNumberInq
	// validator name: requiredstring
	errors = requiredStringValidation(form, 'strData.idNumberInq', '<s:text name="24301.id.id.number" />', continueValidation) || errors;

	return !errors;
}

function validateForm_frmMain() {
	form = document.getElementById("frmMain");
	clearErrorMessages(form);
	clearErrorLabels(form);

	var errors = false;
	var continueValidation = true;
	
	// field name: strData.customerType
	// validator name: requiredstring
	errors = requiredStringValidation(form, 'strData.customerType', '<s:text name="24301.id.customer.type" />', continueValidation) || errors;
	
	// field name: strData.idType
	// validator name: requiredstring
	errors = requiredStringValidation(form, 'strData.idType', '<s:text name="24301.id.id.type" />', continueValidation) || errors;
	
	// field name: idNumber
	// validator name: requiredstring
	errors = requiredStringValidation(form, 'idNumber', '<s:text name="24301.id.id.number" />', continueValidation) || errors;
	
	// field name: idExpiredDate
	// validator name: requiredstring
	errors = requiredStringValidation(form, 'idExpiredDate', '<s:text name="24301.id.id.expired.date" />', continueValidation) || errors;
	
	
	
	// field name: name
	// validator name: requiredstring
	errors = requiredStringValidation(form, 'name', '<s:text name="24301.customer.name" />', continueValidation) || errors;
	
	// field name: strData.gender
	// validator name: requiredstring
	errors = requiredStringValidation(form, 'strData.gender', '<s:text name="24301.customer.gender" />', continueValidation) || errors;
	
	// field name: address1
	// validator name: requiredstring
	errors = requiredStringValidation(form, 'address1', '<s:text name="24301.customer.address" />', continueValidation) || errors;
	
	// field name: strData.maritalStatus
	// validator name: requiredstring
	errors = requiredStringValidation(form, 'strData.maritalStatus', '<s:text name="24301.customer.marital.status" />', continueValidation) || errors;
	
	// field name: phoneNo
	// validator name: requiredstring
	errors = requiredStringValidation(form, 'phoneNo', '<s:text name="24301.customer.phone.no" />', continueValidation) || errors;
	
	// field name: birthPlace
	// validator name: requiredstring
	errors = requiredStringValidation(form, 'birthPlace', '<s:text name="24301.customer.birth.place" />', continueValidation) || errors;
	
	// field name: city
	// validator name: requiredstring
	errors = requiredStringValidation(form, 'city', '<s:text name="24301.customer.city" />', continueValidation) || errors;
	
	// field name: birthDate
	// validator name: requiredstring
	errors = requiredStringValidation(form, 'birthDate', '<s:text name="24301.customer.birth.date" />', continueValidation) || errors;
	
	// field name: state
	// validator name: requiredstring
	errors = requiredStringValidation(form, 'state', '<s:text name="24301.customer.state" />', continueValidation) || errors;
	
	// field name: country
	// validator name: requiredstring
	errors = requiredStringValidation(form, 'country', '<s:text name="24301.customer.country" />', continueValidation) || errors;
	
	// field name: citizenship
	// validator name: requiredstring
	errors = requiredStringValidation(form, 'citizenship', '<s:text name="24301.customer.citizenship" />', continueValidation) || errors;
	
	// field name: occupation
	// validator name: requiredstring
	errors = requiredStringValidation(form, 'occupation', '<s:text name="24301.customer.occupation" />', continueValidation) || errors;
	
	
	
	// field name: residentialAddress1
	// validator name: requiredstring
	errors = requiredStringValidation(form, 'residentialAddress1', '<s:text name="label.wic.customer.residential.address" />', continueValidation) || errors;
	
	// field name: residentialCity
	// validator name: requiredstring
	errors = requiredStringValidation(form, 'residentialCity', '<s:text name="24301.customer.city" />', continueValidation) || errors;
	
	// field name: residentialState
	// validator name: requiredstring
	errors = requiredStringValidation(form, 'residentialState', '<s:text name="24301.customer.state" />', continueValidation) || errors;
	
	// field name: residentialCountry
	// validator name: requiredstring
	errors = requiredStringValidation(form, 'residentialCountry', '<s:text name="24301.customer.country" />', continueValidation) || errors;

	// field name: strData.businessType
	// validator name: requiredstring
	errors = requiredStringValidation(form, 'strData.businessType', '<s:text name="label.wic.customer.business.type" />', continueValidation) || errors;
	
	// field name: strData.sourceOfFunds
	// validator name: requiredstring
	errors = requiredStringValidation(form, 'strData.sourceOfFunds', '<s:text name="label.wic.customer.sourceoffunds" />', continueValidation) || errors;
	
	var sof = form.elements['sourceOfFunds']; 
	if (sof && (sof.value == 'OTH')) {
		// field name: sourceOfFundsOthers
		// validator name: requiredstring
		errors = requiredStringValidation(form, 'sourceOfFundsOthers', '<s:text name="label.wic.customer.sourceoffunds.others" />', continueValidation) || errors;
	}
	
	// field name: trxPurpose
	// validator name: requiredstring
	errors = requiredStringValidation(form, 'trxPurpose', '<s:text name="label.wic.customer.trx.purpose" />', continueValidation) || errors;
	
	
	
	var customerType = document.getElementById("frmMain_customerType");
	if (customerType && (customerType.value == '1')) {
		// field name: jobTitle
		// validator name: requiredstring
		errors = requiredStringValidation(form, 'jobTitle', '<s:text name="24301.customer.job.title" />', continueValidation) || errors;
		
		// field name: homePhoneNo
		// validator name: requiredstring
		errors = requiredStringValidation(form, 'homePhoneNo', '<s:text name="24301.customer.home.phone.no" />', continueValidation) || errors;
		
		// field name: officeName
		// validator name: requiredstring
		errors = requiredStringValidation(form, 'officeName', '<s:text name="24301.customer.office.name" />', continueValidation) || errors;
		
		// field name: officePhoneNo
		// validator name: requiredstring
		errors = requiredStringValidation(form, 'officePhoneNo', '<s:text name="24301.customer.office.phone.no" />', continueValidation) || errors;
		
		
		// field name: officeAddress1
		// validator name: requiredstring
		errors = requiredStringValidation(form, 'officeAddress1', '<s:text name="24301.customer.office.address" />', continueValidation) || errors;
		
		// field name: businessField
		// validator name: requiredstring
		errors = requiredStringValidation(form, 'businessField', '<s:text name="24301.customer.business.field" />', continueValidation) || errors;
		
		// field name: residentialAddress1
		// validator name: requiredstring
		errors = requiredStringValidation(form, 'residentialAddress1', '<s:text name="24301.customer.residential.address" />', continueValidation) || errors;
		
		// field name: mobilePhoneNo
		// validator name: requiredstring
		errors = requiredStringValidation(form, 'mobilePhoneNo', '<s:text name="24301.customer.mobile.phone.no" />', continueValidation) || errors;
		
		// field name: txtData.incomePerMonthType
		// validator name: requiredstring
		errors = requiredStringValidation(form, 'strData.incomePerMonthType', '<s:text name="24301.customer.income.permonth" />', continueValidation) || errors;
		
	}
	else if (customerType && (customerType.value == '2')) {
		// field name: instanceRepresented
		// validator name: requiredstring
		errors = requiredStringValidation(form, 'instanceRepresented', '<s:text name="24301.instance.represented" />', continueValidation) || errors;
		
		// field name: authLetterNo
		// validator name: requiredstring
		errors = requiredStringValidation(form, 'authLetterNo', '<s:text name="24301.courier.auth.letter.no" />', continueValidation) || errors;
		
		// field name: accountRepresented
		// validator name: requiredstring
		errors = requiredStringValidation(form, 'accountRepresented', '<s:text name="24301.courier.account.represented" />', continueValidation) || errors;
		
		// field name: accountBranch
		// validator name: requiredstring
		errors = requiredStringValidation(form, 'accountBranch', '<s:text name="24301.courier.account.branch" />', continueValidation) || errors;
	}
			
	return !errors;
}
