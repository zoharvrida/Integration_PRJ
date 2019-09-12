<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags" %>


<sj:radio 
	id="rbBS" 
	list="{'Inquiry', 'Add', 'Edit', 'Delete', 'Clear'}"
	name="rbBS" 
	disabled="true" 
/>

<s:set name="noCif" value="%{}" scope="session" />
<s:set name="goAction" value="%{}" scope="session" />


<script type="text/javascript">
	var myFunction = function pad(str, max){
		return str.length < max ? pad("0"+str,max) : str;
	};
	/*
	var dateS = new Date('<s:property value="%{#session.dtBusiness}" />');
	dateS = new Date(dateS.getYear(),dateS.getMonth()+1,0);
	dateS = dateS.getDate() + "/" + myFunction((dateS.getMonth()+1).toString(),2) + "/20" +dateS.getYear();
	*/
	// generate next month of dtBusiness
	var nmDate = new Date("<s:date name='%{#session.dtBusiness}' format='yyyy' />", 
				"<s:date name='%{#session.dtBusiness}' format='MM' />"-1+1, "<s:date name='%{#session.dtBusiness}' format='dd' />");
	var dateS = myFunction(nmDate.getDate().toString(), 2) + "/" + myFunction((nmDate.getMonth() + 1).toString(), 2) + "/" + nmDate.getFullYear();
	
	// generate current month of dtBusiness
	var dtIssued = new Date("<s:date name='%{#session.dtBusiness}' format='yyyy' />", 
				"<s:date name='%{#session.dtBusiness}' format='MM' />"-1, "<s:date name='%{#session.dtBusiness}' format='dd' />");
	var dateI = myFunction(dtIssued.getDate().toString(), 2) + "/" + myFunction((dtIssued.getMonth() + 1).toString(), 2) + "/" + dtIssued.getFullYear();
	
	
	function setIssuedDate() { $("#dtIssued").attr("value", dateI); }
	function setExpiryDate() { $("#dtExpiry").attr("value", dateS); }
	
	function lookupDataMaster(master, id, constant, fieldDescName) {
		if (id != null)
			$.post(
				"json/DataMaster_get.action",
				{
					master: master, 
					id: id, 
					constant: (constant==null)? "false": constant, 
					rows:10, 
					page:1
				},
				function(data) {
					if (data.data.id != null) {
						$("#" + fieldDescName).val(data.data.desc);
					}
				},
				"json"
			);
	}
	

	jQuery(document).ready(function() {

		$(window).resize();
		rdb.resetDisabled();
		rdb.resetReadonly();
	
		rdb.setDisabled("frmMain_noCif"          , "0000");
		rdb.setDisabled("frmMain_namCustFull"    , "1111");
		rdb.setDisabled("frmMain_noUdSearch"     , "0100");
		rdb.setDisabled("frmMain_noUd"           , "1000");
		rdb.setDisabled("dtIssued"               , "1111");
		rdb.setDisabled("dtExpiry"               , "1111");
		rdb.setDisabled("ccyUd"                  , "1001");
		rdb.setDisabled("frmMain_amtLimitScr"    , "1000");
		rdb.setDisabled("frmMain_amtLimitScrUSD" , "1000");
		rdb.setDisabled("frmMain_usdIdrRateScr"  , "1000");
		rdb.setDisabled("frmMain_ratFcyIdrScr"   , "1000");
		rdb.setDisabled("frmMain_remarks"        , "1000");
		rdb.setDisabled("frmMain_cdBranch"       , "1000");
		rdb.setDisabled("frmMain_status"         , "1000");

		rdb.setReadonly("frmMain_noCif"          , "0000");
		rdb.setReadonly("frmMain_namCustFull"    , "1111");
		rdb.setReadonly("frmMain_noUdSearch"     , "0100");
		rdb.setReadonly("frmMain_noUd"           , "1011");
		rdb.setReadonly("frmMain_txtTypeUD"      , "1111");
		rdb.setReadonly("frmMain_payeeName"      , "1001");
		rdb.setReadonly("frmMain_tradingProduct" , "1001");
		rdb.setReadonly("payeeCountry_widget"    , "1111");
		rdb.setReadonly("dtExpiry"               , "1111");
		rdb.setReadonly("ccyUd"                  , "1001");
		rdb.setReadonly("frmMain_amtLimitScr"    , "1001");
		rdb.setReadonly("frmMain_amtLimitScrUSD" , "1111");
		rdb.setReadonly("frmMain_usdIdrRateScr"  , "1111");
		rdb.setReadonly("frmMain_ratFcyIdrScr"   , "1001");
		rdb.setReadonly("frmMain_remarks"        , "1001");
		rdb.setReadonly("frmMain_cdBranch"       , "1111");
		rdb.setReadonly("frmMain_status"         , "1111");
	
		rdb.setDisableButton("btnSave"           , "1000");
		rdb.setDisableButton("btnFindUd"         , "1111");
		rdb.setDisableButton("btnClear"          , "0000");
		rdb.setDisableButton("btnUpdateUSD"      , "1001");
		rdb.setDisableButton("btnCatUDLookup"    , "1111");
		rdb.setDisableButton("btnTypeUDLookup"   , "1111");
	
	
		rdb.setOnChange(
			function() {
				changeAction("frmMain", "28302", "");
				$("#frmMain_noCif").focus();
				$("#frmMain_cdBranch").attr("value", null);
				$("#frmMain_status").attr("value", null);
			},
			function() {
				changeAction("frmMain", "28302", "_add");
				$("#frmMain_noCif").focus();
				$("#btnSave").attr("value", '<s:text name="button.save" />');
			},
			function() {
				changeAction("frmMain", "28302", "_edit");
				$("#frmMain_noCif").focus();
				$("#frmMain_cdBranch").attr("value", null);
				$("#frmMain_status").attr("value", null);
				$("#btnSave").attr("value", '<s:text name="button.update" />');
			},
			function() {
				changeAction("frmMain", "28302", "_delete");
				$("#frmMain_noCif").focus();
				$("#frmMain_cdBranch").attr("value", null);
				$("#frmMain_status").attr("value", null);
				$("#btnSave").attr("value", '<s:text name="button.delete" />');
			},
			function(removeMsg) {
				$("#frmMain_noCif").attr("value", null);
				$("#frmMain_namCustFull").attr("value", null);
				$("#frmMain_noUdSearch").attr("value", null);
				$("#frmMain_noUd").attr("value", null);
				$("#frmMain_noUdIsExist").attr("value", "false");
				$("#frmMain_catUD").attr("value", null);
				$("#frmMain_txtCatUD").attr("value", null);
				$("#frmMain_typeUD").attr("value", null);
				$("#frmMain_txtTypeUD").attr("value", null);
				$("#frmMain_payeeName").attr("value", null);
				$("#payeeCountry").attr("value", null);
				$("#payeeCountry_widget").attr("value", null);
				$("#frmMain_tradingProduct").attr("value", null);
				$(".ui-datepicker-trigger").hide();
				$("#dtIssued").attr("value", null);
				$("#dtExpiry").attr("value", null);
				$("#ccyUd").attr("value", null);
				$("#ccyUd_combobox > input").attr("value", null);
				$("#frmMain_amtLimitScr").attr("value", null);
				$("#frmMain_amtLimitScrUSD").attr("value", null);
				$("#frmMain_usdIdrRateScr").attr("value", null);
				$("#frmMain_ratFcyIdrScr").attr("value", null);
				$("#frmMain_remarks").attr("value", null);
				$("#frmMain_cdBranch").attr("value", null);
				$("#frmMain_status").attr("value", null);
				
				$("#btnSave").hide();
				
				if (removeMsg) {
					$("#actionMessage").remove();
					$("#actionError").remove();
					$("span.errorMessage").remove();
					$("label").removeClass("errorLabel");
				}
			}
		);

		rdb.setMenuAccess('<s:property value="%{#session.accessRight}" />', '1111');
		
		var sessionNoCif = '<s:property value="%{#session.noCif}" />';
		if (sessionNoCif!='') {
			setTimeout(function() {
				$("#rbBSAdd").change().click();
				$("#rbBSAdd").button("refresh");
				$("#frmMain_noCif").val(sessionNoCif);
				$("#frmMain_noCif").trigger($.Event("keydown", { keyCode: 9 }));
				$("#frmMain_noUd").focus();
			}, 1000);
		}
	});
</script>