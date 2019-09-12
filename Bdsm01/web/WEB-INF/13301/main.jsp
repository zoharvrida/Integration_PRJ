<%@page import="com.opensymphony.xwork2.ActionContext"%>
<%@page import="com.opensymphony.xwork2.ActionSupport"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<% if (!request.getMethod().equals("GET")) {%>
<s:url var="ajaxUpdateTitle" action="13301_title" />
<s:url var="ajaxUpdateButtons" action="13301_buttons" />

<sj:a id="tempTitle" href="%{ajaxUpdateTitle}" targets="ph-title" cssClass="ui-helper-hidden"></sj:a>
<sj:a id="tempButtons" href="%{ajaxUpdateButtons}" targets="ph-buttons" cssClass="ui-helper-hidden"></sj:a>
<s:form id="tempFrmDlgScheduler" action="dlg">
    <s:hidden name="dialog" value="dlgNone" id="tempStat"/>
    <s:hidden name="typeS" />
    <s:hidden name="namschedProfile" />
    <s:token />
</s:form>
<sj:a id="tempDlgScheduler" formIds="tempFrmDlgScheduler" targets="ph-dlg" cssClass="ui-helper-hidden"></sj:a>
<s:form id="tempFrmDlgAuth" action="dlg">
    <s:hidden name="dialog" value="dlgAuth" />
    <s:hidden name="idMenu" value="13301" />
    <s:token />
</s:form>
<sj:a id="tempDlgAuth" formIds="tempFrmDlgAuth" targets="ph-dlg" cssClass="ui-helper-hidden"></sj:a>
<s:form id="tempValidation" action="13301_validation.action">
    <s:hidden name="oldKey" />
    <s:hidden name="typeS" />
    <s:hidden name="chgStat" />
    <s:hidden name="namschedProfile" />
    <s:hidden name="idschedProfile" />
    <s:hidden name="idFixTemplate" />
    <s:hidden name="validatePass" />
    <s:token />
</s:form>
<sj:a id="tempDlgValidation" formIds="tempValidation" targets="ph-dlg" cssClass="ui-helper-hidden"></sj:a>

<s:form id="frmRefreshParameter" action="13301_refreshParameter">
    <s:token name="tokenRefresh" />
</s:form>

<s:actionmessage id="actionMessage" />
<s:actionerror name="actionError"/>
<s:form id="frmMain" name="frmMain" action="13301" method="post" validate="true" theme="css_xhtml"> 

    <fieldset class="ui-widget-content ui-corner-all">
        <legend class="ui-widget-header ui-corner-all">Scheduler Profile Detail</legend>
        <table id="profile">
            <tbody>
                <tr>
                    <td colspan="2">
                        <sj:checkboxlist
                            id="typeS"
                            list="{'Extract', 'Import'}"
                            name="typeS"
                            key="label.schedType"
                            /> 
                    </td>
                </tr>
                <tr>
                    <td colspan="3">
                        <s:textfield
                            name="namschedProfileSearch"
                            size="30"
                            maxlength="20"
                            cssClass="ui-widget ui-widget-content"
                            key="label.schedulerSearch"
                            disabled="true"
                            />
                    </td><td>
                        <sj:a
                            id="btnFindschedProfile"
                            button="true"
                            disabled="true"
                            >...</sj:a>
                    </td>
                </tr>
                <tr>
                    <td>
                        <s:textfield
                            name="idschedProfile"
                            requiredLabel="true"
                            size="15"
                            maxlength="10"
                            cssClass="ui-widget ui-widget-content"
                            key="label.idScheduler"
                            disabled="true"
                            />  
                    </td>
                </tr>
                <tr>
                    <td width="1180px">
                        <s:textfield
                            name="namschedProfile"
                            requiredLabel="false"
                            size="60"
                            maxlength="40"
                            cssClass="ui-widget ui-widget-content"
                            key="label.schedulerName"
                            disabled="true"
                            />
                    </td>
                </tr>
                <tr>
                    <td >
                        <s:textfield
                            name="statusScheduler"
                            id="statusScheduler"
                            requiredLabel="false"
                            size="7"
                            maxlength="40"
                            cssClass="errorMessage"
                            key="label.schedStat"
                            disabled="true"
                            />
                    </td>
                </tr>
                <tr id="chose">
                    <td colspan="2">
                        <sj:checkboxlist
                            id="choiceAction"
                            name="choiceAction"
                            list="{'Change Status','Change Password'}"
                            button="true"
                            key="label.schedulerChoose"
                            />
                    </td>
                </tr>
            </tbody>
        </table>
    </fieldset> 
    
    <fieldset class="ui-widget-content ui-corner-all" id="status">
        <legend class="ui-widget-header ui-corner-all">Change Status</legend>
        <table>
            <tr>
                <td width="1180px">
                    <sj:radio
                        id="schedStat"
                        key="label.schedType"
                        list="{'Active','Inactive'}"
                        name="schedStat"
                        button="true"
                        />  
                </td>
            </tr>
        </table>
    </fieldset>
    
    <fieldset class="ui-widget-content ui-corner-all" id="validation">
        <legend class="ui-widget-header ui-corner-all">Change Password</legend>
        <table id="changePass">
            <tr id="p0">
                <td width="1180px">
                    <s:password
                        name="oldKey"
                        requiredLabel="true"
                        size="100"
                        maxlength="100"
                        cssClass="ui-widget ui-widget-content"
                        key="label.oldPass"
                        disabled="true"
                        autocomplete="off"
                        />
                </td>
                <td>
                    <s:checkbox 
                        name="check"
                        requiredLabel="false"
                        cssClass="ui-widget ui-widget-content"
                        id = "check"
                        /> 
                </td>
            </tr>
            <tr id="p1">
                <td>
                    <s:password
                        name="keyEncrypt"
                        requiredLabel="true"
                        size="100"
                        maxlength="100"
                        cssClass="ui-widget ui-widget-content"
                        key="label.zipPass"
                        disabled="true"
                        autocomplete="off"
                        />
                </td>
            </tr>
            <tr id="p2">
                <td>
                    <s:password
                        name="keyEncryptS"
                        requiredLabel="true"
                        size="100"
                        maxlength="100"
                        cssClass="ui-widget ui-widget-content"
                        key="label.zipPassconf"
                        disabled="true"
                        autocomplete="off"
                        /> 
                </td>
            </tr>
        </table>
    </fieldset>
    
    <s:hidden name="notification" id="notification" />            
    <s:hidden name="idFixTemplate" id="idFixTemplate" cssClass="ui-widget ui-widget-content"/>
    <s:hidden name="flgStatnow" id="flgStatnow" />
    <s:hidden name="flgStatus" id="flgStatus"/>
    <s:hidden name="validatePass" id="validatePass"/> 
    <s:hidden name="eximStatus" id="eximStatus"/>
    <s:hidden name="chgStat" id="chgStat" />
    <s:hidden name="reset" id="reset" />
    <s:hidden name="passValidate" id="passValidate" />
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

<div style="height:30px"></div>

<fieldset id="fsRefreshParameter" class="ui-widget-content ui-corner-all">
    <legend class="ui-widget-header ui-corner-all">BDSM Parameter Config</legend>
    <table>
        <tr>
            <td width="1180px">
                <sj:submit id="btnRefreshParameter" button="true" key="button.refresh" formIds="frmRefreshParameter" targets="ph-temp" />
            </td>
        </tr>
    </table>
</fieldset>


<script type="text/javascript">
    $('#hiddens').attr('style','display:none');
    $('#hiddend').attr('style','display:none');
    $('#check').attr('style','display:none');
    $('.hideTable').attr('style','display:none');
    $('#choiceActionnone').attr('style','display: none');
    $('.wwlbl').attr('style','width:20%;float:left;text-align:right;vertical-align:middle;margin-right:5px;margin-top:5px');
    $('br').remove();
    
    document.getElementById("statusScheduler").style.background = "transparent";
    document.getElementById("statusScheduler").style.border = "0px";
    document.getElementById("statusScheduler").style.textAlign = "right"; 
    document.getElementById("statusScheduler").style.fontSize = "12px";
    
    /* in IE version < 11: button scrolling when page is scrolled, and extra space when form is displayed default */ 
    if (navigator.appName.indexOf("Internet Explorer") > -1) {
        $("body").find("*[role='button']").css("position", "static");
    }
    /* === [END] alter display === */
    
    
    function removesRow3(rowid){
        var row =  document.getElementById(rowid);
        //alert(rowid);
        row.parentNode.removeChild(row);
    }
    //$("#choiceActionnone").attr("style","display:none").button("refresh");
    function getRadioChosen() {
        if($("#frmMain_idschedProfile").val() != '' && $("#frmMain_namschedProfile").val() != '') {
            tempParams = {};
            tempParams.flgStatus = "flgStatus";
        }
    }
    function testRadio(){
        var status = document.forms[0].schedStat;
        $('input[name=schedStat]').filter('[value=Inactive]').prop('checked',true);
    }
    function enableDisableType() {
        $("#typeS").buttonset("enable");
        $("#typeS-1").button("enable");
        $("#typeS-2").button("enable");
    }
    //key="label.schedType"
    function checkRadio(flgStatus) {     
        if(flgStatus == 'A') {
            $("#schedStatActive").attr("checked", true).button("refresh");
            $("#statusScheduler").val("Active");
            document.getElementById("statusScheduler").style.color = "blue";
            
        } else if(flgStatus == 'I') {
            $("#schedStatInactive").attr("checked", true).button("refresh");
            $("#statusScheduler").val("InActive");
            document.getElementById("statusScheduler").style.color = "red";
        } else {
            $("#schedStatActive").attr("checked", false).button("refresh");
            $("#schedStatInactive").attr("checked", false).button("refresh");
            $("#statusScheduler").val("No Status");
        }
    }
    function validateAll(){
        var summary = 0;
        if($('#chgStat').val()=="Password"){
            if($('#frmMain_keyEncrypt').val() == ""){
                if(document.getElementById("errorpas0")){                    
                    removesRow3("errorpas0");   
                }
                $('#changePass tr#p1').before('<tr id="errorpas0"><td align="center" colspan="2"><span class="errorMessage" classname="errorMessage">'
                    + " New Password can't be blank "+'</span></td></tr>');
                summary++;  
            }     
            else if($('#frmMain_keyEncrypt').val()!= $('#frmMain_keyEncryptS').val()){
                if(document.getElementById("errorpas0")){                    
                    removesRow3("errorpas0");   
                }
                if(document.getElementById("errorpas1")){                    
                    removesRow3("errorpas1");   
                }
                $('#changePass tr#p1').before('<tr id="errorpas0"><td align="center" colspan="2"><span class="errorMessage" classname="errorMessage">'
                    + " Password Missmatch "+'</span></td></tr>');
                $('#changePass tr#p2').before('<tr id="errorpas1"><td align="center" colspan="2"><span class="errorMessage" classname="errorMessage">'
                    + " Password Missmatch "+'</span></td></tr>');
                summary++;
                //alert("New Password Mismatch");
                //return;
            }
            
        }else if($('#chgStat').val()=="Status"){
            if($('#flgStatus').val()== $('#flgStatnow').val()){
                alert("Scheduler Status is already " + $('#flgStatnow').val());
                return;
            }
        }
        else if($('#chgStat').val() == ""){
            if(document.getElementById("errornochoice")){                    
                    removesRow3("errornochoice");   
                }
            $('#profile tr#chose').before('<tr id="errornochoice"><td align="center" colspan="2"><span class="errorMessage" classname="errorMessage">'
                + " Please choose one of following action "+'</span></td></tr>');
            summary++;
        }
        return summary;
    }
    $(function() {
        $('#status').attr('style','display:none');
        $('#notification').attr('style','display:none');
        $("#choiceAction").buttonset("disable");
        $("#choiceAction").button("disable");
        
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
        
        if(!$('#rbBSInquiry').is(':checked')) {
            enableDisableType();
        }
        $("#frmMain_namschedProfileSearch")
        .parent()
        .append(
        $("#btnFindschedProfile").detach());
        
        $("#frmMain_oldKey")
        .parent()
        .append(
        $("#check").detach()
    )  
        ;
        $("#frmMain_namschedProfileSearch").die('keydown');
        $("#frmMain_namschedProfileSearch").live('keydown', function(e) {
            if(e.keyCode == 9) {
                $("#btnFindschedProfile").click();
            }
        });
        $("#frmMain_oldKey").die('keydown');
        $("#frmMain_oldKey").live('keydown' , function (e) {
            if (e.which == 9){
                if($("#frmMain_oldKey").val() != ""){
                    $("#validatePass").val("1");
                    //$("#btnSave").button("disable");
                    //alert($("#idFixTemplate").val());
                    $("#tempValidation_typeS").val($("#eximStatus").val());
                    $("#tempValidation_chgStat").val($("#chgStat").val());
                    $("#tempValidation_oldKey").val($("#frmMain_oldKey").val());
                    $("#tempValidation_idschedProfile").val($("#frmMain_idschedProfile").val());
                    $("#tempValidation_namschedProfile").val($("#frmMain_namschedProfile").val());
                    $("#tempValidation_idFixTemplate").val($("#idFixTemplate").val());
                    $("#tempValidation_validatePass").val($("#validatePass").val());
                
                    $("#passValidate").val($("#frmMain_oldKey").val());
                   
                    $("#btnSave").button("enable");
                    
                    $("#tempDlgValidation").click();
                    passParams = {};
                    passParams.onClose = function(notification){
                        if(notification == ""){
                            if(document.getElementById("errorpas0")){                    
                                removesRow3("errorpas0");   
                            }
                            $("#frmMain_oldKey").attr("readonly","readonly");
                            $("#btnSave").button("enable");
                            //e.preventDefault();
                        } else {
                            $("#notification").val(notification);
                            if(document.getElementById("errorpas0")){                    
                                removesRow3("errorpas0");   
                            }
                            $('#changePass tr#p0').before('<tr id="errorpas0"><td align="center" colspan="2"><span class="errorMessage" classname="errorMessage">'
                                + notification +'</span></td></tr>');
                            $("#btnSave").button("disable");
                            e.preventDefault();
                        }
                    }
                    
                } else {
                    if(document.getElementById("errorpas0")){                    
                        removesRow3("errorpas0");   
                    }
                    $('#changePass tr#p0').before('<tr id="errorpas0"><td align="center" colspan="2"><span class="errorMessage" classname="errorMessage">'
                        +"Old Password cannot be blank"+'</span></td></tr>');
                    $("#btnSave").button("disable");
                }
            }
        });        
        $('#frmMain_keyEncrypt').click(function() {
            if($("#frmMain_oldKey").val() != ""){
                $("#validatePass").val("1");
                
                $("#tempValidation_typeS").val($("#eximStatus").val());
                $("#tempValidation_chgStat").val($("#chgStat").val());
                $("#tempValidation_oldKey").val($("#frmMain_oldKey").val());
                $("#tempValidation_idschedProfile").val($("#frmMain_idschedProfile").val());
                $("#tempValidation_namschedProfile").val($("#frmMain_namschedProfile").val());
                $("#tempValidation_idFixTemplate").val($("#idFixTemplate").val()); 
                $("#tempValidation_validatePass").val($("#validatePass").val());
                $("#passValidate").val($("#frmMain_oldKey").val());
                
                //alert("nokey");
                $("#tempDlgValidation").click(); 
                passParams = {};
                passParams.onClose = function(notification){
                    if(notification == ""){
                        //alert("test");
                        //$("#supervisor").val("");
                        if(document.getElementById("errorpas0")){                    
                            removesRow3("errorpas0");   
                        }
                        $("#frmMain_oldKey").attr("readonly","readonly");
                        $("#btnSave").button("enable");
                        //e.preventDefault();
                    } else {
                        //alert("test2 :" + password);
                        $("#notification").val(notification);
                        if(document.getElementById("errorpas0")){                    
                            removesRow3("errorpas0");   
                        }
                        $('#changePass tr#p0').before('<tr id="errorpas0"><td align="center" colspan="2"><span class="errorMessage" classname="errorMessage">'
                            + notification +'</span></td></tr>');
                        $("#btnSave").button("disable");
                        e.preventDefault();
                    }
                }
            } else {
                if(document.getElementById("errorpas0")){                    
                    removesRow3("errorpas0");   
                }
                $('#changePass tr#p0').before('<tr id="errorpas0"><td align="center" colspan="2"><span class="errorMessage" classname="errorMessage">'
                    +"Old Password cannot be blank"+'</span></td></tr>');
                $("#btnSave").button("disable");
            }
            //$("#passValidate").val($("#frmMain_oldKey").val());
        });
        $("#typeS").click(function() {
            var schedProfile = $(this).val(); 
            $('#eximStatus').val($('input[name=typeS]:checked','#frmMain').val());
            $('input[name=schedStat]:checked','#frmMain').val();
            $("#frmMain_namschedProfile").attr("value", null);
            $("#frmMain_idschedProfile").attr("value", null);
            $("#schedStat").attr("value", null);
            $("#schedProfile").attr("value", null);
            $("#statusScheduler").attr("value", null);
            if(document.getElementById("errornochoice")){                    
                    removesRow3("errornochoice");   
                }
        });
        $('#typeS-1').click(function() {
            $("#typeS-2").attr("checked", false).button("refresh");
            $('#status').attr('style','display:none');
            $('#validation').attr('style','display:none');
            $("#schedStat").buttonset("disable");
            $("#schedStat").button("disable");
            $("#choiceAction-1").attr("checked", false).button("refresh");
            $("#choiceAction-2").attr("checked", false).button("refresh");
            for(var loop = 0;loop<3;loop++){
                if(document.getElementById("errorpas"+loop)){                    
                    removesRow3("errorpas"+loop);   
                }   
            }
            if(document.getElementById("errornochoice")){                    
                    removesRow3("errornochoice");   
                }
                $("#choiceAction").buttonset("disable");
               $("#choiceAction").button("disable");  
        });
        $('#typeS-2').click(function() {
            $("#typeS-1").attr("checked", false).button("refresh");
            $('#status').attr('style','display:none');
            $('#validation').attr('style','display:none');
            $("#schedStat").buttonset("disable");
            $("#schedStat").button("disable");
            $("#choiceAction-1").attr("checked", false).button("refresh");
            $("#choiceAction-2").attr("checked", false).button("refresh");
            for(var loop = 0;loop<3;loop++){
                if(document.getElementById("errorpas"+loop)){                    
                    removesRow3("errorpas"+loop);   
                }   
            }
            if(document.getElementById("errornochoice")){                    
                    removesRow3("errornochoice");   
                }
                $("#choiceAction").buttonset("disable");
               $("#choiceAction").button("disable");
        });
        $("#btnFindschedProfile").click(function() {

            if($('input[name=typeS]:checked','#frmMain').val()=='Extract')
            {
                $('#tempStat').attr('value','dlgExtract');
            }
            else if($('input[name=typeS]:checked','#frmMain').val()=='Import')
            {
                $('#tempStat').attr('value','dlgImport');
            } else {
                alert("Choose one of Scheduler Type");
                return false;
            }
            if(!($("#btnFindschedProfile").button('option').disabled != undefined && 
                $("#btnFindschedProfile").button('option','disabled'))) {
                dlgParams = {};
                dlgParams.idschedProfile = "frmMain_idschedProfile";
                dlgParams.namschedProfile = "frmMain_namschedProfile";
                dlgParams.idFixTemplate = "idFixTemplate";
                dlgParams.flgStatus = "flgStatus";
                dlgParams.onClose = function() {

                    checkRadio($("#flgStatus").val());
                    if($("#flgStatus").val() == "A"){
                        $("#flgStatnow").val("Active");   
                    } else if($("#flgStatus").val() == "I"){
                        $("#flgStatnow").val("Inactive");   
                    } else {
                        $("#flgStatnow").val("notsetted");
                        $("#flgStatus").val("notsetted");
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

                    $("#tempFrmDlgScheduler_namschedProfile").val($("#frmMain_namschedProfileSearch").val());

                    $("#tempDlgScheduler").click();
                })
                .dialog("open");
            }
        });
        $("#choiceAction-1").click(function(){
            $("#choiceAction-2").attr("checked", false).button("refresh");
            if($("#frmMain_idschedProfile").val() != '' && $("#frmMain_namschedProfile").val() != ''){
                $('#status').attr('style','display:block');
                $('#validation').attr('style','display:none');
                $("#schedStat").buttonset("enable");
                $("#schedStat").button("enable");
                $("#frmMain_oldKey").attr('disabled','true');  
                $("#frmMain_keyEncrypt").attr('disabled','true');
                $("#frmMain_keyEncryptS").attr('disabled','true');
                $("#frmMain_oldKey").attr("value", null);
                $("#frmMain_keyEncrypt").attr("value", null);
                $("#frmMain_keyEncryptS").attr("value", null);
                $('#chgStat').val('Status');
                $("#choiceAction").buttonset("enable");
                $("#choiceAction").button("enable");
                $("#btnSave").button("enable");    
            } else {
                alert("Please Pick Scheduler Name");
                $("#choiceAction-1").attr("checked", false).button("refresh");              
                return false;
            }
        })
        $("#choiceAction-2").click(function(){
            $("#choiceAction-1").attr("checked", false).button("refresh");
            if($("#frmMain_idschedProfile").val() != '' && $("#frmMain_namschedProfile").val() != ''){
                $('#validation').attr('style','display:block');
                $('#status').attr('style','display:none');
                $("#schedStat").buttonset("disable");
                $("#schedStat").button("disable");
                //$("#frmMain_oldKey").focus();
                $("#frmMain_keyEncrypt").attr('disabled','true');
                $("#frmMain_keyEncryptS").attr('disabled','true');
                $("#rbBS").data("rdb").callCurrent();
                $('#chgStat').val('Password');               
                $("#choiceAction").buttonset("enable");
                $("#choiceAction").button("enable");
                $("#btnSave").button("disable");
                //$("#statusScheduler").attr("value", null);
            } else {
                alert("Please Pick Scheduler Name");
                $("#choiceAction-2").attr("checked", false).button("refresh");
                return false;
            }
        })
        $("#schedStat").click(function() {
            var schedProfile = $(this).val(); 
            $('#flgStatus').val($('input[name=schedStat]:checked','#frmMain').val());
        });
        $("#check").click(function (){
            //alert($("#check").val());
            $("#frmMain_oldKey").buttonset("disable");
            $("#reset").val($('input:checkbox[name=check]').is(':checked'));
            //alert($("#reset").val());
            //$("#check").attr("checked", false).button("refresh");
        });
        $("#btnSave").unsubscribe("beforeSubmit");
        $("#btnSave").subscribe("beforeSubmit", function(event) {
            $("#frmMain").unbind("submit");
            if ($("#actionMessage").length!=0){
                $("#actionMessage").remove();
            }
            event.originalEvent.options.submit = false;
            var validate = validateAll();
            //alert(validate);
            //result = SeparatorRequest(requester,supervisor,monitoring,separator);
            if(validate == 0){
                for(var loop = 0;loop<2;loop++){
                    if(document.getElementById("errorpas"+loop)){                    
                        removesRow3("errorpas"+loop);   
                    }   
                }
                $("#validatePass").val("0");
                
                    if (validateForm_frmMain()) {  
                        dlgParams = {};
                        dlgParams.idMaintainedBy = "frmMain_idMaintainedBy";
                        dlgParams.idMaintainedSpv = "frmMain_idMaintainedSpv";
                        dlgParams.event = event;
                        dlgParams.onSubmit = function() {
                            dlgParams.event.originalEvent.options.submit = true;
                            $("#btnSave").unsubscribe("beforeSubmit");
                            $("#btnSave").click();
                        $("#btnSave").button("disable");
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
                 
            } else {
                return false;
            }
        });
    });
</script>
<% }%>
