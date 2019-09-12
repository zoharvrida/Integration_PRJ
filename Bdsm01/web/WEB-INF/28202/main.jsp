<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<% if (!request.getMethod().equals("GET")) {%>
<s:url var="ajaxUpdateTitle" action="28202_title_" />
<s:url var="ajaxUpdateButtons" action="28202_buttons" />
<sj:a id="tempTitle" href="%{ajaxUpdateTitle}" targets="ph-title" cssClass="ui-helper-hidden"></sj:a>
<sj:a id="tempButtons" href="%{ajaxUpdateButtons}" targets="ph-buttons" cssClass="ui-helper-hidden"></sj:a>
<s:form id="formCalc" action="28202_getCalc.action">
    <s:hidden name="amtTxn" />
    <s:hidden name="ccyName" />
    <s:hidden name="drCr" />
    <s:hidden name="codCcy" />
    <s:token name="calcToken"/>
</s:form>
<sj:a id="tempformCalc" formIds="formCalc" targets="ph-temp" cssClass="ui-helper-hidden"></sj:a>
<s:form id="formAccount" action="28202_getBalAvailable.action">
    <s:hidden name="noAcct" />
    <s:token name="balToken"/>
</s:form>
<sj:a id="tempformAccount" formIds="formAccount" targets="ph-temp" cssClass="ui-helper-hidden"></sj:a>
<s:form id="formMidRate" action="28202_getMidRate.action">
    <s:hidden name="ccyName" />
    <s:token name="ccyTokens"/>
</s:form>
<sj:a id="tempformMidRate" formIds="formMidRate" targets="ph-temp" cssClass="ui-helper-hidden"></sj:a>
<s:actionmessage id="actionMessage"/>
<s:actionerror name="actionError" />
<script type="text/javascript" src="_js/validation_theme_css_xhtml.js"></script>

<s:form id="frmMain" name="frmMain" action="28202" focusElement="frmMain_noAcct" validate="true">

    <s:textfield
        name="noAcct"
        requiredLabel="false"
        size="16"
        maxlength="20"
        cssClass="ui-widget ui-widget-content"
        key="label.noAcct"
        disabled="true"
        />
    <s:textfield
        name="acctTitle"
        requiredLabel="true"
        size="50"
        maxlength="60"
        cssClass="ui-widget ui-widget-content"
        disabled="true"
        />
    <s:textfield
        name="model.codProd"
        requiredLabel="false"
        size="4"
        maxlength="5"
        cssClass="ui-widget ui-widget-content"
        key="label.productCode"
        disabled="true"
        />
    <s:textfield
        name="prodData.namProduct"
        requiredLabel="true"
        size="50"
        maxlength="60"
        cssClass="ui-widget ui-widget-content"
        disabled="true"
        />
    <s:textfield
        name="balAvailscr"
        requiredLabel="false"
        size="30"
        maxlength="40"
        cssClass="ui-widget ui-widget-content"
        key="label.balAvail"
        disabled="true"
        />
    <s:hidden name="balAvail" />
    <%--<sj:checkboxlist
        id="typeSLLD"
        list="{'Debit', 'Credit'}"
        name="typeSLLD"
        key="label.trx.type"
        />--%>
    <s:url id="getListCcyCode" action="json/20302_getListCcyCode"/>
    <sj:select
        id="ccyUd"
        name="ccyUd"
        href="%{getListCcyCode}"
        list="listCcy"
        cssClass="ui-widget ui-widget-content"
        key="label.ccyTxn"
        emptyOption="true"
        headerKey="-1"
		onChange="getMidRate();"
        />
    <s:hidden name="ccyNames" />
    <s:textfield
        name="midRatescr"
        size="10"
        maxlength="10"
        cssClass="ui-widget ui-widget-content"
        key="label.midRate"
        disabled="true"
        />
    <s:hidden name="ccyMidRate" />
	
	<s:textfield
        name="txnAmountscr"
        size="30"
        maxlength="40"
        cssClass="ui-widget ui-widget-content"
        key="label.amtTxn"
        disabled="true"
        />
    <s:hidden name="txnAmount" />
    
    <s:textfield
        name="amtUsdscr"
        size="20"
        maxlength="30"
        cssClass="ui-widget ui-widget-content"
        key="label.amtTxnUsd"
        disabled="true"
        />
    <s:hidden name="amtUsd" />
    <s:hidden name="reason" />
    <s:hidden name="idMenu" value="%{#session.idMenu}"/>
    <s:hidden name="idMenuRed" />
    <s:hidden name="scrMsg" />
    <s:hidden name="idMaintainedBy" value="%{#session.idUser}" />
    <s:hidden name="idMaintainedSpv" />
    <s:hidden name="eximStatus" id="eximStatus"/>
    <s:hidden name="model.balAvailable" />
    <s:hidden name="model.codCust" />
    <s:hidden name="model.codCcy" />
    <s:hidden name="scrMsg.message" />
    <sj:a
        id="btnValidate"
        button="true"
        disabled="true"
        >Validate</sj:a>
    <sj:a
        id="btnSave"
        button="true"
        disabled="true"
        >Calculate</sj:a>
    <sj:a
        id="btnValas"
        button="true"
        disabled="true"
        />
    <sj:a
        id="btnLLD"
        button="true"
        disabled="true"
        />
    <s:token name="calcLLD"/>
</s:form>
<div id="divMessageCalc" />
<script type="text/javascript">
    function enableDisableType() {
        $("#typeSLLD").buttonset("enable");
        $("#typeSLLD-1").button("enable");
        $("#typeSLLD-2").button("enable");
    }
    function redirect(idMenu, message) {
        var control = "add";
        var Messaging = message +'<br/> Equivalent Amount USD :$' + $("#frmMain_amtUsdscr").val();
        if (idMenu != '') {
            Messaging = Messaging + '<br/> Redirecting to Menu ' + idMenu + ' <br/> Press "OK" to Continue';
        } else {
            idMenu = $("#frmMain_idMenu").val();
            control = "inquiry";
            Messaging = Messaging + ' <br/> Press "OK" to New Inquiry';
        }
        Messaging = Messaging + '<br/> Press "Cancel" to Return on Menu <br/> ' +
                'and Press "Clear" to New Inquiry ';
        //alert('message : ' + Messaging);
        messageBoxOkCancelClear(3, 'divMessageCalc',Messaging, function(event) {
                $("#frmGo_idMenu").val(idMenu);
            $("#frmGo_goAction").val(control);
            $("#frmGo_noCif").attr("value", $("#frmMain_model_codCust").val());
                $("#btnGo").click();
            }, function() {
            //alert("do Nothing");
			$("#btnSave").hide();
			$('#frmMain_txnAmountscr').attr("disabled", "enabled");
            }, function() {
            //alert("Back to Square");
                $("#frmGo_idMenu").attr("value", $("#frmMain_idMenu").val());
            $("#frmGo_noCif").attr("value", $("#frmMain_model_codCust").val());
                $("#frmGo_goAction").val("");
                $("#btnGo").click();
            });
    }
    function calcMidRate() {
        $('#formCalc_amtTxn').attr('value', $("#frmMain_txnAmount").val());
        $('#formCalc_ccyName').attr('value', $("#ccyUd").val());
        $('#formCalc_codCcy').attr('value', $("#frmMain_model_codCcy").val());
        $("#tempformCalc").click();
        calcParams = {};
        calcParams.amtTxn = "frmMain_txnAmount";
        calcParams.reason = "frmMain_reason";
        calcParams.onClose = function(amtTxn, reason, directLink, screenMsg) {
            if(reason === ''){
                screenMsg = $.parseJSON(screenMsg);
                if(directLink != null){
                directLink = $.parseJSON(directLink);
                }
                $("#frmMain_scrMsg_message").val(screenMsg.message);
                $("#frmMain_idMenuRed").val(directLink.destMenu);
                $('#frmMain_amtUsd').attr("value",amtTxn);
                $("#frmMain_amtUsdscr").val($("#frmMain_amtUsd").val());
                $("#frmMain_amtUsdscr").formatCurrency({decimalSymbol: '.', digitGroupSymbol: ',', symbol: ''});
                //alert("PK :" + directLink.pk.aliasName);
                if (directLink.pk === null) {
                    $("#btnValas").click();
                } else {
                    if (directLink.pk.aliasName === "VALAS") {
                        $("#btnValas").click();
                    } else if (directLink.pk.aliasName === "LLD") {
                    $("#btnLLD").click();
                    }
                }
            } else {
                messageBoxClass(2, 'divMessageCalc',reason, function() {
                        $("#frmMain_amtUsdscr").val(null);
                        $('#frmMain_reason').attr("values","");
                    });
            }
        }
    }
    function getMidRate() {
        $('#frmMain_ccyNames').attr('value', $("#frmMain_ccyUd").val());
        $('#formMidRate_ccyName').attr("value", $("#ccyUd").val());
        //alert($('#formMidRate_ccyName').val());
        $("#tempformMidRate").click();
        midParams = {};
        midParams.ccyMidRate = "frmMain_ccyMidRate";
        midParams.reason = "frmMain_reason";
        midParams.onClose = function(ccyName,ccyMidRate,reason) {
            //if (ccy != "USD")
            if (reason == '') {
                //alert("GET DATA midrate :" + ccyMidRate);
                $('#frmMain_ccyMidRate').attr("value",ccyMidRate);
                $("#frmMain_midRatescr").val($("#frmMain_ccyMidRate").val());
                $("#frmMain_midRatescr").formatCurrency({decimalSymbol: '.', digitGroupSymbol: ',', symbol: ''});
                $("#btnSave").button("enable");
            } else {
                messageBoxClass(2, 'divMessageCalc',reason, function() {
                        $("#frmMain_midRatescr").val(null);
                        $('#frmMain_reason').attr("values","");
                });
            }
        };
    }
    function getAcctBal() {
        $('#formAccount_noAcct').attr("value", $("#frmMain_noAcct").val());
        //alert($('#formAccount_noAcct').val());
        $("#tempformAccount").click();
        chParams = {};
        chParams.noAcct = "frmMain_noAcct";
        chParams.acctTitle = "frmMain_acctTitle";
        chParams.balAvail = "frmMain_balAvail";
        chParams.reason = "frmMain_reason";
        chParams.onClose = function(noAcct, acctTitle, balAvail, reason, model, prodData) {
            //if (ccy != "USD")
            if (reason == '') {
                model = $.parseJSON(model);
                prodData = $.parseJSON(prodData);
				$("#frmMain_balAvail").attr("value",balAvail);
				$("#frmMain_acctTitle").attr("value",acctTitle);
                
				$("#frmMain_balAvailscr").val($("#frmMain_balAvail").val());
                $("#frmMain_balAvailscr").formatCurrency({decimalSymbol: '.', digitGroupSymbol: ',', symbol: ''});
                //alert("MODEL :" + model);
                if (model == null) {
                    $("#frmMain_model_codProd").val(null);
                    $("#frmMain_model_codAcctTitle").val(null);
                    $("#frmMain_prodData_namProduct").val(null);
                    messageBoxClass(2, 'divMessageCalc', 'Invalid Account No', function() {
                        $('#frmMain_balAvailscr').attr("values", null);
                        $('#frmMain_reason').attr("values", "");
                    });
                } else {
                    //$("#frmMain_model_nik").val(inputNik);
                    $("#frmMain_model_codCcBrn").val(model.codCcBrn);
                    $("#frmMain_model_codProd").val(model.codProd);
                    //  validateForm_frmButton();
                    $("#frmMain_model_datAcctOpen").val(model.datAcctOpen); //rep to CASA
                    $("#frmMain_model_codAcctTitle").val(model.codAcctTitle);
                    $("#frmMain_model_codCust").val(model.codCust);
                    $("#frmMain_model_codCcy").val(model.codCcy);
                    $("#frmMain_model_codAcctStat").val(model.codAcctStat);
                    $("#frmMain_model_codTds").val(model.codTds);
                    $("#frmMain_model_balAcctMinReqd").val(model.balAcctMinReqd);
                    $('#frmMain_model_balAvailable').attr("value", model.balAvailable);
                    if(prodData != null){
                        $("#frmMain_prodData_namProduct").val(prodData.namProduct);
                    }
                }
            } else {
                messageBoxClass(2, 'divMessageCalc',reason, function() {
                        $('#frmMain_balAvailscr').attr("values",null);
                        $('#frmMain_reason').attr("values","");
                    });
            }
        };
    }
    $(function() {
        $("#ccyUd").combobox({size: 3});
        if ($("#actionError").length == 0 && $("#actionMessage").length == 0) {
            $("#rbBS").buttonset("destroy");
            $("#tempTitle").click();
            $("#tempButtons").click();
        } else {
            $("#rbBS").data("rdb").callCurrent();
            if ($("#actionError").length == 0) {
                $("#rbBS").data("rdb").clear(false);
            }
        }
        //$(".ui-autocomplete-input", $("#frmMain")).addClass("ui-widget ui-widget-content");
		$("#btnValas").hide();
		$("#btnLLD").hide();
        $("#btnSave").hide();
        $("#frmMain_noAcct")
                .parent()
                .append(
                $("#frmMain_acctTitle").detach()
                )
                ;
        $("#frmMain_model_codProd")
                .parent()
                .append(
                $("#frmMain_prodData_namProduct").detach()
                )
                ;
        $("#frmMain_txnAmountscr")
                .parent()
                .append(
                $("#btnValidate").detach()
                )
                ;
        $("#btnValidate")
                .parent()
                .append(
                $("#btnSave").detach()
                )
                ;
        $("#btnValas")
                .parent()
                .append(
                $("#btnLLD").detach()
                )
                ;
        $("#frmMain_noAcct").die('keydown');
        $("#frmMain_noAcct").live('keydown', function(e) {
            if (e.which == 9) {
                if ($("#frmMain_noAcct").val() != "") {
                    getAcctBal();
                    $("#ccyUd").focus();

                }
            }
        });

        $("#ccyUd").die('change');
        $("#ccyUd").live('change', function(e) {
            getMidRate();
        });
        $("#btnValas").click(function() {
            var menus = $("#frmMain_idMenuRed").val();
            var message = $("#frmMain_scrMsg_message").val();
            redirect(menus,message);
        });

        $("#btnLLD").click(function() {
            var menus = $("#frmMain_idMenuRed").val();
            var message = $("#frmMain_scrMsg_message").val();
            redirect(menus,message);
        });
        $("#typeSLLD").click(function() {
            //var schedProfile = $(this).val(); 
            $('#eximStatus').val($('input[name=typeSLLD]:checked', '#frmMain').val());
            $('#formCalc_drCr').attr("value",$('#eximStatus').val());
        });
        $('#typeSLLD-1').click(function() {
            $("#typeSLLD-2").attr("checked", false).button("refresh");
        });
        $('#typeSLLD-2').click(function() {
            $("#typeSLLD-1").attr("checked", false).button("refresh");
        });
        $("#btnSave").click(function() {
            if ($("#frmMain_txnAmountscr").val() != "") {
                calcMidRate();
            }
        });
		$("#frmMain_txnAmountscr").change(function(e) {
				$("#btnSave").hide();
        });

		$("#frmMain_txnAmountscr").die('keydown');
        $("#frmMain_txnAmountscr").live('keydown', function(e) {
            if(e.keyCode == 9) {
                $("#btnSave").hide();
                $("#btnValidate").click();
            }
        });
        $("#btnValidate").click(function() {
            if ($("#frmMain_txnAmountscr").val() != "") {
                //alert("GET DATA midrate :" + $("#frmMain_txnAmountscr").val());
                var regExps = new RegExp('^[0-9\,.]*$');
                if($("#frmMain_txnAmountscr").val().match(regExps)){
                $('#frmMain_txnAmount').attr("value", $("#frmMain_txnAmountscr").val())
                $("#frmMain_txnAmountscr").formatCurrency({decimalSymbol: '.', digitGroupSymbol: ',', symbol: ''});
                var x = $("#frmMain_txnAmountscr").val();
                //alert("GET midrate :" + x);
                $('#frmMain_txnAmountscr').attr("value", x);
                   $('#frmMain_txnAmountscr').attr("readonly", "true");
                   $("#btnSave").show();     
                } else {
                    reason = "Invalid Amount";
                    messageBoxClass(2, 'divMessageCalc', reason, function() {
                        $('#frmMain_txnAmountscr').val(null);
                    });
                }
            } else {
                reason = "Amount cannot be null";
                messageBoxClass(2, 'divMessageCalc', reason, function() {
                });
            }
        });
    });
</script>
<% } %>