<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags" %>

<sj:radio 
	id="rbBS" 
	name="rbBS" 
	list="{'Inquiry', 'Add', 'Clear'}" 
	disabled="true" 
/>

<script type="text/javascript">
	jQuery(document).ready(function() {
		$(window).resize();
		rdb.resetDisabled();
		rdb.resetReadonly();
		rdb.resetDisableButton();
		
		rdb.setDisableButton("btnSearchInq", "0000");
		rdb.setDisableButton("btnPosting", "1111");
		rdb.setDisableButton("btnGeneratePDF", "1111");
		rdb.setDisableButton("btnLookupMaritalStatus", "1011");
		rdb.setDisableButton("btnLookupState", "1011");
		rdb.setDisableButton("btnLookupCity", "1011");
		rdb.setDisableButton("btnLookupEducationName", "1011");
		rdb.setDisableButton("btnLookupOccupation", "1011");
		rdb.setDisableButton("btnLookupHajiType", "1011");
		
		rdb.setReadonly("frmInquiry_acctNoInq", "0011");
		
		rdb.setReadonly("frmMain_svd_namCustFull", "1111");
		rdb.setReadonly("frmMain_svd_fatherName", "1111");
		rdb.setReadonly("frmMain_svd_birthPlace", "1111");
		rdb.setReadonly("frmMain_svd_datBirthCust", "1111");
		rdb.setReadonly("frmMain_objData_hajiId", "1111");
		rdb.setReadonly("frmMain_svd_txtCustSex", "1111");
		rdb.setReadonly("frmMain_svd_codNatId", "1111");
		rdb.setReadonly("frmMain_svd_txtCustAdrAdd1", "1111");
		
		rdb.setReadonly("frmMain_svd_txtCustAdrZip", "1111");
		rdb.setReadonly("frmMain_svd_desaName", "1111");
		rdb.setReadonly("frmMain_svd_kecamatanName", "1111");
		rdb.setReadonly("frmMain_svd_namPermadrState", "1111");
		rdb.setReadonly("frmMain_svd_namPermadrCity", "1111");
		rdb.setReadonly("frmMain_svd_setoranAwal", "1111");
		rdb.setReadonly("frmMain_svd_terbilang", "1111");
		rdb.setReadonly("frmMain_svd_occupationdesc", "1111");
		rdb.setReadonly("frmMain_svd_marstatdesc", "1111");
		rdb.setReadonly("frmMain_svd_txtCustEducn", "1111");
		rdb.setReadonly("frmMain_residentialPostalCode", "1111");
		rdb.setReadonly("residentialCity_widget", "1011");
		rdb.setReadonly("residentialState_widget", "1011");
		rdb.setReadonly("residentialCountry_widget", "1011");
		rdb.setReadonly("frmMain_sourceOfFundsOthers", "1011");
		rdb.setReadonly("frmMain_trxPurpose", "1011");
		
		rdb.setOnChange(
			function() {
				changeAction("frmMain", "31301", "_inquiry");
				
				$(".cls-row-button").hide();
				$("label[for='lblStatusInq']").show();
				$("#frmInquiry_svd_statusInq").show();
			}, 
			function() {
				changeAction("frmMain", "31301", "_add");
				
				$(".cls-row-button").show();
				$("label[for='lblStatusInq']").hide();
				$("#frmInquiry_svd_statusInq").hide();
				$("#frmInquiry_acctNoInq").focus();
			}, 
			null, 
			null, 
			function(removeMsg) {
				$("#frmAcctNo_strData_mode").val(rdb.current);
				$("#frmInquiry_acctNoInq").attr('value', '');
				$("#frmInquiry_svd_statusInq").attr('value', '');
				$("#frmMain_state").val('0');
				$("#frmMain_objData_hajiId").val('');
				$("#frmMain_svd_namCustFull").attr('value', '');
				$("#frmMain_svd_birthPlace").attr('value', '');
				$("#frmMain_svd_fatherName").attr('value', '');
				$("#frmMain_strData_datBirthCust").attr('value', '');
				$("#frmMain_svd_datBirthCust").attr('value', '');
				$("#frmMain_svd_txtCustSex").attr('value', '');
				$("#frmMain_hajiType").attr('value', '');
				$("#frmMain_svd_codNatId").attr('value', '');
				$("#frmMain_svd_txtCustAdrAdd1").attr('value', '');
				$("#frmMain_svd_txtCustAdrZip").attr('value', '');
				$("#frmMain_svd_desaName").attr('value', '');
				$("#frmMain_svd_kecamatanName").attr('value', '');
				$("#frmMain_svd_namPermadrState").attr('value', '');
				$("#frmMain_svd_namPermadrCity").attr('value', '');
				$("#frmMain_strData_setoranAwal").attr('value', '');
				$("#frmMain_svd_setoranAwal").attr('value', '');
				$("#frmMain_svd_terbilang").attr('value', '');
				$("#frmMain_svd_occupationdesc").attr('value', '');
				$("#frmMain_svd_txtProfessCat").attr('value', '');
				$("#frmMain_svd_marstatdesc").attr('value', '');
				$("#frmMain_svd_codCustEducn").attr('value', '');
				$("#frmMain_svd_txtCustEducn").attr('value', '');
				
				$("#frmMain, #frmInquiry").find("input[type='text'], textarea")
					.css("opacity", '')
					.filter("[readonly]").css("opacity", "0.5");
				$("#ph-main").animate({scrollTop: 0}, "slow");
				
				if (removeMsg) {
					$("#actionMessage").remove();
					$("#actionError").remove();
					$("span.errorMessage").remove();
					$("label").removeClass("errorLabel");
					
					clearErrorMessages($("#frmMain").get(0));
				}
			} 
		);
		
		rdb.setMenuAccess('<s:property value="%{#session.accessRight}" />', '1111');
	});
	
</script>