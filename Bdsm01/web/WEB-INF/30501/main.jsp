<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<% if(!request.getMethod().equals("GET")) { %>
<s:url var="ajaxUpdateTitle" action="30501_title" />
<s:url var="ajaxUpdateButtons" action="30501_buttons" />
<sj:a id="tempTitle" href="%{ajaxUpdateTitle}" targets="ph-title" cssClass="ui-helper-hidden"></sj:a>
<sj:a id="tempButtons" href="%{ajaxUpdateButtons}" targets="ph-buttons" cssClass="ui-helper-hidden"></sj:a>
<s:form id="formKtp" action="30501_inquiry.action">
    <s:hidden name="inputNik" />
    <s:token />
</s:form>
<sj:a id="tempKtp" formIds="formKtp" targets="ph-temp" cssClass="ui-helper-hidden"></sj:a>
<s:actionmessage id="actionMessage" />
<s:actionerror name="actionError"/>
<s:form id="frmMain" name="frmMain" action="30501_inquiry.action" focusElement="frmMain_inputNik" method="post" validate="false" theme="simple" >
    <fieldset class=" ui-widget ui-widget-content ui-corner-all"><legend class="ui-widget ui-widget-header ui-corner-all">Input</legend>
<table class="wwFormTable">
<tr>
    <td colspan="4" id="errMsg" style="text-align:center"></td>
</tr>
<tr>
<td class="tdLabel" style="font-style:italic;width:50px"><s:label key="label.E-Ktp.inputNik" for="frmMain_inputNik" /></td>
<td class="tdLabel" style="font-style:italic"><span style="color:red">*</span>:</td>
<td>
    <s:textfield
        name="inputNik"
        size="30"
        maxlength="20"
        cssClass="ui-widget ui-widget-content"
        disabled="false"
    />
</td>
<td style="width:400px">&nbsp;
    <span id="spinner" class="ui-helper-hidden" style="position:relative; float:left"><img src='./spinner1.gif' style='position:absolute; left:0; top:0; width:30px; height:30px; z-index:99' /></span>
</td>    
</tr>
<tr>
<td colspan="2"></td>
<td>
    <sj:submit
        id="btnFind"
        formIds="frmMain"
        buttonIcon="ui-icon-gear"
        button="true"
        key="button.find"
        targets="ph-main" 
        onBeforeTopics="beforeSubmit"
    />
</td>
<td>&nbsp;</td>
</tr>
</table>
</fieldset>
<fieldset class=" ui-widget ui-widget-content ui-corner-all"><legend class="ui-widget ui-widget-header ui-corner-all">Result</legend>
<table class="wwFormTable">
<tr>
<td class="tdLabel" style="font-style:italic"><s:label key="label.E-Ktp.nik" for="frmMain_model_nik" /></td>
<td class="tdLabel" style="font-style:italic"><span style="color:red"></span>:</td>
<td style="padding-right:5px">
    <s:textfield
        name="model.nik"
        size="20"
        maxlength="20"
        cssClass="ui-widget ui-widget-content"
        disabled="true"
    />
</td>
<td class="tdLabel" style="font-style:italic"><s:label key="label.E-Ktp.ektpStatus" for="frmMain_model_ektpStatus" /></td>
<td class="tdLabel" style="font-style:italic"><span style="color:red"></span>:</td>
<td style="padding-right:5px">
    <s:textfield
        name="model.ektpStatus"
        size="40"
        maxlength="50"
        cssClass="ui-widget ui-widget-content"
        disabled="true"
    />
</td>
</tr>
<tr>
<td class="tdLabel" style="font-style:italic"><s:label key="label.E-Ktp.name" for="frmMain_model_name" /></td>
<td class="tdLabel" style="font-style:italic"><span style="color:red"></span>:</td>
<td colspan="4">
    <s:textfield
        name="model.name"
        size="40"
        maxlength="50"
        cssClass="ui-widget ui-widget-content"
        disabled="true"
    />
</td>
</tr>
<tr>
<td class="tdLabel" style="font-style:italic"><s:label key="label.E-Ktp.birthPlace" for="frmMain_model_birthPlace" /></td>
<td class="tdLabel" style="font-style:italic"><span style="color:red"></span>:</td>
<td style="padding-right:5px">
    <s:textfield
        name="model.birthPlace"
        size="30"
        maxlength="50"
        cssClass="ui-widget ui-widget-content"
        disabled="true"
    />
</td>
<td class="tdLabel" style="font-style:italic"><s:label key="label.E-Ktp.dob" for="frmMain_model_dob" /></td>
<td class="tdLabel" style="font-style:italic"><span style="color:red"></span>:</td>
<td style="padding-right:5px">
    <s:textfield
        name="model.dob"
        size="20"
        maxlength="12"
        cssClass="ui-widget ui-widget-content"
        disabled="true"
    />
</td>
</tr>
<tr>
<td class="tdLabel" style="font-style:italic"><s:label key="label.E-Ktp.sex" for="frmMain_model_sex" /></td>
<td class="tdLabel" style="font-style:italic"><span style="color:red"></span>:</td>
<td style="padding-right:5px">
    <s:textfield
        name="model.sex"
        size="10"
        maxlength="10"
        cssClass="ui-widget ui-widget-content"
        disabled="true"
    />
</td>
<td class="tdLabel" style="font-style:italic"><s:label key="label.E-Ktp.bloodType" for="frmMain_model_bloodType" /></td>
<td class="tdLabel" style="font-style:italic"><span style="color:red"></span>:</td>
<td style="padding-right:5px">
    <s:textfield
        name="model.bloodType"
        size="10"
        maxlength="10"
        cssClass="ui-widget ui-widget-content"
        disabled="true"
    />
</td>
</tr>
<tr>
<td class="tdLabel" style="font-style:italic"><s:label key="label.E-Ktp.address" for="frmMain_model_address" /></td>
<td class="tdLabel" style="font-style:italic"><span style="color:red"></span>:</td>
<td colspan="4">
    <s:textfield
        name="model.address"
        size="40"
        maxlength="50"
        cssClass="ui-widget ui-widget-content"
        disabled="true"
    />
</td>
</tr>
<tr>
<td class="tdLabel" style="font-style:italic"><s:label key="label.E-Ktp.rt" for="frmMain_model_rt" /></td>
<td class="tdLabel" style="font-style:italic"><span style="color:red"></span>:</td>
<td style="padding-right:5px">
    <s:textfield
        name="model.rt"
        size="5"
        maxlength="5"
        cssClass="ui-widget ui-widget-content"
        disabled="true"
    />
</td>
<td class="tdLabel" style="font-style:italic"><s:label key="label.E-Ktp.rw" for="frmMain_model_rw" /></td>
<td class="tdLabel" style="font-style:italic"><span style="color:red"></span>:</td>
<td style="padding-right:5px">
    <s:textfield
        name="model.rw"
        size="5"
        maxlength="5"
        cssClass="ui-widget ui-widget-content"
        disabled="true"
    />
</td>
</tr>
<tr>
<td class="tdLabel" style="font-style:italic"><s:label key="label.E-Ktp.namKel" for="frmMain_model_namKel" /></td>
<td class="tdLabel" style="font-style:italic"><span style="color:red"></span>:</td>
<td style="padding-right:5px">
    <s:textfield
        name="model.namKel"
        size="30"
        maxlength="50"
        cssClass="ui-widget ui-widget-content"
        disabled="true"
    />
</td>
<td class="tdLabel" style="font-style:italic"><s:label key="label.E-Ktp.namKec" for="frmMain_model_namKec" /></td>
<td class="tdLabel" style="font-style:italic"><span style="color:red"></span>:</td>
<td style="padding-right:5px">
    <s:textfield
        name="model.namKec"
        size="30"
        maxlength="50"
        cssClass="ui-widget ui-widget-content"
        disabled="true"
    />
</td>
</tr>
<tr>
<td class="tdLabel" style="font-style:italic"><s:label key="label.E-Ktp.namKab" for="frmMain_model_namKab" /></td>
<td class="tdLabel" style="font-style:italic"><span style="color:red"></span>:</td>
<td style="padding-right:5px">
    <s:textfield
        name="model.namKab"
        size="30"
        maxlength="50"
        cssClass="ui-widget ui-widget-content"
        disabled="true"
    />
</td>
<td class="tdLabel" style="font-style:italic"><s:label key="label.E-Ktp.namProp" for="frmMain_model_namProp" /></td>
<td class="tdLabel" style="font-style:italic"><span style="color:red"></span>:</td>
<td style="padding-right:5px">
    <s:textfield
        name="model.namProp"
        size="30"
        maxlength="50"
        cssClass="ui-widget ui-widget-content"
        disabled="true"
    />
</td>
</tr>
<tr>
<td class="tdLabel" style="font-style:italic"><s:label key="label.E-Ktp.posCode" for="frmMain_model_posCode" /></td>
<td class="tdLabel" style="font-style:italic"><span style="color:red"></span>:</td>
<td colspan="4">
    <s:textfield
        name="model.posCode"
        size="5"
        maxlength="10"
        cssClass="ui-widget ui-widget-content"
        disabled="true"
    />
</td>
</tr>
<tr>
<td class="tdLabel" style="font-style:italic"><s:label key="label.E-Ktp.religion" for="frmMain_model_religion" /></td>
<td class="tdLabel" style="font-style:italic"><span style="color:red"></span>:</td>
<td style="padding-right:5px">
    <s:textfield
        name="model.religion"
        size="20"
        maxlength="20"
        cssClass="ui-widget ui-widget-content"
        disabled="true"
    />
</td>
<td class="tdLabel" style="font-style:italic"><s:label key="label.E-Ktp.marStat" for="frmMain_model_marStat" /></td>
<td class="tdLabel" style="font-style:italic"><span style="color:red"></span>:</td>
<td style="padding-right:5px">
    <s:textfield
        name="model.marStat"
        size="20"
        maxlength="20"
        cssClass="ui-widget ui-widget-content"
        disabled="true"
    />
</td>
</tr>
<tr>
<td class="tdLabel" style="font-style:italic"><s:label key="label.E-Ktp.profession" for="frmMain_model_profession" /></td>
<td class="tdLabel" style="font-style:italic"><span style="color:red"></span>:</td>
<td style="padding-right:5px">
    <s:textfield
        name="model.profession"
        size="30"
        maxlength="30"
        cssClass="ui-widget ui-widget-content"
        disabled="true"
    />
</td>
<td class="tdLabel" style="font-style:italic"><s:label key="label.E-Ktp.lastEdu" for="frmMain_model_lastEdu" /></td>
<td class="tdLabel" style="font-style:italic"><span style="color:red"></span>:</td>
<td style="padding-right:5px">
    <s:textfield
        name="model.lastEdu"
        size="30"
        maxlength="40"
        cssClass="ui-widget ui-widget-content"
        disabled="true"
    />
</td>
</tr>
<tr>
<td class="tdLabel" style="font-style:italic"><s:label key="label.E-Ktp.namFather" for="frmMain_model_namFather" /></td>
<td class="tdLabel" style="font-style:italic"><span style="color:red"></span>:</td>
<td colspan="4">
    <s:textfield
        name="model.namFather"
        size="40"
        maxlength="50"
        cssClass="ui-widget ui-widget-content"
        disabled="true"
    />
</td>
</tr>
<tr>
<td class="tdLabel" style="font-style:italic"><s:label key="label.E-Ktp.namMother" for="frmMain_model_namMother" /></td>
<td class="tdLabel" style="font-style:italic"><span style="color:red"></span>:</td>
<td colspan="4">
    <s:textfield
        name="model.namMother"
        size="40"
        maxlength="50"
        cssClass="ui-widget ui-widget-content"
        key="label.E-Ktp.namMother"
        disabled="true"
    />
</td>
</tr>
<tr>
<td class="tdLabel" style="font-style:italic"><s:label key="label.E-Ktp.nokk" for="frmMain_model_nokk" /></td>
<td class="tdLabel" style="font-style:italic"><span style="color:red"></span>:</td>
<td colspan="4">
    <s:textfield
        name="model.nokk"
        size="20"
        maxlength="20"
        cssClass="ui-widget ui-widget-content"
        key="label.E-Ktp.nokk"
        disabled="true"
    />
</td>
</tr>
</table>
</fieldset>
    <s:token />
</s:form>
<script type="text/javascript">
    function clearResult() {
        $("#frmMain_model_nik").attr("value", null);
        $("#frmMain_model_name").attr("value", null);
        $("#frmMain_model_ektpStatus").attr("value", null);
        $("#frmMain_model_birthPlace").attr("value", null);
        $("#frmMain_model_dob").attr("value", null);
        $("#frmMain_model_sex").attr("value", null);
        $("#frmMain_model_bloodType").attr("value", null);
        $("#frmMain_model_address").attr("value", null);
        $("#frmMain_model_rt").attr("value", null);
        $("#frmMain_model_rw").attr("value", null);
        $("#frmMain_model_namKel").attr("value", null);
        $("#frmMain_model_namKec").attr("value", null);
        $("#frmMain_model_namKab").attr("value", null);
        $("#frmMain_model_namProp").attr("value", null);
        $("#frmMain_model_posCode").attr("value", null);
        $("#frmMain_model_religion").attr("value", null);
        $("#frmMain_model_marStat").attr("value", null);
        $("#frmMain_model_profession").attr("value", null);
        $("#frmMain_model_lastEdu").attr("value", null);
        $("#frmMain_model_namFather").attr("value", null);
        $("#frmMain_model_namMother").attr("value", null);
        $("#frmMain_model_nokk").attr("value", null);
    }
    
    $(function() {
        if ("${session.idMenu}" == "30501") {
            if ($("#actionError").length==0 && $("#actionMessage").length==0) {
                $("#rbBS").buttonset("destroy");
                $("#tempTitle").click();
                $("#tempButtons").click();
            } else {
                $("#rbBS").data("rdb").callCurrent();
                if ($("#actionError").length==0) {
                    if ($("#frmMain_model_nik").val().length()==0)
                        $("#rbBS").data("rdb").clear(false);
                }
            }
        }
        
        $("#btnFind").subscribe("beforeSubmit", function(event) {
            event.originalEvent.options.submit = false;
            if ($("#frmMain_inputNik").val().length!=16) {
                alert("Input NIK must be 16 digits");
                return;
            } else if (isNaN($("#frmMain_inputNik").val())) {
                alert("Input NIK must be 16 digits numeric");
                return;
            }
           
            clearResult();
            $("#errMsg").html("");
            $("#spinner").removeClass("ui-helper-hidden");
            tempParams = {};
            tempParams.onClose = function(model) {
                $("#spinner").addClass("ui-helper-hidden");
                if (model==null) {
                    $("#errMsg").html("<ul id=\"actionError\" class=\"errorMessage\"><li><span>Null data</span></li></ul>");
                } else if(model.responseMessage == undefined) {
                    $("#frmMain_model_nik").val(model.nik);
                    $("#frmMain_model_name").val(model.name);
                    $("#frmMain_model_ektpStatus").val(model.ektpStatus);
                    $("#frmMain_model_birthPlace").val(model.birthPlace);
                    $("#frmMain_model_dusun").val(model.dusun);
                    $("#frmMain_model_religion").val(model.religion);
                    $("#frmMain_model_profession").val(model.profession);
                    $("#frmMain_model_lastEdu").val(model.lastEdu);
                    $("#frmMain_model_marStat").val(model.marStat);
                    $("#frmMain_model_bloodType").val(model.bloodType);
                    $("#frmMain_model_sex").val(model.sex);
                    $("#frmMain_model_nokk").val(model.nokk);
                    $("#frmMain_model_namKab").val(model.namKab);
                    $("#frmMain_model_namFather").val(model.namFather);
                    $("#frmMain_model_namKec").val(model.namKec);
                    $("#frmMain_model_rw").val(model.rw);
                    $("#frmMain_model_noKel").val(model.noKel);
                    $("#frmMain_model_rt").val(model.rt);
                    $("#frmMain_model_posCode").val(model.posCode);
                    $("#frmMain_model_noKec").val(model.noKec);
                    $("#frmMain_model_address").val(model.address);
                    $("#frmMain_model_noProp").val(model.noProp);
                    $("#frmMain_model_namMother").val(model.namMother);
                    $("#frmMain_model_namProp").val(model.namProp);
                    $("#frmMain_model_noKab").val(model.noKab);
                    $("#frmMain_model_namKel").val(model.namKel);
                    $("#frmMain_model_dob").val(model.dob);

                    $("#frmMain_inputNik").val("");
                    $("#frmMain_inputNik").focus();
                } else {
                    $("#errMsg").html("<ul id=\"actionError\" class=\"errorMessage\"><li><span>"+ model.responseMessage+"</span></li></ul>");
                    $("#frmMain_inputNik").focus();
                }
            }
            $("#formKtp_inputNik").val($("#frmMain_inputNik").val());
            $("#tempKtp").click();
        });
    });
</script>
<% } %>
