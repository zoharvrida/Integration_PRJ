<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<% if (!request.getMethod().equals("GET")) {%>
<s:url var="ajaxUpdateTitle" action="28401_title_" />
<s:url var="ajaxUpdateButtons" action="28401_buttons" />
<sj:a id="tempTitle" href="%{ajaxUpdateTitle}" targets="ph-title" cssClass="ui-helper-hidden"></sj:a>
<sj:a id="tempButtons" href="%{ajaxUpdateButtons}" targets="ph-buttons" cssClass="ui-helper-hidden"></sj:a>
<s:form id="formGetData" action="28401_getData.action">
    <s:hidden name="noAcct" />
    <s:hidden name="refTxn" />
    <s:hidden name="typMsg" />
    <s:hidden name="typTrx" />
    <s:hidden name="typAcct" />
    <s:token name="getData"/>
</s:form>
<sj:a id="tempGetData" formIds="formGetData" targets="ph-temp" cssClass="ui-helper-hidden"></sj:a>
<s:form id="tempFrmDlgMfcNoBook" action="dlg">
    <s:hidden name="dialog" value="dlgMfcNoBook" />
    <s:hidden name="noAcct" />
    <s:hidden name="dtPost" />
    <s:hidden name="typMsg" />
    <s:hidden name="status" />
    <s:hidden name="idMenu" id="idMenu_LLD" />
    <s:token />
</s:form>
<sj:a id="tempDlgMfcNoBook" formIds="tempFrmDlgMfcNoBook" targets="ph-dlg" cssClass="ui-helper-hidden"></sj:a>
<s:form id="tempFrmDlgFcyTxnFlagCIF" action="dlg">
    <s:hidden name="dialog" value="dlgFcyTxnFlagCIF" />
    <s:hidden name="acctNo" />
    <s:hidden name="period" />
    <s:token />
</s:form>
<sj:a id="tempDlgFcyTxnFlagCIF" formIds="tempFrmDlgFcyTxnFlagCIF" targets="ph-dlg" cssClass="ui-helper-hidden"></sj:a>
<s:form id="tempFrmDlgFcyTxnFlagUD" action="dlg">
    <s:hidden name="dialog" value="dlgFcyTxnFlagUDLLD" />
    <s:hidden name="noCif" />
    <s:hidden name="ccyUd" />
    <s:hidden name="noUd" />
    <s:hidden name="amtTxn" />
    <s:hidden name="noAcct" />
    <s:token />
</s:form>
<sj:a id="tempDlgFcyTxnFlagUD" formIds="tempFrmDlgFcyTxnFlagUD" targets="ph-dlg" cssClass="ui-helper-hidden"></sj:a>
<s:form id="tempFrmDlgAuth" action="dlg">
    <s:hidden name="dialog" value="dlgAuth" />
    <s:hidden name="idMenu" value="%{#session.idMenu}" />
    <s:token />
</s:form>
<sj:a id="tempDlgAuth" formIds="tempFrmDlgAuth" targets="ph-dlg" cssClass="ui-helper-hidden"></sj:a>
<s:actionmessage id="actionMessage" />
<s:actionerror name="actionError" />
<script type="text/javascript" src="_js/validation_theme_css_xhtml.js"></script>
<s:form id="frmMain" name="frmMain" action="28401" focusElement="frmMain_noAcct" validate="true">
    <s:textfield
        name="noAcct"
        requiredLabel="true"
        size="16"
        maxlength="16"
        cssClass="ui-widget ui-widget-content"
        key="label.noAcct"
        disabled="true"
        />
    
	<sj:datepicker
        id="dtPost"
        name="dtPost"
        key="label.dtPost"
        cssClass="ui-widget ui-widget-content"        
        displayFormat="dd/mm/yy"
        firstDay="1"
        buttonImageOnly="true"
        showOn="both"
        readonly="true"
        />
		
    <sj:submit
        id="btnChooseTxn"
        formIds="frmMain"
        buttonIcon="ui-icon-gear"
        button="true"
        key="button.chooseTxn"
        disabled="false"
        targets="ph-main"
        onBeforeTopics="beforeSubmit"
        />
    <s:textfield
        name="refTxn"
        size="40"
        maxlength="40"
        cssClass="ui-widget ui-widget-content"
        key="label.txnRefNo"
        disabled="true"
        />
    <s:textfield
        name="ccyTxn"
        size="3"
        maxlength="3"
        cssClass="ui-widget ui-widget-content"
        key="label.ccyTxn"
        disabled="true"
        />
    <s:textfield
        name="amtTxnScr"
        size="12"
        maxlength="20"
        cssClass="ui-widget ui-widget-content"
        key="label.amtTxn"
        disabled="true"
        />
    <s:hidden name="amtTxn" />
    <s:textfield
        name="ratFcyIdrScr"
        size="10"
        maxlength="10"
        cssClass="ui-widget ui-widget-content"
        key="label.ratFcyIdr"
        disabled="true"
        />
    <s:hidden name="ratFcyIdr" />
    <s:textfield
        name="amtTxnLcyScr"
        size="20"
        maxlength="20"
        cssClass="ui-widget ui-widget-content"
        key="label.amtTxnLcy"
        disabled="true"
        />
    <s:hidden name="amtTxnLcy" />
    <s:textfield
        name="ratUsdIdrScr"
        size="10"
        maxlength="10"
        cssClass="ui-widget ui-widget-content"
        key="label.ratUsdIdr"
        disabled="true"
        />
    <s:hidden name="ratUsdIdr" />
    <s:textfield
        name="amtTxnUsdScr"
        size="12"
        maxlength="20"
        cssClass="ui-widget ui-widget-content"
        key="label.amtTxnUsd"
        disabled="true"
        />
    <s:hidden name="amtTxnUsd" />
    <s:textfield
        name="txnDesc"
        size="50"
        maxlength="120"
        cssClass="ui-widget ui-widget-content"
        key="label.txnDescription"
        disabled="true"
        />
    <s:textfield
        name="dtmTxn"
        size="20"
        maxlength="20"
        cssClass="ui-widget ui-widget-content"
        key="label.dtmTxnTime"
        disabled="true"
        />
    <s:textfield
        name="dtValue"
        size="10"
        maxlength="10"
        cssClass="ui-widget ui-widget-content"
        key="label.dtValue"
        disabled="true"
        />
    <s:textfield
        name="idTxn"
        size="16"
        maxlength="16"
        cssClass="ui-widget ui-widget-content"
        key="label.idUser"
        disabled="true"
        />
    <s:textfield
        name="typMsg"
        size="8"
        maxlength="8"
        cssClass="ui-widget ui-widget-content"
        key="label.typMsg"
        disabled="true"
        />
    <s:textfield
        name="txnBranch"
        size="5"
        maxlength="5"
        cssClass="ui-widget ui-widget-content"
        key="label.txnBranch"
        disabled="true"
        />
    <s:textfield
        name="noCif"
        requiredLabel="true"
        size="10"
        maxlength="10"
        cssClass="ui-widget ui-widget-content"
        key="label.noCif"
        disabled="true"
        />
    <sj:a
        id="btnFindCifNo"
        button="true"
        disabled="true"
        >...</sj:a>    
    <s:textfield
        name="namCustFull"
        size="40"
        maxlength="40"
        cssClass="ui-widget ui-widget-content"
        label=""
        labelSeparator=""
        disabled="true"
        />
    <s:textfield
        name="codAcctCustRel"
        size="5"
        maxlength="3"
        cssClass="ui-widget ui-widget-content"
        label=""
        labelSeparator=""
        disabled="true"
        />
    <%-- <s:textfield
        name="amtAvailUsdScr"
        size="12"
        maxlength="20"
        cssClass="ui-widget ui-widget-content"
        key="label.amtAvailUsd"
        disabled="true"
/>--%>
    <s:hidden name="amtAvailUsdScr" />

    <s:hidden name="amtAvailUsd" />
    <s:textfield
        name="noUd"
        size="45"
        maxlength="45"
        cssClass="ui-widget ui-widget-content"
        key="label.noUd"
        disabled="true"
        />
    <sj:a
        id="btnFindUd"
        button="true"
        disabled="true"
        >...</sj:a>
    <s:hidden name="period" />
    <s:hidden name="idMenu" value="%{#session.idMenu}" />
    <s:hidden name="idMaintainedBy" value="%{#session.idUser}" />
    <s:hidden name="idMaintainedSpv" />
    <s:hidden name="udTypeCategory" />
    <s:hidden name="typTrx" />
    
    <sj:submit
        id="btnSave"
        formIds="frmMain"
        buttonIcon="ui-icon-gear"
        button="true"
        key="button.save"
        disabled="true"
        targets="ph-main"
        onBeforeTopics="beforeSubmitSave"
        />
    <s:token name="saveFlag"/>
</s:form>    
<div id="divMessageFlag" />
<script type="text/javascript">
    function getDate() {
        var datePick = '<s:date name="%{#session.dtBusiness}" format="dd/MM/yyyy" />';
        var datFirst = '<s:date name="%{#session.dtFirstBuss}" format="yyyy-MM-dd" />';
        var datLast = '<s:date name="%{#session.dtLastBuss}" format="yyyy-MM-dd" />';
        $('#dtPost').attr("value", datePick);
        var date = new Date(datLast);
        var currentMonth = date.getMonth();
        var currentDate = date.getDate();
        var currentYear = date.getFullYear();
		var dateMax = new Date(currentYear, currentMonth, currentDate);
		$('#dtPost').datepicker("option", "maxDate", dateMax);
        var dates = new Date(datFirst);
        var currentMonths = dates.getMonth();
        var currentDates = dates.getDate();
        var currentYears = dates.getFullYear();
		var dateMin = new Date(currentYears, currentMonths, currentDates);
        $('#dtPost').datepicker("option", "minDate", dateMin);
    }
	function dataGet(){
		var sessionTest = '<s:property value="%{#session}" />';
		//alert("SES :" + sessionTest); 
		var sessionNoAcct = '<s:property value="%{#session.noAcct}" />';
		var sessionRefTxn = '<s:property value="%{#session.refTxn}" />';
		var sessionTypMsg = '<s:property value="%{#session.typMsg}" />';
		var sessionTypTrx = '<s:property value="%{#session.typTrx}" />';
		var sessionDate = '<s:property value="%{#session.dateTxn}" />';
		
		if (sessionNoAcct!='' && sessionRefTxn!='' && sessionTypMsg!='') {
        //setTimeout(function() {
            //alert("SES 2:" + sessionTest); 
			//alert("ACCT 1:" + noAcct);
            tempParams = {};
			//alert("TEMP :" + tempParams.onClose);
            /*tempParams.onClose = function(noAcct, refTxn, ccyTxn, amtTxn, ratFcyIdr, amtTxnLcy, ratUsdIdr, 
                                          amtTxnUsd, txnDesc, dtmTxn, dtValue, idTxn, typMsg, txnBranch) {*/
            tempParams.onClose = function(noAcct, refTxn, ccyTxn, amtTxn, ratFcyIdr, amtTxnLcy, ratUsdIdr,amtTxnUsd, txnDesc, dtmTxn, dtValue, idTxn, typMsg, txnBranch, typTrx) {
				//alert("acct :" + noAcct);
				$("#frmMain_noAcct").attr('value',noAcct);
                $("#frmMain_refTxn").val(refTxn);
                $("#frmMain_ccyTxn").val(ccyTxn);
                $("#frmMain_amtTxn").val(amtTxn);
                $("#frmMain_amtTxnScr").val($("#frmMain_amtTxn").val());
                $("#frmMain_amtTxnScr").formatCurrency({decimalSymbol:'.', digitGroupSymbol:',', symbol:''});
                $("#frmMain_ratFcyIdr").val(ratFcyIdr);
                $("#frmMain_ratFcyIdrScr").val($("#frmMain_ratFcyIdr").val());
                $("#frmMain_ratFcyIdrScr").formatCurrency({decimalSymbol:'.', digitGroupSymbol:',', symbol:''});
                $("#frmMain_amtTxnLcy").val(amtTxnLcy);
                $("#frmMain_amtTxnLcyScr").val($("#frmMain_amtTxnLcy").val());
                $("#frmMain_amtTxnLcyScr").formatCurrency({decimalSymbol:'.', digitGroupSymbol:',', symbol:''});
                $("#frmMain_ratUsdIdr").val(ratUsdIdr);
                $("#frmMain_ratUsdIdrScr").val($("#frmMain_ratUsdIdr").val());
                $("#frmMain_ratUsdIdrScr").formatCurrency({decimalSymbol:'.', digitGroupSymbol:',', symbol:''});
                $("#frmMain_amtTxnUsd").val(amtTxnUsd);
                $("#frmMain_amtTxnUsdScr").val($("#frmMain_amtTxnUsd").val());
                $("#frmMain_amtTxnUsdScr").formatCurrency({decimalSymbol:'.', digitGroupSymbol:',', symbol:''});
                $("#frmMain_txnDesc").val(txnDesc);
                $("#frmMain_dtmTxn").val(dtmTxn);
                $("#frmMain_dtValue").val(dtValue);
                $("#frmMain_idTxn").val(idTxn);
                if(typMsg == 'O') {
                    $("#frmMain_typMsg").val('Original');
                } else if(typMsg == 'R') {
                    $("#frmMain_typMsg").val('Reversal');
                }                
                $("#frmMain_txnBranch").val(txnBranch);
                $("#btnFindCifNo").button("enable");
            };
            $("#formGetData_noAcct").val(sessionNoAcct);
            $("#formGetData_refTxn").val(sessionRefTxn);
            $("#formGetData_typMsg").val(sessionTypMsg);
            $("#formGetData_typTrx").val(sessionTypTrx);
            $("#tempGetData").click();
        //}, 1000);
		}
	}
    $(function() {
        if ($("#actionError").length==0 && $("#actionMessage").length==0) {
            $("#rbBS").buttonset("destroy");
            $("#tempTitle").click();
            $("#tempButtons").click();
        } else {
            $("#rbBS").data("rdb").callCurrent();
            if ($("#actionError").length==0) {
                $("#rbBS").data("rdb").clear(false);
            }
        }
        
        getDate();
		dataGet();
		$("#frmMain_noCif")
        .parent()
        .append(
        $("#btnFindCifNo").detach()
    )
        .append(
        $("#frmMain_namCustFull").detach()
    )
        .append(
        $("#frmMain_codAcctCustRel").detach()
    )
        ;

        $("#frmMain_noUd")
        .parent()
        .append(
        $("#btnFindUd").detach()
    )
        ;

        $("#frmMain_noUd").die('keydown');
        $("#frmMain_noUd").live('keydown', function(e) {
            if(e.keyCode == 9) {
                $("#btnFindUd").click();
            }
        });

        $("#btnChooseTxn").unsubscribe("beforeSubmit");
        $("#btnChooseTxn").subscribe("beforeSubmit", function(event) {
            if($("#frmMain_noAcct").val() == '') {
                messageBoxClass(2, 'divMessageFlag','Account No Cannot be Null', function() {
				});
                event.originalEvent.options.submit = false;
                return;
            }
            
            dlgParams = {};
            dlgParams.refTxn = "frmMain_refTxn";
            dlgParams.ccyTxn = "frmMain_ccyTxn";
            dlgParams.amtTxn = "frmMain_amtTxn";
            dlgParams.ratFcyIdr = "frmMain_ratFcyIdr";
            dlgParams.amtTxnLcy = "frmMain_amtTxnLcy";
            dlgParams.ratUsdIdr = "frmMain_ratUsdIdr";
            dlgParams.amtTxnUsd = "frmMain_amtTxnUsd";
            dlgParams.txnDesc = "frmMain_txnDesc";
            dlgParams.dtmTxn = "frmMain_dtmTxn";
            dlgParams.dtValue = "frmMain_dtValue";
            dlgParams.idTxn = "frmMain_idTxn";
            dlgParams.typMsg = "frmMain_typMsg";
            dlgParams.txnBranch = "frmMain_txnBranch";
            dlgParams.typTrx = "frmMain_typTrx";
            
            dlgParams.onClose = function() {
                $("#frmMain_amtTxnScr").val($("#frmMain_amtTxn").val());
                $("#frmMain_amtTxnScr").formatCurrency({decimalSymbol:'.', digitGroupSymbol:',', symbol:''});
                $("#frmMain_ratFcyIdrScr").val($("#frmMain_ratFcyIdr").val());
                $("#frmMain_ratFcyIdrScr").formatCurrency({decimalSymbol:'.', digitGroupSymbol:',', symbol:''});
                $("#frmMain_amtTxnLcyScr").val($("#frmMain_amtTxnLcy").val());
                $("#frmMain_amtTxnLcyScr").formatCurrency({decimalSymbol:'.', digitGroupSymbol:',', symbol:''});
                $("#frmMain_ratUsdIdrScr").val($("#frmMain_ratUsdIdr").val());
                $("#frmMain_ratUsdIdrScr").formatCurrency({decimalSymbol:'.', digitGroupSymbol:',', symbol:''});
                $("#frmMain_amtTxnUsdScr").val($("#frmMain_amtTxnUsd").val());
                $("#frmMain_amtTxnUsdScr").formatCurrency({decimalSymbol:'.', digitGroupSymbol:',', symbol:''});
                
                if($("#frmMain_refTxn").val() != '') {
                    $("#btnFindCifNo").button("enable");
                    $("#btnFindUd").button("enable");
                    $("#frmMain_noUd").attr("readonly", null);
                }
            };
            $("#ph-dlg").dialog("option", "position", "center");
            $("#ph-dlg").dialog("option", "width", $("body").width()*3/4);
            //$("#ph-dlg").dialog("option", "height", $("body").height()*3/4);
            $("#ph-dlg").dialog("option", "height", "450");
            $("#ph-dlg")
            .html("Please wait...")
            .unbind("dialogopen")
            .bind("dialogopen", function() {
                $("#tempFrmDlgMfcNoBook_noAcct").val($("#frmMain_noAcct").val());
				//alert("DATEPOST :" + $("#dtPost").val());
                $('#tempFrmDlgMfcNoBook_dtPost').attr("value",$("#dtPost").val());
                if($("#rbBSAdd").attr("checked")) {
                    $("#tempFrmDlgMfcNoBook_typMsg").val('O');
                    $("#tempFrmDlgMfcNoBook_status").val('P');
                } else if($("#rbBSDelete").attr("checked")) {
                    $("#tempFrmDlgMfcNoBook_typMsg").val('R');
                    $("#tempFrmDlgMfcNoBook_status").val('P');
                }
           $('#idMenu_LLD').attr('value',$("#frmMain_idMenu").val());
                $("#tempDlgMfcNoBook").click();
            })
            .dialog("open");
            event.originalEvent.options.submit = false;
        });
        
        $("#btnFindCifNo").click(function() {
            if(!($("#btnFindCifNo").button('option').disabled != undefined && 
                $("#btnFindCifNo").button('option', 'disabled'))) {
                dlgParams = {};
                dlgParams.noCif = "frmMain_noCif";
                dlgParams.namCustFull = "frmMain_namCustFull";
                dlgParams.codAcctCustRel = "frmMain_codAcctCustRel";
                dlgParams.amtAvailUsd = "frmMain_amtAvailUsd";
                dlgParams.onClose = function() {
                    $("#frmMain_amtAvailUsdScr").val($("#frmMain_amtAvailUsd").val());
                    $("#frmMain_amtAvailUsdScr").formatCurrency({decimalSymbol:'.', digitGroupSymbol:',', symbol:''});
                    
                    /*  if(($("#frmMain_amtAvailUsd").val() - $("#frmMain_amtTxnUsd").val() < 0) || ($("#frmMain_codAcctCustRel").val() != "SOW"))  {
                     */
                   
                    $("#frmMain_noUd").focus();
                    /* } else {
                        $("#btnSave").button("enable");                            
                     }*/
                };
                $("#ph-dlg").dialog("option", "position", "center");
                $("#ph-dlg").dialog("option", "width", $("body").width()*3/4);
                //$("#ph-dlg").dialog("option", "height", $("body").height()*3/4);
                $("#ph-dlg").dialog("option", "height", "450");
                $("#ph-dlg")
                .html("Please wait...")
                .unbind("dialogopen")
                .bind("dialogopen", function() {
                    $("#tempFrmDlgFcyTxnFlagCIF_acctNo").val($("#frmMain_noAcct").val());
                    $("#tempFrmDlgFcyTxnFlagCIF_period").val('<s:date name="%{#session.dtBusiness}" format="yyyyMM" />');
                    $("#tempDlgFcyTxnFlagCIF").click();
                })
                .dialog("open");
            }
        });
        
        $("#btnFindUd").click(function() {
            if(!($("#btnFindUd").button('option').disabled != undefined && 
                $("#btnFindUd").button('option', 'disabled'))) {
                dlgParams = {};
                dlgParams.noUd = "frmMain_noUd";
                dlgParams.udTypeCategory = "frmMain_udTypeCategory";
                dlgParams.udTypeValue = "frmMain_udTypeValue";
                dlgParams.onClose = function() {
                    $("#btnSave").button("enable");                            
                };
                $("#ph-dlg").dialog("option", "position", "center");
                $("#ph-dlg").dialog("option", "width", $("body").width()*3/4);
                //$("#ph-dlg").dialog("option", "height", $("body").height()*3/4);
                $("#ph-dlg").dialog("option", "height", "450");
                $("#ph-dlg")
                .html("Please wait...")
                .unbind("dialogopen")
                .bind("dialogopen", function() {
                    $("#tempFrmDlgFcyTxnFlagUD_noCif").val($("#frmMain_noCif").val());
                    $("#tempFrmDlgFcyTxnFlagUD_ccyUd").val($("#frmMain_ccyTxn").val());
                    $("#tempFrmDlgFcyTxnFlagUD_noUd").val($("#frmMain_noUd").val());
                    $("#tempFrmDlgFcyTxnFlagUD_amtTxn").val($("#frmMain_amtTxnUsd").val());
                    $("#tempFrmDlgFcyTxnFlagUD_noAcct").val($("#frmMain_noAcct").val());                  
                    //$("#tempFrmDlgFcyTxnFlagUD_noAcct").val($("#frmMain_noAcct").val());
                    //$('#idMenu_LLDUd').attr('value', $("#frmMain_idMenu").val());
                    $("#tempDlgFcyTxnFlagUD").click();
                })
                .dialog("open");
            }
        });
        
        $("#btnSave").unsubscribe("beforeSubmitSave");
        $("#btnSave").subscribe("beforeSubmitSave", function(event) {
            $("#frmMain").unbind("submit");
            event.originalEvent.options.submit = false;
            if (validateForm_frmMain()) {
                dlgParams = {};
                dlgParams.idMaintainedBy = "frmMain_idMaintainedBy";
                dlgParams.idMaintainedSpv = "frmMain_idMaintainedSpv";
                dlgParams.event = event;
                dlgParams.onSubmit = function() {
                    dlgParams.event.originalEvent.options.submit = true;
                    $("#btnSave").unsubscribe("beforeSubmitSave");
                    $("#btnSave").click();
                };
                $("#ph-dlg").dialog("option", "position", "center");
                //$("#ph-dlg").dialog("option", "width", $("body").width()*2/4);
                //$("#ph-dlg").dialog("option", "height", $("body").height()*2/4);
                $("#ph-dlg").dialog("option", "width", "320");
                $("#ph-dlg").dialog("option", "height", "180");
                $("#ph-dlg")
                .html("Please wait...")
                .unbind("dialogopen")
                .bind("dialogopen", function() {
                    $("#tempDlgAuth").click();
                })
                .dialog("open");
            }
        });
    });
</script>
<% }%>