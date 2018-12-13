<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<% if(!request.getMethod().equals("GET")) { %>
<s:url var="ajaxUpdateTitle" action="10302_title" />
<s:url var="ajaxUpdateButtons" action="10302_buttons" />
<sj:a id="tempTitle" href="%{ajaxUpdateTitle}" targets="ph-title" cssClass="ui-helper-hidden"></sj:a>
<sj:a id="tempButtons" href="%{ajaxUpdateButtons}" targets="ph-buttons" cssClass="ui-helper-hidden"></sj:a>
<s:form id="tempFrmDlgTemplate" action="dlg">
    <s:hidden name="dialog" value="dlgTemplate" />
    <s:hidden name="namTemplate" />
    <s:token />
</s:form>
<sj:a id="tempDlgTemplate" formIds="tempFrmDlgTemplate" targets="ph-dlg" cssClass="ui-helper-hidden"></sj:a>
<s:form id="tempFrmDlgMenu" action="dlg">
    <s:hidden name="dialog" value="dlgMenu" />
    <s:hidden name="namMenu" />
    <s:token />
</s:form>
<sj:a id="tempDlgMenu" formIds="tempFrmDlgMenu" targets="ph-dlg" cssClass="ui-helper-hidden"></sj:a>
<s:form id="formInqAccessRight" action="10302_getAccessRight.action">
    <s:hidden name="idTemplate" />
    <s:hidden name="idMenu" />
    <s:token />
</s:form>
<sj:a id="tempInqAccessRight" formIds="formInqAccessRight" targets="ph-temp" cssClass="ui-helper-hidden" onSuccessTopics="setAccessRight"></sj:a>    
<s:form id="tempFrmDlgAuth" action="dlg">
    <s:hidden name="dialog" value="dlgAuth" />
    <s:hidden name="idMenu" value="10302" />
    <s:token />
</s:form>
<sj:a id="tempDlgAuth" formIds="tempFrmDlgAuth" targets="ph-dlg" cssClass="ui-helper-hidden"></sj:a>
<s:actionmessage id="actionMessage"/>
<s:actionerror name="actionError" />
<s:form id="frmMain" name="frmMain" action="10302" focusElement="frmMain_namTemplateSearch" validate="true">
    <s:textfield
        name="namTemplateSearch"
        size="30"
        maxlength="20"
        cssClass="ui-widget ui-widget-content"
        key="label.namTemplateSearch"
        disabled="true"
    />
    <sj:a
        id="btnFindTemplate"
        button="true"
        disabled="true"
    >...</sj:a>
    <s:textfield
        name="idTemplate"
        requiredLabel="true"
        size="15"
        maxlength="10"
        cssClass="ui-widget ui-widget-content"
        key="label.idTemplate"
        disabled="true"
    />
    <s:textfield
        name="namTemplate"
        size="60"
        maxlength="40"
        cssClass="ui-widget ui-widget-content"
        key="label.namTemplate"
        disabled="true"
    />
    <s:textfield
        name="namMenuSearch"
        size="30"
        maxlength="20"
        cssClass="ui-widget ui-widget-content"
        key="label.namMenuSearch"
        disabled="true"
    />
    <sj:a
        id="btnFindMenu"
        button="true"
        disabled="true"
    >...</sj:a>
    <s:textfield
        name="idMenu"
        requiredLabel="true"
        size="15"
        maxlength="10"
        cssClass="ui-widget ui-widget-content"
        key="label.idMenu"
        disabled="true"
    />
    <s:textfield
        name="namMenu"
        size="60"
        maxlength="40"
        cssClass="ui-widget ui-widget-content"
        key="label.namMenu"
        disabled="true"
    />
    <sj:checkboxlist
        id="accessCode"
        key="label.accessCode"
        list="{'Inquiry', 'Add', 'Edit', 'Delete', 'Authorize'}"
        name="accessCode"
        disabled="true"
    />
    <s:hidden name="availAccess"/>
    <s:hidden name="accessRightChosen"/>
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
<script type="text/javascript">
    function getAccessRightChosen() {
        if($("#frmMain_idTemplate").val() != '' && $("#frmMain_idMenu").val() != '') {
            tempParams = {};
            tempParams.accessRightChosen = "frmMain_accessRightChosen";

            $("#formInqAccessRight_idTemplate").val($("#frmMain_idTemplate").val());
            $("#formInqAccessRight_idMenu").val($("#frmMain_idMenu").val());
            $("#tempInqAccessRight").click();
        }
    }
    
    function checkCheckbox(accessRightChosen) {
        if(accessRightChosen.substring(0, 1) == '0') {
            $("#accessCode-1").attr("checked", false).button("refresh");
        } else if(accessRightChosen.substring(0, 1) == '1') {
            $("#accessCode-1").attr("checked", true).button("refresh");
        }

        if(accessRightChosen.substring(1, 2) == '0') {
            $("#accessCode-2").attr("checked", false).button("refresh");
        } else if(accessRightChosen.substring(1, 2) == '1') {
            $("#accessCode-2").attr("checked", true).button("refresh");
        }

        if(accessRightChosen.substring(2, 3) == '0') {
            $("#accessCode-3").attr("checked", false).button("refresh");
        } else if(accessRightChosen.substring(2, 3) == '1') {
            $("#accessCode-3").attr("checked", true).button("refresh");
        }

        if(accessRightChosen.substring(3, 4) == '0') {
            $("#accessCode-4").attr("checked", false).button("refresh");
        } else if(accessRightChosen.substring(3, 4) == '1') {
            $("#accessCode-4").attr("checked", true).button("refresh");
        }

        if(accessRightChosen.substring(4, 5) == '0') {
            $("#accessCode-5").attr("checked", false).button("refresh");
        } else if(accessRightChosen.substring(4, 5) == '1') {
            $("#accessCode-5").attr("checked", true).button("refresh");
        }
    }
    
    function enableDisableCheckbox(availAccess) {
        $("#accessCode").buttonset("enable");
        if(availAccess.substring(0, 1) == '0') {
            $("#accessCode-1").button("disable");
        } else if(availAccess.substring(0, 1) == '1') {
            $("#accessCode-1").button("enable");
        }

        if(availAccess.substring(1, 2) == '0') {
            $("#accessCode-2").button("disable");
        } else if(availAccess.substring(1, 2) == '1') {
            $("#accessCode-2").button("enable");
        }

        if(availAccess.substring(2, 3) == '0') {
            $("#accessCode-3").button("disable");
        } else if(availAccess.substring(2, 3) == '1') {
            $("#accessCode-3").button("enable");
        }

        if(availAccess.substring(3, 4) == '0') {
            $("#accessCode-4").button("disable");
        } else if(availAccess.substring(3, 4) == '1') {
            $("#accessCode-4").button("enable");
        }

        if(availAccess.substring(4, 5) == '0') {
            $("#accessCode-5").button("disable");
        } else if(availAccess.substring(4, 5) == '1') {
            $("#accessCode-5").button("enable");
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
        $("#frmMain_namTemplateSearch")
            .parent()
            .append(
                $("#btnFindTemplate").detach()
            )
//            .append(
//                $("#frmMain_idTemplate").detach()
//            )
        ;
        
        $("#frmMain_namMenuSearch")
            .parent()
            .append(
                $("#btnFindMenu").detach()
            )
//            .append(
//                $("#frmMain_idMenu").detach()
//            )
        ;
        
        $("#frmMain_namTemplateSearch").die('keydown');
        $("#frmMain_namTemplateSearch").live('keydown', function(e) {
            if(e.keyCode == 9) {
                $("#btnFindTemplate").click();
            }
        });
        
        $("#frmMain_namMenuSearch").die('keydown');
        $("#frmMain_namMenuSearch").live('keydown', function(e) {
            if(e.keyCode == 9) {
                $("#btnFindMenu").click();
            }
        });
        
        $("#btnFindTemplate").click(function() {
            if(!($("#btnFindTemplate").button('option').disabled != undefined && 
                $("#btnFindTemplate").button('option', 'disabled'))) {
                dlgParams = {};
                dlgParams.idTemplate = "frmMain_idTemplate";
                dlgParams.namTemplate = "frmMain_namTemplate";
                dlgParams.onClose = function() {
                    getAccessRightChosen();
                    $("#frmMain_namMenuSearch").focus();
                };
                $("#ph-dlg").dialog("option", "position", "center");
                $("#ph-dlg").dialog("option", "width", $("body").width()*3/4);
                //$("#ph-dlg").dialog("option", "height", $("body").height()*3/4);
                $("#ph-dlg").dialog("option", "height", "450");
                $("#ph-dlg")
                    .html("Please wait...")
                    .unbind("dialogopen")
                    .bind("dialogopen", function() {
                        $("#tempFrmDlgTemplate_namTemplate").val($("#frmMain_namTemplateSearch").val());
                        $("#tempDlgTemplate").click();
                    })
                    .dialog("open");
            }
        });
        
        $("#btnFindMenu").click(function() {
            if(!($("#btnFindMenu").button('option').disabled != undefined && 
                $("#btnFindMenu").button('option', 'disabled'))) {
                dlgParams = {};
                dlgParams.idMenu = "frmMain_idMenu";
                dlgParams.namMenu = "frmMain_namMenu";
                dlgParams.availAccess = "frmMain_availAccess";
                dlgParams.onClose = function() {
                    if ($("#frmMain_idMenu").val() == "20401") {
                        $("#accessCode span.ui-button-text:contains('Inquiry')").replaceWith("<span class='ui-button-text'>-- --</span>");
                        $("#accessCode span.ui-button-text:contains('Add')").replaceWith("<span class='ui-button-text'>Flag</span>");
                        $("#accessCode span.ui-button-text:contains('Edit')").replaceWith("<span class='ui-button-text'>-----</span>");
                        $("#accessCode span.ui-button-text:contains('Delete')").replaceWith("<span class='ui-button-text'>Unflag</span>");
                    } else {
                        $("#accessCode span.ui-button-text:contains('-- --')").replaceWith("<span class='ui-button-text'>Inquiry</span>");
                        $("#accessCode span.ui-button-text:contains('Flag')").replaceWith("<span class='ui-button-text'>Add</span>");
                        $("#accessCode span.ui-button-text:contains('-----')").replaceWith("<span class='ui-button-text'>Edit</span>");
                        $("#accessCode span.ui-button-text:contains('Unflag')").replaceWith("<span class='ui-button-text'>Delete</span>");
                    }
                    if(!$('#rbBSInquiry').is(':checked')) {
                        enableDisableCheckbox($("#frmMain_availAccess").val());
                    }
                    getAccessRightChosen();
                    $("#frmMain_namTemplateSearch").focus();
                };
                $("#ph-dlg").dialog("option", "position", "center");
                $("#ph-dlg").dialog("option", "width", $("body").width()*3/4);
                //$("#ph-dlg").dialog("option", "height", $("body").height()*3/4);
                $("#ph-dlg").dialog("option", "height", "450");
                $("#ph-dlg")
                    .html("Please wait...")
                    .unbind("dialogopen")
                    .bind("dialogopen", function() {
                        $("#tempFrmDlgMenu_namMenu").val($("#frmMain_namMenuSearch").val());
                        $("#tempDlgMenu").click();
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
        
        $("#tempInqAccessRight").unsubscribe("setAccessRight");
        $("#tempInqAccessRight").subscribe("setAccessRight", function(event, data) {
            checkCheckbox($("#frmMain_accessRightChosen").val());
        });
    });
</script>
<% } %>