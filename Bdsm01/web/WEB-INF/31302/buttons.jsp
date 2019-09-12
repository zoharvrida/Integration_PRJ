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
		
		rdb.setDisableButton("btnPosting", "1111");
		rdb.setDisableButton("btnGeneratePDF", "1111");
		rdb.setDisableButton("btnLookupHajiType", "0011");
		
		rdb.setReadonly("frmMain_svdp_namCustFull", "1111");
		rdb.setReadonly("frmMain_svdp_birthPlace", "1111");
		rdb.setReadonly("frmMain_svdp_datBirthCust", "1111");
		rdb.setReadonly("frmMain_hajiTypeDesc", "1111");
		rdb.setReadonly("frmMain_svdp_codNatId", "1111");
		rdb.setReadonly("frmMain_svdp_txtCustAdrAdd1", "1111");
		rdb.setReadonly("frmMain_svdp_noacct", "1111");
		rdb.setReadonly("frmMain_svdp_balance", "1111");
		rdb.setReadonly("frmMain_svdp_biayabpih", "1111");
		rdb.setReadonly("frmMain_svdp_usdexchange", "1111");
		rdb.setReadonly("frmMain_svdp_biayaidr", "1111");
		
		rdb.setOnChange(
			function() {
				changeAction("frmMain", "31302", "_inquiry");
				
				$(".cls-row-button").hide();
				$("label[for='lblStatusInq']").show();
				$("#frmMain_svdp_statusInq").show();
				$("#btnPosting").button("disable");
			}, 
			function() {
				changeAction("frmMain", "31302", "_add");
				
				$(".cls-row-button").show();
				$("label[for='lblStatusInq']").hide();
				$("#frmMain_svdp_statusInq").hide();
				
				if ($("#frmMain_state").val() == "1")
					$("#btnPosting").button("enable");
				$("#frmInquiry_acctNoInq").focus();
			}, 
			null, 
			null, 
			function(removeMsg) {
				$("#formData_strData_mode").val(rdb.current);
				$("#frmInquiry_acctNoInq").attr('value', '');
				$("#frmInquiry_noPorsi").attr('value', '');
				$("#frmInquiry_hajiTypeDesc").attr('value', '');
				$("#frmMain_svdp_statusInq").attr('value', '');
				$("#frmMain_svdp_jamaahName").attr('value', '');
				$("#frmMain_svdp_birthPlace").attr('value', '');
				$("#frmMain_svdp_datBirthCust").attr('value', '');
				$("#frmMain_strData_datBirthCust").attr('value', '');
				$("#frmMain_svdp_hajiType").attr('value', '');
				$("#frmMain_strData_hajiType").attr('value', '');
				$("#frmMain_svdp_codNatId").attr('value', '');
				$("#frmMain_svdp_txtCustAdrAdd1").attr('value', '');
				$("#frmMain_svdp_acctNo").attr('value', '');
				$("#frmMain_svdp_balance").attr('value', '');
				$("#frmMain_svdp_bpihCost").attr('value', '');
				$("#frmMain_svdp_usdExchange").attr('value', '');
				$("#frmMain_svdp_bpihInIDR").attr('value', '');
				$("#frmMain_strData_bpihInIDR").attr('value', '');
				$("#frmMain_svdp_fatherName").attr('value', '');
				$("#frmMain_svdp_codCustEducn").attr('value', '');
				$("#frmMain_svdp_acctNoTo").attr('value', '');
				$("#frmMain_svdp_portionNo").attr('value', '');
				$("#frmMain_svdp_occupation").attr('value', '');
				$("#frmMain_strData_setoranAwal").attr('value', '');
				$("#frmMain_svdp_maritalStatus").attr('value', '');
				$("#frmMain_svdp_setoranAwal").attr('value', '');
				$("#frmMain_setoranAwal_setoranAwal").attr('value', '');
				$("#frmMain_svdp_acctNoInq").attr('value', '');
				$("#frmMain_strData_balance").attr('value', '');
				$("#frmMain_strData_bpihCost").attr('value', '');
				$("#frmMain_strData_usdExchange").attr('value', '');
				$("#frmMain_svdp_sisaLunas").attr('value', '');
				$("#frmMain_strData_sisaLunas").attr('value', '');
				
				$("#btnPosting").button("disable");
				$("#btnGeneratePDF").button("disable");
				
				$("#frmMain, #frmInquiry").find("input[type='text'], textarea")
					.css("opacity", '')
					.filter("[readonly]").css("opacity", "0.5");
				$("#ph-main").animate({scrollTop: 0}, "slow");
				
				if (removeMsg) {
					$("#actionMessage").remove();
					$("#actionError").remove();
					$("span.errorMessage").remove();
					$("label").removeClass("errorLabel");
				}
			} 
		);
		
		rdb.setMenuAccess('<s:property value="%{#session.accessRight}" />', '1111');
	});
	
</script>