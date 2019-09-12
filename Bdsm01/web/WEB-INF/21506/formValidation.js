function validateForm_frmInquiry26301() {
	form = document.getElementById("frmMain");
	clearErrorMessages(form);
	clearErrorLabels(form);
	var errors = false;
	var continueValidation = true;

	errors = requiredStringValidation(form, 'noKartuIden', '<s:text name="error.noKartuIden.26301" />', continueValidation) || errors;
        
        errors = requiredStringValidation(form, 'customerName', '<s:text name="error.custName.26301" />', continueValidation) || errors;
        
        errors = requiredStringValidation(form, 'dateofBirth', '<s:text name="error.Dob.26301" />', continueValidation) || errors;

	return !errors;
}

function validateForm_frmInquiryCard26301() {
	form = document.getElementById("frmMain");
	clearErrorMessages(form);
	clearErrorLabels(form);
	var errors = false;
	var continueValidation = true;
        
	
	errors = lengthStringValidation(form, 'noCard', '<s:text name="26301.label.error.cardlength" />', continueValidation, 16) || errors;
        
	return !errors;
}
/*
 * Revision : Checking range text
 * change : v00019722
 * change date : 15-Januari-2015
 * Begin 1
 */
function validateDate(dateCheck,dateNow,yearRange){
    form = document.getElementById("frmMain");
    clearErrorMessages(form);
    clearErrorLabels(form);
    //alert(dateNow);
	console.log(dateNow);
    var errors = false;
    var continueValidation = true;
	
	var earlCheck = dateCheck.split("/");
	if(parseInt(earlCheck[1], 10)>12){
		errors =  labelingValidation(form, 'dateofBirth', '<s:text name="226301.datbirth.month.validation" />',continueValidation) || errors;
	} else {
		if(parseInt(earlCheck[0], 10)>31){
			errors = labelingValidation(form, 'dateofBirth','<s:text name="26301.datbirth.day.validation" />',continueValidation) || errors;
		} else {
			var dateChk = dateTextToDate(dateCheck);
			var dateRange = dateTextToDateRange(dateNow,yearRange,'Y','-');
			//alert("1");
			errors = dateCompValidation(form, 'dateofBirth', '<s:text name="26301.datbirth.validation" />', continueValidation, dateChk, dateRange) || errors;	
		}	
	}
	
	return !errors;
    
}
/*End 1*/  
        
function requiredButtonValidation(form, elementName,  continueValidation) {
	var err = false;
	
	if (form.elements[elementName]) {
		field = form.elements[elementName];
		if (continueValidation && (field.value !="TUNGGAL") && ((field.value == "BELUM REKAM"))) {
                document.getElementById('btnVal').disabled=true;
            err = true;
		}
	}
	
	return err;
}

function validateForm_frmButton() {
    form = document.getElementById("frmMain");
    clearErrorMessages(form);
    clearErrorLabels(form);

    var errors = false;
    var continueValidation = true;
    errors = requiredStringValidation(form, 'model.ektpStatus',  continueValidation) || errors;

    return !errors;
}