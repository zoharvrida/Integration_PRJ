/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

function validateForm_frmMainCasa() {
	form = document.getElementById("frmMain");
	clearErrorMessages(form);
	clearErrorLabels(form);

	var errors = false;
	var continueValidation = true;
		// field name: so.PermaAddress1
	// validator name: requiredstring
	errors = requiredStringValidation(form, 'so.permaAddress1', l_26301_casa_perma_address, continueValidation) || errors;
	// field name: so.PermaAddress3  permaAddress3
	// validator name: requiredstring
	errors = requiredStringValidation(form, 'so.permaAddress3', l_26301_customer_perma_addr3, continueValidation)|| errors;
	
	// field name: so.mailAddrs3  mailAddrs3
	// validator name: requiredstring
	errors = requiredStringValidation(form, 'so.mailAddrs3', l_26301_customer_perma_addr3, continueValidation)|| errors;
	// field name: so.employerAddress3  employerAddress3
	// validator name: requiredstring
	errors = requiredStringValidation(form, 'so.employerAddress3', l_26301_customer_perma_addr3, continueValidation)|| errors;
	// field name: so.BranchCode
	// validator name: requiredstring
	errors = requiredStringValidation(form, 'so.branchCodeCasa', l_26301_casa_branch, continueValidation) || errors;
		
	// field name: so.ApplicationID
	// validator name: requiredstring
	errors = requiredStringValidation(form, 'so.applicationID', l_26301_id_customer_type, continueValidation) || errors;
	   
		// field name: so.IDcardType
	// validator name: requiredstring
	errors = requiredStringValidation(form, 'so.idcardTDesc', l_26301_casa_IDcardType, continueValidation) || errors;
	errors = requiredStringValidation(form, 'so.idcardType', l_26301_casa_validate_IDcardType, continueValidation) || errors;   
		// field name: so.MailAddrs1
	// validator name: requiredstring
	errors = requiredStringValidation(form, 'so.mailAddrs1', l_26301_casa_mail_address, continueValidation) || errors;
		
		// field name: so.nik
	// validator name: requiredstring
	errors = requiredStringValidation(form, 'so.nik', l_26301_casa_no_ektp, continueValidation) || errors;
		

		// field name: so.Salutation
	// validator name: requiredstring
	errors = requiredStringValidation(form, 'so.salutationDesc', l_26301_casa_salutation, continueValidation) || errors;
	errors = requiredStringValidation(form, 'so.salutation', l_26301_casa_validate_salutation, continueValidation) || errors;	

		// field name: so.FullName
	// validator name: requiredstring
	errors = requiredStringValidation(form, 'so.fullName', l_26301_casa_mail_fullname, continueValidation) || errors;
		
		// field name: so.MaiilingTownCity
	// validator name: requiredstring
	errors = requiredStringValidation(form, 'so.maillingTownCityDesc', l_26301_casa_mail_city, continueValidation) || errors;
	errors = requiredStringValidation(form, 'so.maillingTownCity', l_26301_casa_validate_mail_city, continueValidation) || errors; 
		// field name: so.MaillingState
	// validator name: requiredstring
	errors = requiredStringValidation(form, 'so.maillingStateDesc', l_26301_casa_mail_state, continueValidation) || errors;
	errors = requiredStringValidation(form, 'so.maillingState', l_26301_casa_validate_mail_state, continueValidation) || errors;	
			// field name: so.PermaZipCode
	// validator name: requiredstring
	errors = requiredStringValidation(form, 'so.maillingZipCode', l_26301_casa_mail_zipcode, continueValidation) || errors;
		
		

		// field name: so.PermaZipCode
	// validator name: requiredstring
	errors = requiredStringValidation(form, 'so.permaZipCode', l_26301_casa_perma_zipcode, continueValidation) || errors;
	   

	// field name: so.residencePhoneNo
	// validator name: requiredstring
	errors = requiredStringValidation(form, 'so.residencePhoneNo', l_26301_customer_home_phone_no, continueValidation) || errors;


//	// field name: so.MobilePhone
//	// validator name: requiredstring
	errors = requiredStringValidation(form, 'so.mobilePhone', l_24301_customer_mobile_phone_no, continueValidation) || errors;
	  // field name: so.emailAddress
	errors = requiredStringValidation(form, 'so.emailAddress', l_24301_customer_email, continueValidation) || errors; 
		// field name: so.PermaTownCity
	// validator name: requiredstring
	errors = requiredStringValidation(form, 'so.permaTownCityDesc', l_26301_casa_perma_city, continueValidation) || errors;
	errors = requiredStringValidation(form, 'so.permaTownCity', l_26301_casa_validate_perma_city, continueValidation) || errors;	
		// field name: so.MotherMaidenName
	// validator name: requiredstring
	errors = requiredStringValidation(form, 'so.namMother', l_26301_casa_mother_name, continueValidation) || errors;
		
		// field name: so.PermaState
	// validator name: requiredstring
	errors = requiredStringValidation(form, 'so.permaStateDesc', l_26301_casa_perma_state, continueValidation) || errors;
	errors = requiredStringValidation(form, 'so.permaState', l_26301_casa_validate_perma_state, continueValidation) || errors;	
		// field name: so.BirthPlace
	// validator name: requiredstring
	errors = requiredStringValidation(form, 'so.birthPlace', l_26301_customer_birth_place, continueValidation) || errors;
	
	// field name: so.Religion
	// validator name: requiredstring
		// field name: so.BirthDate
	// validator name: requiredstring
	errors = requiredStringValidation(form, 'so.birthDate', l_26301_customer_birth_date, continueValidation) || errors;
		
		// field name: so.Gender
	// validator name: requiredstring
	errors = requiredStringValidation(form, 'so.genderDesc', l_26301_customer_gender, continueValidation) || errors;
		
		// field name: so.Nationality
	// validator name: requiredstring
	errors = requiredStringValidation(form, 'so.nationalityDesc', l_26301_customer_citizenship, continueValidation) || errors;
	errors = requiredStringValidation(form, 'so.nationality', l_26301_customer_validate_citizenship, continueValidation) || errors;	
		// field name: so.MaritalStatus
	// validator name: requiredstring
	errors = requiredStringValidation(form, 'so.marStatDesc', l_26301_customer_marital_status, continueValidation) || errors;
	errors = requiredStringValidation(form, 'so.marStat', l_26301_customer_validate_marital_status, continueValidation) || errors;	
		// field name: so.lastEdu
	// validator name: requiredstring
	errors = requiredStringValidation(form, 'so.lastEduDesc', l_26301_casa_lastEdu, continueValidation) || errors;
	errors = requiredStringValidation(form, 'so.lastEdu', l_26301_casa_validate_lastEdu, continueValidation) || errors;

	var regex = "^[A-Za-z0-9]{1}[\\w\\-_]+(\\.[\\w\\-_]+)*\\@[\\w\\-_]+\\.[\\w\\-_]+(\\.[\\w\\-_]+)*[A-Za-z0-9]{1}$";
	//var regex = "^[A-Za-z0-9]+(?:\.[a-z0-9'+_]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$";
	errors = regexStringValidation(form, 'so.emailAddress', l_26301_casa_email, continueValidation, regex) || errors;
	
//var regex1 = "^(\\+?\\d{1,4}[\\s-])?(?!0+\\s+,?$)\\d{1,}\\s*,?$";
//var regex1 = "^(([0]{1}[8]{1}[0-9]{3,})|([+]{1}[6]{1}[2]{1}[8]{1}[0-9]{10,}))$";
		var regex1 = "^(([0]{1}[8]{1}[0-9]{3,}))$";
		
		//var regex1 = "^[0-9]*$";
		//var regex1 = "^(\\+?\\d{1,4}[\\s-])?(?!0+\\s+,?$)\\d{5,}\\s*,?$";
		//var regex1 = "^\\(\\+\\d{1,3}[-]?)?\\d{5,12}$";
		var regex1 = "^(([0]{1}[8]{1}[0-9]{3,})|([+]{1}[6]{1}[2]{1}[8]{1}[0-9]{2,}))$";
	errors = regexStringValidation(form, 'so.mobilePhone', l_26301_casa_mobile_phone_no_Length, continueValidation, regex1) || errors;
	
		//update 03-07-2018
		//Changer : v00021902
	var regex8  = "^[A-Za-z0-9]{1}[a-zA-Z0-9 /\'.,-]+[,]{1}[a-zA-Z0-9 /\'.,-]+[A-Za-z0-9]{1}$";
	errors = regexStringValidation(form, 'so.permaAddress3', l_26301_casa_empl_address3, continueValidation, regex8) || errors;
	errors = regexStringValidation(form, 'so.employerAddress3', l_26301_casa_empl_address3, continueValidation, regex8) || errors;
	errors = regexStringValidation(form, 'so.mailAddrs3', l_26301_casa_empl_address3, continueValidation, regex8) || errors;
	var regex2 = "^[a-zA-Z-.'`]+([ ]{1}[a-zA-Z-.'`]+)*$";
	var regex3 = "^[0-9]{5,}$";
	var regex5 = "^[a-zA-Z0-9]+([ ]{1}[a-zA-Z0-9]+)*$";
	var regex6 = "^[\\d]{15,}$";
	var regex7 = "^[a-zA-Z0-9 /\'.,-]+([ ]{2,}[a-zA-Z0-9 /\'.,-]+)*$";
	var regex10 = " {2,}";
	errors = regexStringValidation(form, 'so.birthPlace',l_26301_customer_birth_place_regex, continueValidation, regex5) || errors;
	errors = regexStringValidation(form, 'so.fullName', l_26301_casa_mail_fullname_regex, continueValidation,regex2) || errors;
	errors = regexStringValidation(form, 'so.namMother', l_26301_casa_mother_name_regex, continueValidation,regex2) || errors;
	errors = regexStringValidation(form, 'so.nik', l_26301_casa_no_ektp_regex, continueValidation,regex3) || errors;
	errors = regexStringValidation(form, 'so.permaZipCode', l_26301_casa_perma_zipcode_regex, continueValidation,regex3) || errors;
	errors = regexStringValidation(form, 'so.maillingCode', l_26301_casa_perma_zipcode_regex, continueValidation,regex3) || errors;
	errors = regexStringValidation(form, 'so.employerZipCode', l_26301_casa_perma_zipcode_regex, continueValidation,regex3) || errors;
	errors = regexStringValidation(form, 'so.incomeTaxNo',l_26301_casa_incomeTaxNo_regex, continueValidation,regex6) || errors;
	errors = regexStringValidation2(form, 'so.permaAddress3','addrKelurahan', 'Please check Kelurahan Name', continueValidation, regex10) || errors; 
	errors = regexStringValidation2(form, 'so.permaAddress3','addrKecamatan', 'Please check Kecamatan Name', continueValidation, regex10) || errors; 
	errors = regexStringValidation2(form, 'so.employerAddress3','empKelurahan', 'Please check Kelurahan Name', continueValidation, regex10) || errors; 
	errors = regexStringValidation2(form, 'so.employerAddress3','empKecamatan', 'Please check Kecamatan Name', continueValidation, regex10) || errors; 
	errors = regexStringValidation2(form, 'so.mailAddrs3','mailKelurahan', 'Please check Kelurahan Name', continueValidation, regex10) || errors; 
	errors = regexStringValidation2(form, 'so.mailAddrs3','mailKecamatan', 'Please check Kecamatan Name', continueValidation, regex10) || errors; 
	//if (!errors){
	//	errors = regexStringValidation(form, 'so.mobilePhone', '<s:text name="26301.casa.mobile.phone.no" />', continueValidation, regex1) || errors;
	//	if(!errors) {
	//		errors = regexStringValidation(form, 'so.mobilePhone', '<s:text name="26301.casa.mobile.phone.no1" />', continueValidation, regex2) || errors;
	//	}
	//}	
	   
	   // field name: so.AoBusiness
	// validator name: requiredstring
	errors = requiredStringValidation(form, 'so.aoBusinessDesc', l_26301_casa_ao_business, continueValidation) || errors;
	errors = requiredStringValidation(form, 'so.aoBusiness', l_26301_casa_validate_ao_business, continueValidation) || errors;	
		// field name: so.AoOperation
	// validator name: requiredstring
//	errors = requiredStringValidation(form, 'so.AoOperation', '<s:text name="26301.casa.ao.operation" />', continueValidation) || errors;		  

	errors = requiredStringValidation(form, 'so.dataTransXtractFlagDesc', l_26301_casa_persetujuanData, continueValidation) || errors;
	errors = requiredStringValidation(form, 'so.communicationDesc', l_26301_casa_penawaranData, continueValidation) || errors;
	errors = requiredStringValidation(form, 'so.employerName', l_26301_casa_employerName, continueValidation) || errors;
//# Revision : Change Label Name
//# Change Date : 10-JANUARI-2017   
//# Changer : v00020800
//# Begin 1


 // # End
	errors = requiredStringValidation(form, 'so.businessTypeDesc', l_26301_casa_BusinessType, continueValidation) || errors;
	errors = requiredStringValidation(form, 'so.businessType', l_26301_casa_validate_BusinessType, continueValidation) || errors;
	errors = requiredStringValidation(form, 'so.professionDesc', l_26301_casa_Profession, continueValidation) || errors;
	errors = requiredStringValidation(form, 'so.profession', l_26301_casa_validate_Profession, continueValidation) || errors;
	errors = requiredStringValidation(form, 'so.employerAddress1', l_26301_customer_office_address, continueValidation) || errors; 
	errors = requiredStringValidation(form, 'so.employerTownCityDesc', l_26301_casa_kotaKerjaId, continueValidation) || errors;
	errors = requiredStringValidation(form, 'so.employerTownCity', l_26301_casa_validate_kotaKerjaId, continueValidation) || errors;
	errors = requiredStringValidation(form, 'so.employerState', l_26301_casa_validate_PropinsiKerjaId, continueValidation) || errors;
	errors = requiredStringValidation(form, 'so.employerStateDesc', l_26301_casa_PropinsiKerjaId, continueValidation) || errors;
	errors = requiredStringValidation(form, 'so.employerZipCode', l_26301_casa_kodePosPekerjaan, continueValidation) || errors; 
	errors = requiredStringValidation(form, 'so.monthLyIncomeDesc', l_26301_casa_pendapatan, continueValidation) || errors;
	errors = requiredStringValidation(form, 'so.monthLyIncome', l_26301_casa_validate_pendapatan, continueValidation) || errors;
	errors = requiredStringValidation(form, 'so.expectedLimitDesc', l_26301_casa_nilaiTransaksi, continueValidation) || errors;
	errors = requiredStringValidation(form, 'so.expectedLimit', l_26301_casa_validate_nilaiTransaksi, continueValidation) || errors;
	errors = requiredStringValidation(form, 'so.branchCodeCasaDesc', l_26301_casa_BranchCodeCasa, continueValidation) || errors;
	errors = requiredStringValidation(form, 'so.branchCodeCasa', l_26301_casa_validate_BranchCodeCasa, continueValidation) || errors;
	errors = requiredStringValidation(form, 'so.productCodeDesc', l_26301_casa_ProductCode, continueValidation) || errors;
	errors = requiredStringValidation(form, 'so.productCode', l_26301_casa_validate_ProductCode, continueValidation) || errors;
	errors = requiredStringValidation(form, 'so.maillingCode', l_26301_casa_mail_zipcode, continueValidation) || errors;	
	errors = requiredStringValidation(form, 'so.acctTitle', l_26301_casa_acctTitle, continueValidation) || errors;
	errors = requiredStringValidation(form, 'so.lobDesc', l_26301_casa_LOB, continueValidation) || errors;
	errors = requiredStringValidation(form, 'so.relation', l_26301_casa_ReliwthBank, continueValidation) || errors;
	errors = requiredStringValidation(form, 'so.stateIndicatorDesc', l_26301_casa_stateIndicator, continueValidation) || errors;
	errors = requiredStringValidation(form, 'so.tujuanPembukaanDesc', l_26301_casa_tujuanPembukaan, continueValidation) || errors;
	errors = requiredStringValidation(form, 'so.tujuanPembukaan', l_26301_casa_validate_tujuanPembukaan, continueValidation) || errors;
	errors = requiredStringValidation(form, 'so.sumberDanaDesc', l_26301_casa_sumberDana, continueValidation) || errors;
	errors = requiredStringValidation(form, 'so.sumberDana', l_26301_casa_validate_sumberDana, continueValidation) || errors;
	errors = requiredStringValidation(form, 'so.echannel', l_26301_casa_eChannel, continueValidation) || errors;
	//2015-12-22  
   // errors = requiredStringValidation(form, 'so.homeStatus', '<s:text name="26301.casa.validate.homeStatus" />', continueValidation) || errors;
	errors = requiredStringValidation(form, 'so.greenCard', l_26301_casa_validate_greenCard, continueValidation) || errors;
	errors = requiredStringValidation(form, 'so.alamatAS', l_26301_casa_validate_alamatAS, continueValidation) || errors;
	errors = requiredStringValidation(form, 'so.badanAS', l_26301_casa_validate_badanAS, continueValidation) || errors;
	errors = requiredStringValidation(form, 'so.fatca', l_26301_casa_validate_fatca, continueValidation) || errors;
	errors = requiredStringValidation(form, 'so.aoOperation', l_26301_casa_validate_aoOperation, continueValidation) || errors
	errors = requiredStringValidation(form, 'so.lob', l_26301_casa_validate_lob, continueValidation) || errors;
	errors = requiredStringValidation(form, 'so.stateIndicator', l_26301_casa_validate_stateIndicator, continueValidation) || errors;
	errors = requiredStringValidation(form, 'so.dataTransXtractFlag', l_26301_casa_validate_dataTransXtractFlag, continueValidation) || errors;
	errors = requiredStringValidation(form, 'so.communication', l_26301_casa_validate_communication, continueValidation) || errors;
	errors = requiredStringValidation(form, 'so.gender', l_26301_casa_validate_gender, continueValidation) || errors;
	//2015-12-23
		errors = requiredStringValidation(form, 'so.religion', l_26301_casa_validate_religion, continueValidation) || errors;
		errors = requiredStringValidation(form, 'so.religionDesc', l_26301_casa_validate_religion, continueValidation) || errors;

	errors = fieldCompareValidation(form, 'so.professionDesc', "IBU RUMAH TANGGA", l_26301_casa_gender_profess,'so.gender','M',continueValidation) || errors;
	
	return !errors;
}
/*
 * Revision : Checking range text
 * change : v00019722
 * change date : 15-Januari-2015
 * Begin 1
 */
function validateDate(dateCheck, dateNow, yearRange) {
	form = document.getElementById("frmMain");
	clearErrorMessages(form);
	clearErrorLabels(form);
	//alert(dateNow);
	//console.log(dateNow);
	var errors = false;
	var continueValidation = true;

	var earlCheck = dateCheck.split("/");
	if (parseInt(earlCheck[1], 10) > 12) {
		errors = labelingValidation(form, 'so.birthDate', l_26301_datbirth_month_validation, continueValidation) || errors;
	} else {
		if (parseInt(earlCheck[0], 10) > 31) {
			errors = labelingValidation(form, 'so.birthDate', l_26301_datbirth_day_validation, continueValidation) || errors;
		} else {
			var dateChk = dateTextToDate(dateCheck);
			var dateRange = dateTextToDateRange(dateNow, yearRange, 'Y', '-');
			//alert("1");
			errors = dateCompValidation(form, 'so.birthDate', l_26301_datbirth_validation, continueValidation, dateChk, dateRange) || errors;
		}
	}
	return !errors;

}
/*End 1*/
function regexSplit(form,errorName,strings,regex4,regex5,continueValidation,errors){
	if(errors == false){
		if(form.elements[strings]){
			var temp = new Array();
			var str = form.elements[strings];
			console.log("addr3 "+strings);
			errors2 = regexStringValidation(form, strings, errorName, continueValidation, regex4);   
			console.log("addr33 "+errors + " __ " +str.value);
			if(errors2 == false){
				temp = str.value.split(",");
				for(a in temp){
				errors2 = regexFieldValidation(form, temp[a], errorName, continueValidation,regex5 ) || errors;   
				console.log("Permade addr333 "+temp[a]);
				}
				return errors2;
			} 
			//console.log("Permade addr33 "+temp);		
		}
	}
				return errors;
}
function regexSplit2(form,errorName,strings,arrays,regex4,regex5,errors){
	if(errors == false){
		if(form.elements[strings]){
			var temp = new Array();
			var str = form.elements[strings];
			console.log("addr3 "+strings);
			errors2 = regexStringValidation(form, strings, errorName, true, regex4);   
			console.log("addr33 "+errors2 + " __ " +str.value);
			if(errors2 == false){
				temp = str.value.split(",");
				for(a in temp){
					$("#"+arrays[a]).attr('value',temp[a]);
					console.log("VAL ARRAY :" + arrays[a]);
					console.log("VAL value :" + temp[a]);
					console.log("VALCHANGE :" + $("#"+arrays[a]).val());
					//errors2 = regexFieldValidation(form, temp[a], errorName, true,regex5 ) || errors;   
			} 
				return errors2;
			}else {
			console.log("tes 123 ");
			errors = errors2
			}
			//console.log("Permade addr33 "+temp);		
		}
	}
	return errors;
}
function regexFieldValidation(form, strings,errorMessage,continueValidation, regexp) {
	var err = false;
   // if (form.elements[elementName]) {
		//field = form.elements[strings];//[elementName];
		if (continueValidation && (strings != null) && (strings != "")) {
			var regex = new RegExp(regexp); 
			if(!strings.match(regex)){
				err = false;
			} else {
			addError(field, errorMessage);
			err = true;
		}
		}
   // }	
	return err;
}
function validateForm_frmMainCasaExisting() {
	form = document.getElementById("frmMain");
	clearErrorMessages(form);
	clearErrorLabels(form);

	var errors = false;
	var continueValidation = true;
	
	// field name: so.BranchCode
	// validator name: requiredstring
	errors = requiredStringValidation(form, 'so.branchCodeCasa', l_26301_casa_validate_branch, continueValidation) || errors;
	errors = requiredStringValidation(form, 'so.branchCodeCasaDesc', l_26301_casa_validate_BranchCodeCasa, continueValidation) || errors;
	errors = requiredStringValidation(form, 'so.productCodeDesc', l_26301_casa_ProductCode, continueValidation) || errors;
	errors = requiredStringValidation(form, 'so.productCode', l_26301_casa_validate_ProductCode, continueValidation) || errors;
	errors = requiredStringValidation(form, 'so.acctTitle', l_26301_casa_acctTitle, continueValidation) || errors;
	errors = requiredStringValidation(form, 'so.lobDesc', l_26301_casa_LOB, continueValidation) || errors;
	errors = requiredStringValidation(form, 'so.relation', l_26301_casa_ReliwthBank, continueValidation) || errors;
	errors = requiredStringValidation(form, 'so.stateIndicatorDesc', l_26301_casa_stateIndicator, continueValidation) || errors;
	errors = requiredStringValidation(form, 'so.stateIndicator', l_26301_casa_validate_stateIndicator, continueValidation) || errors;
	errors = requiredStringValidation(form, 'so.tujuanPembukaanDesc', l_26301_casa_tujuanPembukaan, continueValidation) || errors;
	errors = requiredStringValidation(form, 'so.tujuanPembukaan', l_26301_casa_validate_tujuanPembukaan, continueValidation) || errors;
	errors = requiredStringValidation(form, 'so.sumberDanaDesc', l_26301_casa_sumberDana, continueValidation) || errors;
	errors = requiredStringValidation(form, 'so.sumberDana', l_26301_casa_validate_sumberDana, continueValidation) || errors;
	errors = requiredStringValidation(form, 'so.echannel', l_26301_casa_validate_eChannel, continueValidation) || errors;
	errors = requiredStringValidation(form, 'so.echannelDesc', l_26301_casa_eChannel, continueValidation) || errors;
	
	return !errors;
	
}

function validateForm_ktpMapping() {
	form = document.getElementById("frmMain");

	var errors = false;
	var continueValidation = true;
	
	//frmMain_labelmarStat
	// field name: so.BranchCode
	// validator name: requiredstring
	errors = specificStringValidation(form, 'so.lastEdu', 'EKTP', continueValidation) || errors;
	errors = specificStringValidation(form, 'so.religion', 'EKTP', continueValidation) || errors;
	errors = specificStringValidation(form, 'so.permaTownCity', 'EKTP', continueValidation) || errors;
	errors = specificStringValidation(form, 'so.permaState', 'EKTP', continueValidation) || errors;
	errors = specificStringValidation(form, 'so.profession', 'EKTP', continueValidation) || errors;
	errors = specificStringValidation(form, 'so.marStat', 'EKTP', continueValidation) || errors;
	//alert(errors);
	return !errors;
	
}
function validateForm_Email(regex) {
	form = document.getElementById("frmMain");

	var errors = false;
	var continueValidation = true;
	//alert('email_val');
	//frmMain_labelmarStat
	// field name: so.BranchCode
	// validator name: requiredstring
	//var regex = "^\\s*[\\w\\-\\+_]+(\\.[\\w\\-\\+_]+)*\\@[\\w\\-\\+_]+\\.[\\w\\-\\+_]+(\\.[\\w\\-\\+_]+)*\\s*$";
	var regex = "^[A-Za-z0-9]{1}[\\w\\-\\+_]+(\\.[\\w\\-\\+_]+)*\\@[\\w\\-\\+_]+\\.[\\w\\-\\+_]+(\\.[\\w\\-\\+_]+)*[A-Za-z0-9]{1}$";
	//var regex = "^[A-Za-z0-9]+(?:\.[a-z0-9'+_]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$";
	errors = regexStringValidation(form, 'so.emailAddress', l_26301_casa_email ,continueValidation, regex) || errors;
	return !errors;
}	
function validateHighRisk(){
	form = document.getElementById("frmMain");
	
	var codetType = '0000';
	var continueValidation = true;
	var errorsNational = specificStringValidation(form, 'highRisknationalityFlag', 'HR', continueValidation);
	var errorsBuss = specificStringValidation(form, 'highRiskbusinessFlag', 'HR', continueValidation);
	var errorsProf = specificStringValidation(form, 'highRiskprofessionFlag', 'HR', continueValidation);
	var errorsPEP = specificStringValidation(form, 'highRiskprofessionFlag', 'PEP', continueValidation);
	//alert("HR :" + errorsNational + "|" + errorsBuss + "|" + errorsProf + "|" + errorsPEP);
	/*
		Revision : Change Punctuation for HighRisk Message 
					 Change : 07-Januari-2016   
					 Changer : v00019722
					 Begin 1
	*/
	if(errorsNational) {
		if(errorsBuss){
			if(errorsProf){
				if(errorsPEP){
					codetType = '1111';	
				} else {
					codetType = '1110';
				}
			} else {
				if(errorsPEP){
					codetType = '1101';	
				} else {
				codetType = '1100';
			}
			}
		} else {
			if(errorsProf){
				if(errorsPEP){
					codetType = '1011';	
				} else {
					codetType = '1010';
				}
			} else {
				if(errorsPEP){
					codetType = '1001';	
		} else {
			codetType = '1000';
		}
			}
		}
	} else if(errorsBuss){
		if(errorsProf){
			if(errorsPEP){
				codetType = '0111';
			} else {
				codetType = '0110';
			}
		} else {
			if(errorsPEP){
				codetType = '0101';
			} else {
			codetType = '0100';
		}
		}
	} else if(errorsProf) {
		if(errorsPEP){
			codetType = '0011';
		} else {
			codetType = '0010';
		}
	} else if(errorsPEP){
		codetType = '0001';
	}
	// End 1
	//alert(codetType);
	return codetType;
}