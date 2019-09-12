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
	
    // field name: so.BranchCode
    // validator name: requiredstring
    errors = requiredStringValidation(form, 'so.branchCodeCasa', '<s:text name="26301.casa.branch" />', continueValidation) || errors;
        
    // field name: so.ApplicationID
    // validator name: requiredstring
    errors = requiredStringValidation(form, 'so.applicationID', '<s:text name="26301.id.customer.type" />', continueValidation) || errors;
       
    // field name: so.IDcardType
    // validator name: requiredstring
    errors = requiredStringValidation(form, 'so.idcardTDesc', '<s:text name="26301.casa.IDcardType" />', continueValidation) || errors;
    errors = requiredStringValidation(form, 'so.idcardType', '<s:text name="26301.casa.validate.IDcardType" />', continueValidation) || errors;   
    // field name: so.MailAddrs1
    // validator name: requiredstring
    errors = requiredStringValidation(form, 'so.mailAddrs1', '<s:text name="26301.casa.mail.address" />', continueValidation) || errors;
        
    // field name: so.nik
    // validator name: requiredstring
    errors = requiredStringValidation(form, 'so.nik', '<s:text name="26301.casa.no.ektp" />', continueValidation) || errors;
        

    // field name: so.Salutation
    // validator name: requiredstring
    errors = requiredStringValidation(form, 'so.salutationDesc', '<s:text name="26301.casa.salutation" />', continueValidation) || errors;
    errors = requiredStringValidation(form, 'so.salutation', '<s:text name="26301.casa.validate.salutation" />', continueValidation) || errors;    

    // field name: so.FullName
    // validator name: requiredstring
    errors = requiredStringValidation(form, 'so.fullName', '<s:text name="26301.casa.mail.fullname" />', continueValidation) || errors;
        
    // field name: so.MaiilingTownCity
    // validator name: requiredstring
    errors = requiredStringValidation(form, 'so.mailingTownCityDesc', '<s:text name="26301.casa.mail.city" />', continueValidation) || errors;
    errors = requiredStringValidation(form, 'so.mailingTownCity', '<s:text name="26301.casa.validate.mail.city" />', continueValidation) || errors; 
    // field name: so.MaillingState
    // validator name: requiredstring
    errors = requiredStringValidation(form, 'so.maillingStateDesc', '<s:text name="26301.casa.mail.state" />', continueValidation) || errors;
    errors = requiredStringValidation(form, 'so.maillingState', '<s:text name="26301.casa.validate.mail.state" />', continueValidation) || errors;    
    // field name: so.PermaZipCode
    // validator name: requiredstring
    errors = requiredStringValidation(form, 'so.maillingZipCode', '<s:text name="26301.casa.mail.zipcode" />', continueValidation) || errors;
        
    // field name: so.PermaAddress1
    // validator name: requiredstring
    errors = requiredStringValidation(form, 'so.permaAddress1', '<s:text name="26301.casa.perma.address" />', continueValidation) || errors;
        
    // field name: so.PermaZipCode
    // validator name: requiredstring
    errors = requiredStringValidation(form, 'so.permaZipCode', '<s:text name="26301.casa.perma.zipcode" />', continueValidation) || errors;
       

    // field name: so.residencePhoneNo
    // validator name: requiredstring
    errors = requiredStringValidation(form, 'so.residencePhoneNo', '<s:text name="26301.customer.home.phone.no" />', continueValidation) || errors;


    //    // field name: so.MobilePhone
    //    // validator name: requiredstring
    //    errors = requiredStringValidation(form, 'so.mobilePhone', '<s:text name="24301.customer.mobile.phone.no" />', continueValidation) || errors;
        
    // field name: so.PermaTownCity
    // validator name: requiredstring
    errors = requiredStringValidation(form, 'so.permaTownCityDesc', '<s:text name="26301.casa.perma.city" />', continueValidation) || errors;
    errors = requiredStringValidation(form, 'so.permaTownCity', '<s:text name="26301.casa.validate.perma.city" />', continueValidation) || errors;    
    // field name: so.MotherMaidenName
    // validator name: requiredstring
    errors = requiredStringValidation(form, 'so.namMother', '<s:text name="26301.casa.mother.name" />', continueValidation) || errors;
        
    // field name: so.PermaState
    // validator name: requiredstring
    errors = requiredStringValidation(form, 'so.permaStateDesc', '<s:text name="26301.casa.perma.state" />', continueValidation) || errors;
    errors = requiredStringValidation(form, 'so.permaState', '<s:text name="26301.casa.validate.perma.state" />', continueValidation) || errors;    
    // field name: so.BirthPlace
    // validator name: requiredstring
    errors = requiredStringValidation(form, 'so.birthPlace', '<s:text name="26301.customer.birth.place" />', continueValidation) || errors;
	
    // field name: so.Religion
    // validator name: requiredstring
    // field name: so.BirthDate
    // validator name: requiredstring
    errors = requiredStringValidation(form, 'so.birthDate', '<s:text name="26301.customer.birth.date" />', continueValidation) || errors;
        
    // field name: so.Gender
    // validator name: requiredstring
    errors = requiredStringValidation(form, 'so.gender', '<s:text name="26301.customer.gender" />', continueValidation) || errors;
        
    // field name: so.Nationality
    // validator name: requiredstring
    errors = requiredStringValidation(form, 'so.nationalityDesc', '<s:text name="26301.customer.citizenship" />', continueValidation) || errors;
    errors = requiredStringValidation(form, 'so.nationality', '<s:text name="26301.customer.validate.citizenship" />', continueValidation) || errors;    
    // field name: so.MaritalStatus
    // validator name: requiredstring
    errors = requiredStringValidation(form, 'so.marStatDesc', '<s:text name="26301.customer.marital.status" />', continueValidation) || errors;
    errors = requiredStringValidation(form, 'so.marStat', '<s:text name="26301.customer.validate.marital.status" />', continueValidation) || errors;    
    // field name: so.lastEdu
    // validator name: requiredstring
    errors = requiredStringValidation(form, 'so.lastEduDesc', '<s:text name="26301.casa.lastEdu" />', continueValidation) || errors;
    errors = requiredStringValidation(form, 'so.lastEdu', '<s:text name="26301.casa.validate.lastEdu" />', continueValidation) || errors;

    var regex = "^\\s*[\\w\\-\\+_]+(\\.[\\w\\-\\+_]+)*\\@[\\w\\-\\+_]+\\.[\\w\\-\\+_]+(\\.[\\w\\-\\+_]+)*\\s*$";
    errors = regexStringValidation(form, 'so.emailAddress', '<s:text name="26301.casa.email" />', continueValidation, regex) || errors;
        
    // field name: so.AoBusiness
    // validator name: requiredstring
    errors = requiredStringValidation(form, 'so.aoBusinessDesc', '<s:text name="26301.casa.ao.business" />', continueValidation) || errors;
    errors = requiredStringValidation(form, 'so.aoBusiness', '<s:text name="26301.casa.validate.ao.business" />', continueValidation) || errors;    
    // field name: so.AoOperation
    // validator name: requiredstring
    //	errors = requiredStringValidation(form, 'so.AoOperation', '<s:text name="26301.casa.ao.operation" />', continueValidation) || errors;          

    errors = requiredStringValidation(form, 'so.dataTransXtractFlagDesc', '<s:text name="26301.casa.persetujuanData" />', continueValidation) || errors;
    errors = requiredStringValidation(form, 'so.communicationDesc', '<s:text name="26301.casa.penawaranData" />', continueValidation) || errors;
    errors = requiredStringValidation(form, 'so.employerName', '<s:text name="26301.casa.employerName" />', continueValidation) || errors;

    errors = requiredStringValidation(form, 'so.businessTypeDesc', '<s:text name="26301.casa.BusinessType" />', continueValidation) || errors;
    errors = requiredStringValidation(form, 'so.businessType', '<s:text name="26301.casa.validate.BusinessType" />', continueValidation) || errors;
    errors = requiredStringValidation(form, 'so.professionDesc', '<s:text name="26301.casa.Profession" />', continueValidation) || errors;
    errors = requiredStringValidation(form, 'so.profession', '<s:text name="26301.casa.validate.Profession" />', continueValidation) || errors;
    errors = requiredStringValidation(form, 'so.employerAddress1', '<s:text name="26301.customer.office.address" />', continueValidation) || errors;
    errors = requiredStringValidation(form, 'so.employerTownCityDesc', '<s:text name="26301.casa.kotaKerjaId" />', continueValidation) || errors;
    errors = requiredStringValidation(form, 'so.employerTownCity', '<s:text name="26301.casa.validate.kotaKerjaId" />', continueValidation) || errors;
    errors = requiredStringValidation(form, 'so.employerZipCode', '<s:text name="26301.casa.kodePosPekerjaan" />', continueValidation) || errors;
    errors = requiredStringValidation(form, 'so.monthLyIncomeDesc', '<s:text name="26301.casa.pendapatan" />', continueValidation) || errors;
    errors = requiredStringValidation(form, 'so.monthLyIncome', '<s:text name="26301.casa.validate.pendapatan" />', continueValidation) || errors;
    errors = requiredStringValidation(form, 'so.expectedLimitDesc', '<s:text name="26301.casa.nilaiTransaksi" />', continueValidation) || errors;
    errors = requiredStringValidation(form, 'so.expectedLimit', '<s:text name="26301.casa.validate.nilaiTransaksi" />', continueValidation) || errors;
    errors = requiredStringValidation(form, 'so.branchCodeCasaDesc', '<s:text name="26301.casa.BranchCodeCasa" />', continueValidation) || errors;
    errors = requiredStringValidation(form, 'so.branchCodeCasa', '<s:text name="26301.casa.validate.BranchCodeCasa" />', continueValidation) || errors;
    errors = requiredStringValidation(form, 'so.productCodeDesc', '<s:text name="26301.casa.ProductCode" />', continueValidation) || errors;
    errors = requiredStringValidation(form, 'so.productCode', '<s:text name="26301.casa.validate.ProductCode" />', continueValidation) || errors;
    errors = requiredStringValidation(form, 'so.maillingCode', '<s:text name="26301.casa.mail.zipcode" />', continueValidation) || errors;	
    errors = requiredStringValidation(form, 'so.acctTitle', '<s:text name="26301.casa.acctTitle" />', continueValidation) || errors;
    errors = requiredStringValidation(form, 'so.lobDesc', '<s:text name="26301.casa.LOB" />', continueValidation) || errors;
    errors = requiredStringValidation(form, 'so.relation', '<s:text name="26301.casa.ReliwthBank" />', continueValidation) || errors;
    errors = requiredStringValidation(form, 'so.stateIndicatorDesc', '<s:text name="26301.casa.stateIndicator" />', continueValidation) || errors;
    errors = requiredStringValidation(form, 'so.tujuanPembukaanDesc', '<s:text name="26301.casa.tujuanPembukaan" />', continueValidation) || errors;
    errors = requiredStringValidation(form, 'so.tujuanPembukaan', '<s:text name="26301.casa.validate.tujuanPembukaan" />', continueValidation) || errors;
    errors = requiredStringValidation(form, 'so.sumberDanaDesc', '<s:text name="26301.casa.sumberDana" />', continueValidation) || errors;
    errors = requiredStringValidation(form, 'so.sumberDana', '<s:text name="26301.casa.validate.sumberDana" />', continueValidation) || errors;
    errors = requiredStringValidation(form, 'so.echannel', '<s:text name="26301.casa.eChannel" />', continueValidation) || errors;
    errors = fieldCompareValidation(form, 'so.professionDesc', "IBU RUMAH TANGGA", '<s:text name="26301.casa.gender.profess" />','so.gender','M',continueValidation) || errors;
    return !errors;
}


function validateForm_frmMainCasaExisting() {
    form = document.getElementById("frmMain");
    clearErrorMessages(form);
    clearErrorLabels(form);

    var errors = false;
    var continueValidation = true;
	
    // field name: so.BranchCode
    // validator name: requiredstring
    errors = requiredStringValidation(form, 'so.branchCodeCasa', '<s:text name="26301.casa.branch" />', continueValidation) || errors;
    errors = requiredStringValidation(form, 'so.branchCodeCasaDesc', '<s:text name="26301.casa.BranchCodeCasa" />', continueValidation) || errors;
    errors = requiredStringValidation(form, 'so.productCodeDesc', '<s:text name="26301.casa.ProductCode" />', continueValidation) || errors;
    errors = requiredStringValidation(form, 'so.productCode', '<s:text name="26301.casa.validate.ProductCode" />', continueValidation) || errors;
    errors = requiredStringValidation(form, 'so.acctTitle', '<s:text name="26301.casa.acctTitle" />', continueValidation) || errors;
    errors = requiredStringValidation(form, 'so.lobDesc', '<s:text name="26301.casa.LOB" />', continueValidation) || errors;
    errors = requiredStringValidation(form, 'so.relation', '<s:text name="26301.casa.ReliwthBank" />', continueValidation) || errors;
    errors = requiredStringValidation(form, 'so.stateIndicatorDesc', '<s:text name="26301.casa.stateIndicator" />', continueValidation) || errors;
    errors = requiredStringValidation(form, 'so.stateIndicator', '<s:text name="26301.casa.validate.stateIndicator" />', continueValidation) || errors;
    errors = requiredStringValidation(form, 'so.tujuanPembukaanDesc', '<s:text name="26301.casa.tujuanPembukaan" />', continueValidation) || errors;
    errors = requiredStringValidation(form, 'so.tujuanPembukaan', '<s:text name="26301.casa.validate.tujuanPembukaan" />', continueValidation) || errors;
    errors = requiredStringValidation(form, 'so.sumberDanaDesc', '<s:text name="26301.casa.sumberDana" />', continueValidation) || errors;
    errors = requiredStringValidation(form, 'so.sumberDana', '<s:text name="26301.casa.validate.sumberDana" />', continueValidation) || errors;
    errors = requiredStringValidation(form, 'so.echannel', '<s:text name="26301.casa.eChannel" />', continueValidation) || errors;

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
    var regex = "^\\s*[\\w\\-\\+_]+(\\.[\\w\\-\\+_]+)*\\@[\\w\\-\\+_]+\\.[\\w\\-\\+_]+(\\.[\\w\\-\\+_]+)*\\s*$";
    errors = regexStringValidation(form, 'so.emailAddress', '<s:text name="26301.casa.email" />' ,continueValidation, regex) || errors;
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
    if(errorsNational) {
        if(errorsBuss){
            if(errorsProf){
                if(errorsPEP){
                    codetType = '1111';	
                } else {
                    codetType = '1110';
                }
            } else {
                codetType = '1100';
            }
        } else {
            codetType = '1000';
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
    //alert(codetType);
    return codetType;
}