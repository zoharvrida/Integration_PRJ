function validateForm_frmMainAwal() {

    form = document.getElementById("frmMain");
    clearErrorMessages(form);
    clearErrorLabels(form);

    var errors = false;
    var continueValidation = true;
    var paymentType = $.trim($('#frmPayment_paymentType').val());
    //CASA
    if (paymentType === '2') {
        // validator name: requiredstring
        errors = requiredStringValidation(form, 'etax.debitAccountNo', 'Nomor Rekening cannot be empty', continueValidation) || errors;
        
        // validator name: requiredstring
        errors = requiredStringValidation(form, 'etax.debitAccountName', 'Nama Rekening cannot be empty', continueValidation) || errors;

    
    //GL   
    } else if (paymentType === '3') {

        // validator name: requiredstring
        errors = requiredStringValidation(form, 'etax.glAccountNo', 'Nomor GL cannot be empty', continueValidation) || errors;
        
        // validator name: requiredstring
        errors = requiredStringValidation(form, 'etax.glAccountName', 'Nama GL cannot be empty', continueValidation) || errors;

    }
    return !errors;
}
