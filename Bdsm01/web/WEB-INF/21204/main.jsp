<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>

<s:set var="requestMethod" scope="request">${pageContext.request.method}</s:set>

<s:if test="%{#request.requestMethod.equals('POST')}">
    <s:url var="ajaxUpdateTitle" action="21204_title_" />
    <s:url var="ajaxUpdateButtons" action="21204_buttons" />
    <sj:a id="tempButtons" href="%{ajaxUpdateButtons}" targets="ph-buttons" cssClass="ui-helper-hidden" />
    <sj:a id="tempTitle" href="%{ajaxUpdateTitle}" targets="ph-title" cssClass="ui-helper-hidden" />
	<script type="text/javascript" src="_js/jquery.maskedinput-1.2.2.js"></script>
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


    <s:actionmessage id="actionMessage" />
    <s:actionerror name="actionError" />

    <s:form id="frmMain" name="frmMain" action="21204" theme="css_xhtml" method="post" validate="true">
        <script type="text/javascript">
            <%@include file="formValidation.js" %>
        </script>
        <fieldset class=" ui-widget ui-widget-content ui-corner-all">
            <legend class="ui-widget ui-widget-header ui-corner-all">Data Inquiry</legend>
            <table class="wwFormTable">
                <tbody>
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
                        <td class="tdLabel" style="font-style:italic"><s:text name="label.COM.datTXN" /></td>
                        <td class="tdLabel" style="font-style:italic"><span class="required" style="color:red">*</span>:</td>
                        <td style="padding-right: 5px">
                            <s:textfield 
                                name="datTXN"
                                size="10"
                                maxlength="10" 
                                cssClass="ui-widget ui-widget-content" 
                                cssStyle="text-transform:uppercase"
                                onblur="javascript:this.value=this.value.toUpperCase();"
                                id="datTXN"
                                />
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
                                size="20"
                                cssClass="ui-widget ui-widget-content"
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
                        <td>
                            <sj:submit 
                                id="btnDenomSearchBranch" 
                                formIds="frmMain" 
                                buttonIcon="ui-icon-gear"
                                button="true" 
                                key="button.search.denom" 
                                disabled="false" 
                                targets="ph-main"
                                onBeforeTopics="beforeSubmitBranch" 
                                />
                        </td>
                    </tr>
                </tbody>
            </table>
        </fieldset>
        <fieldset class=" ui-widget ui-widget-content ui-corner-all">
            <legend class="ui-widget ui-widget-header ui-corner-all">Transaction Inquiry</legend>
            <table class="wwFormTable"> 
                <s:url var="urlTab" action="21204Tab_none">
                </s:url>
                <sj:tabbedpanel id="tabbedPanel" disabled="true">
                    <sj:tab id="tabCDBranch" href="%{urlTab}" />
                </sj:tabbedpanel>    
            </table>    
        </fieldset>              
    </s:form>
    <sj:a id="btnLookupBranch21204" button="true" >...</sj:a>
    <sj:a id="btnLookupCurrency21204" button="true" >...</sj:a>
    <sj:a id="btnLookupStatus21204" button="true" >...</sj:a>
        <script type="text/javascript">
            jQuery(document).ready(function() {
                //$("#btnDenomSearchBranch").hide();
				$('#datTXN').mask("99/99/9999",{placeholder:"dd/mm/yyyy"}); 
				$("#datTXN").val('<s:date name="%{#session.dtBusiness}" format="d/MM/yyyy" />');
                
                $('#tabbedPanel').hide();
                $('#tabbedPanel ul').attr('style', 'display:none');
                $('#tabbedPanel ul').hide();

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
                $("#frmMain_branch").parent().append($("#frmMain_branchDesc").detach());
                $("#frmMain_branchDesc").parent().append($("#btnLookupBranch21204").detach());
                $("#frmMain_currency").parent().append($("#frmMain_currencyDesc").detach());
                $("#frmMain_currencyDesc").parent().append($("#btnLookupCurrency21204").detach());
                $("#frmMain_statusDesc").parent().append($("#btnLookupStatus21204").detach());

                $("#dtmRequest").die('keydown');
                $("#frmMain_branch").die('keydown');
                $("#frmMain_currency").die('keydown');
                $("#frmMain_txnId").die('keydown');
                $("#frmMain_statusDesc").die('keydown');
                $("#frmMain_vendor").die('keydown');
                $("#frmMain_customer").die('keydown');
                $("#frmMain_totalAmtReq").die('keydown');

                $("#datTXN").live('keydown', function(e) {
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
                $("#frmMain_statusDesc").live('keydown', function(e) {
                    if (e.ctrlKey)
                        e.preventDefault();
                });
                $("#frmMain_vendor").live('keydown', function(e) {
                    if (e.ctrlKey)
                        e.preventDefault();
                });

            });
            $("#btnLookupBranch21204").click(myDialogLike(
                    $(this), '<s:text name="label.COM.branch" />', 'aDlgBranch',
                    'frmMain_branch', 'frmMain_branchDesc', 'frmDlgBranch_term', 'frmMain_branch'
                    ));
            $("#btnLookupCurrency21204").click(myDialogLike(
                    $(this), '<s:text name="label.COM.branch" />', 'aDlgCurrency',
                    'frmMain_currency', 'frmMain_currencyDesc', 'frmDlgCurrency_term', 'frmMain_currency'
                    ));
            $("#btnLookupStatus21204").click(myDialogLike(
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
                };
            $("#btnDenomSearchBranch").unsubscribe("beforeSubmitBranch");
            $("#btnDenomSearchBranch").subscribe("beforeSubmitBranch", function(event) {
                event.originalEvent.options.submit = false;
                //alert(3);
                //alert(4);
                $('#tabbedPanel').tabs('url', 0, '21204Tab_input');
                $('#tabbedPanel').tabs({
                    ajaxOptions: {
                        type: 'POST',
                        data: {
                            'status': $("#frmMain_status").val(),
                            'currency': $("#frmMain_currency").val(),
                            'branch': $("#frmMain_branch").val(),
                            'datTxn': $("#datTXN").val()
                        }
                    }
                });
                $('#tabbedPanel').tabs('load', 0);
                $('#tabbedPanel').show();
                //}
            });
    </script>
</s:if>
