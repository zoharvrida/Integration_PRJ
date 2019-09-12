<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<% if (!request.getMethod().equals("GET")) {%>
<s:url var="ajaxUpdateTitle" action="26301_title" />
<s:url var="ajaxUpdateButtons" action="26301_buttons" />
<sj:a id="tempTitle" href="%{ajaxUpdateTitle}" targets="ph-title" cssClass="ui-helper-hidden"></sj:a>
<sj:a id="tempButtons" href="%{ajaxUpdateButtons}" targets="ph-buttons" cssClass="ui-helper-hidden"></sj:a>
<s:form id="frmBacktoBegin" action="26301_exec">
    <s:hidden name="current" />
    <s:token name="backToken"/>
</s:form>
<sj:a id="aBacktoBegin" formIds="frmBacktoBegin" targets="ph-temp" cssClass="ui-helper-hidden" />

<%-- Form EKTP Reader --%>
<s:form id="frmDlgEKTPReader" action="30503_input.action">
    <s:token />
</s:form>
<sj:a id="aDlgEKTPReader" formIds="frmDlgEKTPReader" targets="ph-dlg" cssClass="ui-helper-hidden" />

<s:actionmessage id="actionMessage" />
<s:actionerror name="actionError"/>
<script type="text/css" >
    .no-close .ui-dialog-titlebar-close {
        display: none;
    }
</script>
<script type="text/javascript" src="_js/validation_theme_css_xhtml.js"></script>
<s:form id="frmMain" name="frmMain" action="26301" method="post" validate="false" theme="simple" >
    <fieldset class=" ui-widget ui-widget-content ui-corner-all"><legend class="ui-widget ui-widget-header ui-corner-all">Dukcapil Data Inquiry</legend>
        <table class="wwFormTable">
            <tr>
                <td colspan="4" id="errMsg" style="text-align:center"></td>
            </tr>
            <tr>
                <td class="tdLabel" style="font-style:italic"><s:label key="label.E-Ktp.nik" for="frmMain_model_nik" /></td>
                <td class="tdLabel" style="font-style:italic"><span style="color:red"></span>:</td>
                <td style="padding-right:5px">
                    <s:textfield
                        name="model.nik"
                        size="20"
                        maxlength="20"
                        cssClass="ui-widget ui-widget-content"
                        readonly="true"
						cssStyle="text-transform:uppercase"
                        onblur="javascript:this.value=this.value.toUpperCase();"
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
                        readonly="true"
						cssStyle="text-transform:uppercase"
                        onblur="javascript:this.value=this.value.toUpperCase();"
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
                        readonly="true"
						cssStyle="text-transform:uppercase"
                        onblur="javascript:this.value=this.value.toUpperCase();"
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
                        readonly="true"
						cssStyle="text-transform:uppercase"
                        onblur="javascript:this.value=this.value.toUpperCase();"
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
                        readonly="true"
						cssStyle="text-transform:uppercase"
                        onblur="javascript:this.value=this.value.toUpperCase();"
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
                        readonly="true"
						cssStyle="text-transform:uppercase"
                        onblur="javascript:this.value=this.value.toUpperCase();"
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
                        readonly="true"
						cssStyle="text-transform:uppercase"
                        onblur="javascript:this.value=this.value.toUpperCase();"
                        />
                </td>
            </tr>
            <tr>
                <td class="tdLabel" style="font-style:italic"><s:label key="label.E-Ktp.address" for="frmMain_model_address" /></td>
                <td class="tdLabel" style="font-style:italic"><span style="color:red"></span>:</td>
                <td style="padding-right:5px">
                    <s:textfield
                        name="model.address"
                        size="40"
                        maxlength="50"
                        cssClass="ui-widget ui-widget-content"
                        readonly="true"
						cssStyle="text-transform:uppercase"
                        onblur="javascript:this.value=this.value.toUpperCase();"
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
                        readonly="true"
						cssStyle="text-transform:uppercase"
                                onblur="javascript:this.value=this.value.toUpperCase();"
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
                        readonly="true"
						cssStyle="text-transform:uppercase"
                                onblur="javascript:this.value=this.value.toUpperCase();"
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
                        readonly="true"
						cssStyle="text-transform:uppercase"
                                onblur="javascript:this.value=this.value.toUpperCase();"
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
                        readonly="true"
						cssStyle="text-transform:uppercase"
                                onblur="javascript:this.value=this.value.toUpperCase();"
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
                        readonly="true"
						cssStyle="text-transform:uppercase"
                                onblur="javascript:this.value=this.value.toUpperCase();"
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
                        readonly="true"
						cssStyle="text-transform:uppercase"
                                onblur="javascript:this.value=this.value.toUpperCase();"
                        />
                </td>
            </tr>
            <tr>
                <td class="tdLabel" style="font-style:italic"><s:label key="label.E-Ktp.posCode" for="frmMain_model_posCode" /></td>
                <td class="tdLabel" style="font-style:italic"><span style="color:red"></span>:</td>
                <td style="padding-right:5px">
                    <s:textfield
                        name="model.posCode"
                        size="5"
                        maxlength="10"
                        cssClass="ui-widget ui-widget-content"
                        readonly="true"
						cssStyle="text-transform:uppercase"
                                onblur="javascript:this.value=this.value.toUpperCase();"
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
                        readonly="true"
						cssStyle="text-transform:uppercase"
                                onblur="javascript:this.value=this.value.toUpperCase();"
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
                        readonly="true"
						cssStyle="text-transform:uppercase"
                                onblur="javascript:this.value=this.value.toUpperCase();"
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
                        readonly="true"
						cssStyle="text-transform:uppercase"
                                onblur="javascript:this.value=this.value.toUpperCase();"
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
                        readonly="true"
						cssStyle="text-transform:uppercase"
                                onblur="javascript:this.value=this.value.toUpperCase();"
                        />
                </td>
            </tr>
            <tr>
                <td class="tdLabel" style="font-style:italic"><s:label key="label.E-Ktp.namMother" for="frmMain_model_namMother" /></td>
                <td class="tdLabel" style="font-style:italic"><span style="color:red"></span>:</td>
                <td style="padding-right:5px">
                    <s:textfield
                        name="model.namMother"
                        size="40"
                        maxlength="50"
                        cssClass="ui-widget ui-widget-content"
                        key="label.E-Ktp.namMother"
                        readonly="true"
						cssStyle="text-transform:uppercase"
                                onblur="javascript:this.value=this.value.toUpperCase();"
                        />
                </td>
            </tr>
        </table>
        <table class="wwFormTable">
            <tr>
                <td colspan="2"></td>
                <td>
                    <sj:submit
                        id="btnVal"
                        formIds="frmMain"
                        buttonIcon="ui-icon-gear"
                        button="true"
                        key="button.validate"
                        targets="ph-main" 
                        onBeforeTopics="beforeSubmit2"
                        />
                </td>
                <td>
                    <sj:submit
                        id="btnCancel"
                        buttonIcon="ui-icon-gear"
                        button="true"
                        key="button.cancel"
                        disabled="false"
                        targets="ph-main"
                        onBeforeTopics="beforeSubmit3"
                        />
                </td>
                <td>
                    <sj:a
                        id="btnOpenEKTPReader"
                        button="true" 
                    >EKTP Reader</sj:a>
                </td>
            </tr>
        </table>
        <div id="divKtp26301"></div>
    </fieldset>
    <s:hidden name="model.nokk"/>
    <s:hidden name="flagButton"/>       
    <s:hidden name="state" />
    <s:hidden name="current" />
    <s:token name="ktp2ndToken" />
    <s:hidden name="so.menuAccount" />
    <s:hidden name="so.applicationID" />
    <s:hidden name="so.extUser" />
    <s:hidden name="so.flagCIF" />
    <s:hidden name="so.branchCode" />
</s:form>
<script type="text/javascript">
    jQuery(document).ready(function(){
        $("#ph-main").scrollTop(0);
        if ($("#actionError").length == 0 && $("#actionMessage").length == 0) {
            $("#rbBS").buttonset("destroy");
            $("#tempTitle").click();
            $("#tempButtons").click();
        } else {
            
            $("#rbBS").data("rdb").callCurrent();
            if ($("#actionError").length == 0) {
                if ($("#frmMain_model_nik").val().length() == 0)
                    $("#rbBS").data("rdb").clear(false);
            }
        }
        
        $("#divMain26301").dialog("close");
        $("#divMain26301").remove();
         $("#spinner").addClass("ui-helper-hidden");
        //alert($("#actionError").val());
        if($("#actionError").val() == undefined){
            //alert('1');
            var ektpstat = $.trim($("#frmMain_model_ektpStatus").val());
            /*if((ektpstat =="BELUM REKAM") || (ektpstat =="BLM REKAM")){
                $("#frmMain_flgExtSource").val("1");
            }*/
            
            if((ektpstat =="TUNGGAL") || (ektpstat =="BELUM REKAM") || (ektpstat =="BLM REKAM")){
                $("#btnVal").attr('enabled', 'enabled');
            } else if(ektpstat == "DUPLIKAT" || ektpstat == "GANDA") {
                messageBoxClass(1,'divKtp26301', 'STATUS KTP '+ektpstat+' Lakukan Proses CDD/EDD' ,function(){
                    $("#frmGo_idMenu").val("26301");
                    $("#frmGo_goAction").val("exec");
                    $("#btnGo").click();
                });
                $("#btnVal").attr('disabled', 'disabled');    
            } else {
                messageBoxClass(1,'divKtp26301', 'STATUS KTP TIDAK DIKENAL :'+ektpstat ,function(){
                    $("#frmGo_idMenu").val("26301");
                    $("#frmGo_goAction").val("exec");
                    $("#btnGo").click();
                });
                $("#btnVal").attr('disabled', 'disabled');    
            }
        } else {
            //alert('2');
            var errMessage = $("#actionError").text();    
            messageBoxClass(1,'divKtp26301', errMessage+'<br> Please Contact Help Desk Operations' ,function(){
                $("#frmGo_idMenu").val("26301");
                $("#frmGo_goAction").val("exec");
                $("#btnGo").click();
            });
            $("#btnVal").attr('disabled', 'disabled');
        }
        
        $("#btnCancel").unsubscribe("beforeSubmit3")
        .subscribe("beforeSubmit3", function(event, data) {
            event.originalEvent.options.submit = false;
            $("#frmGo_idMenu").val("26301");
            $("#frmGo_goAction").val("exec");
            $("#btnGo").click();
        });
        
        $("#btnVal")
        .unsubscribe("beforeSubmit2")
        .subscribe("beforeSubmit2", function(event) {
            $("#frmMain").unbind("submit");
            event.originalEvent.options.submit = true;
            var validate = true;
            if (validate == true) {
                var flags = "2";
                var messaging = "Please Wait Your Request Being Processed..";
                dlgParams = {};
                dlgParams.event = event;
                $("#frmMain_flagButton").val("1");
                $("#frmMain_state").val(flags.toString());
                $("#btnVal").unsubscribe("beforeSubmit2");    
                $("#spinner").removeClass("ui-helper-hidden");    
                waitingMessage(3,messaging,"divKtp26301");
            }
        });

        $("#btnOpenEKTPReader").click(function() {
            var $btn = $(this);
            
            $("#ph-dlg")
                .dialog("option", "position", [($("body").width() * (1/3)), ($("body").height() * (1/10))])
                .dialog("option", "width", "auto")
                .dialog("option", "height", "auto")
                .dialog("option", "modal", false)
                .dialog("option", "resizeable", true)
                .html("Please wait...")
                .unbind("dialogopen")
                .bind("dialogopen", function() {
                    $btn.button("disable");
                    $("#aDlgEKTPReader").click();
                })
                .bind("dialogclose", function() {
                    $btn.removeClass("ui-state-hover");
                    $btn.button("enable");
                })
                .dialog("open");
        });

        var inputNik = null;
        inputNik = <s:property value="%{inputNik}" escapeHtml="false" />;
        
        
        //alert("KTP load :" + inputNik);
        if(inputNik.toString().length == 16){
            model = <s:property value="%{contentData}" escapeHtml="false" />;
            //$("#frmMain_inputNik").val(inputNik);
            //clearResult();
            $("#errMsg").html("");
            $("#spinner").removeClass("ui-helper-hidden");

            $("#spinner").addClass("ui-helper-hidden");
            //$("#frmMain_model_nik").val(inputNik);
        
            if (model == null) {
                $("#btnVal").attr('disabled', 'true');
                $("#errMsg").html("<ul id=\"actionError\" class=\"errorMessage\"><li><span>Null data</span></li></ul>");
            } else if (model.responseMessage == undefined) {
                //$("#frmMain_model_nik").val(inputNik);
                $("#frmMain_model_name").val(model.name);
                $("#frmMain_model_ektpStatus").val(model.ektpStatus);
                //  validateForm_frmButton();
                $("#frmMain_model_birthPlace").val(model.birthPlace); //rep to CASA
                $("#frmMain_model_dusun").val(model.dusun);
                $("#frmMain_model_religion").val(model.religion); // rep to CASA
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
            } else {
                // $("#errMsg").html("<ul id=\"actionError\" class=\"errorMessage\"><li><span>" + model.responseMessage + "</span></li></ul>");
                $("#frmMain_inputNik").focus();
            

            }
            $("#formKtp_inputNik").val($("#frmMain_inputNik").val());
        }
    });
   
</script>
<% }%>
