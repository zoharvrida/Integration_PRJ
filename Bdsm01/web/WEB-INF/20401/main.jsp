<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags" %>

<% if(!request.getMethod().equals("GET")) { %>
    <s:url var="ajaxUpdateTitle" action="20401_title" />
    <s:url var="ajaxUpdateButtons" action="20401_buttons" />
    <sj:a id="tempTitle" href="%{ajaxUpdateTitle}" targets="ph-title" cssClass="ui-helper-hidden"></sj:a>
    <sj:a id="tempButtons" href="%{ajaxUpdateButtons}" targets="ph-buttons" cssClass="ui-helper-hidden"></sj:a>
    
    
    <s:form id="formGetData" action="20401_getData.action">
        <s:hidden name="noAcct" />
        <s:hidden name="refTxn" />
        <s:hidden name="typMsg" />
        <s:token />
    </s:form>
    <sj:a id="tempGetData" formIds="formGetData" targets="ph-temp" cssClass="ui-helper-hidden"></sj:a>
    
    <s:form id="tempFrmDlgMfcNoBook" action="dlg">
        <s:hidden name="dialog" value="dlgMfcNoBook" />
        <s:hidden name="noAcct" />
        <s:hidden name="dtPost" />
        <s:hidden name="typMsg" />
        <s:hidden name="status" />
        <s:hidden name="idMenu" id="idMenu_Valas"/>
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
        <s:hidden name="dialog" value="dlgFcyTxnFlagUD" />
        <s:hidden name="noCif" />
        <s:hidden name="ccyUd" />
        <s:hidden name="noUd" />
        <s:hidden name="amtTxn" />
        <s:token />
    </s:form>
    <sj:a id="tempDlgFcyTxnFlagUD" formIds="tempFrmDlgFcyTxnFlagUD" targets="ph-dlg" cssClass="ui-helper-hidden"></sj:a>
    
    <s:form id="tempFrmDlgAuth" action="dlg">
        <s:hidden name="dialog" value="dlgAuth" />
        <s:hidden name="idMenu" value="20401" />
        <s:token />
    </s:form>
    <sj:a id="tempDlgAuth" formIds="tempFrmDlgAuth" targets="ph-dlg" cssClass="ui-helper-hidden"></sj:a>
    
    <s:form id="formMessage" action="20401_retrieveRateMessage.action">
        <s:hidden name="tagMessage" value="TXT_CLS_RATE" />
        <s:hidden name="dtValue" />
        <s:hidden name="dtmTxn" />
        <s:token />
    </s:form>
    <sj:a id="tempformMessage" formIds="formMessage" targets="ph-temp" cssClass="ui-helper-hidden"></sj:a>
        
    <s:actionmessage id="actionMessage" />
    <s:actionerror name="actionError" />
    
    
    <s:form id="frmMain" name="frmMain" action="20401" focusElement="frmMain_noAcct" validate="true">
        <s:textfield
            name="noAcct"
            requiredLabel="true"
            size="16"
            maxlength="16"
            cssClass="ui-widget ui-widget-content"
            key="label.noAcct"
            disabled="true"
        />
        <s:textfield
            name="dtPost"
            size="10"
            maxlength="10"
            cssClass="ui-widget ui-widget-content"
            key="label.dtPost"
            disabled="true"
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
        <s:textfield
            name="amtAvailUsdScr"
            size="12"
            maxlength="20"
            cssClass="ui-widget ui-widget-content"
            key="label.amtAvailUsd"
            disabled="true"
        />
        <s:hidden name="amtAvailUsd" />
        <s:textfield
            name="noUd"
            size="20"
            maxlength="20"
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
        <s:hidden name="idMaintainedBy" value="%{#session.idUser}" />
        <s:hidden name="idMaintainedSpv" />
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
        <s:token />
    </s:form>
    <s:div id="divMessage"></s:div>
    
    
    <script type="text/javascript">
        function messageBox(type, message, onCloseFunction) {
            var title = "";
            var icon = "";
            
            if (type == 1) {
                title = "Warning";
                icon = "ui-icon-alert";
            }
            else if (type == 2) {
                title = "Error";
                icon = "ui-icon-circle-close";
            }
            else if (type == 3) {
                title= "Information";
                icon = "ui-icon-info";
            }
            
            var $current = $("#divMessage");
            var $clone = $current.clone();
            var $parent = $current.parent();
            var $prevSibling = $current.prev();
            
            if ($current.length) {
                $current
                .html("<p style='text-align: center; vertical-align: middle'>" + message + "</p>")
                .dialog({
                    autoOpen: false,
                    modal: true,
                    title: '<span class="ui-icon ' + icon + '" style="float:left; margin:0 7px 0px 0;"></span>' + title,
                    resizable: false,
                    buttons: {
                        Ok: function() {
                            if ((onCloseFunction != undefined) && (typeof onCloseFunction == 'function'))
                                onCloseFunction();
                            $(this)
                                .dialog("close")
                                .dialog("destroy")
                                .remove();
                        }
                    },
                    close: function(e) {
                        if ($prevSibling.length)
                            $prevSibling.after($clone);
                        else
                            $parent.append($clone);
                    }
                })
                .dialog("open");
            }
        };
        
        
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
                    alert('Account No cant be blank');
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
                    
                    $("#frmMain_amtTxnUsd")
                        .val($("#frmMain_amtTxnUsdScr").val())
                        .toNumber({decimalSymbol:'.', digitGroupSymbol:',', symbol:''})
                        .val(Number($("#frmMain_amtTxnUsd").val()));
                    
                    if($("#frmMain_refTxn").val() != '') {
                        msgParams = {}; 
                        msgParams.onClose = function(message) {
                               messageBox(3, message, function() { $("#btnFindCifNo").button("enable"); });
                           };
                           $("#formMessage_dtValue").val($("#frmMain_dtValue").val());
                           $("#formMessage_dtmTxn").val($("#frmMain_dtmTxn").val());
                           $("#tempformMessage").click();
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
                        $("#tempFrmDlgMfcNoBook_dtPost").val($("#frmMain_dtPost").val());
                        if($("#rbBSAdd").attr("checked")) {
                            $("#tempFrmDlgMfcNoBook_typMsg").val('O');
                            $("#tempFrmDlgMfcNoBook_status").val('P');
                        } else if($("#rbBSDelete").attr("checked")) {
                            $("#tempFrmDlgMfcNoBook_typMsg").val('R');
                            $("#tempFrmDlgMfcNoBook_status").val('P');
                        }
                        
                        $('#idMenu_Valas').attr('value',$("#frmMain_idMenu").val());
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
                        
                        if(($("#frmMain_amtAvailUsd").val() - $("#frmMain_amtTxnUsd").val() < 0) || ($("#frmMain_codAcctCustRel").val() != "SOW"))  {
                            $("#btnFindUd").button("enable");
                            $("#frmMain_noUd").attr("readonly", null);
                            $("#frmMain_noUd").focus();
                        } else {
                            $("#btnSave").button("enable");                            
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
<% } %>