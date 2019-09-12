/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

function validateForm_frmMainAfter() {
    form = document.getElementById("frmMain");
	clearErrorMessages(form);
	clearErrorLabels(form);
    
    var errors = false;
	var continueValidation = true;
    
    errors = requiredStringValidation(form, 'trxRefNo', '<s:text name="29402.mcrafter.trxrefo" />', continueValidation) || errors;
    errors = requiredStringValidation(form, 'currencyCode', '<s:text name="29402.mcrafter.currencyCode" />', continueValidation) || errors;
    
    
    return !errors;
}


