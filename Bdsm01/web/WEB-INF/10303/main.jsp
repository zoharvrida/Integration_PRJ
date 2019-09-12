<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<% if (!request.getMethod().equals("GET")) {%>
<s:url var="ajaxUpdateTitle" action="10303_title" />
<s:url var="ajaxUpdateButtons" action="10303_buttons" />
<sj:a id="tempTitle" href="%{ajaxUpdateTitle}" targets="ph-title" cssClass="ui-helper-hidden"></sj:a>
<sj:a id="tempButtons" href="%{ajaxUpdateButtons}" targets="ph-buttons" cssClass="ui-helper-hidden"></sj:a>
<s:form id="tempFrmDlgUser" action="dlg">
    <s:hidden name="dialog" value="dlgUser" />
    <s:hidden name="namUser" />
    <s:token />
</s:form>
<sj:a id="tempDlgUser" formIds="tempFrmDlgUser" targets="ph-dlg" cssClass="ui-helper-hidden"></sj:a>

<s:form id="formidUserInfo" action="10303_inquiry.action">
    <s:hidden name="idUser" />
    <s:token />
</s:form>
<sj:a id="tempidUserInfo" formIds="formidUserInfo" targets="ph-temp" cssClass="ui-helper-hidden"></sj:a>

<s:form id="tempFrmDlgTemplate" action="dlg">
    <s:hidden name="dialog" value="dlgTemplate" />
    <s:hidden name="namTemplate" />
    <s:token />
</s:form>
<sj:a id="tempDlgTemplate" formIds="tempFrmDlgTemplate" targets="ph-dlg" cssClass="ui-helper-hidden"></sj:a>
<s:form id="tempFrmDlgAuth" action="dlg">
    <s:hidden name="dialog" value="dlgAuth" />
    <s:hidden name="idMenu" value="10303" />
    <s:token />
</s:form>
<sj:a id="tempDlgAuth" formIds="tempFrmDlgAuth" targets="ph-dlg" cssClass="ui-helper-hidden"></sj:a>
<s:actionmessage id="actionMessage"/>
<s:actionerror name="actionError"/>
<s:form id="frmMain" name="frmMain" action="10303" focusElement="frmMain_namUserSearch" validate="true">
    <s:textfield
        name="namUserSearch"
        size="30"
        maxlength="20"
        cssClass="ui-widget ui-widget-content"
        key="label.namUserSearch"
        disabled="true"
        />
    <sj:a
        id="btnFindUser"
        button="true"
        disabled="true"
        >...</sj:a>
    <s:textfield
        name="idUser"
        requiredLabel="true"
        size="20"
        maxlength="20"
        cssClass="ui-widget ui-widget-content"
        cssStyle="text-transform:uppercase"
        onblur="javascript:this.value=this.value.toUpperCase();"
        key="label.idUser"
        disabled="true"
        />
    <sj:a
        id="btnCheckAD"
        button="true"
        disabled="true"
        >Check Name From AD</sj:a>
    <s:textfield
        name="namUser"
        size="60"
        requiredLabel="true"
        maxlength="40"
        cssClass="ui-widget ui-widget-content"
        key="label.namUser"
        disabled="true"
        cssStyle="text-transform:uppercase"
        />
    <s:textfield
        name="cdBranch"
        requiredLabel="true"
        size="5"
        maxlength="4"
        cssClass="ui-widget ui-widget-content"
        key="label.cdBranch"
        disabled="true"
        />
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
    function validateForm2_frmMain() {
        form = document.getElementById("frmMain");
        var errors = false;
        // field name: namUserAD
        // validator name: fieldexpression
        if (form.elements['namUser']) {
            field = form.elements['namUser'];
            var error = "User Name must be validated";
            if ($("#btnCheckAD").attr("disabled") == undefined && field.value != null && (field.value == "")) {
                addError(field, error);
                errors = true;

            }
        }
        return !errors;
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
        $("#frmMain_namUserSearch")
        .parent()
        .append(
        $("#btnFindUser").detach()
    )
        //            .append(
        //                $("#frmMain_idUser").detach()
        //            )
        ;

        $("#frmMain_idUser")
        .parent()
        .append(
        $("#btnCheckAD").detach()
    )
        //            .append(
        //                $("#frmMain_namUserAD").detach()
        //            )
        ;

        $("#frmMain_namTemplateSearch")
        .parent()
        .append(
        $("#btnFindTemplate").detach()
    )
        //            .append(
        //                $("#frmMain_idTemplate").detach()
        //            )
        ;

        $("#btnCheckAD").click(function(e) {
            if(!($("#btnCheckAD").button('option').disabled != undefined &&
                $("#btnCheckAD").button('option', 'disabled'))) {
                var idUserTemp = $.trim($("#frmMain_idUser").val());
                if (idUserTemp=="") {
                    e.preventDefault();
                    return;
                }

                tempParams = {};
                tempParams.onClose = function(namUser) {
                    if(namUser == "") {
                        alert("User ID: '" + idUserTemp + "' is not a valid AD User ID");
                        $("#frmMain_namUser").val("");
                        $("#frmMain_idUser").focus();
                    } else {
                        $("#frmMain_idUser").attr("readonly", "true");
                        $("#frmMain_namUser").val(namUser.toUpperCase());
                    }
                };

                $("#formidUserInfo_idUser").val(idUserTemp);
                $("#tempidUserInfo").click();
            }
        });

        $("#frmMain_namUserSearch").die('keydown');
        $("#frmMain_namUserSearch").live('keydown', function(e) {
            if(e.keyCode == 9) {
                $("#btnFindUser").click();
            }
        });

        $("#frmMain_namTemplateSearch").die('keydown');
        $("#frmMain_namTemplateSearch").live('keydown', function(e) {
            if(e.keyCode == 9) {
                $("#btnFindTemplate").click();
            }
        });

        $("#btnFindUser").click(function() {
            if(!($("#btnFindUser").button('option').disabled != undefined &&
                $("#btnFindUser").button('option', 'disabled'))) {
                dlgParams = {};
                dlgParams.idUser = "frmMain_idUser";
                dlgParams.namUser = "frmMain_namUser";
                dlgParams.cdBranch = "frmMain_cdBranch";
                dlgParams.idTemplate = "frmMain_idTemplate";
                dlgParams.namTemplate = "frmMain_namTemplate";

                $("#ph-dlg").dialog("option", "position", "center");
                $("#ph-dlg").dialog("option", "width", $("body").width()*3/4);
                //$("#ph-dlg").dialog("option", "height", $("body").height()*3/4);
                $("#ph-dlg").dialog("option", "height", "450");
                $("#ph-dlg")
                .html("Please wait...")
                .unbind("dialogopen")
                .bind("dialogopen", function() {
                    $("#tempFrmDlgUser_namUser").val($("#frmMain_namUserSearch").val());
                    $("#tempDlgUser").click();
                })
                .dialog("open");
            }
        });

        $("#btnFindTemplate").click(function() {
            if(!($("#btnFindTemplate").button('option').disabled != undefined &&
                $("#btnFindTemplate").button('option', 'disabled'))) {
                dlgParams = {};
                dlgParams.idTemplate = "frmMain_idTemplate";
                dlgParams.namTemplate = "frmMain_namTemplate";

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

        $("#btnSave").subscribe("beforeSubmit", function(event) {
            $("#frmMain").unbind("submit");
            event.originalEvent.options.submit = false;
            var err1 = validateForm_frmMain();
            var err2 = validateForm2_frmMain();
            if (err2 && err1) {
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
    });
</script>
<% }%>