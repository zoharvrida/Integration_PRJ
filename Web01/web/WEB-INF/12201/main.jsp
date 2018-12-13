<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<% if (!request.getMethod().equals("GET")) {%>
<s:url var="ajaxUpdateTitle" action="12201_title" />
<s:url var="ajaxUpdateButtons" action="12201_buttons" />
<sj:a id="tempTitle" href="%{ajaxUpdateTitle}" targets="ph-title" cssClass="ui-helper-hidden"></sj:a>
<sj:a id="tempButtons" href="%{ajaxUpdateButtons}" targets="ph-buttons" cssClass="ui-helper-hidden"></sj:a>

<s:form id="tempFrmDlgReport" action="dlg">
    <s:hidden name="dialog" value="dlgReportRequest"/>
    <s:hidden name="reportName" id="repPot"/>
    <s:hidden name="idTemplate" value="%{#session.idTemplate}" /> 
    <s:token />
</s:form>
<sj:a id="tempDlgReport" formIds="tempFrmDlgReport" targets="ph-dlg" cssClass="ui-helper-hidden"></sj:a>
<s:form id="tempFrmDlgAuth" action="dlg">
    <s:hidden name="dialog" value="dlgAuth" />
    <s:hidden name="idMenu" value="12201" />
    <s:token />
</s:form>
<sj:a id="tempDlgAuth" formIds="tempFrmDlgAuth" targets="ph-dlg" cssClass="ui-helper-hidden"></sj:a>

<s:actionmessage id="actionMessage" />
<s:actionerror name="actionError"/>
<s:form id="frmMain" name="frmMain" action="12201" focusElement="frmMain_namReportSearch" method="post" validate="true">
    <table id="reportTab">
        <s:textfield
            name="namReportSearch"
            size="30"
            maxlength="20"
            cssClass="ui-widget ui-widget-content"
            key="label.namReportSearch"
            disabled="false"
            />
        <sj:a
            id="btnFindReport"
            button="true"
            >...</sj:a>
        <s:textfield
            name="idReport"
            requiredLabel="true"
            size="15"
            maxlength="10"
            cssClass="ui-widget ui-widget-content"
            key="label.idReport"
            disabled="true"
            />
        <s:textfield
            name="reportName"
            requiredLabel="false"
            size="60"
            maxlength="40"
            cssClass="ui-widget ui-widget-content"
            key="label.reportName"
            disabled="true"
            />	
    </table>
    <s:hidden name="idScheduler" id="idScheduler"></s:hidden>
    <s:hidden name="dummy" id="dummy"></s:hidden>
    <s:hidden name="dummyNoHide" id="dummyNoHide"></s:hidden>
    <s:hidden name="remarks" id="remarks"></s:hidden>
    <s:hidden name="params" id="params"></s:hidden>
    <s:hidden name="format" id="format"></s:hidden>
    <s:hidden name="mandatory" id="mandatory"></s:hidden>
    <s:hidden name="maxLength" id="maxLength"></s:hidden>
    <s:hidden name="regexp" id="regexp"></s:hidden>
    <s:hidden name="escape" id="escape"></s:hidden>

    <s:hidden name="validCo" id="validCo"></s:hidden>
    <s:hidden name="wrongCo" id="wrongCo"></s:hidden>

    <s:hidden name="resultS" id="resultS"></s:hidden>
    <s:hidden name="flgAuth" id="flgAuth"></s:hidden>

	<s:hidden name="idMaintainedBy" value="%{#session.idUser}" />
    <s:hidden name="idMaintainedSpv" />
    <s:hidden name="idUser" value="%{#session.idUser}"/>
    <s:hidden name="idTemplate" value="%{#session.idTemplate}"/>
    <s:hidden name="namUser" value="%{#session.namUser}"/>
    <s:hidden name="cdBranch" value="%{#session.cdBranch}"/>
    <s:hidden name="dtBusiness" value="%{#session.dtBusiness}" />
    <s:hidden name="namTemplate" value="%{#session.namTemplate}"/>
    <s:hidden name="temporary" id="temporary"/>
    <sj:submit
        id="btnSave"
        formIds="frmMain"
        buttonIcon="ui-icon-gear"
        button="true"
        key="button.request"
        disabled="true"
        targets="ph-main" 
        onBeforeTopics="beforeSubmit"
        />
    <s:token name="tokenReportRequest" />
</s:form>
<script type="text/javascript">

    function removesRow(rowid){
        var row =  document.getElementById(rowid);
        //alert(rowid);
        row.parentNode.removeChild(row);
    }  
    function JoinString(joinArray){
    
        var joinAll = joinArray.join("|");
        return joinAll;
    }
    function singleValidation(){
        var counter = $("#dummy").val();
        var arrayRegexp = SeparatorParameters($("#regexp").val());
        var arraymandaTory = SeparatorParameters($("#mandatory").val());
        var i=0;
        var w=0;
        var result;
        
        if($("#validCo").val()!=""){
         
            var arrayValidation = Commaseparator($("#validCo").val());
            
            for(var c=0;c<arrayValidation.length;c++){
                var k=arrayValidation[c];
                document.getElementById("labelparam"+k).style.color = "black";
                
                removesRow("error"+k);
            }
            $("#validCo").val("");
        }
        if($("#wrongCo").val()!=""){
         
            var arrayValidation = Commaseparator($("#wrongCo").val());
            
            for(var c=0;c<arrayValidation.length;c++){
                var k=arrayValidation[c];
                document.getElementById("labelparam"+k).style.color = "black";
                
                removesRow("error"+k);
            }
            $("#wrongCo").val("");
        }
        var validArray = new Array();
        var globalArray = new Array();
        var wrongArray = new Array();
   
        for(var k=0;k<counter;k++){
           
            if($("#parameter" + k).val() == ""){
                if(arraymandaTory[k]== "Y"){
                    validArray[i]=k;
                    i++; 
                } else {
                    globalArray[k] = $("#parameter" + k).val();   
                }
            } else {
                
                if(arrayRegexp[k] != "default"){
                    var regExps = new RegExp(arrayRegexp[k]);   
                } else {
                    var regExps = new RegExp();
                }
               
                if($("#parameter" + k).val().match(regExps)){
                    globalArray[k] = $("#parameter" + k).val();
                } else {
                    wrongArray[w]=k;
                    w++;
                }
            }
           
        }

        var validLength = validArray.length;
        var wronglength = wrongArray.length;
        
        $("#validCo").val(validArray);
        $("#wrongCo").val(wrongArray);
        //alert(validLength + " " + wronglength);
        for(var v=0;v<validLength;v++){
            var k=validArray[v];
            //deleteRow("error"+k);
            document.getElementById("labelparam"+k).style.color = "red";
            $('#reportTab tbody tr#'+k).before('<tr id="error'+k+'"><td align="center" colspan="2"><span class="errorMessage" classname="errorMessage">'+$("#labelparam"+k).val()+" cannot be Empty"+'</span></td></tr>');
        }
        for(var v=0;v<wronglength;v++){
            var k=wrongArray[v];
            document.getElementById("labelparam"+k).style.color = "red";
            $('#reportTab tbody tr#'+k).before('<tr id="error'+k+'"><td align="center" colspan="2"><span class="errorMessage" classname="errorMessage">'+$("#labelparam"+k).val()+" Format Missmatch"+'</span></td></tr>');
        }
        if(validLength == 0 && wronglength == 0 && $("#dummy").val()!="" && $("#resultS").val()== ""){
            $("#resultS").val(JoinString(globalArray));
            return true;
        } else if(validLength == 0 && wronglength == 0 && $("#dummy").val()!=""){
            var join = $("#resultS").val()+JoinString(globalArray);
            $("#resultS").val(join);
            return true;
        } else {
            return false;
        }

    }
    function Commaseparator(params)
    {
        var arrayParams = new Array();
        var arrayRegexp = new Array();

        var Lparam = params[params.length-1];
        if(Lparam == ","){
            var lengParam = params.length-1;
            params = params.substr(0,lengParam);

        }
        arrayParams = params.split(",");
        var param = arrayParams.length;

        return arrayParams;
   
    }
    function SeparatorParameters(params)
    {

        var arrayParams = new Array();
        var arrayRegexp = new Array();
        
        var Lparam = params[params.length-1];
        if(Lparam == ";"){
            var lengParam = params.length-1;
            params = params.substr(0,lengParam);
        }
        arrayParams = params.split(";");
        var param = arrayParams.length;

        for(var p=0;p<param;p++){

            if(arrayParams[p] == "default"){
                arrayRegexp[p]="^[a-zA-Z0-9 _@.;-]*$";
                //alert("default Change :" + arrayRegexp[p]);
            } else {
                arrayRegexp[p]=arrayParams[p];
            }
        }
        return arrayRegexp;
        //return param;
    }
    $(function() {
        if ($("#actionError").length==0 && $("#actionMessage").length==0) {
            $("#rbBS").buttonset("destroy");
            $("#tempTitle").click();
            $("#tempButtons").click();
            //} else if(!$("#actionMessage").length==0){
            //    $("#rbBS").data("rdb").clear(true);
        } else {
            /*if ($("#actionError").length==0) {
                $("#rbBS").data("rdb").clear(false);
            } else {
                $("#rbBS").data("rdb").callCurrent();  
            }*/
            $("#rbBS").data("rdb").callCurrent();
            if ($("#actionError").length==0) {
                $("#rbBS").data("rdb").clear(false);
            }
        }
		$('input [type=text]#dtExpiry').datepicker("destroy");	
        $('img [type=img].ui-datepicker-trigger').hide();
		
		$("#frmMain_namReportSearch")
        .parent()
        .append(
        $("#btnFindReport").detach()
	)

        $("#frmMain_namReportSearch").die('keydown');
        $("#frmMain_namReportSearch").live('keydown', function(e) {
            if(e.keyCode == 9) {
                $("#btnFindReport").click();
            }
        });
        
        $("#btnFindReport").click(function() {
            if(!($("#btnFindReport").button('option').disabled != undefined && 
                $("#btnFindReport").button('option','disabled'))) {
				if ($("#actionMessage").length!=0) {
					$("#rbBS").data("rdb").clear(true);
					$("#resultS").val("");
					$("#dummy").val("");
				}
                $('#repPot').attr('value',$("#frmMain_namReportSearch").val());
                var mainTab = document.getElementById("frmMain");
                var table = mainTab.getElementsByTagName('table')[1];
                var tRealBody = table.getElementsByTagName('tbody')[0];
                //alert($("#dummy").val());
                
                dlgParams = {};
                dlgParams.idReport = "frmMain_idReport";
                dlgParams.reportName = "frmMain_reportName";
                dlgParams.idScheduler = "idScheduler";
                dlgParams.remarks = "remarks";
                dlgParams.flgAuth = "flgAuth";
				dlgParams.onClose = function() {
                    if($("#dummy").val() != ""){
						var conversion = $("#dummy").val();
						//$("#frmMain_idReport").attr("value", null);
						//$("#frmMain_reportName").attr("value", null);
						for(var i=0;i<conversion;i++){
							var delS = document.getElementById(i);
							tRealBody.removeChild(delS);
						}
						if($("#validCo").val()!=""){
							var arrayValidation = Commaseparator($("#validCo").val());
            
							for(var c=0;c<arrayValidation.length;c++){
								var k=arrayValidation[c];  
								removesRow("error"+k);
							}
							$("#validCo").val("");
						}
						if($("#wrongCo").val()!=""){
							var arrayValidation = Commaseparator($("#wrongCo").val());
            
							for(var c=0;c<arrayValidation.length;c++){
								var k=arrayValidation[c];  
								removesRow("error"+k);
							}
							$("#wrongCo").val("");
						}
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
                    $("#tempFrmDlgReport_reportName").val($("#frmMain_namReportSearch").val());
                    $("#tempDlgReport").click();
                })
                .dialog("open");                
            }
        });
        $("#btnSave").unsubscribe("beforeSubmit");
        $("#btnSave").subscribe("beforeSubmit", function(event) {
            var result = "";
			var flagAuth = "";
			
            flagAuth = $("#flgAuth").val();
			//alert(flgAuth);
			
            $("#frmMain").unbind("submit");
            event.originalEvent.options.submit = false;   
            result = singleValidation();
            if(result == true){
                if (validateForm_frmMain()) {
                    dlgParams = {};
                    dlgParams.idMaintainedBy = "frmMain_idMaintainedBy";
                    dlgParams.idMaintainedSpv = "frmMain_idMaintainedSpv";
                    dlgParams.event = event;
                    dlgParams.onSubmit = function() {
                        dlgParams.event.originalEvent.options.submit = true;
                        $("#btnSave").unsubscribe("beforeSubmit");
                        //alert("CLICK");
                        $("#btnSave").click();
						$("#btnSave").button("disable");
                    };
					if(flagAuth == "Y"){
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
					} else {
						dlgParams.event.originalEvent.options.submit = false;
						$("#btnSave").unsubscribe("beforeSubmit");
						$("#btnSave").click();
						$("#btnSave").button("disable");
					}
                    
                }
            }
        });
    });
</script>
<% }%>