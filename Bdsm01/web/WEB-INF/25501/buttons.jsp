<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<sj:radio
    id="rbBS"
	list="{'Inquiry','Add','Edit','Delete','Clear'}" 
	name="rbBS"
	disabled="true" 
    />
<script type="text/javascript">
    jQuery(document).ready(function() {
       
		$(window).resize();
        rdb.resetDisabled();
        rdb.resetReadonly();

        rdb.setDisabled("frmMain_AccountNo", "0000");
        rdb.setReadonly("frmMain_AccountNo", "0000");
        rdb.setDisableButton("frmMain_AccountNo", "0000");

        rdb.setDisabled("frmMain_btnFindAccount", "0000");
        rdb.setReadonly("frmMain_btnFindAccount", "0000");
        rdb.setDisableButton("frmMain_btnFindAccount", "0000");
        
        rdb.setDisabled("frmMain_codProd", "0000");
        rdb.setReadonly("frmMain_codProd", "1111");
        rdb.setDisableButton("frmMain_codProd", "0000");
        
        rdb.setDisabled("frmMain_giftCode", "0000");
        rdb.setReadonly("frmMain_giftCode", "0000");
        rdb.setDisableButton("frmMain_giftCode", "0000");
        
        rdb.setDisabled("frmMain_btnFindgiftCode", "0000");
        rdb.setReadonly("frmMain_btnFindgiftCode", "0000");
        rdb.setDisableButton("frmMain_btnFindgiftCode", "0000");
        
        rdb.setDisabled("frmMain_programID", "0000");
        rdb.setReadonly("frmMain_programID", "1111");
        rdb.setDisableButton("frmMain_programID", "0000");
        
        rdb.setDisabled("frmMain_Term", "0000");
        rdb.setReadonly("frmMain_Term", "0000");
        rdb.setDisableButton("frmMain_Term", "0000");
		
		rdb.setDisabled("frmMain_tax", "0000");
        rdb.setReadonly("frmMain_tax", "1111");
        rdb.setDisableButton("frmMain_tax", "0000");
        
		rdb.setDisabled("frmMain_btnFindTerm", "0000");
        rdb.setReadonly("frmMain_btnFindTerm", "0000");
        rdb.setDisableButton("frmMain_btnFindTerm", "0000");
		
        rdb.setDisabled("frmMain_Status", "0000");
        rdb.setReadonly("frmMain_Status", "1111");
        rdb.setDisableButton("frmMain_Status", "0000");
        
        rdb.setDisabled("frmMain_Opendate", "0000");
        rdb.setReadonly("frmMain_Opendate", "1111");
        rdb.setDisableButton("frmMain_Opendate", "0000");
        
        rdb.setDisabled("frmMain_Canceldate", "0000");
        rdb.setReadonly("frmMain_Canceldate", "1111");
        rdb.setDisableButton("frmMain_Canceldate", "0000");
        
        rdb.setDisabled("frmMain_giftPrices", "0000");
        rdb.setReadonly("frmMain_giftPrices", "1111");
        rdb.setDisableButton("frmMain_giftPrices", "0000");
        
		rdb.setDisabled("frmMain_holdAmounts", "0000");
        rdb.setReadonly("frmMain_holdAmounts", "1000");
        rdb.setDisableButton("frmMain_holdAmounts", "0000");
		
		rdb.setDisabled("frmMain_RangeholdAmount", "0000");
        rdb.setReadonly("frmMain_RangeholdAmount", "1111");
        rdb.setDisableButton("frmMain_RangeholdAmount", "0000");
		
		rdb.setDisabled("btnSubmit", "1111");
        rdb.setReadonly("btnSubmit", "1000");
        rdb.setDisableButton("btnSubmit", "1000");
		
        rdb.setOnChange(
		function() {
			changeAction("frmMain", "25501", "");
            $("#modes").val("3");
            $("#tempValidation_mode").val("3");
            $("Table#detailVal tbody#body2 tr#date1 td#dateOpen").attr('style','display:block');
            $("Table#detailVal tbody#body2 tr#date1 td#dateClose").attr('style','display:block');
            $("#wwlbl_frmMain_label_amortize_Account_closedDate").attr('style','display:block');
            $("#wwlbl_frmMain_label_amortize_Account_openDate").attr('style','display:block');
			$("Table#detailVal tbody#body2 tr#date1 td#closepad").attr('colspan','1');
			$("#wwctrl_frmMain_label_amortize_fieldset_legend_OpeningDetail").remove();
			$("div#wwgrp_frmMain_label_amortize_fieldset_legend_OpeningDetail br").remove();
			$("#wwctrl_frmMain_label_amortize_fieldset_legend_gifOpening").remove();
			$("div#wwgrp_frmMain_label_amortize_fieldset_legend_gifOpening br").remove();  			
		},
		function() {
			changeAction("frmMain", "25501", "_add");
			//$('#OpenGiftDetails').attr('style','display:none');
            $("#tempValidation_mode").val("1");
			$("#modes").val("1");
			$("#wwlbl_frmMain_label_amortize_Account_openDate").attr('style','display:block');
			$("Table#detailVal tbody#body2 tr#date1 td#dateOpen").attr('style','display:block');
            $("Table#detailVal tbody#body2 tr#date1 td#dateClose").attr('style','display:none');
            $("#wwlbl_frmMain_label_amortize_Account_closedDate").attr('style','display:none');
		},
        null,
		function() {
			changeAction("frmMain", "25501", "_delete");
			$("#modes").val("2");
            $("#tempValidation_mode").val("2");
            $("Table#detailVal tbody#body2 tr#date1 td#dateOpen").attr('style','display:none');
            $("#wwlbl_frmMain_label_amortize_Account_openDate").attr('style','display:none');
			$("Table#detailVal tbody#body2 tr#date1 td#dateClose").attr('style','display:block');
            $("#wwlbl_frmMain_label_amortize_Account_closedDate").attr('style','display:block');
			$("Table#detailVal tbody#body2 tr#date1 td#closepad").attr('colspan','2');
		},
		function(removeMsg) {
			//$('#tabbedPanel').hide();
			$("#frmMain_Term").attr("value", null);
            $("#frmMain_Status").attr("value", null);
            $("#frmMain_giftPrices").attr("value", null);
            $("#frmMain_AccountNo").attr("value", null);
            $("#frmMain_codProd").attr("value", null);
            $("#frmMain_giftCode").attr("value", null);
			$("#frmMain_programID").attr("value", null);
            $("#frmMain_RangeholdAmount").attr("value", null);
            $("#frmMain_holdAmounts").attr("value", null);
            $("#frmMain_tax").attr("value", null);
            $("#frmMain_Opendate").attr("value", null);
            $("#frmMain_Canceldate").attr("value", null);
			$("#giftName").attr("value", null);
			$("#giftCodes").attr("value", null);
			if (removeMsg) {
				$("#actionMessage").remove();
				$("#actionError").remove();
				$("span.errorMessage").remove();
				$("label").removeClass("errorLabel");
			}
        },null
	);
        rdb.setMenuAccess('<s:property value="%{#session.accessRight}" />', '1101');
    });
</script>