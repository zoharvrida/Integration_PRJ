<%@page import="java.util.UUID" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags" %>

<% if(!request.getMethod().equals("GET")) { %>
    <s:url var="ajaxUpdateTitle" action="20402_title" />
    <s:url var="ajaxUpdateButtons" action="20402_buttons" />
    <sj:a id="tempTitle" href="%{ajaxUpdateTitle}" targets="ph-title" cssClass="ui-helper-hidden"></sj:a>
    <sj:a id="tempButtons" href="%{ajaxUpdateButtons}" targets="ph-buttons" cssClass="ui-helper-hidden"></sj:a>
    <s:form id="tempFrmDlgFcyTxnManInput" action="dlg">
        <s:hidden name="dialog" value="dlgFcyTxnManInput" />
        <s:hidden name="noAcct" />
        <s:hidden name="typMsg" />
        <s:hidden name="typAcct" />
        <s:hidden name="refTxn" />
        <s:token />
    </s:form>
    <sj:a id="tempDlgFcyTxnManInput" formIds="tempFrmDlgFcyTxnManInput" targets="ph-dlg" cssClass="ui-helper-hidden"></sj:a>
    <s:form id="formCifInfo" action="20402_inqCif.action">
        <s:hidden name="noCif" />
        <s:hidden name="period" />
        <s:token />
    </s:form>
    <sj:a id="tempCifInfo" formIds="formCifInfo" targets="ph-temp" cssClass="ui-helper-hidden"></sj:a>
    <s:form id="formUdInfo" action="20402_inqUd.action">
        <s:hidden name="noCif" />
        <s:hidden name="refTxn" />
        <s:token />
    </s:form>
    <sj:a id="tempUdInfo" formIds="formUdInfo" targets="ph-temp" cssClass="ui-helper-hidden"></sj:a>
    <s:form id="formGetRatUsdIdr" action="20402_inqRatUsdIdr.action">
        <s:token />
    </s:form>
    <sj:a id="tempGetRatUsdIdr" formIds="formGetRatUsdIdr" targets="ph-temp" cssClass="ui-helper-hidden"></sj:a>
    <s:form id="formGetRatFcyIdr" action="20402_inqRatFcyIdr.action">
        <s:hidden name="ccyTxn" />
        <s:token />
    </s:form>
    <sj:a id="tempGetRatFcyIdr" formIds="formGetRatFcyIdr" targets="ph-temp" cssClass="ui-helper-hidden"></sj:a>
    <s:form id="tempFrmDlgInqUD" action="dlg">
        <s:hidden name="dialog" value="dlgFcyTxnManInputUD" />
        <s:hidden name="noCif" />
        <s:hidden name="noUd" />
        <s:hidden name="ccyUd" />
        <s:hidden name="amtTxn" />
        <s:token />
    </s:form>
    <sj:a id="tempDlgInqUD" formIds="tempFrmDlgInqUD" targets="ph-dlg" cssClass="ui-helper-hidden"></sj:a>
    <s:form id="tempFrmDlgAuth" action="dlg">
        <s:hidden name="dialog" value="dlgAuth" />
        <s:hidden name="idMenu" value="20402" />
        <s:token />
    </s:form>
    <sj:a id="tempDlgAuth" formIds="tempFrmDlgAuth" targets="ph-dlg" cssClass="ui-helper-hidden"></sj:a>

    <s:form id="formMessage" action="20402_retrieveRateMessage.action">
        <s:hidden name="tagMessage" value="TXT_CLS_RATE" />
        <s:token />
    </s:form>
    <sj:a id="tempformMessage" formIds="formMessage" targets="ph-temp" cssClass="ui-helper-hidden"></sj:a>

    <s:actionmessage id="actionMessage" />
    <s:actionerror name="actionError" />

    <s:form id="frmMain" name="frmMain" action="20402" focusElement="frmMain_noCif" validate="true">
        <s:textfield
            name="noCif"
            requiredLabel="true"
            size="10"
            maxlength="10"
            cssClass="ui-widget ui-widget-content"
            key="label.noCif"
            disabled="true"
        />
        <s:textfield
            name="namCustFull"
            size="20"
            maxlength="50"
            cssClass="ui-widget ui-widget-content"
            label=""
            labelSeparator=""
            disabled="true"
        />
        <s:textfield
            name="refTxn"
            size="40"
            maxlength="40"
            cssClass="ui-widget ui-widget-content"
            key="label.refTxn"
            disabled="true"
        />
        <sj:a
            id="btnFindRefTxn"
            button="true"
            disabled="true"
        >...</sj:a>
        <sj:datepicker
            id="dtmTxn"
            name="dtmTxn"
            key="label.dtmTxn"
            cssClass="ui-widget ui-widget-content"
            displayFormat="dd/mm/yy"
            minDate="%{new java.util.Date(#session.dtBusiness.getTime())}"
            buttonImageOnly="true"
            readonly="true"
        />
        <s:url id="getListCcyCode" action="json/20302_getListCcyCode"/>
        <sj:select
            id="ccyTxn"
            name="ccyTxn"
            href="%{getListCcyCode}"
            list="listCcy"
            cssClass="ui-widget ui-widget-content"
            key="label.ccyTxn"
            emptyOption="true"
            headerKey="-1"
        />
        <s:textfield
            name="amtTxnScr"
            requiredLabel="true"
            size="12"
            maxlength="20"
            cssClass="ui-widget ui-widget-content"
            key="label.amtTxn"
            disabled="true"
        />
        <s:hidden name="amtTxn" />
        <s:textfield
            name="ratFcyIdrScr"
            requiredLabel="true"
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
            size="20"
            maxlength="20"
            cssClass="ui-widget ui-widget-content"
            key="label.amtTxnUsd"
            disabled="true"
        />
        <s:hidden name="amtTxnUsd" />
        <s:textfield
            name="txnDesc"
            requiredLabel="true"
            size="50"
            maxlength="120"
            cssClass="ui-widget ui-widget-content"
            key="label.txnDesc"
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
        <s:hidden name="txnBranch" value="%{#session.cdBranch}" />    
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
            onBeforeTopics="beforeSubmit"
        />
        <s:token />
    </s:form>
    <div id="divMessage"></div>
    
    
    <script type="text/javascript">
        function calcRatFcy(ccy) {
            tmpParamsCcy = {};
            tmpParamsCcy.onClose = function(ratFcyIdr) {
                $("#frmMain_ratFcyIdr").val(ratFcyIdr);
                $("#frmMain_ratFcyIdrScr").val($("#frmMain_ratFcyIdr").val());
                $("#frmMain_ratFcyIdrScr").formatCurrency({decimalSymbol: '.', digitGroupSymbol: ',', symbol: ''});            
            };
            $("#formGetRatFcyIdr_ccyTxn").val(ccy);
            $("#tempGetRatFcyIdr").click();
        }
        
        function calcAmount() {
            if($("#frmMain_amtTxnScr").val() != '' && $("#frmMain_ratFcyIdrScr").val() != '') {
                tmpParams = {};
                tmpParams.onClose = function(ratUsdIdr) {
                    $("#frmMain_ratUsdIdr").val(ratUsdIdr);
                    
                    $("#frmMain_amtTxnLcy").val($("#frmMain_amtTxn").val() * $("#frmMain_ratFcyIdr").val());
                    
                    if($("#ccyTxn").val() == 'USD') {
                        $("#frmMain_amtTxnUsd").val($("#frmMain_amtTxn").val());
                    } else {
                        $("#frmMain_amtTxnUsd").val($("#frmMain_amtTxnLcy").val() / $("#frmMain_ratUsdIdr").val());                    
                    }
                                                    
                    if($("#frmMain_amtAvailUsd").val() - $("#frmMain_amtTxnUsd").val() < 0) {
                        $("#frmMain_noUd").attr("readonly", null);
                        $("#btnFindUd").button("enable");
                        $("#btnSave").button("disable");
                    } else {
                        $("#frmMain_noUd").attr("readonly", "true");
                        $("#btnFindUd").button("disable");
                        $("#btnSave").button("enable");
                        $("#frmMain_noUd").attr("value", null);
                    }
                    
                    $("#frmMain_amtTxnLcyScr").val($("#frmMain_amtTxnLcy").val());
                    $("#frmMain_amtTxnLcyScr").formatCurrency({decimalSymbol:'.', digitGroupSymbol:',', symbol:''});
                    $("#frmMain_ratUsdIdrScr").val($("#frmMain_ratUsdIdr").val());
                    $("#frmMain_ratUsdIdrScr").formatCurrency({decimalSymbol:'.', digitGroupSymbol:',', symbol:''});
                    $("#frmMain_amtTxnUsdScr").val($("#frmMain_amtTxnUsd").val());
                    $("#frmMain_amtTxnUsdScr").formatCurrency({decimalSymbol:'.', digitGroupSymbol:',', symbol:''});
                    
                    $("#frmMain_amtTxnUsd").val($("#frmMain_amtTxnUsdScr").val());
                    $("#frmMain_amtTxnUsd").toNumber({decimalSymbol:'.', digitGroupSymbol:',', symbol:''});
                };

                $("#tempGetRatUsdIdr").click();
            }
        }
        
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
            $("#ccyTxn").combobox({size:3});
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
                    $("#frmMain_namCustFull").detach()
                )
            ;
            
            $("#frmMain_refTxn")
                .parent()
                .append(
                    $("#btnFindRefTxn").detach()
                )
            ;
            
            $("#frmMain_noUd")
                .parent()
                .append(
                    $("#btnFindUd").detach()
                )
            ;
            
            $("#frmMain_refTxn").die('keydown');
            $("#frmMain_refTxn").live('keydown', function(e) {
                if(e.keyCode == 9) {
                    if($("#rbBSInquiry").attr("checked") || $("#rbBSDelete").attr("checked")) {
                        $("#btnFindRefTxn").click();
                    }
                }
            });
            
            $("#frmMain_noCif").die('keydown');
            $("#frmMain_noCif").live('keydown', function(e) {
                if(e.keyCode == 9) {
                    var noCif = $.trim($("#frmMain_noCif").val());
                    if (noCif=="" || isNaN(noCif) || Number(noCif)<0) {
                        if(isNaN(noCif)) alert('CIF No only accept numbers.');
                        e.preventDefault();
                        return;
                    }

                    if ($("#frmMain_namCustFull").val()=="") {
                        tempParams = {};
                        tempParams.onClose = function(namCustFull, amtAvailUsd) {
                            if(namCustFull == '') {
                                alert("CIF: '" + $("#frmMain_noCif").val() + "' not found");
                                $("#frmMain_namCustFull").val("");
                                $("#frmMain_noCif").focus();
                                e.preventDefault();
                            } else {
                                if($('#rbBSAdd').is(':checked')) {
                                    $("#frmMain_refTxn").val('<%= UUID.randomUUID().toString().replaceAll("-", "")%>');
                                }
                                
                                $("#frmMain_namCustFull").val(namCustFull);
                                $("#frmMain_amtAvailUsd").val(amtAvailUsd);
                                $("#frmMain_amtAvailUsdScr").val($("#frmMain_amtAvailUsd").val());
                                $("#frmMain_amtAvailUsdScr").formatCurrency({decimalSymbol:'.', digitGroupSymbol:',', symbol:''});
                                $("#frmMain_noCif").attr("readonly", "true");
                                if(!$("#rbBSAdd").attr("checked")) {
                                    $("#frmMain_refTxn").attr("disabled", null);
                                    $("#frmMain_refTxn").attr("readonly", null);
                                    $("#btnFindRefTxn").button("enable");
                                }
                            }
                        };

                        $("#formCifInfo_noCif").val($("#frmMain_noCif").val());
                        $("#formCifInfo_period").val('<s:date name="%{#session.dtBusiness}" format="yyyyMM" />');
                        $("#tempCifInfo").click();
                    }
                }
            });
            
            $("#ccyTxn")
                .unbind('change')
                .bind('change', function(e) {
                    calcRatFcy($("#ccyTxn").val());
                    $("#frmMain_ratFcyIdrScr").blur();
                });
            
            $("#frmMain_amtTxnScr").focus(function() {
                if($("#frmMain_amtTxnScr").val() != '') {
                    $("#frmMain_amtTxnScr").val($("#frmMain_amtTxn").val());
                }
            });
            
            $("#frmMain_amtTxnScr").blur(function(e) {
                var amtTxnScr = $.trim($("#frmMain_amtTxnScr").val());
                if(isNaN(amtTxnScr) || Number(amtTxnScr)<0) {
                    if(isNaN(amtTxnScr)) {
                        alert('Txn Amount only accept numbers.');
                        $("#frmMain_amtTxnScr").val("");
                        $("#frmMain_amtTxnScr").focus();
                    }
                    e.preventDefault();
                    return;
                } 
                
                if($("#frmMain_amtTxnScr").val() != '') {
                    $("#frmMain_amtTxn").val($("#frmMain_amtTxnScr").val());
                    $("#frmMain_amtTxnScr").formatCurrency({decimalSymbol:'.', digitGroupSymbol:',', symbol:''});
                    calcAmount();
                }
            });
            
            $("#frmMain_amtTxnScr").die('keydown');
            $("#frmMain_amtTxnScr").live('keydown', function(e) {
                if(e.keyCode == 9) {
    //                calcAmount();                
                }
            });
            
            $("#frmMain_ratFcyIdrScr").focus(function() {
                if($("#frmMain_ratFcyIdrScr").val() != '') {
                    $("#frmMain_ratFcyIdrScr").val($("#frmMain_ratFcyIdr").val());
                }
            });
            
            $("#frmMain_ratFcyIdrScr").blur(function(e) {
                var ratFcyIdrScr = $.trim($("#frmMain_ratFcyIdrScr").val());
                if(isNaN(ratFcyIdrScr) || Number(ratFcyIdrScr)<0) {
                    if(isNaN(ratFcyIdrScr)) {
                        alert('FCY/IDR Rate only accept numbers.');
                        $("#frmMain_ratFcyIdrScr").val("");
                        $("#frmMain_ratFcyIdrScr").focus();
                    }
                    e.preventDefault();
                    return;
                } 
                
                if($("#frmMain_ratFcyIdrScr").val() != '') {
                    $("#frmMain_ratFcyIdr").val($("#frmMain_ratFcyIdrScr").val());
                    $("#frmMain_ratFcyIdrScr").formatCurrency({decimalSymbol:'.', digitGroupSymbol:',', symbol:''});
                    calcAmount();
                }
            });
            
            $("#frmMain_ratFcyIdrScr").die('keydown');
            $("#frmMain_ratFcyIdrScr").live('keydown', function(e) {
                if(e.keyCode == 9) {
    //                calcAmount();                
                }
            });
            
            $("#btnFindRefTxn").click(function() {
                if(!($("#btnFindRefTxn").button('option').disabled != undefined && 
                    $("#btnFindRefTxn").button('option', 'disabled'))) {
                    dlgParams = {};
                    dlgParams.refTxn = "frmMain_refTxn";
                    dlgParams.dtmTxn = "dtmTxn";
                    dlgParams.ccyTxn = "ccyTxn";
                    dlgParams.amtTxn = "frmMain_amtTxn";
                    dlgParams.ratFcyIdr = "frmMain_ratFcyIdr";
                    dlgParams.amtTxnLcy = "frmMain_amtTxnLcy";
                    dlgParams.ratUsdIdr = "frmMain_ratUsdIdr";
                    dlgParams.amtTxnUsd = "frmMain_amtTxnUsd";
                    dlgParams.txnDesc = "frmMain_txnDesc";
                    dlgParams.onClose = function() {
                        $("#ccyTxn_combobox > input").val($("#ccyTxn").val());
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
                        
                        tmpParams = {};
                        tmpParams.onClose = function(noUd) {
                            if(!$("#rbBSAdd").attr("checked")) {
                                $("#frmMain_noUd").val(noUd);
                            }
                        };

                        $("#formUdInfo_noCif").val($("#frmMain_noCif").val());
                        $("#formUdInfo_refTxn").val($("#frmMain_refTxn").val());
                        $("#tempUdInfo").click();
                    };
                    $("#ph-dlg").dialog("option", "position", "center");
                    $("#ph-dlg").dialog("option", "width", $("body").width()*3/4);
                    //$("#ph-dlg").dialog("option", "height", $("body").height()*3/4);
                    $("#ph-dlg").dialog("option", "height", "450");
                    $("#ph-dlg")
                        .html("Please wait...")
                        .unbind("dialogopen")
                        .bind("dialogopen", function() {
                            $("#tempFrmDlgFcyTxnManInput_noAcct").val($("#frmMain_noCif").val());
                            $("#tempFrmDlgFcyTxnManInput_typMsg").val('O');
                            $("#tempFrmDlgFcyTxnManInput_typAcct").val('M');
                            $("#tempFrmDlgFcyTxnManInput_refTxn").val($("#frmMain_refTxn").val());
                            $("#tempDlgFcyTxnManInput").click();
                        })
                        .dialog("open");
                }
            });

            $("#frmMain_noUd").die('keydown');
            $("#frmMain_noUd").live('keydown', function(e) {
                if(e.keyCode == 9) {
                    if($("#rbBSAdd").attr("checked")) {
                        $("#btnFindUd").click();
                    }
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
                            $("#tempFrmDlgInqUD_noCif").val($("#frmMain_noCif").val());
                            $("#tempFrmDlgInqUD_noUd").val($("#frmMain_noUd").val());
                            $("#tempFrmDlgInqUD_ccyUd").val($("#ccyTxn").val());
                            $("#tempFrmDlgInqUD_amtTxn").val($("#frmMain_amtTxnUsd").val());
                            $("#tempDlgInqUD").click();
                        })
                        .dialog("open");
                }
            });
            
            $("#btnSave").subscribe("beforeSubmit", function(event) {
                $("#frmMain").unbind("submit");
                event.originalEvent.options.submit = false;
                if (validateForm_frmMain()) {
                dlgParams = {};
                dlgParams.idMaintainedBy = "frmMain_idMaintainedBy";
                dlgParams.idMaintainedSpv = "frmMain_idMaintainedSpv";
                dlgParams.event = event;
                dlgParams.onSubmit = function() {
                    dlgParams.event.originalEvent.options.submit = true;
                    $("#btnSave").unsubscribe("beforeSubmit");
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
            
            msgParams = {}; 
            msgParams.isOpen = false;
            msgParams.onClose = function(message) {
                messageBox(3, message, function() { msgParams.isOpen = false; });
            };
        });
    </script>
<% } %>