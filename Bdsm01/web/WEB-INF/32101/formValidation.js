function validateForm_frmMainAwal() {

    form = document.getElementById("frmMain");
    clearErrorMessages(form);
    clearErrorLabels(form);

    var errors = false;
    var continueValidation = true;
    var paymentType = $.trim($('#frmPayment_paymentType').val());
    //CASA
    if (paymentType === '1'){
        errors = requiredStringValidation(form, 'etax.cashCustomerName', 'Nama Penyetor cannot be empty', continueValidation) || errors;
        
        errors = requiredStringValidation(form, 'etax.cashIdType', 'Jenis Identitas cannot be empty', continueValidation) || errors;
        
        errors = requiredStringValidation(form, 'etax.cashIdNo', 'Nomor Identitas cannot be empty', continueValidation) || errors;
        
        errors = requiredStringValidation(form, 'etax.cashCustomerType', 'Customer Type cannot be empty', continueValidation) || errors;
        
        errors = requiredStringValidation(form, 'etax.cashCustomerPhone', 'No. Telepon Aktif cannot be empty', continueValidation) || errors;
        
        errors = requiredStringValidation(form, 'etax.cashCustomerAddress', 'Alamat cannot be empty', continueValidation) || errors;
    }else if (paymentType === '2') {
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
