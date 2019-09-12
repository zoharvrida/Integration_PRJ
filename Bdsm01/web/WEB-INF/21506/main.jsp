<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>

<s:set var="requestMethod" scope="request">${pageContext.request.method}</s:set>

<s:if test="%{#request.requestMethod.equals('POST')}">
    <s:url var="ajaxUpdateTitle" action="21506_title_" />
    <s:url var="ajaxUpdateButtons" action="21506_buttons" />
    <sj:a id="tempTitle" href="%{ajaxUpdateTitle}" targets="ph-title" cssClass="ui-helper-hidden" />
    <sj:a id="tempButtons" href="%{ajaxUpdateButtons}" targets="ph-buttons" cssClass="ui-helper-hidden" />

    <s:form id="frmDlgBranch" action="dlg">
        <s:hidden name="dialog" value="dlgCOMBranch" />
        <s:hidden name="master" value="branchCOM" />
        <s:hidden name="term" value="" />
        <s:token />
    </s:form>
    <sj:a id="aDlgBranch" formIds="frmDlgBranch" targets="ph-dlg" cssClass="ui-helper-hidden" />

    <s:form id="frmDlgCurrency" action="dlg">
        <s:hidden name="dialog" value="dlgCOMCurrency" />
        <s:hidden name="master" value="currencyCOM" />
        <s:hidden name="term" value="" />
        <s:token />
    </s:form>
    <sj:a id="aDlgCurrency" formIds="frmDlgCurrency" targets="ph-dlg" cssClass="ui-helper-hidden" />

    <s:form id="frmDlgStatus" action="dlg">
        <s:hidden name="dialog" value="dlgCOMStatus" />
        <s:hidden name="master" value="statusCOM" />
        <s:hidden name="term" value="" />
        <s:token />
    </s:form>
    <sj:a id="aDlgStatus" formIds="frmDlgStatus" targets="ph-dlg" cssClass="ui-helper-hidden" />
    
    <s:form id="tempGetBatch" action="21506_getTrxBatch.action">
        <s:hidden name="branch" />
        <s:hidden name="dateTxn" />
        <s:token />
    </s:form>
    <sj:a id="tempDlgGetBatch" formIds="tempGetBatch" targets="ph-dlg" cssClass="ui-helper-hidden"></sj:a>

    <s:actionmessage id="actionMessage" />
    <s:actionerror name="actionError" />
    <s:form id="frmMain" name="frmMain" action="21506" theme="css_xhtml" method="post" validate="true">
         <script type="text/javascript">
            <%@include file="formValidation.js" %>
        </script>
        <fieldset class=" ui-widget ui-widget-content ui-corner-all">
            <legend class="ui-widget ui-widget-header ui-corner-all">Request</legend>
            <table class="wwFormTable">
                <tbody>
                    <tr>
                        <td class="tdLabel" style="font-style:italic"><s:text name="label.COM.datRequest" /></td>
                        <td class="tdLabel" style="font-style:italic"><span class="required" style="color:red">*</span>:</td>
                        <td style="padding-right: 5px">
                            <sj:datepicker
                                    name="dtmRequest"
                                    displayFormat="dd/mm/yy"
                                    firstDay="1"
                                    maxDate="+0d"
                                    minDate="-100y"
                                    yearRange="-120:-10"
                                    buttonImageOnly="true"
                                    showOn="both"
                                    requiredLabel="true" 
                                    cssClass="ui-widget ui-widget-content" 
                                    changeMonth="true" 
                                    changeYear="true"
                                    numberOfMonths="1"
                                    id="dtmRequest"
                                    />
                        </td>
                    </tr>
                    <tr>
                        <td class="tdLabel" style="font-style:italic"><s:text name="label.COM.branch" /></td>
                        <td class="tdLabel" style="font-style:italic"><span class="required" style="color:red">*</span>:</td>
                        <td style="padding-right: 5px">
                            <s:textfield 
                                name="branch" 
                                size="5"
                                maxlength="5" 
                                cssClass="ui-widget ui-widget-content"
                                cssStyle="text-transform:uppercase"
                                onblur="javascript:this.value=this.value.toUpperCase();"
                                />
                            <s:textfield 
                                name="branchDesc"
                                size="70"
                                cssClass="ui-widget ui-widget-content"
                                />
                            <s:hidden name="brnBranch"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="tdLabel" style="font-style:italic"><s:text name="label.COM.currency" /></td>
                        <td class="tdLabel" style="font-style:italic"><span class="required" style="color:red">*</span>:</td>
                        <td style="padding-right: 5px">
                            <s:textfield 
                                name="currency" 
                                size="10"
                                maxlength="10" 
                                cssClass="ui-widget ui-widget-content"
                                cssStyle="text-transform:uppercase"
                                onblur="javascript:this.value=this.value.toUpperCase();"
                                />
                            <s:textfield 
                                name="currencyDesc"
                                size="70"
                                cssClass="ui-widget ui-widget-content"
                                />
                        </td>
                    </tr>
                    <tr>
                        <td class="tdLabel" style="font-style:italic"><s:text name="label.COM.txnId" /></td>
                        <td class="tdLabel" style="font-style:italic"><span class="required" style="color:red">*</span>:</td>
                        <td style="padding-right: 5px">
                            <s:textfield 
                                name="txnId" 
                                size="100"
                                maxlength="90" 
                                cssClass="ui-widget ui-widget-content"
                                cssStyle="text-transform:uppercase"
                                onblur="javascript:this.value=this.value.toUpperCase();"
                                />
                        </td>
                    </tr>
                    <tr>
                        <td class="tdLabel" style="font-style:italic"><s:text name="label.COM.status" /></td>
                        <td class="tdLabel" style="font-style:italic"><span class="required" style="color:red">*</span>:</td>
                        <td style="padding-right: 5px">
                            <s:hidden name="status"/>
                            <s:textfield 
                                name="statusDesc" 
                                size="20"
                                maxlength="20" 
                                cssClass="ui-widget ui-widget-content"
                                cssStyle="text-transform:uppercase"
                                onblur="javascript:this.value=this.value.toUpperCase();"
                                />
                        </td>
                    </tr>
                    <tr>
                        <td class="tdLabel" style="font-style:italic"><s:text name="label.COM.vendor" /></td>
                        <td class="tdLabel" style="font-style:italic"><span class="required" style="color:red">*</span>:</td>
                        <td style="padding-right: 5px">
                            <s:textfield 
                                name="vendor" 
                                size="100"
                                maxlength="90" 
                                cssClass="ui-widget ui-widget-content"
                                cssStyle="text-transform:uppercase"
                                onblur="javascript:this.value=this.value.toUpperCase();"
                                />
                        </td>
                    </tr>
                    <tr>
                        <td class="tdLabel" style="font-style:italic"><s:text name="label.COM.customer" /></td>
                        <td class="tdLabel" style="font-style:italic"><span class="required" style="color:red">*</span>:</td>
                        <td style="padding-right: 5px">
                            <s:textfield 
                                name="customer" 
                                size="100"
                                maxlength="90" 
                                cssClass="ui-widget ui-widget-content"
                                cssStyle="text-transform:uppercase"
                                onblur="javascript:this.value=this.value.toUpperCase();"
                                />
                        </td>
                    </tr>
                    <tr>
                        <td class="tdLabel" style="font-style:italic"><s:text name="label.COM.totalAmt" /></td>
                        <td class="tdLabel" style="font-style:italic"><span class="required" style="color:red">*</span>:</td>
                        <td style="padding-right: 5px">
                            <s:textfield 
                                name="totalAmtReq" 
                                size="100"
                                maxlength="90" 
                                cssClass="ui-widget ui-widget-content"
                                cssStyle="text-transform:uppercase"
                                onblur="javascript:this.value=this.value.toUpperCase();"
                                />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <sj:submit 
                                id="btnDenomSearch" 
                                formIds="frmMain" 
                                buttonIcon="ui-icon-gear"
                                button="true" 
                                key="button.search.customer" 
                                disabled="false" 
                                targets="ph-main"
                                onBeforeTopics="beforeSubmit" 
                                />
                        </td>
                    </tr>
                </tbody>
            </table>
        </fieldset>
        <fieldset class=" ui-widget ui-widget-content ui-corner-all">
            <legend class="ui-widget ui-widget-header ui-corner-all">Transaction Inquiry</legend>
            <table class="wwFormTable"> 
                <s:url var="urlTab" action="21506Tab_none">
            </s:url>
                    <sj:tabbedpanel id="tabbedPanel" disabled="true">
                        <sj:tab id="tabCDRequest" href="%{urlTab}" />
                    </sj:tabbedpanel>    
                <tbody>
                    <tr><td>           
                            <sj:submit 
                                id="btnRequest" 
                                formIds="frmMain" 
                                buttonIcon="ui-icon-gear"
                                button="true" 
                                key="button.casa.next" 
                                disabled="false" 
                                targets="ph-main"
                                onBeforeTopics="beforeNext"
                                />
                        </td>
                </tbody>
            </table>    
        </fieldset>
    </s:form>
    <sj:a id="btnLookupBranch" button="true" >...</sj:a>
    <sj:a id="btnLookupCurrency" button="true" >...</sj:a>
    <sj:a id="btnLookupCustomer" button="true" >...</sj:a>
        <script type="text/javascript">
                jQuery(document).ready(function() {
                $('#tabbedPanel').hide();
                $('#tabbedPanel ul').attr('style', 'display:none');
                $('#tabbedPanel ul').hide();
                    
                $("#tempGetBatch_branch").val('<s:property value="%{#session.cdBranch}" />');
                $("#tempGetBatch_dateTxn").val($("#frmGo_datTxnCOM").val());
                
                $("#tempDlgGetBatch").click();
                trxParams = {};
                trxParams.onClose = function(notification) {
                    if (notification == "") {
                        alert("FAILED TO GET BATCH");
                        //e.preventDefault();
                    } else {
                        $("#frmMain_txnId").val(notification);
                    }
                }

                if (($("#actionError").length == 0) && ($("#actionMessage").length == 0)) {
                    $("#rbBS").buttonset("destroy");
                    $("#tempTitle").click();
                    $("#tempButtons").click();
                } else {
                    $("#rbBS").data("rdb").callCurrent();
                    if ($("#actionError").length == 0) {
                        $("#rbBS").data("rdb").clear(false);
                    }
                }

                $("#dtmRequest").val($("#frmGo_datTxnCOM").val());
                $("#frmMain_status").val($("#frmGo_statusCOM").val());
                $("#frmMain_statusDesc").val($("#frmGo_statusDescCOM").val());
                    //$("#btnDenomSearch").hide();
                    $("#btnRequest").hide();
                    $("#frmMain_branch").parent().append($("#frmMain_branchDesc").detach());
                    $("#frmMain_branchDesc").parent().append($("#btnLookupBranch").detach());
                    $("#frmMain_currency").parent().append($("#frmMain_currencyDesc").detach());
                    $("#frmMain_currencyDesc").parent().append($("#btnLookupCurrency").detach());
					$("#frmMain_customer").parent().append($("#btnLookupCustomer").detach());
					
                    $("#dtmRequest").die('keydown');
                    $("#frmMain_branch").die('keydown');
                    $("#frmMain_currency").die('keydown');
                    $("#frmMain_txnId").die('keydown');
                    $("#frmMain_statusDesc").die('keydown');
                    $("#frmMain_vendor").die('keydown');
                    $("#frmMain_customer").die('keydown');
                    $("#frmMain_totalAmtReq").die('keydown');
                    $("#dtmRequest").live('keydown', function(e) {
                        if (e.ctrlKey)
                            e.preventDefault();
                    });
                    $("#frmMain_branch").live('keydown', function(e) {
                        if (e.ctrlKey)
                            e.preventDefault();
                    });
                    $("#frmMain_currency").live('keydown', function(e) {
                        if (e.ctrlKey)
                            e.preventDefault();
                    });
                    $("#frmMain_txnId").live('keydown', function(e) {
                        if (e.ctrlKey)
                            e.preventDefault();
                    });
                    $("#frmMain_statusDesc").live('keydown', function(e) {
                        if (e.ctrlKey)
                            e.preventDefault();
                    });
                    $("#frmMain_vendor").live('keydown', function(e) {
                        if (e.ctrlKey)
                            e.preventDefault();
                    });
                    $("#frmMain_branch").die('keydown');
                    $("#frmMain_branch").live('keydown', function(e) {
                        if (e.keyCode == 9) {
                            $("#btnLookupBranch").click();
                        }
                    });
                    $("#frmMain_currency").die('keydown');
                    $("#frmMain_currency").live('keydown', function(e) {
                        if (e.keyCode == 9) {
                            $("#btnLookupCurrency").click();
                        }
                    });
                });

                $("#btnLookupBranch").click(myDialogLike(
                        $(this), '<s:text name="label.COM.branch" />', 'aDlgBranch',
                        'frmMain_branch', 'frmMain_branchDesc', 'frmDlgBranch_term', 'frmMain_branch'
                        ));
                $("#btnLookupCurrency").click(myDialogLike(
                        $(this), '<s:text name="label.COM.branch" />', 'aDlgCurrency',
                        'frmMain_currency', 'frmMain_currencyDesc', 'frmDlgCurrency_term', 'frmMain_currency'
                        )); 
                $("#btnLookupStatus").click(myDialogLike(
                        $(this), '<s:text name="label.COM.status" />', 'aDlgStatus',
                        'frmMain_status', 'frmMain_statusDesc', 'frmDlgStatus_term', 'frmMain_status'
                        ));  
                function myDialogLike(eButton, title, eDialog, eId, eDesc, eLike, strLike, closeFunction) {

                    return function() {
                        if (!(
                                (eButton.button('option').disabled != undefined) &&
                                (eButton.button('option', 'disabled'))
                                )) {
                            dlgParams = {};
                            dlgParams.id = eId;
                            dlgParams.desc = eDesc;
                            var $tmp = $("#ph-dlg").dialog("option", "title");
                            $("#ph-dlg").dialog("option", "position", "center");
                            $("#ph-dlg").dialog("option", "width", ($("body").width() * (3 / 4)));
                            $("#ph-dlg").dialog("option", "height", 450);
                            $("#ph-dlg")
                                    .html("Please wait...")
                                    .unbind("dialogopen")
                                    .bind("dialogopen", function() {
                                //console.log('eLike:' + eLike + ', strLike: ' + strLike);
                                $("#" + eLike).attr("value", $('#' + strLike).val());
                                $("#" + eDialog).click();
                            })
                                    .unbind("dialogclose")
                                    .bind("dialogclose", function() {
                                $(this).dialog({title: $tmp});
                                if (closeFunction != undefined) {
                                    closeFunction();
                                } 
                            })
                                    .dialog({
                                title: title
                            })
                                    .dialog("open");
                        }
                    };
                }
                ;
                $("#btnDenomSearch").unsubscribe("beforeSubmit");
                $("#btnDenomSearch").subscribe("beforeSubmit", function(event) {
                    event.originalEvent.options.submit = false;

                    var noCard = $.trim($("#frmMain_noCard").val());
                    //alert(3);
                    //alert(4);
                    $('#tabbedPanel').tabs('url', 0, '21506Tab_input');
                    $('#tabbedPanel').tabs({
                        ajaxOptions: {
                            type: 'POST',
                            data: {
                                'status': $("#frmMain_status").val(),
                                'currency': $("#frmMain_currency").val(),
                            'branch': $("#frmMain_branch").val(),
                            'txnId': $("#frmMain_txnId").val()
                            }
                        }
                    });
                    $('#tabbedPanel').tabs('load', 0);
                    $('#tabbedPanel').show();
                    $('#btnRequest').show();
                    //}
                });
            
            $("#btnRequest").subscribe("beforeSubmit", function(event) {
                $("#frmMain").unbind("submit");
                event.originalEvent.options.submit = false;
                if (validateForm_frmMain()) {
                    dlgParams = {};
                    dlgParams.idMaintainedBy = "frmMain_idMaintainedBy";
                    dlgParams.idMaintainedSpv = "frmMain_idMaintainedSpv";
                    dlgParams.event = event;
                    dlgParams.onSubmit = function() {
                        dlgParams.event.originalEvent.options.submit = true;
                        $("#btnRequest").unsubscribe("beforeSubmit");
                        $("#btnRequest").click();
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
    </script>
</s:if>